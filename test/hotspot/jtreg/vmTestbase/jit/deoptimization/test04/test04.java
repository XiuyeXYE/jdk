/*
 * Copyright (c) 2008, 2020, Oracle and/or its affiliates. All rights reserved.
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
 * @summary converted from VM Testbase jit/deoptimization/test04.
 * VM Testbase keywords: [jit, quick]
 *
 * @library /vmTestbase
 *          /test/lib
 * @build jit.deoptimization.test04.test04
 * @run driver ExecDriver --java jit.deoptimization.test04.test04
 */

package jit.deoptimization.test04;

import nsk.share.TestFailure;

/*
 *
 */

class test04 {
        public static void main (String[] args) {
                A obj = new A();

                int result = -1;
                for (int index = 0; index < 1; index++) {
                        result += obj.used_alot();
                }

                if (result != 1) {
                        throw new TestFailure("result : " + result + " must equal 1");
                }
        }
}


class A {
        protected int count;
        public int foo(int index) {
                int rv = ++count;
                if (index < A.sIndex / 2)
                        rv = index;
                else {
                        try {
                                rv = ((A)Class.forName("jit.deoptimization.test04.B").newInstance()).foo(index);
                        }
                        catch(Exception e) {
                        }
                }
                return rv;
        }

        public int used_alot() {
                int result = 1;
                for (int i = 0; i < A.sIndex; i++) {
                        result = foo(i);
                }
                return result;
        }

        protected static final int sIndex = 25000;
}

class B extends A {
        public int foo(int index) {
                int rv = 0;
                int qrtr = A.sIndex / 4;
                if (index > 3 * qrtr) {
                        try {
                                if (index < A.sIndex - qrtr)
                                        rv = ((B)Class.forName("jit.deoptimization.test04.C").newInstance()).foo(index);
                                else
                                        rv = ((B)Class.forName("jit.deoptimization.test04.D").newInstance()).foo(index);
                        }
                        catch(Exception e) {
                        }
                }
                else {
                        rv = 1;
                }
                return rv;
        }
}

class C extends B {
        public C () {
                --this.count;
        }

        public int foo(int index) {
                int j = count;
                for (int i=0; i<500; i++)
                        j += used_alot();
                return j;
        }

        public int used_alot() {
                int i=1;
                int j=4;
                int k=i+j;
                byte ba[] = new byte[1000];
                int ia[] = new int[1000];
                return this.count + (ba.length + i + j - k - ia.length);
        }

        protected int count = 1;
}

class D extends C {
        public D () {
                super();

                this.count+=2;
        }

        public int foo(int index) {
                return super.foo(index);
        }

        public synchronized int used_alot() {
                count += (--count);
                return 0;
        }
}
