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
 * @summary Checks that the appropriate value is given to the canonical ctr
 * @compile --enable-preview -source ${jdk.version} DifferentStreamFieldsTest.java
 * @run testng/othervm --enable-preview DifferentStreamFieldsTest
 * @run testng/othervm/java.security.policy=empty_security.policy --enable-preview DifferentStreamFieldsTest
 */

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import static java.io.ObjectStreamConstants.SC_SERIALIZABLE;
import static java.io.ObjectStreamConstants.STREAM_MAGIC;
import static java.io.ObjectStreamConstants.STREAM_VERSION;
import static java.io.ObjectStreamConstants.TC_CLASSDESC;
import static java.io.ObjectStreamConstants.TC_ENDBLOCKDATA;
import static java.io.ObjectStreamConstants.TC_NULL;
import static java.io.ObjectStreamConstants.TC_OBJECT;
import static java.io.ObjectStreamConstants.TC_STRING;
import static java.lang.System.out;
import static org.testng.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.io.UncheckedIOException;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Checks that the appropriate value is given to the canonical ctr.
 */
public class DifferentStreamFieldsTest {

    record R01(boolean x) implements Serializable {}

    record R02(byte x) implements Serializable {}

    record R03(short x) implements Serializable {}

    record R04(char x) implements Serializable {}

    record R05(int x) implements Serializable {}

    record R06(long x) implements Serializable {}

    record R07(float x) implements Serializable {}

    record R08(double x) implements Serializable {}

    record R09(Object x) implements Serializable {}

    record R10(String x) implements Serializable {}

    record R11(int[]x) implements Serializable {}

    record R12(Object[]x) implements Serializable {}

    record R13(R12 x) implements Serializable {}

    record R14(R13[]x) implements Serializable {}

    @DataProvider(name = "recordTypeAndExpectedValue")
    public Object[][] recordTypeAndExpectedValue() {
        return new Object[][]{
            new Object[]{R01.class, false},
            new Object[]{R02.class, (byte) 0},
            new Object[]{R03.class, (short) 0},
            new Object[]{R04.class, '\u0000'},
            new Object[]{R05.class, 0},
            new Object[]{R06.class, 0L},
            new Object[]{R07.class, 0.0f},
            new Object[]{R08.class, 0.0d},
            new Object[]{R09.class, null},
            new Object[]{R10.class, null},
            new Object[]{R11.class, null},
            new Object[]{R12.class, null},
            new Object[]{R13.class, null},
            new Object[]{R14.class, null}
        };
    }

    @Test(dataProvider = "recordTypeAndExpectedValue")
    public void testWithDifferentTypes(Class<?> clazz, Object expectedXValue)
    throws Exception {
        out.println("\n---");
        assert clazz.isRecord();
        byte[] bytes = SerialByteStreamBuilder
            .newBuilder(clazz.getName())
            .build();

        Object obj = deserialize(bytes);
        out.println("deserialized: " + obj);
        Object actualXValue = clazz.getDeclaredMethod("x").invoke(obj);
        assertEquals(actualXValue, expectedXValue);

        bytes = SerialByteStreamBuilder
            .newBuilder(clazz.getName())
            .addPrimitiveField("y", int.class, 5)  // stream junk
            .build();

        obj = deserialize(bytes);
        out.println("deserialized: " + obj);
        actualXValue = clazz.getDeclaredMethod("x").invoke(obj);
        assertEquals(actualXValue, expectedXValue);
    }

    // --- all together

    @Test
    public void testWithAllTogether() throws Exception {
        out.println("\n---");
        record R15(boolean a, byte b, short c, char d, int e, long f, float g,
                   double h, Object i, String j, long[]k, Object[]l)
            implements Serializable {}

        byte[] bytes = SerialByteStreamBuilder
            .newBuilder(R15.class.getName())
            .addPrimitiveField("x", int.class, 5)  // stream junk
            .build();

        R15 obj = deserialize(bytes);
        out.println("deserialized: " + obj);
        assertEquals(obj.a, false);
        assertEquals(obj.b, 0);
        assertEquals(obj.c, 0);
        assertEquals(obj.d, '\u0000');
        assertEquals(obj.e, 0);
        assertEquals(obj.f, 0l);
        assertEquals(obj.g, 0f);
        assertEquals(obj.h, 0d);
        assertEquals(obj.i, null);
        assertEquals(obj.j, null);
        assertEquals(obj.k, null);
        assertEquals(obj.l, null);
    }

