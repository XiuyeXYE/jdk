/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 8203792 8243113
 * @summary Remove "compatibility" features from Head.java
 * @library /tools/lib ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.* toolbox.ToolBox builder.ClassBuilder
 * @run main TestHeadTag
 */


import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;

import builder.ClassBuilder;
import toolbox.ToolBox;

import javadoc.tester.JavadocTester;

public class TestHeadTag extends JavadocTester {

    final ToolBox tb;
    final String version = System.getProperty("java.specification.version");

    public static void main(String... args) throws Exception {
        TestHeadTag tester = new TestHeadTag();
        tester.runTests(m -> new Object[]{Paths.get(m.getName())});
    }

    TestHeadTag() {
        tb = new ToolBox();
    }

    @Test
    public void test(Path base) throws Exception {
        Path srcDir = base.resolve("src");
        createTestClass(srcDir);

        Path outDir = base.resolve("out");
        javadoc("-d", outDir.toString(),
                "-sourcepath", srcDir.toString(),
                "pkg");

        checkExit(Exit.OK);

        checkOrder("pkg/A.html",
                "Generated by javadoc (" + version + ")",
                "<meta name=\"dc.created\"");
    }

    @Test
    public void testWithNoTimestamp(Path base) throws Exception {
        Path srcDir = base.resolve("src");
        createTestClass(srcDir);

        Path outDir = base.resolve("out-1");
        javadoc("-d", outDir.toString(),
                "-notimestamp",
                "-sourcepath", srcDir.toString(),
                "pkg");

        checkExit(Exit.OK);

        checkOutput("pkg/A.html", true,
                "<!-- Generated by javadoc (" + version + ") -->");
        checkOutput("pkg/A.html", false,
                "<meta name=\"dc.created\"");
    }

    void createTestClass(Path srcDir) throws Exception {
        new ClassBuilder(tb, "pkg.A")
                .setModifiers("public", "class")
                .write(srcDir);
    }
}
