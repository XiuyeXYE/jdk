/*
 * Copyright (c) 2020, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.
 *
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 *
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 *
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package org.openjdk.bench.jdk.incubator.foreign;

import jdk.incubator.foreign.MemoryAddress;
import jdk.incubator.foreign.MemoryLayout;
import jdk.incubator.foreign.MemorySegment;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.annotations.Warmup;
import sun.misc.Unsafe;

import java.lang.invoke.VarHandle;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.concurrent.TimeUnit;

import static jdk.incubator.foreign.MemoryLayout.PathElement.sequenceElement;
import static jdk.incubator.foreign.MemoryLayouts.JAVA_INT;

@BenchmarkMode(Mode.AverageTime)
@Warmup(iterations = 5, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@Measurement(iterations = 10, time = 500, timeUnit = TimeUnit.MILLISECONDS)
@State(org.openjdk.jmh.annotations.Scope.Thread)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Fork(3)
public class LoopOverNonConstantHeap {

    static final Unsafe unsafe = Utils.unsafe;

    static final int ELEM_SIZE = 1_000_000;
    static final int CARRIER_SIZE = (int)JAVA_INT.byteSize();
    static final int ALLOC_SIZE = ELEM_SIZE * CARRIER_SIZE;
    static final int UNSAFE_BYTE_BASE = unsafe.arrayBaseOffset(byte[].class);

    static final VarHandle VH_int = MemoryLayout.ofSequence(JAVA_INT).varHandle(int.class, sequenceElement());
    MemorySegment segment;
    byte[] base;

    ByteBuffer byteBuffer;

    @Setup
    public void setup() {
        base = new byte[ALLOC_SIZE];
        for (int i = 0; i < ELEM_SIZE; i++) {
            unsafe.putInt(base, UNSAFE_BYTE_BASE + (i * CARRIER_SIZE) , i);
        }
        segment = MemorySegment.ofArray(base);
        byteBuffer = ByteBuffer.wrap(base).order(ByteOrder.nativeOrder());
    }

    @TearDown
    public void tearDown() {
        segment.close();
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int unsafe_get() {
        return unsafe.getInt(base, UNSAFE_BYTE_BASE);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int segment_get() {
        return (int) VH_int.get(segment.baseAddress(), 0L);
    }

    @Benchmark
    @OutputTimeUnit(TimeUnit.NANOSECONDS)
    public int BB_get() {
        return byteBuffer.getInt(0);
    }

    @Benchmark
    public int unsafe_loop() {
        int res = 0;
        for (int i = 0; i < ELEM_SIZE; i ++) {
            res += unsafe.getInt(base, UNSAFE_BYTE_BASE + (i * CARRIER_SIZE));
        }
        return res;
    }

    @Benchmark
    public int segment_loop() {
        int sum = 0;
        MemoryAddress base = segment.baseAddress();
        for (int i = 0; i < ELEM_SIZE; i++) {
            sum += (int) VH_int.get(base, (long) i);
        }
        return sum;
    }

    @Benchmark
    public int segment_loop_slice() {
        int sum = 0;
        MemoryAddress base = segment.asSlice(0, segment.byteSize()).baseAddress();
        for (int i = 0; i < ELEM_SIZE; i++) {
            sum += (int) VH_int.get(base, (long) i);
        }
        return sum;
    }

    @Benchmark
    public int segment_loop_readonly() {
        int sum = 0;
        MemoryAddress base = segment.withAccessModes(MemorySegment.READ).baseAddress();
        for (int i = 0; i < ELEM_SIZE; i++) {
            sum += (int) VH_int.get(base, (long) i);
        }
        return sum;
    }

    @Benchmark
    public int BB_loop() {
        int sum = 0;
        ByteBuffer bb = byteBuffer;
        for (int i = 0; i < ELEM_SIZE; i++) {
            sum += bb.getInt(i * CARRIER_SIZE);
        }
        return sum;
    }

}
