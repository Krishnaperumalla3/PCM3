/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * https://pragmaedge.com/licenseagreement
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.pe.pcm.si.pwd.security;


import com.pe.pcm.si.pwd.security.nis.PwdCrypto;

import java.util.HashMap;

public class PwdSecurityMode {

    public static final int SECURITY_MODE;
    private static String disabledAlgorithms;
    private static String SSLHelloProtocolForNISTStrict;
    private static String SSLHelloProtocolForNISTTransition;
    private static String SSLHelloProtocol;
    private static String SSLHelloProtocolForFIPS;
    private static String defaultSSLHelloProtocolForNISTStrict;
    private static String defaultSSLHelloProtocolForNISTTransition;
    private static String defaultSSLHelloProtocol;
    private static String defaultSSLHelloProtocolForFIPS;
    private static HashMap<String, String[]> protocolMap;


    static {
        PwdSecurityMode.disabledAlgorithms = "";
        PwdSecurityMode.SSLHelloProtocolForNISTStrict = "TLS1.2";
        PwdSecurityMode.SSLHelloProtocolForNISTTransition = "TLS1-TLS1.2";
        PwdSecurityMode.SSLHelloProtocol = "TLS1-TLS1.2";
        PwdSecurityMode.SSLHelloProtocolForFIPS = "TLS1";
        PwdSecurityMode.defaultSSLHelloProtocolForNISTStrict = "TLS1.2";
        PwdSecurityMode.defaultSSLHelloProtocolForNISTTransition = "TLS1-TLS1.2";
        PwdSecurityMode.defaultSSLHelloProtocol = "TLS1-TLS1.2";
        PwdSecurityMode.defaultSSLHelloProtocolForFIPS = "TLS1";
        PwdSecurityMode.protocolMap = new HashMap<String, String[]>();
        final String nistProp = "off";
        if (nistProp != null) {
            if ("strict".equalsIgnoreCase(nistProp)) {
                SECURITY_MODE = 3;
            } else if ("transition".equalsIgnoreCase(nistProp)) {
                SECURITY_MODE = 2;
            } else {
                SECURITY_MODE = 1;
            }
        } else {
            SECURITY_MODE = 1;
        }
        final String sslProtocolForStrict = "TLS1.2";
        if (sslProtocolForStrict == null || sslProtocolForStrict.trim().isEmpty()) {
            System.out.println("Missing SSLHelloProtocolForNISTStrict, set it to defaul value " + PwdSecurityMode.defaultSSLHelloProtocolForNISTStrict);
            PwdSecurityMode.SSLHelloProtocolForNISTStrict = PwdSecurityMode.defaultSSLHelloProtocolForNISTStrict;
        } else {
            PwdSecurityMode.SSLHelloProtocolForNISTStrict = sslProtocolForStrict.trim();
        }
        final String sslProtocol = "TLS1-TLS1.2";
        if (sslProtocol == null || sslProtocol.trim().isEmpty()) {
            System.out.println("Missing SSLHelloProtocol, set it to defaul value " + PwdSecurityMode.defaultSSLHelloProtocol);
            PwdSecurityMode.SSLHelloProtocol = PwdSecurityMode.defaultSSLHelloProtocol;
        } else {
            PwdSecurityMode.SSLHelloProtocol = sslProtocol.trim();
        }
        final String sslProtocolForTransition = "TLS1-TLS1.2";
        if (sslProtocolForTransition == null || sslProtocolForTransition.trim().isEmpty()) {
            System.out.println("Missing SSLHelloProtocolForNISTTransition, set it to defaul value " + PwdSecurityMode.defaultSSLHelloProtocolForNISTTransition);
            PwdSecurityMode.SSLHelloProtocolForNISTTransition = PwdSecurityMode.defaultSSLHelloProtocolForNISTTransition;
        } else {
            PwdSecurityMode.SSLHelloProtocolForNISTTransition = sslProtocolForTransition.trim();
        }
        final String sslProtocolForFIPS = "TLS1-TLS1.2";
        if (sslProtocolForFIPS == null || sslProtocolForFIPS.trim().isEmpty()) {
            System.out.println("Missing SSLHelloProtocolForFIPS, set it to defaul value " + PwdSecurityMode.defaultSSLHelloProtocolForFIPS);
            PwdSecurityMode.SSLHelloProtocolForFIPS = PwdSecurityMode.defaultSSLHelloProtocolForFIPS;
        } else {
            PwdSecurityMode.SSLHelloProtocolForFIPS = sslProtocolForFIPS.trim();
        }

        System.out.println("Security mode is set to " + PwdSecurityMode.SECURITY_MODE);
        if (PwdSecurityMode.SECURITY_MODE == 2) {
            PwdSecurityMode.disabledAlgorithms = "RSA keySize < 1024, DSA keySize < 1024, EC keySize < 160, MD2, MD4, MD5, RC2, RC4, DES";
            if (PwdSecurityMode.disabledAlgorithms == null) {
                System.out.println("transition.disabledAlgorithms property is not defined");
                PwdSecurityMode.disabledAlgorithms = "RSA keySize < 1024, DSA keySize < 1024, EC keySize < 160, MD5, RC2, RC4, DES";
            }
        } else if (PwdSecurityMode.SECURITY_MODE == 3) {
            PwdSecurityMode.disabledAlgorithms = "RSA keySize < 2048, DSA keySize < 2048, EC keySize < 224, SHA1, SHA-1, MD2, MD4, MD5, RC2, RC4, DES";
            if (PwdSecurityMode.disabledAlgorithms == null) {
                System.out.println("strict.disabledAlgorithms property is not defined");
                PwdSecurityMode.disabledAlgorithms = "RSA keySize < 2048, DSA keySize < 2048, EC keySize < 224, SHA1, MD5, RC2, RC4, DES";
            }
        }
        PwdSecurityMode.protocolMap.put("SSL3-TLS1.2", new String[]{"SSL3", "TLS1.0", "TLS1.1", "TLS1.2"});
        PwdSecurityMode.protocolMap.put("SSL3-TLS1.1", new String[]{"SSL3", "TLS1.0", "TLS1.1"});
        PwdSecurityMode.protocolMap.put("TLS1.2-ONLY", new String[]{"TLS1.2"});
        PwdSecurityMode.protocolMap.put("TLS1-TLS1.2", new String[]{"TLS1.0", "TLS1.1", "TLS1.2"});
        PwdSecurityMode.protocolMap.put("TLS1.1-TLS1.2", new String[]{"TLS1.1", "TLS1.2"});
        PwdCrypto.setDisabledAlgorithmConstraints(PwdSecurityMode.SECURITY_MODE, PwdSecurityMode.disabledAlgorithms);
    }
}
