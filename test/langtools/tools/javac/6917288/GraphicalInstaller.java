/*
 * Copyright (c) 2010, Oracle and/or its affiliates. All rights reserved.
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

/**
 * Complex example for the "access constructor tags" issue.
 * This test causes Lower to evaluate all classes defined in installNext
 * before they are lowered, thus preventing the use of Lower.classdefs
 * as a way of determining whether a class has been translated or not.
 */
class GraphicalInstaller  {
    private BackgroundInstaller backgroundInstaller;

    private void installNext() {
        final Integer x = 0;
        class X {
          Object o = new Object() { int y = x; };
        };
        new X();
        if (false) {
            new Runnable(){
                public void run() {
                }
            };
        }
    }

    private void installSuiteCommon() {
        backgroundInstaller = new BackgroundInstaller();
    }

    private static class BackgroundInstaller {
        private BackgroundInstaller() {
        }
    }
}
