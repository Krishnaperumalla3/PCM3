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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.cert.Certificate;
import java.util.LinkedList;
import java.util.StringTokenizer;
import java.util.concurrent.ConcurrentHashMap;

public class PwdController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PwdController.class);

    public static final transient String FIPS_HASH_NAMES;
    public static final transient String ALL_HASH_NAMES;
    public static final transient String FIPS_SIG_NAMES;
    public static final transient String ALL_SIG_NAMES;
    public static final transient String ALL_SYM_NAMES;
    private static final boolean mBFIPSMode;
    private static PwdController instance;
    private static final PwdAlgData[] m_SupportedHashes;
    private static final PwdAlgData[] m_SupportedSignatures;
    private static final PwdAlgData[] m_SupportedKeyTransAlgs;
    private static final PwdAlgData[] m_SupportedSymAlgs;
    private static final String m_sFIPSProvider;
    private static final String m_sTLSProto;
    private static final String[] m_OtherFIPSKeyTransProviders = new String[0];
    private static final String[] m_OtherFIPSSigProviders = new String[0];
    private static final int m_iKeyCacheSize;
    private static BasicCache m_KeyReferenceCache;
    private static BasicCache m_CertificateCache;
    private static BasicCache m_CertificateChainCache;

    public static synchronized PwdController getInstance() {
        try {
            if (PwdController.instance == null) {
                PwdController.instance = new PwdController();
            }
            return PwdController.instance;
        } catch (Exception e) {
            return null;
        }
    }

    public static boolean getFIPSMode() {
        return PwdController.mBFIPSMode;
    }

    public static String getTLSProto() {
        return PwdController.m_sTLSProto;
    }

    public static String getFIPSProvider() {
        return PwdController.m_sFIPSProvider;
    }

    public static String[] getSupportedHashList() {
        if (PwdController.m_SupportedHashes == null) {
            return null;
        }
        final LinkedList aList = new LinkedList();
        for (int i = 0; i < PwdController.m_SupportedHashes.length; ++i) {
            aList.add(PwdController.m_SupportedHashes[i].getJavaName());
        }
        return (String[]) aList.toArray(new String[0]);
    }

    public String[] getSupportedSigList() {
        if (PwdController.m_SupportedSignatures == null) {
            return null;
        }
        final LinkedList aList = new LinkedList();
        for (int i = 0; i < PwdController.m_SupportedSignatures.length; ++i) {
            aList.add(PwdController.m_SupportedSignatures[i].getJavaName());
        }
        return (String[]) aList.toArray(new String[0]);
    }

    public String[] getSupportedKeyTransAlgList() {
        if (PwdController.m_SupportedKeyTransAlgs == null) {
            return null;
        }
        final LinkedList aList = new LinkedList();
        for (int i = 0; i < PwdController.m_SupportedKeyTransAlgs.length; ++i) {
            aList.add(PwdController.m_SupportedKeyTransAlgs[i].getJavaName());
        }
        return (String[]) aList.toArray(new String[0]);
    }

    public String[] getSupportedSymAlgList() {
        if (PwdController.m_SupportedSymAlgs == null) {
            return null;
        }
        final LinkedList aList = new LinkedList();
        for (int i = 0; i < PwdController.m_SupportedSymAlgs.length; ++i) {
            aList.add(PwdController.m_SupportedSymAlgs[i].getJavaName());
        }
        return (String[]) aList.toArray(new String[0]);
    }

    private static String[] getList(final String key, final String delim, final String defaultValue) {
        final String list = "DESede,DESede,192;3DES,DESede,192;AES,AES,128;AES-128,AES,128;AES-192,AES,192;AES-256,AES,256";
        final StringTokenizer st = new StringTokenizer(list, delim);
        final LinkedList algList = new LinkedList();
        while (st.hasMoreTokens()) {
            algList.add(st.nextToken());
        }
        return (String[]) algList.toArray(new String[0]);
    }

    private static final PwdAlgData[] getAlgDataFromList(final String key, final String delim, final String defaultValue) {
        final String[] sList = getList(key, delim, defaultValue);
        if (sList == null) {
            return null;
        }
        final LinkedList aList = new LinkedList();
        for (int i = 0; i < sList.length; ++i) {
            aList.add(new PwdAlgData(sList[i]));
        }
        return (PwdAlgData[]) aList.toArray(new PwdAlgData[0]);
    }

    public static final PwdAlgData getHashAlgInfo(final String sAlgorithm) throws NoSuchAlgorithmException {
        if (PwdController.m_SupportedHashes == null) {
            throw new NoSuchAlgorithmException("Algorithm " + sAlgorithm + " unsupported");
        }
        for (int ic = 0; ic < PwdController.m_SupportedHashes.length; ++ic) {
            if (PwdController.m_SupportedHashes[ic].compareAlias(sAlgorithm)) {
                return new PwdAlgData(PwdController.m_SupportedHashes[ic].getAlias(), PwdController.m_SupportedHashes[ic].getJavaName(), PwdController.m_SupportedHashes[ic].getKeyLen());
            }
        }
        throw new NoSuchAlgorithmException("Algorithm " + sAlgorithm + " unsupported");
    }

    public static final PwdAlgData getSigAlgInfo(final String sAlgorithm) throws NoSuchAlgorithmException {
        if (PwdController.m_SupportedSignatures == null) {
            throw new NoSuchAlgorithmException("Algorithm " + sAlgorithm + " unsupported");
        }
        for (int ic = 0; ic < PwdController.m_SupportedSignatures.length; ++ic) {
            if (PwdController.m_SupportedSignatures[ic].compareAlias(sAlgorithm)) {
                return new PwdAlgData(PwdController.m_SupportedSignatures[ic].getAlias(), PwdController.m_SupportedSignatures[ic].getJavaName(), PwdController.m_SupportedSignatures[ic].getKeyLen());
            }
        }
        throw new NoSuchAlgorithmException("Algorithm " + sAlgorithm + " unsupported");
    }

    public static final PwdAlgData getKeyTransAlgInfo(final String sAlgorithm) throws NoSuchAlgorithmException {
        if (PwdController.m_SupportedKeyTransAlgs == null) {
            throw new NoSuchAlgorithmException("Algorithm " + sAlgorithm + " unsupported");
        }
        for (int ic = 0; ic < PwdController.m_SupportedKeyTransAlgs.length; ++ic) {
            if (PwdController.m_SupportedKeyTransAlgs[ic].compareAlias(sAlgorithm)) {
                return new PwdAlgData(PwdController.m_SupportedKeyTransAlgs[ic].getAlias(), PwdController.m_SupportedKeyTransAlgs[ic].getJavaName(), PwdController.m_SupportedKeyTransAlgs[ic].getKeyLen());
            }
        }
        throw new NoSuchAlgorithmException("Algorithm " + sAlgorithm + " unsupported");
    }

    public static final PwdAlgData getSymAlgInfo(final String sAlgorithm) throws NoSuchAlgorithmException {
        if (PwdController.m_SupportedSymAlgs == null) {
            throw new NoSuchAlgorithmException("Algorithm " + sAlgorithm + " unsupported");
        }
        for (int ic = 0; ic < PwdController.m_SupportedSymAlgs.length; ++ic) {
            if (PwdController.m_SupportedSymAlgs[ic].compareAlias(sAlgorithm)) {
                return new PwdAlgData(PwdController.m_SupportedSymAlgs[ic].getAlias(), PwdController.m_SupportedSymAlgs[ic].getJavaName(), PwdController.m_SupportedSymAlgs[ic].getKeyLen());
            }
        }
        throw new NoSuchAlgorithmException("Algorithm " + sAlgorithm + " unsupported");
    }

    public static final boolean isSymAlgAvailable(final String sAlgorithm) throws NoSuchAlgorithmException {
        try {
            final PwdAlgData anAlgData = getSymAlgInfo(sAlgorithm);
            if (anAlgData == null) {
                throw new NoSuchAlgorithmException("Algorithm " + sAlgorithm + " unavailable");
            }
        } catch (Exception e) {
            LOGGER.error("Exception determining if symmetric algorithm is available", e);
            throw new NoSuchAlgorithmException(e.getMessage());
        }
        return true;
    }

    public static final boolean isOtherFIPSKeyTransProvider(final String ssName) {
        if (PwdController.m_OtherFIPSKeyTransProviders != null) {
            int iCounter;
            for (iCounter = 0, iCounter = 0; iCounter < PwdController.m_OtherFIPSKeyTransProviders.length; ++iCounter) {
                if (PwdController.m_OtherFIPSKeyTransProviders[iCounter].compareToIgnoreCase(ssName) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    public static final boolean isOtherFIPSSignatureProvider(final String ssName) {
        if (PwdController.m_OtherFIPSSigProviders != null) {
            int iCounter;
            for (iCounter = 0, iCounter = 0; iCounter < PwdController.m_OtherFIPSKeyTransProviders.length; ++iCounter) {
                if (PwdController.m_OtherFIPSSigProviders[iCounter].compareToIgnoreCase(ssName) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    /*public final synchronized PrivateKey getPrivateKeyObject(final Object token, final SCIKeyReference keyRef) throws InvalidParameterException, CertificateException, CertificateExpiredException, CertificateNotYetValidException, CertificateHeldException, CertificateRevokedException, IssuerNotFoundException, InvalidKeyException {
        if (keyRef != null) {
            return keyRef.retrievePrivateKey();
        }
        return null;
    }*/

    public static void addKeyCacheEntry(final String ssID, final PrivateKey pk) {
        if (PwdController.m_iKeyCacheSize > 0) {
            PwdController.m_KeyReferenceCache.addEntry(ssID, pk);
        }
    }

    public static void removeKeyCacheEntry(final String ssID) {
        PwdController.m_KeyReferenceCache.removeEntry(ssID);
    }

    public static PrivateKey getKeyCacheEntry(final String ssID) {
        if (PwdController.m_iKeyCacheSize > 0) {
            final Object o = PwdController.m_KeyReferenceCache.getEntry(ssID);
            if (o != null) {
                final PrivateKey toReturn = (PrivateKey) o;
                return toReturn;
            }
        }
        return null;
    }

    public static void addSystemCertCacheEntry(final String ssID, final Certificate pk) {
        if (PwdController.m_iKeyCacheSize > 0) {
            PwdController.m_CertificateCache.addEntry(ssID, pk);
        }
    }

    public static void removeSystemCertCacheEntry(final String ssID) {
        PwdController.m_CertificateCache.removeEntry(ssID);
    }

    public static Certificate getSystemCertCacheEntry(final String ssID) {
        if (PwdController.m_iKeyCacheSize > 0) {
            final Object o = PwdController.m_CertificateCache.getEntry(ssID);
            if (o != null) {
                final Certificate toReturn = (Certificate) o;
                return toReturn;
            }
        }
        return null;
    }

    public static void addSystemCertChainCacheEntry(final String ssID, final Certificate[] pk) {
        if (PwdController.m_iKeyCacheSize > 0) {
            PwdController.m_CertificateChainCache.addEntry(ssID, pk);
        }
    }

    public static void removeSystemCertChainCacheEntry(final String ssID) {
        PwdController.m_CertificateChainCache.removeEntry(ssID);
    }

    public static Certificate[] getSystemCertChainCacheEntry(final String ssID) {
        if (PwdController.m_iKeyCacheSize > 0) {
            final Certificate[] toReturn = (Certificate[]) PwdController.m_CertificateChainCache.getEntry(ssID);
            if (toReturn != null) {
                return toReturn;
            }
        }
        return null;
    }

    static {
        PwdController.instance = null;
        PwdController.m_KeyReferenceCache = new BasicCache();
        PwdController.m_CertificateCache = new BasicCache();
        PwdController.m_CertificateChainCache = new BasicCache();
        if (PwdSecurityMode.SECURITY_MODE == 1) {
            FIPS_HASH_NAMES = "SHA-224,SHA224,0; SHA-1,SHA1,0; SHA-256,SHA256,0; SHA-384,SHA384,0; SHA-512,SHA512,0";
            ALL_HASH_NAMES = "SHA-224,SHA224,0; MD2,MD2,0;MD4,MD4,0;MD5,MD5,0;SHA,SHA,0;SHA-1,SHA1,0; SHA-256,SHA256,0; SHA-384,SHA384,0; SHA-512,SHA512,0";
            ALL_SIG_NAMES = "SHA224withRSA,SHA224/RSA,0;MD2withRSA,SHA1/RSA,0;MD4withRSA,SHA1/RSA,0;MD5withRSA,SHA1/RSA,0;SHA1withRSA,SHA1/RSA,0;SHA256withRSA,SHA256/RSA,0;SHA384withRSA,SHA384/RSA,0;SHA512withRSA,SHA512/RSA,0;SHA1withDSA,SHA1/DSA,0;SHA224withDSA,SHA224/DSA,0;SHA256withDSA,SHA256/DSA,0;SHA384withDSA,SHA384/DSA,0;SHA512withDSA,SHA512/DSA,0";
            FIPS_SIG_NAMES = "SHA224withRSA,SHA224/RSA,0;SHA1withRSA,SHA1/RSA,0;SHA256withRSA,SHA256/RSA,0;SHA384withRSA,SHA384/RSA,0;SHA512withRSA,SHA512/RSA,0;SHA1withDSA,SHA1/DSA,0;SHA224withDSA,SHA224/DSA,0;SHA256withDSA,SHA256/DSA,0;SHA384withDSA,SHA384/DSA,0;SHA512withDSA,SHA512/DSA,0";
            ALL_SYM_NAMES = "DESede,DESede,192;3DES,DESede,192;DES,DES,56;AES,AES,128;AES-128,AES,128;AES-192,AES,192;AES-256,AES,256;RC2,RC2,128;ARC2,RC2,128;RC2-40,RC2,40;ARC2-40,RC2,40;RC2-128,RC2,128;ARC2-128,RC2,128;RC4,RC4,128;ARC4,RC4,128;RC4-40,RC4,40;ARC4-40,RC4,40;RC4-128,RC4,128;ARC4-128,RC4,128;";
        } else {
            FIPS_HASH_NAMES = "SHA-224,SHA224,0; SHA-256,SHA256,0; SHA-384,SHA384,0; SHA-512,SHA512,0";
            ALL_HASH_NAMES = "SHA-224,SHA224,0; MD2,MD2,0;MD4,MD4,0;SHA,SHA,0;SHA-256,SHA256,0; SHA-384,SHA384,0; SHA-512,SHA512,0";
            if (PwdSecurityMode.SECURITY_MODE == 2) {
                ALL_SIG_NAMES = "SHA224withRSA,SHA224/RSA,0;MD2withRSA,SHA1/RSA,0;MD4withRSA,SHA1/RSA,0;SHA1withRSA,SHA1/RSA,0;SHA256withRSA,SHA256/RSA,0;SHA384withRSA,SHA384/RSA,0;SHA512withRSA,SHA512/RSA,0;SHA1withDSA,SHA1/DSA,0;SHA224withDSA,SHA224/DSA,0;SHA256withDSA,SHA256/DSA,0;SHA384withDSA,SHA384/DSA,0;SHA512withDSA,SHA512/DSA,0";
                FIPS_SIG_NAMES = "SHA224withRSA,SHA224/RSA,0;SHA1withRSA,SHA1/RSA,0;SHA256withRSA,SHA256/RSA,0;SHA384withRSA,SHA384/RSA,0;SHA512withRSA,SHA512/RSA,0;SHA1withDSA,SHA1/DSA,0;SHA224withDSA,SHA224/DSA,0;SHA256withDSA,SHA256/DSA,0;SHA384withDSA,SHA384/DSA,0;SHA512withDSA,SHA512/DSA,0";
            } else {
                ALL_SIG_NAMES = "SHA224withRSA,SHA224/RSA,0;MD2withRSA,SHA1/RSA,0;MD4withRSA,SHA1/RSA,0;SHA256withRSA,SHA256/RSA,0;SHA384withRSA,SHA384/RSA,0;SHA512withRSA,SHA512/RSA,0;SHA224withDSA,SHA224/DSA,0;SHA256withDSA,SHA256/DSA,0;SHA384withDSA,SHA384/DSA,0;SHA512withDSA,SHA512/DSA,0";
                FIPS_SIG_NAMES = "SHA224withRSA,SHA224/RSA,0;SHA256withRSA,SHA256/RSA,0;SHA384withRSA,SHA384/RSA,0;SHA512withRSA,SHA512/RSA,0;SHA224withDSA,SHA224/DSA,0;SHA256withDSA,SHA256/DSA,0;SHA384withDSA,SHA384/DSA,0;SHA512withDSA,SHA512/DSA,0";
            }
            ALL_SYM_NAMES = "DESede,DESede,192;3DES,DESede,192;DES,DES,56;AES,AES,128;AES-128,AES,128;AES-192,AES,192;AES-256,AES,256;";
        }
        boolean bTempMode = false;
        String ssTempCerticomFIPSSignatureFile = null;
        PwdAlgData[] TempSupportedHashes = null;
        PwdAlgData[] TempSupportedSignatures = null;
        PwdAlgData[] TempSupportedSymAlgs = null;
        PwdAlgData[] TempSupportedKeyTransAlgs = null;
        final String[] TempOtherFIPSKeyTransProviders = null;
        final String[] TempOtherFIPSSigProviders = null;
        final int iTempKeyCacheSize = 100;
        String sTempFIPSProvider = null;
        String sTempTLSProto = null;
        try {
            if (bTempMode) {
                TempSupportedHashes = getAlgDataFromList("SupportedHashAlgorithms", ";", PwdController.FIPS_HASH_NAMES);
                TempSupportedSignatures = getAlgDataFromList("SupportedSignatureAlgorithms", ";", PwdController.FIPS_SIG_NAMES);
                TempSupportedSymAlgs = getAlgDataFromList("SupportedSymmetricAlgorithms", ";", "DESede,DESede,192;3DES,DESede,192;AES,AES,128;AES-128,AES,128;AES-192,AES,192;AES-256,AES,256");
                TempSupportedKeyTransAlgs = getAlgDataFromList("SupportedKeyTransportAlgorithms", ";", "RSA,RSA,2048");
            } else {
                LOGGER.info("Controller loading lists...");
                TempSupportedHashes = getAlgDataFromList("SupportedHashAlgorithms", ";", PwdController.ALL_HASH_NAMES);
                TempSupportedSignatures = getAlgDataFromList("SupportedSignatureAlgorithms", ";", PwdController.ALL_SIG_NAMES);
                TempSupportedSymAlgs = getAlgDataFromList("SupportedSymmetricAlgorithms", ";", PwdController.ALL_SYM_NAMES);
                TempSupportedKeyTransAlgs = getAlgDataFromList("SupportedKeyTransportAlgorithms", ";", "RSA,RSA,2048");
            }
            sTempFIPSProvider = "Certicom";
            sTempTLSProto = "TLSProto";
        } catch (Exception e) {
            LOGGER.error("", e);
        } finally {
            mBFIPSMode = bTempMode;
            m_SupportedHashes = TempSupportedHashes;
            m_SupportedSignatures = TempSupportedSignatures;
            m_SupportedSymAlgs = TempSupportedSymAlgs;
            m_SupportedKeyTransAlgs = TempSupportedKeyTransAlgs;
            m_sFIPSProvider = sTempFIPSProvider;
            m_sTLSProto = sTempTLSProto;
            m_iKeyCacheSize = iTempKeyCacheSize;
            if (PwdController.m_KeyReferenceCache != null) {
                PwdController.m_KeyReferenceCache.setMaxSize(PwdController.m_iKeyCacheSize);
            }
            if (PwdController.m_CertificateCache != null) {
                PwdController.m_CertificateCache.setMaxSize(PwdController.m_iKeyCacheSize);
            }
            if (PwdController.m_CertificateChainCache != null) {
                PwdController.m_CertificateChainCache.setMaxSize(PwdController.m_iKeyCacheSize);
            }
        }
    }

    private static class BasicCache {
        int m_iMaxSize;
        ConcurrentHashMap<String, Object> m_Keys;
        LinkedList m_Order;

        private BasicCache() {
            this.m_iMaxSize = 100;
            this.m_Keys = new ConcurrentHashMap<String, Object>();
            this.m_Order = new LinkedList();
        }

        void setMaxSize(final int iNewSize) {
            this.m_iMaxSize = iNewSize;
        }

        synchronized void addEntry(final String ssID, final Object pk) {
            if (!this.m_Keys.containsKey(ssID)) {
                if (this.m_Keys.size() == this.m_iMaxSize) {
                    String ssLastUsed = null;
                    synchronized (this.m_Order) {
                        ssLastUsed = (String) this.m_Order.removeLast();
                        this.m_Order.addFirst(ssID);
                    }
                    if (ssLastUsed != null) {
                        this.m_Keys.remove(ssLastUsed);
                    }
                } else {
                    synchronized (this.m_Order) {
                        this.m_Order.addFirst(ssID);
                    }
                }
                this.m_Keys.put(ssID, pk);
            }
        }

        synchronized void removeEntry(final String ssID) {
            if (this.m_Keys.containsKey(ssID)) {
                synchronized (this.m_Order) {
                    final int ir = this.m_Order.indexOf(ssID);
                    this.m_Order.remove(ir);
                }
                this.m_Keys.remove(ssID);
            }
        }

        synchronized Object getEntry(final String ssID) {
            if (this.m_Keys.containsKey(ssID)) {
                synchronized (this.m_Order) {
                    final int ir = this.m_Order.indexOf(ssID);
                    this.m_Order.remove(ir);
                    this.m_Order.addFirst(ssID);
                }
                final Object toReturn = this.m_Keys.get(ssID);
                return toReturn;
            }
            return null;
        }
    }
}
