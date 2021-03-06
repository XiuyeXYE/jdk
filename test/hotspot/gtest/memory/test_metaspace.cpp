/*
 * Copyright (c) 2018, 2019, Oracle and/or its affiliates. All rights reserved.
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

#include "precompiled.hpp"
#include "memory/metaspace.hpp"
#include "memory/metaspace/virtualSpaceList.hpp"
#include "runtime/mutexLocker.hpp"
#include "runtime/os.hpp"
#include "unittest.hpp"

using namespace metaspace;

TEST_VM(MetaspaceUtils, reserved) {
  size_t reserved = MetaspaceUtils::reserved_bytes();
  EXPECT_GT(reserved, 0UL);

  size_t reserved_metadata = MetaspaceUtils::reserved_bytes(Metaspace::NonClassType);
  EXPECT_GT(reserved_metadata, 0UL);
  EXPECT_LE(reserved_metadata, reserved);
}

TEST_VM(MetaspaceUtils, reserved_compressed_class_pointers) {
  if (!UseCompressedClassPointers) {
    return;
  }
  size_t reserved = MetaspaceUtils::reserved_bytes();
  EXPECT_GT(reserved, 0UL);

  size_t reserved_class = MetaspaceUtils::reserved_bytes(Metaspace::ClassType);
  EXPECT_GT(reserved_class, 0UL);
  EXPECT_LE(reserved_class, reserved);
}

TEST_VM(MetaspaceUtils, committed) {
  size_t committed = MetaspaceUtils::committed_bytes();
  EXPECT_GT(committed, 0UL);

  size_t reserved  = MetaspaceUtils::reserved_bytes();
  EXPECT_LE(committed, reserved);

  size_t committed_metadata = MetaspaceUtils::committed_bytes(Metaspace::NonClassType);
  EXPECT_GT(committed_metadata, 0UL);
  EXPECT_LE(committed_metadata, committed);
}

TEST_VM(MetaspaceUtils, committed_compressed_class_pointers) {
  if (!UseCompressedClassPointers) {
    return;
  }
  size_t committed = MetaspaceUtils::committed_bytes();
  EXPECT_GT(committed, 0UL);

  size_t committed_class = MetaspaceUtils::committed_bytes(Metaspace::ClassType);
  EXPECT_GT(committed_class, 0UL);
  EXPECT_LE(committed_class, committed);
}

TEST_VM(MetaspaceUtils, virtual_space_list_large_chunk) {
  VirtualSpaceList* vs_list = new VirtualSpaceList(os::vm_allocation_granularity());
  MutexLocker cl(MetaspaceExpand_lock, Mutex::_no_safepoint_check_flag);
  // A size larger than VirtualSpaceSize (256k) and add one page to make it _not_ be
  // vm_allocation_granularity aligned on Windows.
  size_t large_size = (size_t)(2*256*K + (os::vm_page_size() / BytesPerWord));
  large_size += (os::vm_page_size() / BytesPerWord);
  vs_list->get_new_chunk(large_size, 0);
}
