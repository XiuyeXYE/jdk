/*
 * Copyright (c) 2015, Oracle and/or its affiliates. All rights reserved.
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

package jdk.test.lib.jittester.factories;

import java.util.List;

import jdk.test.lib.jittester.IRNode;
import jdk.test.lib.jittester.LiteralInitializer;
import jdk.test.lib.jittester.ProductionFailedException;
import jdk.test.lib.jittester.SymbolTable;
import jdk.test.lib.jittester.Type;
import jdk.test.lib.jittester.TypeList;
import jdk.test.lib.jittester.VariableInfo;
import jdk.test.lib.jittester.utils.TypeUtil;
import jdk.test.lib.jittester.loops.CounterInitializer;
import jdk.test.lib.jittester.types.TypeKlass;
import jdk.test.lib.jittester.utils.PseudoRandom;

class CounterInitializerFactory extends SafeFactory<CounterInitializer> {
    private final int counterValue;
    private final TypeKlass ownerClass;

    CounterInitializerFactory(TypeKlass ownerClass, int counterValue) {
        this.ownerClass = ownerClass;
        this.counterValue = counterValue;
    }

    @Override
    protected CounterInitializer sproduce() throws ProductionFailedException {
        List<Type> types = TypeUtil.getMoreCapaciousThan(TypeList.getBuiltIn(), TypeList.INT);
        types.add(TypeList.INT);
        final Type selectedType = PseudoRandom.randomElement(types);
        IRNode init = new LiteralInitializer(counterValue, selectedType);
        String resultName = "var_" + SymbolTable.getNextVariableNumber();
        VariableInfo varInfo = new VariableInfo(resultName, ownerClass, selectedType,
                VariableInfo.FINAL | VariableInfo.LOCAL | VariableInfo.INITIALIZED);
        SymbolTable.add(varInfo);
        return new CounterInitializer(varInfo, init);
    }
}
