/*
 * Copyright (c) 2007, 2018, Oracle and/or its affiliates. All rights reserved.
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
#include <jni.h>
#include <time.h>
#include <stdlib.h>
#include "jni_tools.h"

extern "C" {

/*
 * Class:     nsk_share_gc_lock_malloc_MallocLocker
 * Method:    mallocSection
 * Signature: (JJ)V
 */
JNIEXPORT void JNICALL Java_nsk_share_gc_lock_malloc_MallocLocker_mallocSection
(JNIEnv *env, jobject o, jlong enterTime, jlong sleepTime) {
        void *ptr;
        time_t current_time, old_time;
        old_time = time(NULL);
        enterTime /= 1000;
        current_time = 0;
        while (current_time - old_time < enterTime) {
                ptr = malloc(1);
                mssleep((long) sleepTime);
                free(ptr);
                mssleep((long) sleepTime);
                current_time = time(NULL);
        }
}

}
