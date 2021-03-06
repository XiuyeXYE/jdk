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

package nsk.jdwp.ObjectReference.SetValues;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

import java.io.*;

/**
 * This class represents debuggee part of the test.
 */
public class setvalues001a {

    public static final String OBJECT_FIELD_NAME = "object";

    static ArgumentHandler argumentHandler = null;
    static Log log = null;

    public static void main(String args[]) {
        setvalues001a _setvalues001a = new setvalues001a();
        System.exit(setvalues001.JCK_STATUS_BASE + _setvalues001a.runIt(args, System.err));
    }

    public int runIt(String args[], PrintStream out) {
        // make log for debugee messages
        ArgumentHandler argumentHandler = new ArgumentHandler(args);
        log = new Log(out, argumentHandler);

        // make communication pipe to debugger
        log.display("Creating pipe");
        IOPipe pipe = argumentHandler.createDebugeeIOPipe(log);

        // ensure tested class loaded
        log.display("Creating object of all required classes");
        OriginalValuesClass original = new OriginalValuesClass();
        TargetValuesClass target = new TargetValuesClass();
        ObjectClass.object = new TestedClass();

        // send debugger signal READY
        log.display("Sending signal to debugger: " + setvalues001.READY);
        pipe.println(setvalues001.READY);

        // wait for signal RUN from debugeer
        log.display("Waiting for signal from debugger: " + setvalues001.RUN);
        String signal = pipe.readln();
        log.display("Received signal from debugger: " + signal);
        // check received signal
        if (signal == null || !signal.equals(setvalues001.RUN)) {
            log.complain("Unexpected communication signal from debugee: " + signal
                        + " (expected: " + setvalues001.RUN + ")");
            log.display("Debugee FAILED");
            return setvalues001.FAILED;
        }

        // check assigned values
        if (checkValues(ObjectClass.object)) {
            log.display("Sending signal to debugger: " + setvalues001.DONE);
            pipe.println(setvalues001.DONE);
        } else {
            log.display("Sending signal to debugger: " + setvalues001.ERROR);
            pipe.println(setvalues001.ERROR);
        }

        // wait for signal QUIT from debugeer
        log.display("Waiting for signal from debugger: " + setvalues001.QUIT);
        signal = pipe.readln();
        log.display("Received signal from debugger: " + signal);
        // check received signal
        if (! signal.equals(setvalues001.QUIT)) {
            log.complain("Unexpected communication signal from debugee: " + signal
                        + " (expected: " + setvalues001.QUIT + ")");
            log.display("Debugee FAILED");
            return setvalues001.FAILED;
        }

        // exit debugee
        log.display("Debugee PASSED");
        return setvalues001.PASSED;
    }

