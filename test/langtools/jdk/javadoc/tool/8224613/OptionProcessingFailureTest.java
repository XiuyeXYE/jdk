/*
 * Copyright (c) 2020, Oracle and/or its affiliates. All rights reserved.
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
 * @bug 8224613
 * @library /tools/lib ../../lib
 * @modules jdk.javadoc/jdk.javadoc.internal.tool
 * @build javadoc.tester.* toolbox.ToolBox builder.ClassBuilder
 * @run main/othervm OptionProcessingFailureTest
 */

import builder.ClassBuilder;
import javadoc.tester.JavadocTester;
import jdk.javadoc.doclet.Doclet;
import jdk.javadoc.doclet.DocletEnvironment;
import jdk.javadoc.doclet.Reporter;
import toolbox.ToolBox;

import javax.lang.model.SourceVersion;
import javax.tools.Diagnostic;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/*
 * Tests that a particular error is raised only if a doclet has not reported any
 * errors (to Reporter), and yet at least one of that Doclet's options returned
 * `false` from the `Doclet.Option.process` method.
 *
 * This test explores scenarios generated by combining a few independent factors
 * involved in failing a doclet run. These factors are:
 *
 *   1. Reporting errors in Doclet.init(...)
 *   2. Reporting errors in Doclet.Option.process(...)
 *   3. Returning `false` from Doclet.Option.process(...)
 *
 * A doclet that behaves according to a scenario is run by the javadoc tool. An
 * output of that run is then examined for presence of a particular error. That
 * error must be in the output if and only if one or more options returned
 * `false` from Doclet.Option.process(...) and no other errors were reported.
 *
 * Configuration of the doclet is performed out-of-band, using System Properties
 * (when running the javadoc tool from the command line this could be achieved
 * using -J-Dsystem.property.name=value syntax). The "puppet" doclet is
 * instructed on which options is has, how many errors it should report,
 * and how each individual option should be processed.
 */
public class OptionProcessingFailureTest extends JavadocTester {

    private final ToolBox tb = new ToolBox();

    public static void main(String... args) throws Exception {
        new OptionProcessingFailureTest().runTests(m -> new Object[]{Paths.get(m.getName())});
    }

    @Test
    public void test(Path base) throws IOException {
        Path srcDir = base.resolve("src");

        // Since the minimal successful run of the javadoc tool with a custom
        // doclet involves source files, a package with a class in it is generated:
        new ClassBuilder(tb, "pkg.A")
                .setModifiers("public", "class")
                .write(srcDir);

        generateScenarios(base, this::testScenario);
    }

    private static void generateScenarios(Path base, ScenarioConsumer consumer) {
        for (int nInitErrors : List.of(0, 1)) { // the number of errors the Doclet.init method should report
            for (int nOptions : List.of(0, 1, 2)) { // the number of options a doclet should have
                generateOptionsCombinations(base, nInitErrors, nOptions, consumer);
            }
        }
    }

    private static void generateOptionsCombinations(Path base,
                                                    int nInitErrors,
                                                    int nOptions,
                                                    ScenarioConsumer consumer) {
        var emptyDescriptions = new PuppetOption.Description[nOptions];
        generateOptionsCombinations(base, nInitErrors, 0, emptyDescriptions, consumer);
    }


    private static void generateOptionsCombinations(Path base,
                                                    int nInitErrors,
                                                    int descriptionsIndex,
                                                    PuppetOption.Description[] descriptions,
                                                    ScenarioConsumer consumer) {
        if (descriptionsIndex >= descriptions.length) {
            consumer.consume(base, nInitErrors, List.of(descriptions));
            return;
        }
        for (boolean success : List.of(true, false)) { // return values of Doclet.Options.process
            for (int nErrors : List.of(0, 1)) { // the number of errors Doclet.Options.process should report
                String optionName = "--doclet-option-" + descriptionsIndex;
                descriptions[descriptionsIndex] = new PuppetOption.Description(optionName, success, nErrors);
                generateOptionsCombinations(base, nInitErrors, descriptionsIndex + 1, descriptions, consumer);
            }
        }
    }

    private void testScenario(Path base,
                              int nInitErrors,
                              List<PuppetOption.Description> optionDescriptions) {
        System.out.printf("nInitErrors=%s, optionDescriptions=%s%n", nInitErrors, optionDescriptions);

        List<String> args = new ArrayList<>(
                List.of("-doclet", PuppetDoclet.class.getName(),
                        "-docletpath", System.getProperty("test.classes", "."),
                        "-sourcepath", base.resolve("src").toString(),
                        "pkg"));

        optionDescriptions.forEach(d -> args.add(d.name)); // options passed to the doclet

        /* The puppet doclet does not create any output directories, so there is
           no need for any related checks; other checks are disabled to speed up
           the processing and reduce the size of the output. */

        setOutputDirectoryCheck(DirectoryCheck.NONE);
        setAutomaticCheckAccessibility(false);
        setAutomaticCheckLinks(false);

        /* Ideally, the system properties should've been passed to the `javadoc`
           method below. However, since there's no such option, the system
           properties are manipulated in an external fashion. */

        String descriptions = System.getProperty("puppet.descriptions");
        if (descriptions != null) {
            throw new IllegalStateException(descriptions);
        }
        String errors = System.getProperty("puppet.errors");
        if (errors != null) {
            throw new IllegalStateException(errors);
        }
        try {
            System.setProperty("puppet.descriptions", PuppetDoclet.descriptionsToString(optionDescriptions));
            System.setProperty("puppet.errors", String.valueOf(nInitErrors));
            javadoc(args.toArray(new String[0]));
        } finally {
            System.clearProperty("puppet.descriptions");
            System.clearProperty("puppet.errors");
        }

        long sumErrors = optionDescriptions.stream().mapToLong(d -> d.nProcessErrors).sum() + nInitErrors;
        boolean success = optionDescriptions.stream().allMatch(d -> d.success);

        checkOutput(Output.OUT, sumErrors != 0 || !success, "error - ");
    }

