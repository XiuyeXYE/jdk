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

package nsk.jvmti.SetFieldAccessWatch;

import java.io.PrintStream;

public class setfldw006 {

    final static int JCK_STATUS_BASE = 95;
    final static int NUM_TRIES = 1000;

    static {
        try {
            System.loadLibrary("setfldw006");
        } catch (UnsatisfiedLinkError ule) {
            System.err.println("Could not load setfldw006 library");
            System.err.println("java.library.path:"
                + System.getProperty("java.library.path"));
            throw ule;
        }
    }

    native static void getReady(int n);
    native static int check(boolean flag);

    static boolean staticBoolean = true;
    static byte staticByte = 1;
    static short staticShort = 2;
    static int staticInt = 3;
    static long staticLong = 4L;
    static float staticFloat = 0.5F;
    static double staticDouble = 0.6;
    static char staticChar = '\u0007';
    static Object staticObject = new Object();
    static int staticArrInt[] = {8};

    boolean instanceBoolean = false;
    byte instanceByte = 10;
    short instanceShort = 20;
    int instanceInt = 30;
    long instanceLong = 40L;
    float instanceFloat = 0.05F;
    double instanceDouble = 0.06;
    char instanceChar = '\u0070';
    Object instanceObject = new Object();
    int instanceArrInt[] = {80};

    public static void main(String args[]) {
        args = nsk.share.jvmti.JVMTITest.commonInit(args);

        // produce JCK-like exit status.
        System.exit(run(args, System.out) + JCK_STATUS_BASE);
    }

    public static int run(String args[], PrintStream out) {
        setfldw006 t = new setfldw006();
        getReady(NUM_TRIES);
        t.tryAccess();
        check(true);
        t.tryModification();
        return check(false);
    }

    public void tryAccess() {
        int count = 0;
        for (int i = 0; i < NUM_TRIES; i++) {
            if (staticBoolean == true) count++;
            if (staticByte == 1) count++;
            if (staticShort == 2) count++;
            if (staticInt == 3) count++;
            if (staticLong == 4L) count++;
            if (staticFloat == 0.5F) count++;
            if (staticDouble == 0.6) count++;
            if (staticChar == '\u0007') count++;
            if (staticObject != null) count++;
            if (staticArrInt != null) count++;
            if (this.instanceBoolean == false) count++;
            if (this.instanceByte == 10) count++;
            if (this.instanceShort == 20) count++;
            if (this.instanceInt == 30) count++;
            if (this.instanceLong == 40L) count++;
            if (this.instanceFloat == 0.05F) count++;
            if (this.instanceDouble == 0.06) count++;
            if (this.instanceChar == '\u0070') count++;
            if (this.instanceObject != null) count++;
            if (this.instanceArrInt != null) count++;
        }
    }

    public void tryModification() {
        for (int i = 0; i < NUM_TRIES; i++) {
            staticBoolean = false;
            staticByte = 10;
            staticShort = 20;
            staticInt = 30;
            staticLong = 40L;
            staticFloat = 0.05F;
            staticDouble = 0.06;
            staticChar = '\u0070';
            staticObject = null;
            staticArrInt = null;
            this.instanceBoolean = true;
            this.instanceByte = 1;
            this.instanceShort = 2;
            this.instanceInt = 3;
            this.instanceLong = 4L;
            this.instanceFloat = 0.5F;
            this.instanceDouble = 0.6;
            this.instanceChar = '\u0007';
            this.instanceObject = null;
            this.instanceArrInt = null;
        }
    }
}
