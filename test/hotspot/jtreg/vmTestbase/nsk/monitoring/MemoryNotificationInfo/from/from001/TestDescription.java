/*
 * Copyright (c) 2017, 2020, Oracle and/or its affiliates. All rights reserved.
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
 * @key randomness
 *
 * @summary converted from VM Testbase nsk/monitoring/MemoryNotificationInfo/from/from001.
 * VM Testbase keywords: [quick, monitoring]
 * VM Testbase readme:
 * DESCRIPTION
 *     The test checks that
 *         MemoryNotificationInfo.from(CompositeData)
 *     returns correct results:
 *     1. null, if CompositeData is null;
 *     2. trows IllegalArgumentException, if CompositeData doest not represnt
 *        MemoryNotificationInfo;
 *     3. correct MemoryNotificationInfo object, if CompositeData is correst (i.e
 *        all attributes of the CompositeData must have correct values: pool name,
 *        count; init, used, committed, max (from MemoryUsage).
 * COMMENT
 *     Updated according to:
 *     5024531 Fix MBeans design flaw that restricts to use JMX CompositeData
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm
 *      -XX:-UseGCOverheadLimit
 *      nsk.monitoring.MemoryNotificationInfo.from.from001
 *      -testMode=server
 */

