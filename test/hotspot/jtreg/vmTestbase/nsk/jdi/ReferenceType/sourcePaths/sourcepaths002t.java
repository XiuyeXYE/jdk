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

package nsk.jdi.ReferenceType.sourcePaths;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

//    THIS TEST IS LINE NUMBER SENSITIVE

/**
 * This is debuggee class.
 */
public class sourcepaths002t {
    public static void main(String args[]) {
        System.exit(run(args) + Consts.JCK_STATUS_BASE);
    }

    public static int run(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();

        pipe.println(sourcepaths002.COMMAND_READY);
        String cmd = pipe.readln();
        if (cmd.equals(sourcepaths002.COMMAND_QUIT)) {
            System.err.println("Debuggee: exiting due to the command: "
                + cmd);
            return Consts.TEST_PASSED;
        }

        int stopMeHere = 0;// sourcepaths002.DEBUGGEE_STOPATLINE

        cmd = pipe.readln();
        if (!cmd.equals(sourcepaths002.COMMAND_QUIT)) {
            System.err.println("TEST BUG: unknown debugger command: "
                + cmd);
            return Consts.TEST_FAILED;
        }
        return Consts.TEST_PASSED;
    }

    // classes representing primitive types used to check
    // AbsentInformationException throwing in the debugger
    static Class boolCls = Boolean.TYPE;
    static Class byteCls = Byte.TYPE;
    static Class charCls = Character.TYPE;
    static Class doubleCls = Double.TYPE;
    static Class floatCls = Float.TYPE;
    static Class intCls = Integer.TYPE;
    static Class longCls = Long.TYPE;
    static Class shortCls = Short.TYPE;

    // arrays used to check AbsentInformationException throwing
    // in the debugger
    static Boolean boolClsArr[] = {new Boolean(false)};
    static Byte byteClsArr[] = {new Byte((byte) 127)};
    static Character charClsArr[] = {new Character('a')};
    static Double doubleClsArr[] = {new Double(6.2D)};
    static Float floatClsArr[] = {new Float(5.1F)};
    static Integer intClsArr[] = {new Integer(2147483647)};
    static Long longClsArr[] = {new Long(9223372036854775807L)};
    static Short shortClsArr[] = {new Short((short) -32768)};

    static boolean boolArr[] = {true};
    static byte byteArr[] = {Byte.MAX_VALUE};
    static char charArr[] = {'z'};
    static double doubleArr[] = {Double.MAX_VALUE};
    static float floatArr[] = {Float.MAX_VALUE};
    static int intArr[] = {Integer.MAX_VALUE};
    static long longArr[] = {Long.MAX_VALUE};
    static short shortArr[] = {Short.MAX_VALUE};
}
