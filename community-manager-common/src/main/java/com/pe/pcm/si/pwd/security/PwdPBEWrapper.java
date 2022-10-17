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

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;

public class PwdPBEWrapper {
    int mode;
    private String alg;
    private byte[] encrypted;
    private String mSsAlgorithm;
    PwdMessageDigest md;
    private final String m_ssAlgorithmID;
    private int m_iKeyBits;
    private int m_iKeyBytes;
    private static Logger logger = LoggerFactory.getLogger(PwdPBEWrapper.class);

    public String getAlgorithmID() {
        return this.m_ssAlgorithmID;
    }

    public PwdPBEWrapper() throws Exception {
        this.mode = -1;
        this.alg = "DESede";
        this.encrypted = null;
        this.mSsAlgorithm = "DESede/CBC/PKCS5Padding";
        this.md = null;
        this.m_ssAlgorithmID = null;
        this.m_iKeyBits = 192;
        this.m_iKeyBytes = 24;
        this.mode = 1;
        this.md = PwdMessageDigest.getInstance("SHA1");
    }

    public byte[] encrypt(final int ic, final byte[] password, final byte[] salt, final byte[] content) throws Exception {
        if (this.mode != 1) {
            throw new Exception("PBEWrapper:Wrong mode!!! ");
        }
        if (password == null || salt == null) {
            throw new Exception("PBEWrapper:NULL password!!! ");
        }
        if (this.md == null) {
            throw new Exception("PBEWrapper:NULL password!!! ");
        }
        try {
            this.alg = "DESede";
            logger.debug("SCIPBEWrapper: prepare encrytpion using {}", this.alg);
        } catch (Exception e) {
            logger.debug("SCIPBEWrapper: using default DESede.");
        }
        if (this.alg.length() == 0) {
            this.alg = "DESede";
        }
        if (this.alg.equalsIgnoreCase("none")) {
            this.mSsAlgorithm = "none";
            this.m_iKeyBytes = 0;
            this.encrypted = content;
        } else {
            this.mSsAlgorithm = this.alg;
            if (!PwdController.isSymAlgAvailable(this.mSsAlgorithm)) {
                this.m_iKeyBits = 0;
                this.m_iKeyBytes = 0;
                throw new Exception("Requested algorithm " + this.mSsAlgorithm + " not supported for Ops encryption");
            }
            final PwdSymmetricCipher theCipher = PwdSymmetricCipher.getInstance(this.mSsAlgorithm, null, null);
            this.m_iKeyBits = theCipher.getKeyBits();
            this.m_iKeyBytes = this.m_iKeyBits / 8;
            final byte[] key = PwdPKCS12KeyIV.genKeyBytes(this.md, password, salt, ic, this.m_iKeyBytes);
            final byte[] iv = PwdPKCS12KeyIV.genIVBytes(this.md, password, salt, ic, 8);
            theCipher.initEncryption(iv, key);
            this.encrypted = theCipher.doFinal(content);
        }
        final ByteArrayOutputStream baos = new ByteArrayOutputStream();
        final ObjectOutputStream oos = new ObjectOutputStream(baos);
        oos.writeObject(this.alg);
        oos.writeObject(salt);
        oos.writeObject(new Integer(ic));
        oos.writeObject(this.encrypted);
        oos.flush();
        final byte[] result = baos.toByteArray();
        oos.close();
        oos.reset();
        baos.close();
        return result;
    }

    public PwdPBEWrapper(final byte[] encrypted) throws Exception {
        this.mode = -1;
        this.alg = "DESede";
        this.encrypted = null;
        this.mSsAlgorithm = "DESede/CBC/PKCS5Padding";
        this.md = null;
        this.m_ssAlgorithmID = null;
        this.m_iKeyBits = 192;
        this.m_iKeyBytes = 24;
        this.mode = 2;
        this.encrypted = encrypted;
        this.md = PwdMessageDigest.getInstance("SHA1");
    }

    public byte[] decrypt(final byte[] password) {
        try {

            byte[] bOutput;
            if (password == null) {
                throw internalServerError("PBEWrapper:NULL password!!! ");
            }
            if (this.mode != 2) {
                throw internalServerError("PBEWrapper:Wrong mode!!! ");
            }
            if (this.md == null) {
                throw internalServerError("PBEWrapper:NULL password!!! ");
            }
            final ObjectInputStream ois = new ObjectInputStream(new ByteArrayInputStream(this.encrypted));
            final String alg0 = (String) ois.readObject();
            final byte[] salt = (byte[]) ois.readObject();
            final int ic = (Integer) ois.readObject();
            final byte[] encrypted0 = (byte[]) ois.readObject();
            if (alg0.equalsIgnoreCase("none")) {
                this.mSsAlgorithm = "none";
                this.m_iKeyBytes = 0;
                bOutput = encrypted0;
            } else {
                this.mSsAlgorithm = alg0;
                if (!PwdController.isSymAlgAvailable(this.mSsAlgorithm)) {
                    this.m_iKeyBits = 0;
                    this.m_iKeyBytes = 0;
                    throw internalServerError("Requested algorithm " + this.mSsAlgorithm + " not supported for encryption");
                }
                final PwdSymmetricCipher theCipher = PwdSymmetricCipher.getInstance(this.mSsAlgorithm, null, null);
                this.m_iKeyBits = theCipher.getKeyBits();
                this.m_iKeyBytes = this.m_iKeyBits / 8;
                final byte[] key = PwdPKCS12KeyIV.genKeyBytes(this.md, password, salt, ic, this.m_iKeyBytes);
                final byte[] iv = PwdPKCS12KeyIV.genIVBytes(this.md, password, salt, ic, 8);
                theCipher.initDecryption(iv, key);
                bOutput = theCipher.doFinal(encrypted0);
            }
            return bOutput;

        } catch (Exception e) {
            throw internalServerError(e.getMessage());
        }
    }

}
