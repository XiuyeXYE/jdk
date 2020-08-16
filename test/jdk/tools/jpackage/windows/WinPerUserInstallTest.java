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

import jdk.jpackage.test.PackageTest;
import jdk.jpackage.test.PackageType;
import jdk.jpackage.test.Annotations.Test;

/**
 * Test --win-per-user-install, --win-menu, --win-menu-group parameters.
 * Output of the test should be WinPerUserInstallTest-1.0.exe installer. The
 * output installer should provide the same functionality as the default
 * installer (see description of the default installer in
 * SimplePackageTest.java) plus it should create application menu in Windows
 * Menu and installation should be per user and not machine wide.
 */

/*
 * @test
 * @summary jpackage with --win-per-user-install, --win-menu, --win-menu-group
 * @library ../helpers
 * @key jpackagePlatformPackage
 * @build jdk.jpackage.test.*
 * @requires (os.family == "windows")
 * @modules jdk.incubator.jpackage/jdk.incubator.jpackage.internal
 * @compile WinPerUserInstallTest.java
 * @run main/othervm/timeout=360 -Xmx512m jdk.jpackage.test.Main
 *  --jpt-run=WinPerUserInstallTest
 */

public class WinPerUserInstallTest {
    @Test
    public static void test() {
        new PackageTest()
        .forTypes(PackageType.WINDOWS)
        .configureHelloApp()
        .addInitializer(cmd -> cmd.addArguments(
                "--win-menu",
                "--win-menu-group", "WinPerUserInstallTest_MenuGroup",
                "--win-per-user-install"))
        .run();
    }
}
