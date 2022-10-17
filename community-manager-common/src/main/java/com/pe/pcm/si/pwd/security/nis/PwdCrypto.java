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

package com.pe.pcm.si.pwd.security.nis;

import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.EnumSet;
import java.util.Set;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;

public class PwdCrypto {
    public static final int DEFAULT_MODE = 1;
    public static final int TRANSITION_MODE = 2;
    public static final int STRICT_MODE = 3;
    public static final int PERMITTED = 0;
    public static final int ALGORITHM_NOT_PERMITTED = 1;
    public static final int KEY_ALGORITHM_NOT_PERMITTED = 2;
    public static final int KEY_STRENGTH_NOT_PERMITTED = 3;
    protected static PwdDisabledAlgorithmConstraints certPathDefaultConstraints;
    public static int securityMode = 1;
    private static final Set<PwdCryptoPrimitive> SIGNATURE_PRIMITIVE_SET;

    public PwdCrypto() {
    }

    public static void setDisabledAlgorithmConstraints(int mode, String disabledAlgorithms) {
        if (disabledAlgorithms != null) {
            certPathDefaultConstraints = new PwdDisabledAlgorithmConstraints("jdk.certpath.disabledAlgorithms", disabledAlgorithms);
            securityMode = mode;
        }

    }

    public static void AlgorithmRestrictionCheck(Key key, String algName)  {
        if (securityMode != 1 && certPathDefaultConstraints != null) {
            int result = certPathDefaultConstraints.permits(SIGNATURE_PRIMITIVE_SET, algName, key, null);
            switch(result) {
                case 1:
                    throw internalServerError("Algorithm check failed: " + algName + " is disabled due to non NIST SP800-131a compliant");
                case 2:
                    throw internalServerError("Key algorithm check failed: " + key.getAlgorithm() + " is disabled due to non NIST SP800-131a compliant");
                case 3:
                    throw internalServerError("Key strength check failed: " + key.getAlgorithm() + " keySize=" + PwdDisabledAlgorithmConstraints.getKeySize(key) + " is disabled due to non NIST SP800-131a compliant");
                default:
            }
        }
    }

    public static void AlgorithmRestrictionCheck(Certificate cert, String algName){
        if (securityMode != 1 && certPathDefaultConstraints != null) {
            AlgorithmRestrictionCheck(cert);
            if (certPathDefaultConstraints.permits(SIGNATURE_PRIMITIVE_SET, algName, (AlgorithmParameters)null) == 1) {
                throw internalServerError("algorithm check failed: " + algName + " is disabled due to non NIST SP800-131a compliant");
            }
        }
    }

    public static void AlgorithmRestrictionCheckWithAlias(Certificate cert, String alias) {
        if (securityMode != 1 && certPathDefaultConstraints != null) {
            String aliasInfo = "";
            if (alias != null && !alias.isEmpty()) {
                aliasInfo = "certificate alias: " + alias;
            }

            String certSigAlgName = ((X509Certificate)cert).getSigAlgName();
            if (certSigAlgName != null && certSigAlgName.length() != 0) {
                int result = certPathDefaultConstraints.permits(SIGNATURE_PRIMITIVE_SET, certSigAlgName, cert.getPublicKey(), (AlgorithmParameters)null);
                switch(result) {
                    case 1:
                        throw internalServerError("Cert signature algorithm check failed: " + certSigAlgName + " is disabled due to non NIST SP800-131a compliant. " + aliasInfo);
                    case 2:
                        throw internalServerError("Key algorithm check failed: " + cert.getPublicKey().getAlgorithm() + " is disabled due to non NIST SP800-131a compliant. " + aliasInfo);
                    case 3:
                        throw internalServerError("Key strength check failed: " + PwdDisabledAlgorithmConstraints.getKeySize(cert.getPublicKey()) + " is disabled due to non NIST SP800-131a compliant. " + aliasInfo);
                    default:
                }
            } else {
                throw new IllegalArgumentException("No algorithm name specified");
            }
        }
    }

    public static void AlgorithmRestrictionCheck(Certificate cert) {
        AlgorithmRestrictionCheckWithAlias(cert, null);
    }

    public static void AlgorithmRestrictionCheck(String algName){
        if (securityMode != 1 && certPathDefaultConstraints != null) {
            if (certPathDefaultConstraints.permits(SIGNATURE_PRIMITIVE_SET, algName, (AlgorithmParameters)null) == 1) {
                throw internalServerError("algorithm check failed: " + algName + " is disabled due to non NIST SP800-131a compliant");
            }
        }
    }

   public static void MessageDigestRestrictionCheck(String algName) {
        if (securityMode != 1 && certPathDefaultConstraints != null && !"sha1".equalsIgnoreCase(algName) && !"SHA-1".equalsIgnoreCase(algName)) {
            if (certPathDefaultConstraints.permits(SIGNATURE_PRIMITIVE_SET, algName, (AlgorithmParameters)null) == 1) {
                throw internalServerError("algorithm check failed: " + algName + " is disabled due to non NIST SP800-131a compliant");
            }
        }
    }

   /* public static void certificateStoreRestrictionCheck(String fileName, String password) throws InvalidAlgorithmException, FileNotFoundException, KeyStoreException, CertificateException, NoSuchAlgorithmException, IOException {
        File file = new File(fileName);
        FileInputStream is = new FileInputStream(file);
        KeyStore keystore = KeyStore.getInstance(KeyStore.getDefaultType());
        keystore.load(is, password.toCharArray());
        Enumeration enumeration = keystore.aliases();

        while(enumeration.hasMoreElements()) {
            String alias = (String)enumeration.nextElement();
            Certificate certificate = keystore.getCertificate(alias);
            AlgorithmRestrictionCheckWithAlias(certificate, alias);
        }

    }*/

   /* public static void XMLAlgorithmRestrictionCheck(String algName) throws InvalidAlgorithmException {
        if (securityMode != 1 && certPathDefaultConstraints != null) {
            if (securityMode != 2 || algName == null || !algName.contains("SHA1") && !algName.contains("sha1") && !algName.contains("SHA-1") && !algName.contains("sha-1")) {
                AlgorithmRestrictionCheck(algName);
            } else {
                throw new InvalidAlgorithmException("algorithm check failed: " + algName + " is disabled due to non NIST SP800-131a compliant", 1);
            }
        }
    }*/

    static {
        SIGNATURE_PRIMITIVE_SET = EnumSet.of(PwdCryptoPrimitive.SIGNATURE);
    }
}
