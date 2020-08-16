/*
 * Copyright (c) 2011, Oracle and/or its affiliates. All rights reserved.
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

/**
 * @test
 * @bug 6896088
 * @run main/othervm B6896088
 * @summary URLClassLoader.close() apparently not working for JAR URLs on Windows
 */

import java.net.*;
import java.io.*;
import java.nio.file.Path;

public class B6896088 {

   public static void main(String[] args) throws Exception {

       String dir = System.getProperty ("test.classes");
       File jarf = new File (dir, "foo.jar");
       FileOutputStream fos = new FileOutputStream (jarf);
       fos.write(bytes(nums));
       fos.close();

       // Create URL using JAR protocol
       String jarName = (jarf.toURI()).toString();
       URL url = new URL("jar", "", jarName + "!/");

       // Create URLClassLoader from the URL
       URLClassLoader loader = new URLClassLoader(new URL[]{url});
       Class c = loader.loadClass("Foo");

       // Close the URLClassLoader so we can delete/update the jar file
       loader.close();

       // Now try to delete the jar file

       if (jarf.delete() && !jarf.exists()) {
           System.out.println(jarf.getName()+" File Deleted");
       } else {
           System.out.println(jarf.getName()+" File Not Deleted");
           throw new RuntimeException ("File not deleted");
       }
   }

   static byte[] bytes (int[] i) {
        byte[] buf = new byte [i.length];

        for (int j=0; j<i.length; j++) {
            buf[j] = (byte)i[j];
        }
        return buf;
   }

   /* contents of foo.jar */

