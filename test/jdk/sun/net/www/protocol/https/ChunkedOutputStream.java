/*
 * Copyright (c) 2004, 2019, Oracle and/or its affiliates. All rights reserved.
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

/**
 * @test
 * @bug 5026745
 * @modules java.base/sun.net.www
 * @build TestHttpsServer HttpCallback
 * @run main/othervm ChunkedOutputStream
 * @run main/othervm -Djava.net.preferIPv6Addresses=true ChunkedOutputStream
 *
 *     SunJSSE does not support dynamic system properties, no way to re-use
 *     system properties in samevm/agentvm mode.
 * @summary Cannot flush output stream when writing to an HttpUrlConnection
 */

import java.io.*;
import java.net.*;
import javax.net.ssl.*;
import java.util.concurrent.atomic.AtomicInteger;

public class ChunkedOutputStream implements HttpCallback {
    /*
     * Where do we find the keystores for ssl?
     */
    static String pathToStores = "../../../../../javax/net/ssl/etc";
    static String keyStoreFile = "keystore";
    static String trustStoreFile = "truststore";
    static String passwd = "passphrase";
    static int count = 0;
    static final AtomicInteger rogueCount = new AtomicInteger();

    static final String str1 = "Helloworld1234567890abcdefghijklmnopqrstuvwxyz"+
                                "1234567890abcdefkjsdlkjflkjsldkfjlsdkjflkj"+
                                "1434567890abcdefkjsdlkjflkjsldkfjlsdkjflkj";

    static final String str2 = "Helloworld1234567890abcdefghijklmnopqrstuvwxyz"+
                                "1234567890";

