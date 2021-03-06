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

/*
 * A Simple TransportService used by
 * nsk/jdi/PlugConnectors/TransportService/transportService003 test
 */

package nsk.jdi.PlugConnectors.TransportService.transportService003.connectors;

import nsk.share.jdi.*;
import com.sun.jdi.*;
import com.sun.jdi.connect.*;
import com.sun.jdi.connect.spi.*;
import java.util.*;

public class PlugTransportService003 extends PlugTransportService {

    static String plugTransportServiceName = "PlugTransportService003_Name";
    static String plugTransportServiceDescription = "PlugTransportService003_Description";
    static TransportService.Capabilities plugTransportServiceCapabilities =
        new TestCapabilities(
            true,  // supportsAcceptTimeout
            true,  // supportsAttachTimeout
            true,  // supportsHandshakeTimeout
            true   // supportsMultipleConnections
            );

    public PlugTransportService003() {

        super(
            plugTransportServiceName,
            plugTransportServiceDescription,
            plugTransportServiceCapabilities
            );

        String exceptionMessage =
            "<## PlugTransportService003: This RuntimeException is thrown intentionally by TransportService "
            + "constructor to check creating of pluggable connectors on base of such TransportService. ##>";

        throw new RuntimeException(exceptionMessage);

    }

} // end of PlugTransportService003 class