   static final int nums[] = {
       0x50,0x4b,0x03,0x04,0x14,0x00,0x08,0x08,0x08,0x00,0xc8,0x61,
       0x90,0x3d,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
       0x00,0x00,0x09,0x00,0x04,0x00,0x4d,0x45,0x54,0x41,0x2d,0x49,
       0x4e,0x46,0x2f,0xfe,0xca,0x00,0x00,0x03,0x00,0x50,0x4b,0x07,
       0x08,0x00,0x00,0x00,0x00,0x02,0x00,0x00,0x00,0x00,0x00,0x00,
       0x00,0x50,0x4b,0x03,0x04,0x14,0x00,0x08,0x08,0x08,0x00,0xc8,
       0x61,0x90,0x3d,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
       0x00,0x00,0x00,0x14,0x00,0x00,0x00,0x4d,0x45,0x54,0x41,0x2d,
       0x49,0x4e,0x46,0x2f,0x4d,0x41,0x4e,0x49,0x46,0x45,0x53,0x54,
       0x2e,0x4d,0x46,0xf3,0x4d,0xcc,0xcb,0x4c,0x4b,0x2d,0x2e,0xd1,
       0x0d,0x4b,0x2d,0x2a,0xce,0xcc,0xcf,0xb3,0x52,0x30,0xd4,0x33,
       0xe0,0xe5,0x72,0x2e,0x4a,0x4d,0x2c,0x49,0x4d,0xd1,0x75,0xaa,
       0x04,0x09,0x98,0xeb,0x19,0xe8,0x66,0xe6,0x95,0xa4,0x16,0xe5,
       0x25,0xe6,0x28,0x68,0xf8,0x17,0x25,0x26,0xe7,0xa4,0x2a,0x38,
       0xe7,0x17,0x15,0xe4,0x17,0x25,0x96,0x00,0x35,0x69,0xf2,0x72,
       0xf1,0x72,0x01,0x00,0x50,0x4b,0x07,0x08,0x4c,0xd8,0x5e,0x68,
       0x49,0x00,0x00,0x00,0x4a,0x00,0x00,0x00,0x50,0x4b,0x03,0x04,
       0x14,0x00,0x08,0x08,0x08,0x00,0xbd,0x61,0x90,0x3d,0x00,0x00,
       0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x09,0x00,
       0x00,0x00,0x46,0x6f,0x6f,0x2e,0x63,0x6c,0x61,0x73,0x73,0x3b,
       0xf5,0x6f,0xd7,0x3e,0x06,0x06,0x06,0x63,0x06,0x5e,0x2e,0x06,
       0x66,0x06,0x2e,0x76,0x06,0x6e,0x76,0x06,0x1e,0x46,0x06,0x36,
       0x9b,0xcc,0xbc,0xcc,0x12,0x3b,0x46,0x06,0x66,0x0d,0xcd,0x30,
       0x46,0x06,0x16,0xe7,0xfc,0x94,0x54,0x46,0x06,0x7e,0x9f,0xcc,
       0xbc,0x54,0xbf,0xd2,0xdc,0xa4,0xd4,0xa2,0x90,0xc4,0xa4,0x1c,
       0xa0,0x08,0x57,0x70,0x7e,0x69,0x51,0x72,0xaa,0x5b,0x26,0x88,
       0xc3,0xe1,0x96,0x9f,0xaf,0x97,0x95,0x58,0x96,0xc8,0xc3,0xc0,
       0xc2,0xc0,0x0a,0xd4,0x0b,0xe4,0x33,0x32,0x08,0x80,0x44,0xf4,
       0x73,0x12,0xf3,0xd2,0xf5,0xfd,0x93,0xb2,0x52,0x93,0x4b,0x18,
       0x14,0x19,0x98,0x80,0x76,0x81,0x00,0x23,0x10,0x02,0x95,0x02,
       0x49,0x36,0x20,0x4f,0x16,0xcc,0x67,0x60,0x60,0xd5,0xda,0xce,
       0xc0,0xb8,0x11,0x2c,0xcd,0x0e,0x24,0xd9,0xc0,0x82,0x20,0x29,
       0x0e,0x20,0xcd,0xc4,0xc0,0x09,0x00,0x50,0x4b,0x07,0x08,0x3e,
       0x0a,0xdc,0x88,0x98,0x00,0x00,0x00,0xb4,0x00,0x00,0x00,0x50,
       0x4b,0x01,0x02,0x14,0x00,0x14,0x00,0x08,0x08,0x08,0x00,0xc8,
       0x61,0x90,0x3d,0x00,0x00,0x00,0x00,0x02,0x00,0x00,0x00,0x00,
       0x00,0x00,0x00,0x09,0x00,0x04,0x00,0x00,0x00,0x00,0x00,0x00,
       0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x4d,0x45,0x54,
       0x41,0x2d,0x49,0x4e,0x46,0x2f,0xfe,0xca,0x00,0x00,0x50,0x4b,
       0x01,0x02,0x14,0x00,0x14,0x00,0x08,0x08,0x08,0x00,0xc8,0x61,
       0x90,0x3d,0x4c,0xd8,0x5e,0x68,0x49,0x00,0x00,0x00,0x4a,0x00,
       0x00,0x00,0x14,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,
       0x00,0x00,0x00,0x00,0x3d,0x00,0x00,0x00,0x4d,0x45,0x54,0x41,
       0x2d,0x49,0x4e,0x46,0x2f,0x4d,0x41,0x4e,0x49,0x46,0x45,0x53,
       0x54,0x2e,0x4d,0x46,0x50,0x4b,0x01,0x02,0x14,0x00,0x14,0x00,
       0x08,0x08,0x08,0x00,0xbd,0x61,0x90,0x3d,0x3e,0x0a,0xdc,0x88,
       0x98,0x00,0x00,0x00,0xb4,0x00,0x00,0x00,0x09,0x00,0x00,0x00,
       0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0x00,0xc8,0x00,
       0x00,0x00,0x46,0x6f,0x6f,0x2e,0x63,0x6c,0x61,0x73,0x73,0x50,
       0x4b,0x05,0x06,0x00,0x00,0x00,0x00,0x03,0x00,0x03,0x00,0xb4,
       0x00,0x00,0x00,0x97,0x01,0x00,0x00,0x00,0x00,0x00
   };
}
