/*
 * Copyright (c) 1996, Oracle and/or its affiliates. All rights reserved.
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

/*
 * This file contains macro definitions for the Fetching category of
 * the macros used by the generic scaleloop function.
 *
 * This implementation can load 8-bit pixels from an array of bytes
 * where the data for pixel (srcX, srcY) is loaded from index
 * (srcOff + srcY * srcScan + srcX) in the array.
 */

#define DeclareInputVars                                        \
    pixptr srcP;

#define InitInput(srcBPP)                                               \
    img_check(srcBPP == 8)

#define SetInputRow(pixels, srcOff, srcScan, srcY, srcOY)               \
    srcP.vp = pixels;                                                   \
    srcP.bp += srcOff + ((srcY-srcOY) * srcScan)

#define GetPixelInc()                                                   \
    ((int) *srcP.bp++)

#define GetPixel(srcX)                                                  \
    ((int) srcP.bp[srcX])

#define InputPixelInc(X)                                                \
    srcP.bp += X

#define VerifyPixelRange(pixel, mapsize)                                \
    do {                                                                \
        img_check(((unsigned int) pixel) <= 255);                       \
        img_check(mapsize >= 256);                                      \
    } while (0)
