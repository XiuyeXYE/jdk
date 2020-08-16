/*
 * Copyright (c) 2019, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
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

package jdk.jfr.api.consumer.recordingstream;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicBoolean;

import jdk.jfr.Event;
import jdk.jfr.consumer.RecordingStream;

/**
 * @test
 * @summary Tests RecordingStream::onClose(...)
 * @key jfr
 * @requires vm.hasJFR
 * @library /test/lib
 * @run main/othervm jdk.jfr.api.consumer.recordingstream.TestOnClose
 */
public class TestOnClose {

    private static class CloseEvent extends Event {
    }

    public static void main(String... args) throws Exception {
        testOnCloseNull();
        testOnClosedUnstarted();
        testOnClosedStarted();
    }

    private static void testOnCloseNull() {
       try (RecordingStream rs = new RecordingStream()) {
          try {
              rs.onClose(null);
              throw new AssertionError("Expected NullPointerException from onClose(null");
          } catch (NullPointerException npe) {
              // OK; as expected
          }
       }
    }

    private static void testOnClosedStarted() throws InterruptedException {
        AtomicBoolean onClose = new AtomicBoolean(false);
        CountDownLatch event = new CountDownLatch(1);
        try (RecordingStream r = new RecordingStream()) {
            r.onEvent(e -> {
                event.countDown();
            });
            r.onClose(() -> {
                onClose.set(true);
            });
            r.startAsync();
            CloseEvent c = new CloseEvent();
            c.commit();
            event.await();
        }
        if (!onClose.get()) {
            throw new AssertionError("OnClose was not called");
        }
    }

    private static void testOnClosedUnstarted() {
        AtomicBoolean onClose = new AtomicBoolean(false);
        try (RecordingStream r = new RecordingStream()) {
            r.onClose(() -> {
                onClose.set(true);
            });
        }
        if (!onClose.get()) {
            throw new AssertionError("OnClose was not called");
        }
    }
}
