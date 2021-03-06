/*
 * Copyright (c) 1997, 2019, Oracle and/or its affiliates. All rights reserved.
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
 *
 */

#include "precompiled.hpp"
#include "gc/shared/collectedHeap.hpp"
#include "memory/universe.hpp"
#include "runtime/safepoint.hpp"
#include "runtime/safepointVerifiers.hpp"
#include "utilities/debug.hpp"

#ifdef ASSERT

NoSafepointVerifier::NoSafepointVerifier() : _thread(Thread::current()) {
  _thread->_no_safepoint_count++;
}

NoSafepointVerifier::~NoSafepointVerifier() {
  _thread->_no_safepoint_count--;
}

PauseNoSafepointVerifier::PauseNoSafepointVerifier(NoSafepointVerifier* nsv)
    : _nsv(nsv) {
  assert(_nsv->_thread == Thread::current(), "must be");
  _nsv->_thread->_no_safepoint_count--;
}

PauseNoSafepointVerifier::~PauseNoSafepointVerifier() {
  _nsv->_thread->_no_safepoint_count++;
}
#endif // ASSERT
