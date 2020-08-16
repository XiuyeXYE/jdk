/*
 * Copyright (c) 1998, 2018, Oracle and/or its affiliates. All rights reserved.
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

import javax.naming.Context;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;

/*
 * @test
 * @bug 8210339
 * @summary Test that we can lookup the nns of a DNS entry.
 * @library ../lib/ ../FactoryTests/
 * @modules java.naming/com.sun.jndi.toolkit.dir
 *          java.base/sun.security.util
 * @build FedSubordinateNs FedObjectFactory
 * @run main/othervm LookupNns
 */

public class LookupNns extends LookupFactoryBase {

    public static void main(String[] args) throws Exception {
        new LookupNns().run(args);
    }

    /*
     * Test that we can lookup the nns of a DNS entry.
     */
    @Override
    public void runTest() throws Exception {
        // initial context with object factory
        env().put(Context.OBJECT_FACTORIES, "FedObjectFactory");
        setContext(new InitialDirContext(env()));

        Object nns = context().lookup(getKey() + "/");
        verifyLookupObject(nns, DirContext.class);
    }
}
