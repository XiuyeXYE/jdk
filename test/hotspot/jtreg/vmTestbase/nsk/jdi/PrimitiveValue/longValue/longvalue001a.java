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

package nsk.jdi.PrimitiveValue.longValue;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;


/**
 * This class is used as debuggee application for the longvalue001a JDI test.
 */

public class longvalue001a {

    //----------------------------------------------------- templete section

    static final int PASSED = 0;
    static final int FAILED = 2;
    static final int PASS_BASE = 95;

    //--------------------------------------------------   log procedures

    static boolean verbMode = false;  // debugger may switch to true

    private static void log1(String message) {
        if (verbMode)
            System.err.println("**> longvalue001a: " + message);
    }

    private static void logErr(String message) {
        if (verbMode)
            System.err.println("!!**> longvalue001a: " + message);
    }

    //====================================================== test program

    static boolean bl1 = true;
    static boolean bl0 = false;
    static byte    bt1 = Byte.MAX_VALUE;
    static byte    bt0 = 0;
    static char    ch1 = Character.MAX_VALUE;
    static char    ch0 = 0;
    static double  db1 = Double.MAX_VALUE;
    static double  db0 = 0.0d;
    static float   fl1 = Float.MAX_VALUE;
    static float   fl0 = 0.0f;
    static int     in1 = Integer.MAX_VALUE;
    static int     in0 = 0;
    static long    ln1 = Long.MAX_VALUE;
    static long    ln0 = 0;
    static short   sh1 = Short.MAX_VALUE;
    static short   sh0 = 0;

    //----------------------------------------------------   main method

    public static void main (String argv[]) {

        for (int i=0; i<argv.length; i++) {
            if ( argv[i].equals("-vbs") || argv[i].equals("-verbose") ) {
                verbMode = true;
                break;
            }
        }
        log1("debuggee started!");

        // informing a debugger of readyness
        ArgumentHandler argHandler = new ArgumentHandler(argv);
        IOPipe pipe = argHandler.createDebugeeIOPipe();
        pipe.println("ready");


        int exitCode = PASSED;
        for (int i = 0; ; i++) {

            String instruction;

            log1("waiting for an instruction from the debugger ...");
            instruction = pipe.readln();
            if (instruction.equals("quit")) {
                log1("'quit' recieved");
                break ;

            } else if (instruction.equals("newcheck")) {
                switch (i) {

    //------------------------------------------------------  section tested

                case 0:
                                pipe.println("checkready");
                                break ;

    //-------------------------------------------------    standard end section

                default:
                                pipe.println("checkend");
                                break ;
                }

            } else {
                logErr("ERRROR: unexpected instruction: " + instruction);
                exitCode = FAILED;
                break ;
            }
        }

        System.exit(exitCode + PASS_BASE);
    }
}