    public void request(HttpTransaction req) {
        try {
            // this is needed (count++ doesn't work), 'cause we
            // are doing concurrent tests
            String path = req.getRequestURI().getPath();
            if (path.equals("/d0")) {
                count = 0;
            } else if (path.equals("/d01")) {
                count = 1;
            } else if (path.equals("/d3")) {
                count = 2;
            } else if (path.equals("/d4") || path.equals("/d5")) {
                count = 3;
            } else if (path.equals("/d6")) {
                count = 3;
            }  else if (path.equals("/d7")) {
                count = 4;
            }  else if (path.equals("/d8")) {
                count = 5;
            }

            switch (count) {
            case 0: /* test1 -- keeps conn alive */
            case 1: /* test2 -- closes conn */
                String reqbody = req.getRequestEntityBody();
                if (!reqbody.equals(str1)) {
                    req.sendResponse(500, "Internal server error");
                    req.orderlyClose();
                }
                String chunk = req.getRequestHeader("Transfer-encoding");
                if (!"chunked".equals(chunk)) {
                    req.sendResponse(501, "Internal server error");
                    req.orderlyClose();
                }
                req.setResponseEntityBody(reqbody);
                if (count == 1) {
                    req.setResponseHeader("Connection", "close");
                }
                req.sendResponse(200, "OK");
                if (count == 1) {
                    req.orderlyClose();
                }
                break;
            case 2: /* test 3 */
                reqbody = req.getRequestEntityBody();
                if (!reqbody.equals(str2)) {
                    req.sendResponse(500, "Internal server error");
                    req.orderlyClose();
                }
                int clen = Integer.parseInt (
                        req.getRequestHeader("Content-length"));
                if (clen != str2.length()) {
                    req.sendResponse(501, "Internal server error");
                    req.orderlyClose();
                }
                req.setResponseEntityBody (reqbody);
                req.setResponseHeader("Connection", "close");
                req.sendResponse(200, "OK");
                req.orderlyClose();
                break;
            case 3: /* test 6 */
                req.setResponseHeader("Location", "https://foo.bar/");
                req.setResponseHeader("Connection", "close");
                req.sendResponse(307, "Temporary Redirect");
                req.orderlyClose();
                break;
            case 4: /* test 7 */
            case 5: /* test 8 */
                reqbody = req.getRequestEntityBody();
                if (reqbody != null && !"".equals(reqbody)) {
                    req.sendResponse(501, "Internal server error");
                    req.orderlyClose();
                }
                req.setResponseHeader("Connection", "close");
                req.sendResponse(200, "OK");
                req.orderlyClose();
                break;
            default:
                req.sendResponse(404, "Not Found");
                req.orderlyClose();
                break;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean dropPlainTextConnections() {
        System.out.println("Unrecognized SSL message, plaintext connection?");
        System.out.println("TestHttpsServer receveived rogue connection: ignoring it.");
        rogueCount.incrementAndGet();
        return true;
    }

    static void readAndCompare(InputStream is, String cmp) throws IOException {
        int c;
        byte buf[] = new byte[1024];
        int off = 0;
        int len = 1024;
        while ((c=is.read(buf, off, len)) != -1) {
            off += c;
            len -= c;
        }
        String s1 = new String(buf, 0, off, "ISO8859_1");
        if (!cmp.equals(s1)) {
            throw new IOException("strings not same");
        }
    }

    /* basic smoke test: verify that server drops plain connections */
    static void testPlainText(String authority) throws Exception {
        URL url = new URL("http://" + authority + "/Donauschiffsgesellschaftskapitaenskajuete");
        System.out.println("client opening connection to: " + url);
        HttpURLConnection urlc = (HttpURLConnection)url.openConnection(Proxy.NO_PROXY);
        int rogue = rogueCount.get();
        try {
            int code  = urlc.getResponseCode();
            System.out.println("Unexpected response: " + code);
            throw new AssertionError("Unexpected response: " + code);
        } catch (SocketException x) {
            // we expect that the server will drop the connection and
            // close the accepted socket, so we should get a SocketException
            // on the client side, and confirm that this::dropPlainTextConnections
            // has ben called.
            if (rogueCount.get() == rogue) throw x;
            System.out.println("Got expected exception: " + x);
        }
    }

    /* basic chunked test (runs twice) */

    static void test1(String u) throws Exception {
        URL url = new URL(u);
        System.out.println("client opening connection to: " + u);
        HttpURLConnection urlc = (HttpURLConnection)url.openConnection(Proxy.NO_PROXY);
        urlc.setChunkedStreamingMode(20);
        urlc.setDoOutput(true);
        urlc.setRequestMethod("POST");
        OutputStream os = urlc.getOutputStream();
        os.write(str1.getBytes());
        os.close();
        InputStream is = urlc.getInputStream();
        readAndCompare(is, str1);
        is.close();
    }

    /* basic fixed length test */

    static void test3(String u) throws Exception {
        URL url = new URL(u);
        System.out.println("client opening connection to: " + u);
        HttpURLConnection urlc = (HttpURLConnection)url.openConnection(Proxy.NO_PROXY);
        urlc.setFixedLengthStreamingMode(str2.length());
        urlc.setDoOutput(true);
        urlc.setRequestMethod("POST");
        OutputStream os = urlc.getOutputStream();
        os.write (str2.getBytes());
        os.close();
        InputStream is = urlc.getInputStream();
        readAndCompare(is, str2);
        is.close();
    }

    /* write too few bytes */

    static void test4(String u) throws Exception {
        URL url = new URL(u);
        System.out.println("client opening connection to: " + u);
        HttpURLConnection urlc = (HttpURLConnection)url.openConnection(Proxy.NO_PROXY);
        urlc.setFixedLengthStreamingMode(str2.length()+1);
        urlc.setDoOutput(true);
        urlc.setRequestMethod("POST");
        OutputStream os = urlc.getOutputStream();
        os.write(str2.getBytes());
        try {
            os.close();
            throw new Exception("should have thrown IOException");
        } catch (IOException e) {}
    }

    /* write too many bytes */

    static void test5(String u) throws Exception {
        URL url = new URL(u);
        System.out.println("client opening connection to: " + u);
        HttpURLConnection urlc = (HttpURLConnection)url.openConnection(Proxy.NO_PROXY);
        urlc.setFixedLengthStreamingMode(str2.length()-1);
        urlc.setDoOutput(true);
        urlc.setRequestMethod("POST");
        OutputStream os = urlc.getOutputStream();
        try {
            os.write(str2.getBytes());
            throw new Exception("should have thrown IOException");
        } catch (IOException e) {}
    }

    /* check for HttpRetryException on redirection */

    static void test6(String u) throws Exception {
        URL url = new URL(u);
        System.out.println("client opening connection to: " + u);
        HttpURLConnection urlc = (HttpURLConnection)url.openConnection(Proxy.NO_PROXY);
        urlc.setChunkedStreamingMode(20);
        urlc.setDoOutput(true);
        urlc.setRequestMethod("POST");
        OutputStream os = urlc.getOutputStream();
        os.write(str1.getBytes());
        os.close();
        try {
            InputStream is = urlc.getInputStream();
            throw new Exception("should have gotten HttpRetryException");
        } catch (HttpRetryException e) {
            if (e.responseCode() != 307) {
                throw new Exception("Wrong response code " + e.responseCode());
            }
            if (!e.getLocation().equals("https://foo.bar/")) {
                throw new Exception("Wrong location " + e.getLocation());
            }
        }
    }

    /* next two tests send zero length posts */

    static void test7(String u) throws Exception {
        URL url = new URL(u);
        System.out.println("client opening connection to: " + u);
        HttpURLConnection urlc = (HttpURLConnection)url.openConnection(Proxy.NO_PROXY);
        urlc.setChunkedStreamingMode(20);
        urlc.setDoOutput(true);
        urlc.setRequestMethod("POST");
        OutputStream os = urlc.getOutputStream();
        os.close();
        int ret = urlc.getResponseCode();
        if (ret != 200) {
            throw new Exception("Expected 200: got " + ret);
        }
    }

    static void test8(String u) throws Exception {
        URL url = new URL(u);
        System.out.println("client opening connection to: " + u);
        HttpURLConnection urlc = (HttpURLConnection)url.openConnection(Proxy.NO_PROXY);
        urlc.setFixedLengthStreamingMode(0);
        urlc.setDoOutput(true);
        urlc.setRequestMethod("POST");
        OutputStream os = urlc.getOutputStream();
        os.close();
        int ret = urlc.getResponseCode();
        if (ret != 200) {
            throw new Exception("Expected 200: got " + ret);
        }
    }

    static TestHttpsServer server;

    public static void main(String[] args) throws Exception {
        // setup properties to do ssl
        String keyFilename =
            System.getProperty("test.src", "./") + "/" + pathToStores +
                "/" + keyStoreFile;
        String trustFilename =
            System.getProperty("test.src", "./") + "/" + pathToStores +
                "/" + trustStoreFile;

        InetAddress loopback = InetAddress.getLoopbackAddress();

        HostnameVerifier reservedHV =
            HttpsURLConnection.getDefaultHostnameVerifier();
        try {
            System.setProperty("javax.net.ssl.keyStore", keyFilename);
            System.setProperty("javax.net.ssl.keyStorePassword", passwd);
            System.setProperty("javax.net.ssl.trustStore", trustFilename);
            System.setProperty("javax.net.ssl.trustStorePassword", passwd);
            HttpsURLConnection.setDefaultHostnameVerifier(new NameVerifier());

            try {
                server = new TestHttpsServer(
                        new ChunkedOutputStream(), 1, 10, loopback, 0);
                System.out.println("Server started: listening on: " + server.getAuthority());
                testPlainText(server.getAuthority());
                // the test server doesn't support keep-alive yet
                // test1("http://" + server.getAuthority() + "/d0");
                test1("https://" + server.getAuthority() + "/d01");
                test3("https://" + server.getAuthority() + "/d3");
                test4("https://" + server.getAuthority() + "/d4");
                test5("https://" + server.getAuthority() + "/d5");
                test6("https://" + server.getAuthority() + "/d6");
                test7("https://" + server.getAuthority() + "/d7");
                test8("https://" + server.getAuthority() + "/d8");
            } catch (Exception e) {
                if (server != null) {
                    server.terminate();
                }
                throw e;
            }
            server.terminate();
        } finally {
            HttpsURLConnection.setDefaultHostnameVerifier(reservedHV);
        }
    }

    static class NameVerifier implements HostnameVerifier {
        public boolean verify(String hostname, SSLSession session) {
            return true;
        }
    }

    public static void except(String s) {
        server.terminate();
        throw new RuntimeException(s);
    }
}
