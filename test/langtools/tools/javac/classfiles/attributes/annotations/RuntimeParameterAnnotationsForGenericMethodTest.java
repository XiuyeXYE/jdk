/*
 * Copyright (c) 2015, 2016, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 8044411
 * @summary Tests the RuntimeParameterVisibleAnnotations/RuntimeParameterInvisibleAnnotations attribute.
 *          Checks that the attribute is generated for bridge method.
 * @modules jdk.jdeps/com.sun.tools.classfile
 *          jdk.compiler/com.sun.tools.javac.api
 *          jdk.compiler/com.sun.tools.javac.main
 * @library /tools/lib /tools/javac/lib ../lib
 * @build toolbox.ToolBox InMemoryFileManager TestResult TestBase
 * @build WorkAnnotations TestCase ClassType TestAnnotationInfo
 * @build RuntimeParameterAnnotationsForGenericMethodTest AnnotationsTestBase RuntimeParameterAnnotationsTestBase
 * @run main RuntimeParameterAnnotationsForGenericMethodTest
 */

import java.util.ArrayList;
import java.util.List;

/**
 * RuntimeParameterAnnotationsGenericMethodTest is a test which check that
 * RuntimeVisibleParameterAnnotationsAttribute and
 * RuntimeInvisibleParameterAnnotationsAttribute are generated for both
 * generic and appropriate bridge methods.
 * All possible combinations of retention policies are tested.
 *
 * The test generates class which looks as follows:
 *
 * public class Test extends java.util.ArrayList&lt;Integer&gt; {
 *
 *     public boolean add(here some annotations java.lang.Integer) {
 *         return false;
 *     }
 * }
 *
 * Thereafter, various of combinations of annotations are applied
 * to the add, the source is compiled and the generated byte code is checked.
 *
 * See README.txt for more information.
 */
public class RuntimeParameterAnnotationsForGenericMethodTest extends RuntimeParameterAnnotationsTestBase {
    @Override
    public List<TestCase> generateTestCases() {
        List<TestCase> testCases = new ArrayList<>();
        for (TestAnnotationInfos annotations : getAllCombinationsOfAnnotations()) {
            // generate: public class Test extends java.util.ArrayList<Integer>
            TestCase testCase = new TestCase();
            TestCase.TestClassInfo clazz = testCase.addClassInfo("java.util.ArrayList<Integer>", ClassType.CLASS, "Test");
            TestCase.TestParameterInfo parameter = clazz.addMethodInfo("add(java.lang.Integer)", "public").addParameter("Integer", "i");
            annotations.annotate(parameter);
            TestCase.TestParameterInfo synParameter = clazz.addMethodInfo("add(java.lang.Object)", true, "public").addParameter("Object", "i");
            annotations.annotate(synParameter);
            testCases.add(testCase);
        }
        return testCases;
    }

    public static void main(String[] args) throws TestFailedException {
        new RuntimeParameterAnnotationsForGenericMethodTest().test();
    }
}