    // check values of static fields for both classes
    static boolean checkValues(TestedClass object) {
        int different = 0;
        log.display("Checking that values have been set correctly:");

        // check value of the field
        if (object.booleanValue != TargetValuesClass.booleanValue) {
            different++;
            log.complain("  booleanValue = " + object.booleanValue + "\n"
                       + "    setting: " + OriginalValuesClass.booleanValue
                                + " -> " + TargetValuesClass.booleanValue);
            if (object.booleanValue == OriginalValuesClass.booleanValue) {
                log.complain("      not changed!");
            } else {
                log.complain("      changed incorrectly!");
            }
        } else {
            log.display("  booleanValue: " + OriginalValuesClass.booleanValue
                                    + " -> " + TargetValuesClass.booleanValue);
        }

        // check value of the field
        if (object.byteValue != TargetValuesClass.byteValue) {
            different++;
            log.complain("  byteValue = " + object.byteValue + "\n"
                       + "    setting: " + OriginalValuesClass.byteValue
                                + " -> " + TargetValuesClass.byteValue);
            if (object.byteValue == OriginalValuesClass.byteValue) {
                log.complain("      not changed!");
            } else {
                log.complain("      changed incorrectly!");
            }
        } else {
            log.display("  byteValue: " + OriginalValuesClass.byteValue
                                    + " -> " + TargetValuesClass.byteValue);
        }

        // check value of the field
        if (object.charValue != TargetValuesClass.charValue) {
            different++;
            log.complain("  charValue = " + object.charValue + "\n"
                       + "    setting: " + OriginalValuesClass.charValue
                                + " -> " + TargetValuesClass.charValue);
            if (object.charValue == OriginalValuesClass.charValue) {
                log.complain("      not changed!");
            } else {
                log.complain("      changed incorrectly!");
            }
        } else {
            log.display("  charValue: " + OriginalValuesClass.charValue
                                    + " -> " + TargetValuesClass.charValue);
        }

        // check value of the field
        if (object.intValue != TargetValuesClass.intValue) {
            different++;
            log.complain("  intValue = " + object.intValue + "\n"
                       + "    setting: " + OriginalValuesClass.intValue
                                + " -> " + TargetValuesClass.intValue);
            if (object.intValue == OriginalValuesClass.intValue) {
                log.complain("      not changed!");
            } else {
                log.complain("      changed incorrectly!");
            }
        } else {
            log.display("  intValue: " + OriginalValuesClass.intValue
                                    + " -> " + TargetValuesClass.intValue);
        }

        // check value of the field
        if (object.shortValue != TargetValuesClass.shortValue) {
            different++;
            log.complain("  shortValue = " + object.shortValue + "\n"
                       + "    setting: " + OriginalValuesClass.shortValue
                                + " -> " + TargetValuesClass.shortValue);
            if (object.shortValue == OriginalValuesClass.shortValue) {
                log.complain("      not changed!");
            } else {
                log.complain("      changed incorrectly!");
            }
        } else {
            log.display("  shortValue: " + OriginalValuesClass.shortValue
                                    + " -> " + TargetValuesClass.shortValue);
        }

        // check value of the field
        if (object.longValue != TargetValuesClass.longValue) {
            different++;
            log.complain("  longValue = " + object.longValue + "\n"
                       + "    setting: " + OriginalValuesClass.longValue
                                + " -> " + TargetValuesClass.longValue);
            if (object.longValue == OriginalValuesClass.longValue) {
                log.complain("      not changed!");
            } else {
                log.complain("      changed incorrectly!");
            }
        } else {
            log.display("  longValue: " + OriginalValuesClass.longValue
                                    + " -> " + TargetValuesClass.longValue);
        }

        // check value of the field
        if (object.floatValue != TargetValuesClass.floatValue) {
            different++;
            log.complain("  floatValue = " + object.floatValue + "\n"
                       + "    setting: " + OriginalValuesClass.floatValue
                                + " -> " + TargetValuesClass.floatValue);
            if (object.floatValue == OriginalValuesClass.floatValue) {
                log.complain("      not changed!");
            } else {
                log.complain("      changed incorrectly!");
            }
        } else {
            log.display("  floatValue: " + OriginalValuesClass.floatValue
                                    + " -> " + TargetValuesClass.floatValue);
        }

        // check value of the field
        if (object.doubleValue != TargetValuesClass.doubleValue) {
            different++;
            log.complain("  doubleValue = " + object.doubleValue + "\n"
                       + "    setting: " + OriginalValuesClass.doubleValue
                                + " -> " + TargetValuesClass.doubleValue);
            if (object.doubleValue == OriginalValuesClass.doubleValue) {
                log.complain("      not changed!");
            } else {
                log.complain("      changed incorrectly!");
            }
        } else {
            log.display("  doubleValue: " + OriginalValuesClass.doubleValue
                                    + " -> " + TargetValuesClass.doubleValue);
        }

        // check value of the field
        if (object.stringValue != TargetValuesClass.stringValue) {
            different++;
            log.complain("  stringValue = " + object.stringValue + "\n"
                       + "    setting: " + OriginalValuesClass.stringValue
                                + " -> " + TargetValuesClass.stringValue);
            if (object.stringValue == OriginalValuesClass.stringValue) {
                log.complain("      not changed!");
            } else {
                log.complain("      changed incorrectly!");
            }
        } else {
            log.display("  stringValue: " + OriginalValuesClass.stringValue
                                    + " -> " + TargetValuesClass.stringValue);
        }

        // check value of the field
        if (object.objectValue != TargetValuesClass.objectValue) {
            different++;
            log.complain("  objectValue = " + object.objectValue + "\n"
                       + "    setting: " + OriginalValuesClass.objectValue
                                + " -> " + TargetValuesClass.objectValue);
            if (object.objectValue == OriginalValuesClass.objectValue) {
                log.complain("      not changed!");
            } else {
                log.complain("      changed incorrectly!");
            }
        } else {
            log.display("  objectValue: " + OriginalValuesClass.objectValue
                                    + " -> " + TargetValuesClass.objectValue);
        }

/*
        // check value of the field
        if (object.Value != TargetValuesClass.Value) {
            different++;
            log.complain("  Value = " + object.Value + "\n"
                       + "    setting: " + OriginalValuesClass.Value
                                + " -> " + TargetValuesClass.Value);
            if (object.Value == OriginalValuesClass.Value) {
                log.complain("      not changed!");
            } else {
                log.complain("      changed incorrectly!");
            }
        } else {
            log.display("  Value: " + OriginalValuesClass.Value
                                    + " -> " + TargetValuesClass.Value);
        }
*/

        // check taht no any changed value differs from target
        if (different > 0) {
            log.complain("Values of " + different + " fields have not been set correctly");
            return false;
        }

        log.display("Values of all fields have been set correctly");
        return true;
    }

    // class with the original values of static fields
    public static class OriginalValuesClass {
        static final boolean booleanValue = true;
        static final byte    byteValue    = (byte)0x01;
        static final char    charValue    = 'Z';
        static final int     intValue     = 100;
        static final short   shortValue   = (short)10;
        static final long    longValue    = (long)1000000;
        static final float   floatValue   = (float)3.14;
        static final double  doubleValue  = (double)2.8e-12;
        static final String  stringValue  = "text";
        static final Object  objectValue  = new OriginalValuesClass();
    }

    // class with the original values of static fields
    public static class TargetValuesClass {
        static final boolean booleanValue = false;
        static final byte    byteValue    = (byte)0x0F;
        static final char    charValue    = 'A';
        static final int     intValue     = 999;
        static final short   shortValue   = (short)88;
        static final long    longValue    = (long)11111111;
        static final float   floatValue   = (float)7.19;
        static final double  doubleValue  = (double)4.6e24;
        static final String  stringValue  = "new text";
        static final Object  objectValue  = new TargetValuesClass();
    }

    // tested class with own static fields values
    public static class TestedClass {
        private   boolean  booleanValue = OriginalValuesClass.booleanValue;
        private   byte     byteValue    = OriginalValuesClass.byteValue;
        protected char     charValue    = OriginalValuesClass.charValue;
        protected int      intValue     = OriginalValuesClass.intValue;
        public    short    shortValue   = OriginalValuesClass.shortValue;
        public    long     longValue    = OriginalValuesClass.longValue;
                  float    floatValue   = OriginalValuesClass.floatValue;
                  double   doubleValue  = OriginalValuesClass.doubleValue;
                  String   stringValue  = OriginalValuesClass.stringValue;
                  Object   objectValue  = OriginalValuesClass.objectValue;
    }

    // class with static field with the tested object
    public static class ObjectClass {
        // static field with the tested object
        public static TestedClass object = null;
    }

}
