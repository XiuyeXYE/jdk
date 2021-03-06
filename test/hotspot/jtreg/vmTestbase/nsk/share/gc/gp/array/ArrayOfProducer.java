/*
 * Copyright (c) 2007, 2018, Oracle and/or its affiliates. All rights reserved.
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

package nsk.share.gc.gp.array;

import nsk.share.gc.gp.GarbageProducer;
import nsk.share.gc.gp.DerivedProducer;
import nsk.share.gc.gp.GarbageUtils;
import nsk.share.gc.gp.MemoryStrategy;
import nsk.share.gc.Memory;
import nsk.share.TestFailure;

/**
 * GarbageProducer implementation that produces arrays of objects
 * determined by parent garbage producer. A memory strategy is
 * used to determine how memory is distributed between array size
 * and size of each object in array.
 */
public class ArrayOfProducer<T> extends DerivedProducer<Object[], T> {
        private int n;

        public ArrayOfProducer(GarbageProducer<T> parent, int n) {
                super(parent);
                this.n = n;
        }

        public ArrayOfProducer(GarbageProducer<T> parent) {
                this(parent, 2);
        }

        public Object[] create(long memory) {
                Object[] array = new Object[n];
                long objectSize = (memory - Memory.getArrayExtraSize() - Memory.getReferenceSize() * n) / n;
                for (int i = 0; i < n; ++i)
                        array[i] = createParent(objectSize);
                return array;
        }

        public void validate(Object[] obj) {
                for (int i = 0; i < obj.length; ++i)
                        validateParent((T) obj[i]);
        }
}
