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

package nsk.jvmti.IterateOverObjectsReachableFromObject;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class iterobjreachobj001 extends DebugeeClass {

    /** Load native library if required.*/
    static {
        loadLibrary("iterobjreachobj001");
    }

    /** Run test from command line. */
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    /** Run test from JCK-compatible environment. */
    public static int run(String argv[], PrintStream out) {
        return new iterobjreachobj001().runIt(argv, out);
    }

    /* =================================================================== */

    /* scaffold objects */
    ArgumentHandler argHandler = null;
    Log log = null;
    long timeout = 0;
    int status = Consts.TEST_PASSED;

    /* constants */
    public static final int DEFAULT_CHAIN_LENGTH = 4;

    /** Tested object. */
    public static iterobjreachobj001RootTestedClass object = null;

    /** Run debugee code. */
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000; // milliseconds

        int chainLength = argHandler.findOptionIntValue("objects", DEFAULT_CHAIN_LENGTH);

        log.display("Creating chain of tested objects: " + chainLength + " length");
        object = new iterobjreachobj001RootTestedClass(chainLength);

        log.display("Sync: objects created");
        status = checkStatus(status);

        log.display("Cleaning links to unreachable objects");
        iterobjreachobj001TestedClass savedChain = object.cleanUnreachable();

        log.display("Sync: objects are unreachable");
        status = checkStatus(status);

        return status;
    }
}

/* =================================================================== */

/** Class for root tested object. */
class iterobjreachobj001RootTestedClass {
    int length;

    iterobjreachobj001TestedClass reachableChain = null;
    iterobjreachobj001TestedClass unreachableChain = null;

    public iterobjreachobj001RootTestedClass(int length) {
        this.length = length;
        reachableChain = new iterobjreachobj001TestedClass(length);
        unreachableChain = new iterobjreachobj001TestedClass(length);
    }

    public iterobjreachobj001TestedClass cleanUnreachable() {
        iterobjreachobj001TestedClass chain = unreachableChain;
        unreachableChain = null;
        return chain;
    }
}

/** Class for tested chain object. */
class iterobjreachobj001TestedClass {
    int level;

    iterobjreachobj001TestedClass tail = null;

    public iterobjreachobj001TestedClass(int length) {
        this.level = length;
        if (length > 1) {
            tail = new iterobjreachobj001TestedClass(length - 1);
        }
    }
}
