/*
 * Copyright (c) 2005, 2019, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 *
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
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

package com.sun.source.tree;

/**
 * A tree node for an {@code instanceof} expression.
 *
 * For example:
 * <pre>
 *   <em>expression</em> instanceof <em>type</em>
 * </pre>
 *
 * @jls 15.20.2 Type Comparison Operator instanceof
 *
 * @author Peter von der Ah&eacute;
 * @author Jonathan Gibbons
 * @since 1.6
 */
public interface InstanceOfTree extends ExpressionTree {
    /**
     * Returns the expression to be tested.
     * @return the expression
     */
    ExpressionTree getExpression();

    /**
     * Returns the type for which to check.
     * @return the type
     */
    Tree getType();

    /**
     * {@preview Associated with pattern matching for instanceof, a preview feature of
     *           the Java language.
     *
     *           This method is associated with <i>pattern matching for instanceof</i>, a preview
     *           feature of the Java language. Preview features
     *           may be removed in a future release, or upgraded to permanent
     *           features of the Java language.}
     *
     * Returns the tested pattern, or null if this instanceof does not use
     * a pattern.
     *
     * <p>For instanceof with a pattern, i.e. in the following form:
     * <pre>
     *   <em>expression</em> instanceof <em>type</em> <em>variable name</em>
     * </pre>
     * returns the pattern.
     *
     * <p>For instanceof without a pattern, i.e. in the following form:
     * <pre>
     *   <em>expression</em> instanceof <em>type</em>
     * </pre>
     * returns null.
     *
     * @return the tested pattern, or null if this instanceof does not use a pattern.
     * @since 14
     */
    PatternTree getPattern();
}
