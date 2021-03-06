/*
 * Copyright (c) 2003, 2018, Oracle and/or its affiliates. All rights reserved.
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
/*
 * %W% %E
 * SUN PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */

package nsk.jvmti.unit;

import java.io.PrintStream;

public class extmech {

    final static int JCK_STATUS_BASE = 95;

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {

        System.out.println("Extension dump...");
        dumpExtensions();

        System.out.println("----------------");

        System.out.println("Testing Sun specific extension function: " +
            "com.sun.hotspot.functions.IsClassUnloadingEnabled");

        boolean enabled = isClassUnloadingEnabled();
        if (enabled) {
            System.out.println("class unloading is enabled");
        } else {
            System.out.println("class unloading is not enabled");
        }

        System.out.println("----------------");

        enableClassUnloadEvent(true);
        enableClassUnloadEvent(false);

        System.out.println("----------------");

        System.out.println("All test(s) completed.");

        return getResult();
    }


    // ---

    static native void dumpExtensions();
    static native boolean isClassUnloadingEnabled();
    static native void enableClassUnloadEvent(boolean enable);
    static native int getResult();

    static {
        System.loadLibrary("extmech");
    }

}
