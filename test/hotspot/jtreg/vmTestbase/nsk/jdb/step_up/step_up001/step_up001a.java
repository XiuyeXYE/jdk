/*
 * Copyright (c) 2002, 2018, Oracle and/or its affiliates. All rights reserved.
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

package nsk.jdb.step_up.step_up001;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdb.*;

import java.io.*;

/* This is debuggee aplication */
public class step_up001a {
    public static void main(String args[]) {
       step_up001a _step_up001a = new step_up001a();
       System.exit(step_up001.JCK_STATUS_BASE + _step_up001a.runIt(args, System.out));
    }

    static void lastBreak () {}

    static final String MYTHREAD  = "MyThread";
    static final int numThreads   = 2;   // number of threads.

    static JdbArgumentHandler argumentHandler;
    static Log log;

    static Object waitnotify = new Object();

    public int runIt(String args[], PrintStream out) {

        argumentHandler = new JdbArgumentHandler(args);
        log = new Log(out, argumentHandler);

        Thread holder [] = new Thread[numThreads];

        for (int i = 0; i < numThreads ; i++) {
            holder[i] = new MyThread();
            holder[i].start();
            try {
                holder[i].join();
            } catch (InterruptedException e) {
                log.complain("Main thread was interrupted while waiting for finish of " + MYTHREAD + "-" + i);
                return step_up001.FAILED;
            }
        }

        log.display("Debuggee PASSED");
        return step_up001.PASSED;
    }
}


class MyThread extends Thread {
    public void run() {
        int runLocal = func1(100);
    }

    public int func1(int i) {
        return func2(i);
    }

    public int func2(int i) {
        step_up001a.lastBreak();

        return func3(i);
    }

    public int func3(int i) {
        return i*i;
    }
}
