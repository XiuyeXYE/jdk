/*
 * Copyright (c) 2017, 2020, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
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

package jdk.incubator.jpackage.internal;

import java.io.PrintWriter;
import java.util.spi.ToolProvider;

/**
 * JPackageToolProvider
 *
 * This is the ToolProvider implementation exported
 * to java.util.spi.ToolProvider and ultimately javax.tools.ToolProvider
 */
public class JPackageToolProvider implements ToolProvider {

    public String name() {
        return "jpackage";
    }

    public synchronized int run(
            PrintWriter out, PrintWriter err, String... args) {
        try {
            return new jdk.incubator.jpackage.main.Main().execute(out, err, args);
        } catch (RuntimeException re) {
            Log.fatalError(re.getMessage());
            Log.verbose(re);
            return 1;
        }
    }
}