    @Test
    public void testInt() throws Exception {
        out.println("\n---");
        {
            record R(int x) implements Serializable {}

            var r = new R(5);
            byte[] OOSBytes = serialize(r);

            byte[] builderBytes = SerialByteStreamBuilder
                .newBuilder(R.class.getName())
                .addPrimitiveField("x", int.class, 5)
                .build();

            var deser1 = deserialize(OOSBytes);
            assertEquals(deser1, r);
            var deser2 = deserialize(builderBytes);
            assertEquals(deser2, deser1);
        }
        {
            record R(int x, int y) implements Serializable {}

            var r = new R(7, 8);
            byte[] OOSBytes = serialize(r);
            var deser1 = deserialize(OOSBytes);
            assertEquals(deser1, r);

            byte[] builderBytes = SerialByteStreamBuilder
                .newBuilder(R.class.getName())
                .addPrimitiveField("x", int.class, 7)
                .addPrimitiveField("y", int.class, 8)
                .build();

            var deser2 = deserialize(builderBytes);
            assertEquals(deser2, deser1);

            builderBytes = SerialByteStreamBuilder
                .newBuilder(R.class.getName())
                .addPrimitiveField("y", int.class, 8)  // reverse order
                .addPrimitiveField("x", int.class, 7)
                .build();
            deser2 = deserialize(builderBytes);
            assertEquals(deser2, deser1);

            builderBytes = SerialByteStreamBuilder
                .newBuilder(R.class.getName())
                .addPrimitiveField("w", int.class, 6) // additional fields
                .addPrimitiveField("x", int.class, 7)
                .addPrimitiveField("y", int.class, 8)
                .addPrimitiveField("z", int.class, 9) // additional fields
                .build();
            deser2 = deserialize(builderBytes);
            assertEquals(deser2, deser1);

            r = new R(0, 0);
            OOSBytes = serialize(r);
            deser1 = deserialize(OOSBytes);
            assertEquals(deser1, r);

            builderBytes = SerialByteStreamBuilder
                .newBuilder(R.class.getName())
                .addPrimitiveField("y", int.class, 0)
                .addPrimitiveField("x", int.class, 0)
                .build();
            deser2 = deserialize(builderBytes);
            assertEquals(deser2, deser1);

            builderBytes = SerialByteStreamBuilder
                .newBuilder(R.class.getName())  // no field values
                .build();
            deser2 = deserialize(builderBytes);
            assertEquals(deser2, deser1);
        }
    }

    @Test
    public void testString() throws Exception {
        out.println("\n---");

        record Str(String part1, String part2) implements Serializable {}

        var r = new Str("Hello", "World!");
        var deser1 = deserialize(serialize(r));
        assertEquals(deser1, r);

        byte[] builderBytes = SerialByteStreamBuilder
            .newBuilder(Str.class.getName())
            .addField("part1", String.class, "Hello")
            .addField("part2", String.class, "World!")
            .build();

        var deser2 = deserialize(builderBytes);
        assertEquals(deser2, deser1);

        builderBytes = SerialByteStreamBuilder
            .newBuilder(Str.class.getName())
            .addField("cruft", String.class, "gg")
            .addField("part1", String.class, "Hello")
            .addField("part2", String.class, "World!")
            .addPrimitiveField("x", int.class, 13)
            .build();

        var deser3 = deserialize(builderBytes);
        assertEquals(deser3, deser1);
    }

    @Test
    public void testArrays() throws Exception {
        out.println("\n---");
        {
            record IntArray(int[]ints, long[]longs) implements Serializable {}
            IntArray r = new IntArray(new int[]{5, 4, 3, 2, 1}, new long[]{9L});
            IntArray deser1 = deserialize(serialize(r));
            assertEquals(deser1.ints(), r.ints());
            assertEquals(deser1.longs(), r.longs());

            byte[] builderBytes = SerialByteStreamBuilder
                .newBuilder(IntArray.class.getName())
                .addField("ints", int[].class, new int[]{5, 4, 3, 2, 1})
                .addField("longs", long[].class, new long[]{9L})
                .build();

            IntArray deser2 = deserialize(builderBytes);
            assertEquals(deser2.ints(), deser1.ints());
            assertEquals(deser2.longs(), deser1.longs());
        }
        {
            record StrArray(String[]stringArray) implements Serializable {}
            StrArray r = new StrArray(new String[]{"foo", "bar"});
            StrArray deser1 = deserialize(serialize(r));
            assertEquals(deser1.stringArray(), r.stringArray());

            byte[] builderBytes = SerialByteStreamBuilder
                .newBuilder(StrArray.class.getName())
                .addField("stringArray", String[].class, new String[]{"foo", "bar"})
                .build();

            StrArray deser2 = deserialize(builderBytes);
            assertEquals(deser2.stringArray(), deser1.stringArray());
        }
    }

