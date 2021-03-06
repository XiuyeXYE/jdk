/*
 * Copyright (c) 2004, 2018, Oracle and/or its affiliates. All rights reserved.
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

package nsk.jvmti.scenarios.multienv.MA10;

import java.io.PrintStream;

import nsk.share.*;
import nsk.share.jvmti.*;

public class ma10t008 extends DebugeeClass {

    // run test from command line
    public static void main(String argv[]) {
        argv = nsk.share.jvmti.JVMTITest.commonInit(argv);

        // JCK-compatible exit
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    // run test from JCK-compatible environment
    public static int run(String argv[], PrintStream out) {
        return new ma10t008().runIt(argv, out);
    }

    /* =================================================================== */

    // scaffold objects
    ArgumentHandler argHandler = null;
    int status = Consts.TEST_PASSED;
    Log log = null;
    long timeout = 0;

    // tested thread
    ma10t008Thread testedThread = null;

    // run debuggee
    public int runIt(String argv[], PrintStream out) {
        argHandler = new ArgumentHandler(argv);
        log = new Log(out, argHandler);
        timeout = argHandler.getWaitTime() * 60 * 1000;

        log.display("Debugee started");

        testedThread = new ma10t008Thread("Debuggee Thread", timeout);

        synchronized (testedThread.endingMonitor) {
            // run thread
            try {
                // start thread
                synchronized (testedThread.startingMonitor) {
                    testedThread.start();
                    testedThread.startingMonitor.wait(timeout);
                }
                if (!testedThread.checkReady()) {
                    throw new Failure("Unable to run " + testedThread);
                }

                // testing sync
                log.display("Testing sync: thread ready");
                if ((status = checkStatus(status)) != Consts.TEST_PASSED)
                    return status;
                testedThread.letFinish();

                // pause to provoke contention
                testedThread.sleep(500);
            } catch (InterruptedException e) {
                throw new Failure(e);
            }
            log.display("Testing sync: thread finish");
        }

        try {
            testedThread.join(timeout);
        } catch (InterruptedException e) {
            throw new Failure(e);
        }

        log.display("Debugee finished");

        return checkStatus(status);
    }
}

/* =================================================================== */

class ma10t008Thread extends Thread {
    public Object startingMonitor = new Object();
    public Object endingMonitor = new Object();
    private Object waitingMonitor = new Object();
    private long timeout = 0;

    public ma10t008Thread(String name, long timeout) {
        super(name);
        this.timeout = timeout;
    }

    public void run() {
        synchronized (waitingMonitor) {

            // notify about starting
            synchronized (startingMonitor) {
                startingMonitor.notify();
            }

            // wait on monitor
            try {
                waitingMonitor.wait(timeout);
            } catch (InterruptedException ignore) {}
        }

        // wait until main thread release monitor
        synchronized (endingMonitor) {}
    }

    public boolean checkReady() {
        // wait until waitingMonitor released on wait()
        synchronized (waitingMonitor) {}
        return true;
    }

    public void letFinish() {
        synchronized (waitingMonitor) {
            waitingMonitor.notify();
        }
    }
}
