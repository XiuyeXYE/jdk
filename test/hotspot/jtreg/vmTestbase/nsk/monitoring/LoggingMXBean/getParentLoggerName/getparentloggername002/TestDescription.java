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
 *
 * @summary converted from VM Testbase nsk/monitoring/LoggingMXBean/getParentLoggerName/getparentloggername002.
 * VM Testbase keywords: [quick, monitoring]
 * VM Testbase readme:
 * DESCRIPTION
 *         Interface: java.util.logging.LoggingMXBean
 *         Method: getParentLoggerName(String loggerName)
 *         Returns the name of the parent for the specified logger.
 *         Access to metrics is provided in following way: default MBeanServer.
 *     The test checks that
 *         1. it returns the name of the parent for the specified logger
 *         2. if the specified logger does not exist, null is returned
 *         3. if the specified logger is the root Logger in the namespace, the
 *             result will be an empty string
 * COMMENT
 *
 * @library /vmTestbase
 *          /test/lib
 * @run main/othervm
 *      nsk.monitoring.LoggingMXBean.getParentLoggerName.getparentloggername001
 *      -testMode=server
 *      -MBeanServer=default
 */

