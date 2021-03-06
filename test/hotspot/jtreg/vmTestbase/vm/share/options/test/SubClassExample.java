/*
 * Copyright (c) 2008, 2018, Oracle and/or its affiliates. All rights reserved.
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
 * This is a very simple test for our framework.
 */
package vm.share.options.test;

import vm.share.options.Option;
import vm.share.options.OptionSupport;

/**
 * This is an example with annotated superclasses
 * @see vm.share.options.Option
 * @see vm.share.options.OptionSupport
 */
public class SubClassExample extends SimpleExampleWithOptionsAnnotation
{

    @Option(name = "timeout", default_value = "100", description = "timeout")
    int timeout;


    @Option(name = "logging_mode", description = "quiet or verbose")
    private String logging_mode;

    @Override
    public void run()
    {
    // ..do actual testing here..
         System.out.println("iterations = " + iterations);
         System.out.println("RM : " + running_mode);
         System.out.println("logging_mode" + logging_mode);
         System.out.println("timeout " + timeout);
         System.out.println("stresstime " + stressOptions.getStressTime());
    }


    public static void main(String[] args)
    {
        SubClassExample test = new SubClassExample();
        OptionSupport.setup(test, args); // instead of manually
                                         // parsing arguments
        // now test.iterations is set to 10 or 100 (default value).
        test.run();
    }
}
