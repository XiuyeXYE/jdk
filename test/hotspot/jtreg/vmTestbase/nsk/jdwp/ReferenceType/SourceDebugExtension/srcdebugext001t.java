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

package nsk.jdwp.ReferenceType.SourceDebugExtension;

import nsk.share.*;
import nsk.share.jpda.*;
import nsk.share.jdwp.*;

/**
 * This is a debuggee class.
 */
public class srcdebugext001t {
    public static void main(String args[]) {
        ArgumentHandler argHandler = new ArgumentHandler(args);
        IOPipe pipe = argHandler.createDebugeeIOPipe();
        Log log = new Log(System.out, argHandler);

        pipe.println(srcdebugext001.COMMAND_READY);
        String cmd = pipe.readln();
        if (!cmd.equals(srcdebugext001.COMMAND_QUIT)) {
            log.complain("TEST BUG: unknown debugger command: "
                + cmd);
            System.exit(srcdebugext001.JCK_STATUS_BASE +
                srcdebugext001.FAILED);
        }
        log.display("Received command: " + cmd
            + "\nDebuggee is exiting...");
        System.exit(srcdebugext001.JCK_STATUS_BASE +
            srcdebugext001.PASSED);
    }
}
