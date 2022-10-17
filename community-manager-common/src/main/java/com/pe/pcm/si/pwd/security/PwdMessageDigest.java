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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.util.Arrays;

public class PwdMessageDigest extends PwdCrypto implements com.pe.pcm.si.pwd.PwdMessageDigest {
    private static boolean fipsMode;
    private static String fipsProvider;
    private static String HASH_DEFAULT;
    private String requestedProvider;
    private String[] myHashList;
    private MessageDigest md;
    private static Logger logger = LoggerFactory.getLogger(PwdMessageDigest.class);

    private PwdMessageDigest(final String myHash, final String provider, final boolean legacy) throws Exception {
        this.requestedProvider = null;
        this.myHashList = null;
        this.md = null;
        if (myHash != null) {
            this.myHashList = new String[]{myHash};
        }
        this.requestedProvider = provider;
        logger.debug(this.getClass().getName() + " is instantiated with " + myHash + ":" + provider);
        this.init(legacy);
    }


    public static PwdMessageDigest getInstance() throws Exception {
        return new PwdMessageDigest(null, null, false);
    }

    static PwdMessageDigest getInstance(final String myHash) throws Exception {
        return new PwdMessageDigest(myHash, null, false);
    }

    private static MessageDigest getOneInstance(final String alg, final String provider, final boolean legacy) throws Exception {
        if (!legacy) {
            MessageDigestRestrictionCheck(alg);
        }
        return MessageDigest.getInstance(alg, provider);
    }

    private static MessageDigest getOneInstance(final String alg, final boolean legacy) throws Exception {
        if (!legacy) {
            MessageDigestRestrictionCheck(alg);
        }
        return MessageDigest.getInstance(alg);
    }

    public boolean equals(final byte[] digesta, final byte[] digestb) throws Exception {
        final PwdMessageDigest md = getInstance();
        return isEqual(digesta, digestb);
    }

    public static boolean isEqual(final byte[] digesta, final byte[] digestb) {
        return Arrays.equals(digesta, digestb);
    }

    private void init(final boolean legacy) throws Exception {
        if (this.myHashList == null || this.myHashList.length == 0) {
            this.myHashList = new String[]{PwdMessageDigest.HASH_DEFAULT};
        }
        if (!PwdMessageDigest.fipsMode) {
            final int j = 0;
            try {
                if (this.requestedProvider != null) {
                    this.md = getOneInstance(this.myHashList[j], this.requestedProvider, legacy);
                } else {
                    this.md = getOneInstance(this.myHashList[j], legacy);
                }
                return;
            } catch (NoSuchAlgorithmException | NoSuchProviderException ex) {
                System.out.println("Unable to instantiate " + this.myHashList[j] + (Exception) ex);
                throw ex;
            }
        } else {
            if (this.requestedProvider != null && !this.requestedProvider.equalsIgnoreCase(PwdMessageDigest.fipsProvider)) {
                throw new NoSuchProviderException("In FIPS mode cannot use requested provider " + this.requestedProvider);
            }
            final int j = 0;
            if (j < this.myHashList.length) {
                try {
                    this.md = getOneInstance(this.myHashList[j], PwdMessageDigest.fipsProvider, legacy);
                    if (this.myHashList[j].equalsIgnoreCase("md5")) {
                        /*if (SCIMessageDigest.LOG.debug) {
                            SCIMessageDigest.LOG.logDebug("MD5 is not FIPS approved algorithm");
                        }*/
                        throw new NoSuchAlgorithmException("MD5 is not FIPS approved algorithm");
                    }
                    return;
                } catch (NoSuchProviderException ex2) {
                    System.out.println("Unable to instantiate " + this.myHashList[j] + (Exception) ex2);
                    throw ex2;
                }
            }
        }
        throw new NoSuchAlgorithmException("No requested algorithm is available");
    }

    public void reset() {
        this.md.reset();
    }

    private int getDigestLength() {
        return this.md.getDigestLength();
    }

    public void update(final byte b) {
        this.md.update(b);
    }

    public void update(final byte[] b) {
        this.md.update(b);
    }

    public void update(final byte[] b, final int off, final int len) {
        this.md.update(b, off, len);
    }

    public byte[] digest() {
        return this.md.digest();
    }

    public byte[] digest(final byte[] data) {
        this.md.update(data);
        return this.md.digest();
    }

    public String getAlgorithm() {
        return this.md.getAlgorithm();
    }

    public String getProvider() {
        return this.md.getProvider().getName();
    }

    public MessageDigest clone() throws CloneNotSupportedException {
        return (MessageDigest) this.md.clone();
    }

    public String toString() {
        return new String("[SCIMessageDigest] \nprovider = " + this.getProvider() + "algorithm = " + this.getAlgorithm() + " digest length = " + this.getDigestLength());
    }

    public static void main(final String[] args) {
        try {
            final byte[] b = "to be hashed".getBytes();
            final PwdMessageDigest md = getInstance("SHA1");
            System.out.println("hash provider is " + md.getProvider());
            md.update(b);
            final byte[] mdVal = md.digest();
            System.out.println("The md length is " + md.getDigestLength());
            System.out.println("The md hash alg chosen is " + md.getAlgorithm());
            System.out.println("The hash return length  is " + mdVal.length);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static {
        PwdMessageDigest.fipsMode = false;
        PwdMessageDigest.fipsProvider = "Certicom";
        PwdMessageDigest.HASH_DEFAULT = "SHA1";
    }
}

