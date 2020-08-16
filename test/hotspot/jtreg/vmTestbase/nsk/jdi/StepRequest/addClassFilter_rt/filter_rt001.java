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

package nsk.jdi.StepRequest.addClassFilter_rt;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdi.*;

import com.sun.jdi.*;
import com.sun.jdi.event.*;
import com.sun.jdi.request.*;

import java.util.*;
import java.io.*;

/**
 * The test for the implementation of an object of the type
 * StepRequest.
 *
 * The test checks that results of the method
 * <code>com.sun.jdi.StepRequest.addClassFilter(ReferenceType)</code>
 * complies with its spec.
 *
 * The test checks up on the following assertion:
 *    Restricts the events generated by this request to those
 *    whose location is in the given reference type or
 *    any of its subtypes.
 * The cases to check include Steps in debuggee's four types,
 * two in TestClass10 and its sub-class TestClass11, and
 * two in TestClass20 and its sub-class TestClass21.
 * The filter restricts StepEvent to only TestClass11.
 *
 * The test works as follows.
 * - The debugger resumes the debuggee and waits for the BreakpointEvent.
 * - The debuggee creates and starts two threads, thread1 and thread2;
 *   thread1 will invoke methods in TestClass10 and TestClass11, and
 *   thread2 in TestClass20 and TestClass21,
 *   and steps into methods are objects to test;
 *   and invokes the methodForCommunication to be suspended and
 *   to inform the debugger with the event.
 * - Upon getting the BreakpointEvent, the debugger
 *   - gets ReferenceTypes to use to filter StepEvents,
 *   - sets up StepRequest for the events,
 *   - restricts the events to those in TestClass10, that is, in thread1,
 *   - and resumes the debuggee and waits for the events.
 * - Upon getting the events, the debugger performs checks required.
 */

public class filter_rt001 extends TestDebuggerType1 {

    public static void main (String argv[]) {
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    public static int run (String argv[], PrintStream out) {
        debuggeeName = "nsk.jdi.StepRequest.addClassFilter_rt.filter_rt001a";
        return new filter_rt001().runThis(argv, out);
    }

    private String testedClassName =
        "nsk.jdi.StepRequest.addClassFilter_rt.TestClass11";

    protected void testRun() {

        EventRequest    eventRequest1      = null;
        String          property1          = "StepRequest1";
        ReferenceType   testClassReference = null;
        ThreadReference thread1            = null;
        String          threadName1        = "thread1";

        for (int i = 0; ; i++) {

            if (!shouldRunAfterBreakpoint()) {
                vm.resume();
                break;
            }

            display(":::::: case: # " + i);

            switch (i) {

                case 0:
                testClassReference = (ReferenceType)debuggee.classByName(testedClassName);
                thread1 = debuggee.threadByName(threadName1);
                if (thread1 == null)
                    throw new Failure("Cannot get ThreadReference for " + threadName1);
                eventRequest1 = setting21StepRequest(thread1, testClassReference,
                                             EventRequest.SUSPEND_ALL, property1);

                display("......waiting for StepEvent in expected thread");
                Event newEvent = eventHandler.waitForRequestedEvent(new EventRequest[]{eventRequest1}, waitTime, false);

                if ( !(newEvent instanceof StepEvent)) {
                    setFailedStatus("ERROR: new event is not StepEvent");
                } else {

                    String property = (String) newEvent.request().getProperty("number");
                    display("       got new StepEvent with property 'number' == " + property);

                    if ( !property.equals(property1) ) {
                        setFailedStatus("ERROR: property is not : " + property1);
                    }

                    ReferenceType refType = ((StepEvent)newEvent).location().declaringType();
                    if (!refType.equals(testClassReference)) {
                        setFailedStatus("Received unexpected declaring type of the event: " + refType.name() +
                            "\n\texpected one: " + testClassReference.name());
                    }
                }
                vm.resume();
                break;

                default:
                throw new Failure("** default case 1 **");
            }
        }
        return;
    }

    private StepRequest setting21StepRequest ( ThreadReference thread,
                                               ReferenceType   testedClass,
                                               int             suspendPolicy,
                                               String          property        ) {
        try {
            display("......setting up StepRequest:");
            display("       thread: " + thread + "; property: " + property);

            StepRequest
            str = eventRManager.createStepRequest(thread, StepRequest.STEP_LINE, StepRequest.STEP_INTO);
            str.putProperty("number", property);
            str.setSuspendPolicy(suspendPolicy);
            str.addClassFilter(testedClass);
            str.addCountFilter(1);

            display("      StepRequest has been set up");
            return str;
        } catch ( Exception e ) {
            throw new Failure("FAILURE to set up StepRequest:" + e);
        }
    }

}