    /* Creating a specialized consumer is even more lightweight than creating a POJO */
    @FunctionalInterface
    public interface ScenarioConsumer {
        void consume(Path src, int nInitErrors, List<PuppetOption.Description> optionDescriptions);
    }

    public static final class PuppetDoclet implements Doclet {

        private final int nErrorsInInit;
        private final Set<PuppetOption.Description> descriptions;
        private Set<Option> options;
        private Reporter reporter;

        public PuppetDoclet() {
            this(nInitErrorsFromString(System.getProperty("puppet.errors")),
                 descriptionsFromString(System.getProperty("puppet.descriptions")));
        }

        private static Collection<PuppetOption.Description> descriptionsFromString(String value) {
            if (value.isEmpty())
                return List.of(); // special case of description of zero-length
            String[] split = value.split(",");
            List<PuppetOption.Description> descriptions = new ArrayList<>();
            for (int i = 0; i < split.length; i += 3) {
                String name = split[i];
                boolean success = Boolean.parseBoolean(split[i + 1]);
                int nErrors = Integer.parseInt(split[i + 2]);
                descriptions.add(new PuppetOption.Description(name, success, nErrors));
            }
            return descriptions;
        }

        public static String descriptionsToString(Collection<? extends PuppetOption.Description> descriptions) {
            return descriptions.stream()
                    .map(d -> d.name + "," + d.success + "," + d.nProcessErrors)
                    .collect(Collectors.joining(","));
        }

        private static int nInitErrorsFromString(String value) {
            return Integer.parseInt(Objects.requireNonNull(value));
        }

        public PuppetDoclet(int nErrorsInInit, Collection<PuppetOption.Description> descriptions) {
            if (nErrorsInInit < 0) {
                throw new IllegalArgumentException();
            }
            this.nErrorsInInit = nErrorsInInit;
            this.descriptions = Set.copyOf(descriptions);
        }

        @Override
        public void init(Locale locale, Reporter reporter) {
            this.reporter = reporter;
            for (int i = 0; i < nErrorsInInit; i++) {
                reporter.print(Diagnostic.Kind.ERROR, "init error #%s".formatted(i));
            }
        }

        @Override
        public String getName() {
            return getClass().getSimpleName();
        }

        @Override
        public Set<? extends Option> getSupportedOptions() {
            if (options == null) {
                options = new HashSet<>();
                for (PuppetOption.Description d : descriptions) {
                    options.add(new PuppetOption(d, reporter));
                }
            }
            return options;
        }

        @Override
        public SourceVersion getSupportedSourceVersion() {
            return SourceVersion.latestSupported();
        }

        @Override
        public boolean run(DocletEnvironment environment) {
            return true;
        }
    }

    private static final class PuppetOption implements Doclet.Option {

        private final Reporter reporter;
        private final Description description;

        public PuppetOption(Description description, Reporter reporter) {
            this.description = description;
            this.reporter = reporter;
        }

        @Override
        public int getArgumentCount() {
            return 0;
        }

        @Override
        public String getDescription() {
            return description.name
                    + ": success=" + description.success
                    + ", nProcessErrors=" + description.nProcessErrors;
        }

        @Override
        public Kind getKind() {
            return Kind.STANDARD;
        }

        @Override
        public List<String> getNames() {
            return List.of(description.name);
        }

        @Override
        public String getParameters() {
            return "";
        }

        @Override
        public boolean process(String option, List<String> arguments) {
            for (int i = 0; i < description.nProcessErrors; i++) {
                reporter.print(Diagnostic.Kind.ERROR,
                               "option: '%s', process error #%s".formatted(description.name, i));
            }
            return description.success;
        }

        public final static class Description {

            public final String name;
            public final boolean success;
            public final int nProcessErrors;

            Description(String name, boolean success, int nErrors) {
                this.name = name;
                this.success = success;
                this.nProcessErrors = nErrors;
            }

            @Override
            public String toString() {
                return "Description{" +
                        "name='" + name + '\'' +
                        ", success=" + success +
                        ", nProcessErrors=" + nProcessErrors +
                        '}';
            }
        }
    }
}

