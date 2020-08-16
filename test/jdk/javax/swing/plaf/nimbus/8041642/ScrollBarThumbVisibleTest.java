/*
 * Copyright (c) 2015, 2017, Oracle and/or its affiliates. All rights reserved.
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

/**
 * @test
 * @key headful
 * @bug 8134828
 * @summary Scrollbar thumb disappears with Nimbus L&F
 * @author Semyon Sadetsky
 */

import javax.swing.*;
import java.awt.*;

public class ScrollBarThumbVisibleTest
{
    private static JFrame frame;
    private static Point point;
    private static JScrollBar bar;

    public static void main(String[] args) throws Exception {
        for (UIManager.LookAndFeelInfo info : UIManager
                .getInstalledLookAndFeels()) {
            if ("Nimbus".equals(info.getName())) {
                try {
                    UIManager.setLookAndFeel(info.getClassName());
                } catch (Exception ex) {
                }
                break;
            }
        }
        try {
            SwingUtilities.invokeAndWait(new Runnable() {
                public void run() {
                    frame = new JFrame();
                    frame.setUndecorated(true);
                    frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    setup(frame);
                }
            });
            final Robot robot = new Robot();
            robot.delay(200);
            robot.waitForIdle();
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    point = bar.getLocationOnScreen();
                }
            });
            Color color1 = robot.getPixelColor(point.x + 48, point.y + 55);
            Color color2 = robot.getPixelColor(point.x + 48, point.y + 125);
            System.out.println(color1);
            System.out.println(color2);
           if (color1.equals(color2)) {
                throw new RuntimeException("Thump is not visible");
            }
        } finally {
            SwingUtilities.invokeAndWait(new Runnable() {
                @Override
                public void run() {
                    if (frame != null) { frame.dispose(); }
                }
            });
        }
        System.out.println("ok");
    }

    static void setup(JFrame frame) {
        bar = new JScrollBar(Adjustable.VERTICAL, 500, 0, 0, 1000);
        frame.getContentPane().add(bar);
        frame.setSize(50, 250);
        frame.setLocation(100, 100);
        frame.setVisible(true);
    }
}
