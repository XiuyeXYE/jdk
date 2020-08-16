/*
 * Copyright (c) 2014, 2016, Oracle and/or its affiliates. All rights reserved.
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

package sax;

import javax.xml.datatype.DatatypeConfigurationException;

import org.testng.annotations.Listeners;
import org.testng.annotations.Test;
import org.testng.Assert;
import org.xml.sax.helpers.XMLReaderFactory;

/*
 * @test
 * @bug 6925410
 * @library /javax/xml/jaxp/libs /javax/xml/jaxp/unittest
 * @run testng/othervm -DrunSecMngr=true sax.Bug6925410Test
 * @run testng/othervm sax.Bug6925410Test
 * @summary Test XMLReaderFactory can createXMLReader repeatedly.
 */
@Listeners({jaxp.library.BasePolicy.class})
public class Bug6925410Test {

    @Test
    public void test() throws DatatypeConfigurationException {
        try {
            int times = 100;
            long start = System.currentTimeMillis();
            for (int i = 0; i < times; i++) {
                XMLReaderFactory.createXMLReader();
            }
            long end = System.currentTimeMillis();
            double speed = ((end - start));
            System.out.println(speed + "ms");
        } catch (Throwable e) {
            e.printStackTrace();
            Assert.fail(e.toString());
        }

    }

}
