/*
 * Copyright (c) 2013, 2018, Oracle and/or its affiliates. All rights reserved.
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

package vm.runtime.defmeth.shared;

/**
 * Represents all supported methods of method invocation for testing purposes.
 * Test scenarios can be run in all of these modes (with some exceptions).
 */
public enum ExecutionMode {
    DIRECT,            // invoke methods using invoke* instructions on bytecode level
    REFLECTION,        // use Reflection API (java.lang.reflect) for method invocation
    REDEFINITION,      // use RedefineClasses
    INVOKE_EXACT,      // use MethodHandle.invokeExact(...)
    INVOKE_GENERIC,    // use MethodHandle.invoke(...)
    INVOKE_WITH_ARGS,  // use MethodHandle.invokeWithArguments(...)
    INDY;              // use invokedynamic instruction

    public boolean isReflectionBased() {
        return (this == REFLECTION || this == INVOKE_WITH_ARGS);
    }
}
