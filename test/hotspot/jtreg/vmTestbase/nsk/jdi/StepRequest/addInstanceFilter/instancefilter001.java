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

package nsk.jdi.StepRequest.addInstanceFilter;

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
 * <code>com.sun.jdi.StepRequest.addInstanceFilter()</code>
 * complies with its spec.
 *
 * The test checks up on the following assertion:
 *    Restricts the events generated by this request to those
 *    in which the currently executing instance is the object specified.
 * The cases to test include invocations of the method addInstanceFilter() on
 * two StepRequest objects with different array element (instances) arguments.
 * (1) For StepRequest2, a testing thread will not execute an ObjectRefernce
 * instance used as the filter;
 * hence, the test expects this Step event will not be received.
 * (2) For StepRequest1, a testing thread will do execute an ObjectRefernce
 * instance used as the filter;
 * hence, the test expects this Step event will be received.
 *
 * The test works as follows.
 * - The debugger resumes the debuggee and waits for the BreakpointEvent.
 * - The debuggee creates a special array with three Object instances,
 *   and two threads, thread1 and thread2, and invokes
 *   the methodForCommunication to be suspended and
 *   to inform the debugger with the event.
 * - Upon getting the BreakpointEvent, the debugger
 *   - sets up a StepRequest within the method
 *     within the method in the class TestClass
 *     whose array element instances #0 and #1
 *     will be calling by the thread1 and the thread2 accordingly,
 *   - invokes addInstanceFilter() on array element #0 for the thread1
 *     and #2 for the thread2,
 *     thus restricting the Watchpoint event only to thread1,
 *   - resumes debuggee's main thread, and
 *   - waits for the event.
 * - Debuggee's main thread starts both threads.
 * - Upon getting the event, the debugger performs the checks required.
 */

public class instancefilter001 extends TestDebuggerType1 {

    public static void main (String argv[]) {
        System.exit(run(argv, System.out) + Consts.JCK_STATUS_BASE);
    }

    public static int run (String argv[], PrintStream out) {
        debuggeeName = "nsk.jdi.StepRequest.addInstanceFilter.instancefilter001a";
        return new instancefilter001().runThis(argv, out);
    }

    private String testedClassName =
      "nsk.jdi.StepRequest.addInstanceFilter.TestClass";


    protected void testRun() {

        if ( !vm.canUseInstanceFilters() ) {
            display("......vm.canUseInstanceFilters == false :: test cancelled");
            vm.exit(Consts.JCK_STATUS_BASE);
            return;
        }

        EventRequest eventRequest1 = null;
        EventRequest eventRequest2 = null;

        ThreadReference thread1 = null;
        ThreadReference thread2 = null;

        String thread1Name = "thread1";
        String thread2Name = "thread2";

        String property1 = "StepRequest1";
        String property2 = "StepRequest2";

        String         arrayName = "objTC";

        ObjectReference instance1 = null;
        ObjectReference instance2 = null;

        for (int i = 0; ; i++) {

            if (!shouldRunAfterBreakpoint()) {
                vm.resume();
                break;
            }

            display(":::::: case: # " + i);

            switch (i) {

            case 0:
                thread1 = (ThreadReference) debuggeeClass.getValue(
                        debuggeeClass.fieldByName(thread1Name));
                eventRequest1 = setting2StepRequest (thread1,
                        EventRequest.SUSPEND_ALL, property1);
                instance1 = (ObjectReference)
                ((ArrayReference) debuggeeClass.getValue(
                        debuggeeClass.fieldByName(arrayName)) ).getValue(0);
                ((StepRequest) eventRequest1).addInstanceFilter(instance1);

                thread2 = (ThreadReference) debuggeeClass.getValue(
                        debuggeeClass.fieldByName(thread2Name));
                eventRequest2 = setting2StepRequest (thread2,
                        EventRequest.SUSPEND_ALL, property2);
                instance2 = (ObjectReference)
                ((ArrayReference) debuggeeClass.getValue(
                        debuggeeClass.fieldByName(arrayName)) ).getValue(2);
                ((StepRequest) eventRequest2).addInstanceFilter(instance2);

                display("......waiting for StepEvent in expected thread");
                Event newEvent = eventHandler.waitForRequestedEvent(new EventRequest[]{eventRequest1}, waitTime, true);

                if ( !(newEvent instanceof StepEvent)) {
                    setFailedStatus("ERROR: new event is not StepEvent");
                    throw new Failure("** unexpected event **");
                } else {
                    Location location = ((StepEvent) newEvent).location();
                    StepRequest stepR = (StepRequest) newEvent.request();
                    String property = (String) stepR.getProperty("number");
                    display("       got new StepEvent with property 'number' == " + property);
                    display("       thread name == " + stepR.thread().name());
                    display("       size == " + stepR.size() + "; depth == " + stepR.depth());
                    display("       lineNumber == " + location.lineNumber());

                    if ( !property.equals(property1) ) {
                        setFailedStatus("ERROR: property is not : " + property1);
                    }
                }

                vm.resume();
                break;

            default:
                throw new Failure("** default case 2 **");
            }
        }
        return;
    }

    private StepRequest setting2StepRequest ( ThreadReference thread,
                                              int             suspendPolicy,
                                              String          property        )
            throws Failure {
        try {
            display("......setting up StepRequest:");
            display("       thread: " + thread + "; property: " + property);

            StepRequest
            str = eventRManager.createStepRequest(thread, StepRequest.STEP_LINE, StepRequest.STEP_INTO);
            str.putProperty("number", property);
            str.setSuspendPolicy(suspendPolicy);

            display("      StepRequest has been set up");
            return str;
        } catch ( Exception e ) {
            throw new Failure("** FAILURE to set up StepRequest **");
        }
    }
}
