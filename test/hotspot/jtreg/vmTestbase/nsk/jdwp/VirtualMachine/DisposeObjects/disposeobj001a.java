/*
 * Copyright (c) 2001, 2018, Oracle and/or its affiliates. All rights reserved.
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

package nsk.jdwp.VirtualMachine.DisposeObjects;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

/**
 * This class represents debuggee part in the test.
 */
public class disposeobj001a {

    public static void main(String args[]) {
        disposeobj001a _disposeobj001a = new disposeobj001a();
        System.exit(disposeobj001.JCK_STATUS_BASE + _disposeobj001a.runIt(args, System.err));
    }

    public int runIt(String args[], PrintStream out) {
        //make log for debugee messages
        ArgumentHandler argumentHandler = new ArgumentHandler(args);
        Log log = new Log(out, argumentHandler);

        // make communication pipe to debugger
        log.display("Creating pipe");
        IOPipe pipe = argumentHandler.createDebugeeIOPipe(log);

        // ensure tested class loaded
        log.display("Creating object of tested class");
        TestedClass foo = new TestedClass();

        // send debugger signal READY
        log.display("Sending signal to debugger: " + disposeobj001.READY);
        pipe.println(disposeobj001.READY);

        // wait for signal QUIT from debugeer
        log.display("Waiting for signal from debugger: " + disposeobj001.QUIT);
        String signal = pipe.readln();
        log.display("Received signal from debugger: " + signal);

        // check received signal
        if (! signal.equals(disposeobj001.QUIT)) {
            log.complain("Unexpected communication signal from debugee: " + signal
                        + " (expected: " + disposeobj001.QUIT + ")");
            log.display("Debugee FAILED");
            return disposeobj001.FAILED;
        }

        // exit debugee
        log.display("Debugee PASSED");
        return disposeobj001.PASSED;
    }

    // tested class
    public static class TestedClass {

        // tested objects
        static Object objectValue = new Object();
        static String stringValue = new String();
        static TestedClass classValue = new TestedClass();

        int foo = 0;

        TestedClass() {
            foo = 100;
        }
    }

}