    @Test
    public void testCompatibleFieldTypeChange() throws Exception {
        out.println("\n---");

        {
            record NumberHolder(Number n) implements Serializable {}

            var r = new NumberHolder(123);
            var deser1 = deserialize(serialize(r));
            assertEquals(deser1, r);

            byte[] builderBytes = SerialByteStreamBuilder
                .newBuilder(NumberHolder.class.getName())
                .addField("n", Integer.class, 123)
                .build();

            var deser2 = deserialize(builderBytes);
            assertEquals(deser2, deser1);
        }

        {
            record IntegerHolder(Integer i) implements Serializable {}

            var r = new IntegerHolder(123);
            var deser1 = deserialize(serialize(r));
            assertEquals(deser1, r);

            byte[] builderBytes = SerialByteStreamBuilder
                .newBuilder(IntegerHolder.class.getName())
                .addField("i", Number.class, 123)
                .build();

            var deser2 = deserialize(builderBytes);
            assertEquals(deser2, deser1);
        }
    }

    @Test
    public void testIncompatibleRefFieldTypeChange() throws Exception {
        out.println("\n---");

        record StringHolder(String s) implements Serializable {}

        var r = new StringHolder("123");
        var deser1 = deserialize(serialize(r));
        assertEquals(deser1, r);

        byte[] builderBytes = SerialByteStreamBuilder
            .newBuilder(StringHolder.class.getName())
            .addField("s", Integer.class, 123)
            .build();

        try {
            var deser2 = deserialize(builderBytes);
            throw new AssertionError(
                "Unexpected success of deserialization. Deserialized value: " + deser2);
        } catch (InvalidObjectException e) {
            // expected
        }
    }

    @Test
    public void testIncompatiblePrimitiveFieldTypeChange() throws Exception {
        out.println("\n---");

        record IntHolder(int i) implements Serializable {}

        var r = new IntHolder(123);
        var deser1 = deserialize(serialize(r));
        assertEquals(deser1, r);

        byte[] builderBytes = SerialByteStreamBuilder
            .newBuilder(IntHolder.class.getName())
            .addPrimitiveField("i", long.class, 123L)
            .build();

        try {
            var deser2 = deserialize(builderBytes);
            throw new AssertionError(
                "Unexpected success of deserialization. Deserialized value: " + deser2);
        } catch (InvalidClassException e) {
            // expected
        }
    }

