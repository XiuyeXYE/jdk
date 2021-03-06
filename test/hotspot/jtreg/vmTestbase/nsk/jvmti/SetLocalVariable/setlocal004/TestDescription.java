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
 * @summary converted from VM Testbase nsk/jvmti/SetLocalVariable/setlocal004.
 * VM Testbase keywords: [quick, jpda, jvmti, noras]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test exercises JVMTI functions which allow to set a value of local
 *     variable:
 *         SetLocalObject(thread, depth, slot, value)
 *         SetLocalInt(thread, depth, slot, value)
 *         SetLocalLong(thread, depth, slot, value)
 *         SetLocalFloat(thread, depth, slot, value)
 *         SetLocalDouble(thread, depth, slot, value)
 *     The test checks if the functions return JVMTI_ERROR_TYPE_MISMATCH
 *     when the variable is not an appropriate type for the function used.
 * COMMENTS
 *     Ported from JVMDI.
 *
 * @library /vmTestbase
 *          /test/lib
 *
 * @comment make sure setlocal004 is compiled with full debug info
 * @build nsk.jvmti.SetLocalVariable.setlocal004
 * @clean nsk.jvmti.SetLocalVariable.setlocal004
 * @compile -g:lines,source,vars ../setlocal004.java
 *
 * @run main/othervm/native -agentlib:setlocal004 nsk.jvmti.SetLocalVariable.setlocal004
 */

