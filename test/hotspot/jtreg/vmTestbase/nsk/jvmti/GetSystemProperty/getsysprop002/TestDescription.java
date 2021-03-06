/*
 * Copyright (c) 2018, 2020, Oracle and/or its affiliates. All rights reserved.
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
 * @summary converted from VM Testbase nsk/jvmti/GetSystemProperty/getsysprop002.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     This JVMTI test exercises JVMTI function GetSystemProperty().
 *     This test checks that GetSystemProperty() returns not expected values
 *     for system properties defined via command line option "-Dproperty=value",
 *     even if defined property has empty or not empty value.
 *     This is checked either for OnLoad and live phases.
 * COMMENTS
 *     For other testcases see tests for SetSystemProperty() function.
 *
 * @library /vmTestbase
 *          /test/lib
 * @build ExecDriver
 *        nsk.jvmti.GetSystemProperty.getsysprop002
 * @run main/othervm/native PropertyResolvingWrapper ExecDriver --java
 *      -agentlib:getsysprop002=-waittime=5
 *      "-Dnsk.jvmti.test.property=value of nsk.jvmti.test.property"
 *      "-Dnsk.jvmti.test.property.empty="
 *      nsk.jvmti.GetSystemProperty.getsysprop002
 */

