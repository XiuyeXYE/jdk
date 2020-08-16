/*
 * Copyright (c) 2016, Oracle and/or its affiliates. All rights reserved.
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
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.logging.Handler;
import java.util.logging.LogRecord;
import java.util.logging.Logger;

/**
 * @test
 * @bug 8152515
 * @summary Checks that LinkageError are ignored when closing handlers
 *          during Shutdown.
 * @build LinkageErrorTest
 * @run main/othervm LinkageErrorTest
 */

public class LinkageErrorTest {

    public static class TestHandler extends Handler {

        private volatile boolean closed;
        public TestHandler() {
            INSTANCES.add(this);
        }

        @Override
        public void publish(LogRecord record) {
        }

        @Override
        public void flush() {
        }

        @Override
        public void close() throws SecurityException {
            closed = true;
            try {
                System.out.println(INSTANCES);
            } catch (Throwable t) {
                // ignore
            }
            throw new LinkageError();
        }

        @Override
        public String toString() {
            return super.toString() + "{closed=" + closed + '}';
        }

        private static final CopyOnWriteArrayList<Handler> INSTANCES
                = new CopyOnWriteArrayList<>();
    }

    private static final Logger LOGGER = Logger.getLogger("test");
    private static final Logger GLOBAL = Logger.getGlobal();

    public static void main(String[] args) {
        LOGGER.addHandler(new TestHandler());
        LOGGER.addHandler(new TestHandler());
        GLOBAL.addHandler(new TestHandler());
    }
}
