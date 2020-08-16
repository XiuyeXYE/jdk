/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 * Copyright (c) 2018, Google and/or its affiliates. All rights reserved.
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

package MyPackage;

/**
 * @test
 * @summary Verifies that a VM event callback does not recurse if a VM object is allocated during callback.
 * @compile VMEventRecursionTest.java
 * @run main/othervm/native -agentlib:VMEventTest MyPackage.VMEventRecursionTest
 */
public class VMEventRecursionTest implements Cloneable {
    // Implement a simple clone. A call will provoke a JVMTI event for VM allocations, which tries to
    // call this again.
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public static void main(String[] args) {
        VMEventRecursionTest obj = new VMEventRecursionTest();
        try {
            obj.clone();
        } catch(CloneNotSupportedException e) {
            // NOP.
        }
    }
}