    <T> byte[] serialize(T obj) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(obj);
        oos.close();
        return baos.toByteArray();
    }

    @SuppressWarnings("unchecked")
    static <T> T deserialize(byte[] streamBytes)
    throws IOException, ClassNotFoundException {
        ByteArrayInputStream bais = new ByteArrayInputStream(streamBytes);
        ObjectInputStream ois = new ObjectInputStream(bais);
        return (T) ois.readObject();
    }

    static class SerialByteStreamBuilder {

        private final ObjectOutputStream objectOutputStream;
        private final ByteArrayOutputStream byteArrayOutputStream;

        record NameAndType<T>(String name, Class<T>type) {}

        private String className;
        private final LinkedHashMap<NameAndType<?>, Object> primFields = new LinkedHashMap<>();
        private final LinkedHashMap<NameAndType<?>, Object> objectFields = new LinkedHashMap<>();

        private SerialByteStreamBuilder() {
            try {
                byteArrayOutputStream = new ByteArrayOutputStream();
                objectOutputStream = new ObjectOutputStream(byteArrayOutputStream);
            } catch (IOException e) {
                throw new UncheckedIOException(e);
            }
        }

        public static SerialByteStreamBuilder newBuilder(String className) {
            return (new SerialByteStreamBuilder()).className(className);
        }

        private SerialByteStreamBuilder className(String className) {
            this.className = className;
            return this;
        }

        public <T> SerialByteStreamBuilder addPrimitiveField(String name, Class<T> type, T value) {
            if (!type.isPrimitive())
                throw new IllegalArgumentException("Unexpected non-primitive field: " + type);
            primFields.put(new NameAndType<>(name, type), value);
            return this;
        }

        public <T> SerialByteStreamBuilder addField(String name, Class<T> type, T value) {
            if (type.isPrimitive())
                throw new IllegalArgumentException("Unexpected primitive field: " + type);
            objectFields.put(new NameAndType<>(name, type), value);
            return this;
        }

        private static int getPrimitiveSignature(Class<?> cl) {
            if (cl == Integer.TYPE) return 'I';
            else if (cl == Byte.TYPE) return 'B';
            else if (cl == Long.TYPE) return 'J';
            else if (cl == Float.TYPE) return 'F';
            else if (cl == Double.TYPE) return 'D';
            else if (cl == Short.TYPE) return 'S';
            else if (cl == Character.TYPE) return 'C';
            else if (cl == Boolean.TYPE) return 'Z';
            else throw new InternalError();
        }

        private static void writeUTF(DataOutputStream out, String str) throws IOException {
            int utflen = str.length(); // assume ASCII
            assert utflen <= 0xFFFF;
            out.writeShort(utflen);
            out.writeBytes(str);
        }

        private void writePrimFieldsDesc(DataOutputStream out) throws IOException {
            for (Map.Entry<NameAndType<?>, Object> entry : primFields.entrySet()) {
                assert entry.getKey().type() != void.class;
                out.writeByte(getPrimitiveSignature(entry.getKey().type())); // prim_typecode
                out.writeUTF(entry.getKey().name());                         // fieldName
            }
        }

        private void writePrimFieldsValues(DataOutputStream out) throws IOException {
            for (Map.Entry<NameAndType<?>, Object> entry : primFields.entrySet()) {
                Class<?> cl = entry.getKey().type();
                Object value = entry.getValue();
                if (cl == Integer.TYPE) out.writeInt((int) value);
                else if (cl == Byte.TYPE) out.writeByte((byte) value);
                else if (cl == Long.TYPE) out.writeLong((long) value);
                else if (cl == Float.TYPE) out.writeFloat((float) value);
                else if (cl == Double.TYPE) out.writeDouble((double) value);
                else if (cl == Short.TYPE) out.writeShort((short) value);
                else if (cl == Character.TYPE) out.writeChar((char) value);
                else if (cl == Boolean.TYPE) out.writeBoolean((boolean) value);
                else throw new InternalError();
            }
        }

        private void writeObjectFieldDesc(DataOutputStream out) throws IOException {
            for (Map.Entry<NameAndType<?>, Object> entry : objectFields.entrySet()) {
                Class<?> cl = entry.getKey().type();
                assert !cl.isPrimitive();
                // obj_typecode
                if (cl.isArray()) {
                    out.writeByte('[');
                } else {
                    out.writeByte('L');
                }
                writeUTF(out, entry.getKey().name());
                out.writeByte(TC_STRING);
                writeUTF(out,
                         (cl.isArray() ? cl.getName() : "L" + cl.getName() + ";")
                             .replace('.', '/'));
            }
        }

        private void writeObject(DataOutputStream out, Object value) throws IOException {
            objectOutputStream.reset();
            byteArrayOutputStream.reset();
            objectOutputStream.writeUnshared(value);
            out.write(byteArrayOutputStream.toByteArray());
        }

        private void writeObjectFieldValues(DataOutputStream out) throws IOException {
            for (Map.Entry<NameAndType<?>, Object> entry : objectFields.entrySet()) {
                Class<?> cl = entry.getKey().type();
                assert !cl.isPrimitive();
                if (cl == String.class) {
                    out.writeByte(TC_STRING);
                    writeUTF(out, (String) entry.getValue());
                } else {
                    writeObject(out, entry.getValue());
                }
            }
        }

        private int numFields() {
            return primFields.size() + objectFields.size();
        }

        public byte[] build() {
            try {
                ByteArrayOutputStream baos = new ByteArrayOutputStream();
                DataOutputStream dos = new DataOutputStream(baos);
                dos.writeShort(STREAM_MAGIC);
                dos.writeShort(STREAM_VERSION);
                dos.writeByte(TC_OBJECT);
                dos.writeByte(TC_CLASSDESC);
                dos.writeUTF(className);
                dos.writeLong(0L);
                dos.writeByte(SC_SERIALIZABLE);
                dos.writeShort(numFields());      // number of fields
                writePrimFieldsDesc(dos);
                writeObjectFieldDesc(dos);
                dos.writeByte(TC_ENDBLOCKDATA);   // no annotations
                dos.writeByte(TC_NULL);           // no superclasses
                writePrimFieldsValues(dos);
                writeObjectFieldValues(dos);
                dos.close();
                return baos.toByteArray();
            } catch (IOException unexpected) {
                throw new AssertionError(unexpected);
            }
        }
    }
}
