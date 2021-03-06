/*
 * Copyright (c) 2005, 2016, Oracle and/or its affiliates. All rights reserved.
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

package jdk.test.lib.jittester;

import jdk.test.lib.jittester.visitors.Visitor;

public class TernaryOperator extends Operator {
    public enum TernaryPart {
        CONDITION,
        TRUE,
        FALSE,
    }
    //protected Production conditionalExpression, leftExpression, rightExpression;

    public TernaryOperator(IRNode condition, IRNode trueBranch, IRNode falseBranch) {
        super(null, 2, trueBranch.getResultType());
        resizeUpChildren(TernaryPart.values().length);
        setChild(TernaryPart.CONDITION.ordinal(), condition);
        setChild(TernaryPart.TRUE.ordinal(), trueBranch);
        setChild(TernaryPart.FALSE.ordinal(), falseBranch);
    }

    @Override
    public long complexity() {
        IRNode conditionalExp = getChild(TernaryPart.CONDITION.ordinal());
        IRNode trueBranch = getChild(TernaryPart.TRUE.ordinal());
        IRNode falseBranch = getChild(TernaryPart.FALSE.ordinal());
        if (conditionalExp != null && trueBranch != null && falseBranch != null) {
            return conditionalExp.complexity() + trueBranch.complexity() + falseBranch.complexity() + 1;
        } else {
            return 0;
        }
    }

    @Override
    public<T> T accept(Visitor<T> v) {
        return v.visit(this);
    }
}
