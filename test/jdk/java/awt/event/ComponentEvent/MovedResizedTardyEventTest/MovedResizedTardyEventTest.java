/*
 * Copyright (c) 2004, 2015, Oracle and/or its affiliates. All rights reserved.
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

/*
  @test
  @key headful
  @bug          4985250
  @summary      COMPONENT_MOVED/RESIZED tardy events shouldn't be generated.
  @modules java.desktop/sun.awt
  @run main MovedResizedTardyEventTest
*/

import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;

public class MovedResizedTardyEventTest {
    Frame f1 = new Frame("F-1");
    Frame f2 = new Frame("F-2");

    boolean eventFlag = false;

    public static void main(String[] args) {
        MovedResizedTardyEventTest a = new MovedResizedTardyEventTest();
        a.start();
    }

    public void start() {
        f1.setVisible(true);
        f2.setVisible(true);

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {}

        f1.addComponentListener(new ComponentAdapter() {
                public void componentMoved(ComponentEvent e) {
                    MovedResizedTardyEventTest.this.eventFlag = true;
                    System.err.println(e);
                }
                public void componentResized(ComponentEvent e) {
                    MovedResizedTardyEventTest.this.eventFlag = true;
                    System.err.println(e);
                }
            });

        f1.toFront();

        waitForIdle();

        try { // wait more...
            Thread.sleep(500);
        } catch (InterruptedException e) {}

        if (eventFlag) {
            throw new RuntimeException("Test failed!");
        }
    }

    void waitForIdle() {
        try {
            (new Robot()).waitForIdle();
            EventQueue.invokeAndWait( new Runnable() {
                    public void run() {} // Dummy implementation
                });
        } catch(InterruptedException ie) {
            System.err.println("waitForIdle, non-fatal exception caught:");
            ie.printStackTrace();
        } catch(InvocationTargetException ite) {
            System.err.println("waitForIdle, non-fatal exception caught:");
            ite.printStackTrace();
        } catch(AWTException rex) {
            rex.printStackTrace();
            throw new RuntimeException("unexpected exception");
        }
    }
}
