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
 * @test
 *
 * @summary converted from VM Testbase jit/t/t070.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @run driver jdk.test.lib.FileInstaller . .
 * @build jit.t.t070.t070
 * @run driver ExecDriver --java jit.t.t070.t070
 */

package jit.t.t070;

import nsk.share.TestFailure;
import nsk.share.GoldChecker;

// Dup, dup_x2, and dup2_x2.

class t070
{
    public static final GoldChecker goldChecker = new GoldChecker( "t070" );

    public static void main(String[] argv)
    {
        int ia[] = new int[2];
        long la[] = new long[2];
        int i, j;
        int x = 39, y = 42;

        ia[0] = ia[1] = x + y;
        la[1] = la[0] = x - y;
        t070.goldChecker.println(ia[0] + " " + ia[1] + " " + la[1] + " " + la[0]);

        i = 0;
        j = 1;

        ia[i] = ia[j] = x + y;
        la[j] = la[i] = x - y;
        t070.goldChecker.println(ia[i] + " " + ia[j] + " " + la[j] + " " + la[i]);
        t070.goldChecker.check();
    }
}
