/*
 * Copyright (c) 2002, 2020, Oracle and/or its affiliates. All rights reserved.
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
 * @summary converted from VM Testbase gc/gctests/mallocWithGC1.
 * VM Testbase keywords: [gc]
 * VM Testbase readme:
 * LD_LIBRARY PATH must include "$TESTBASE/src/misc/gc/utils/lib/sparc(or i386)"
 * while running these tests. The native code for all the mallocWithGC* tests
 * has been bunched up into a single .so.
 * In this test, 2 threads are created, one thread(javaHeapEater)
 * creates garbage by nulling out the elements of a vector, which formerly
 * held points to circular linked lists. These elements are again repopulated
 * with new linked lists. The second thread invokes a native function
 * that continually mallocs and frees one byte of memory for 3 minutes
 * a hold on a malloc lock.
 * The idea here is  to see if the vm deadlocks (if it does, it is ofcourse
 * a failure ). This test was created because of the following problem
 * that the vm used to have :
 *  "The malloc/GC deadlock problem is that a gc may suspend a thread (in native
 * or VM code) that is in the middle of a malloc, so it has the "malloc" lock.
 * GC may want to do a malloc, but it can't get the lock, so it deadlocks. "
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm/native/timeout=300 gc.gctests.mallocWithGC1.mallocWithGC1
 */

package gc.gctests.mallocWithGC1;

import nsk.share.test.*;
import nsk.share.gc.*;
import nsk.share.TestFailure;
import java.util.Vector;

public class mallocWithGC1 implements Test {
        private int objectSize = 100;

        static {
                System.loadLibrary("mallocWithGC1");
        }

        public native void getMallocLock01();

        class javaHeapEater extends Thread {
                private Vector v;

                public javaHeapEater(Vector v) {
                        this.v = v;
                }

                public void run() throws OutOfMemoryError {
                        int  gc_count;

                        for(int i = 0; i < 5 ; i++)
                                v.addElement(buildCircularLinkedList());
                        gc_count = 0;
                        while( gc_count < 10 ) {

                                for(int i = 0; i < 5 ; i++)
                                        v.setElementAt(null, i);

                                for(int i = 0; i < 5 ; i++)
                                        v.setElementAt(buildCircularLinkedList(),i);

                                gc_count++;
                                System.out.println("Finished iteration # " + gc_count);
                        }
                }
        }

        class cHeapEater extends Thread{
                public void run() {
                        getMallocLock01();
                }
        }

        public void run() {
                Vector v = new Vector(5);
                Thread tArray[] = new Thread[2];

                tArray[0] = new javaHeapEater(v);
                tArray[1] = new cHeapEater();

                try {
                        for(int i = 0; i < tArray.length ; i++ )
                                tArray[i].start();
                        for(int i = 0; i < tArray.length ; i++ )
                                tArray[i].join();
                } catch (Exception e) {
                        throw new TestFailure("Test Failed.", e);
                }
                System.out.println("Test Passed.");
        }

        // build a circular linked list of 0.2 Meg

        private CircularLinkedList  buildCircularLinkedList() {
                CircularLinkedList cl = new CircularLinkedList(objectSize);
                for(int i = 0; i < 2000; i++)
                        cl.grow();
                return cl;
        }

        public static void main(String args[]){
                Tests.runTest(new mallocWithGC1(), args);
        }
}
