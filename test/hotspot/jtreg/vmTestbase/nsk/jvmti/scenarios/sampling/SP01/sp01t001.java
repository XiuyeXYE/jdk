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

package nsk.jvmti.scenarios.sampling.SP01;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class sp01t001 extends DebugeeClass {

    // run test from command line
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    // run test from JCK-compatible environment
    public static int run(String argv[], PrintStream out) {
        return new sp01t001().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    Log log = null;
    int status = Consts.TEST_PASSED;

    // tested threads list
    static sp01t001Thread threads[] = null;
    static int indexStartedThread = 0;

    // run debuggee class
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);

        // create threads list
        threads = new sp01t001Thread[] {
            // not started thread
            new sp01t001ThreadNotStarted(),
            // started threads
            new sp01t001ThreadFinished()
        };
        indexStartedThread = 1;

        // run threads
        try {
            // start threads
            for (int i = indexStartedThread; i < threads.length; i++) {
                synchronized (threads[i].startingMonitor) {
                    threads[i].start();
                    threads[i].startingMonitor.wait();
                }
            }

            // wait for started threads to finish
            for (int i = indexStartedThread; i < threads.length; i++) {
                threads[i].join();
            }

            // testing sync
            log.display("Testing sync: threads ready");
            status = checkStatus(status);

        } catch (InterruptedException e) {
            throw new Failure(e);
        }
        return status;
    }

}

/* =================================================================== */

// basic class for tested threads
abstract class sp01t001Thread extends Thread {
    public Object startingMonitor = new Object();
}

/* =================================================================== */


class sp01t001ThreadNotStarted extends sp01t001Thread {
    public void run() {
        // do nothing
    }
}

class sp01t001ThreadFinished extends sp01t001Thread {
    public void run() {
        // notify about starting
        synchronized (startingMonitor) {
            startingMonitor.notifyAll();
        }
        // just finish
    }
}
