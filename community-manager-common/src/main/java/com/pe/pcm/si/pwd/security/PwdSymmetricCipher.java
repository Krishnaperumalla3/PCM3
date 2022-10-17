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

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.RC2ParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.*;
import java.security.cert.Certificate;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.InvalidParameterSpecException;

public class PwdSymmetricCipher {

        public static final String DEFAULT_MODE = "CBC";
        public static final String DEFAULT_PADDING = "PKCS5Padding";
        private static final boolean fipsMode;
        private String m_sProvider;
        private String m_sAlgorithm;
        private String m_sMode;
        private String m_sPadding;
        private int m_iKeyLen;
        private transient Cipher m_CipherObject;
        private transient byte[] m_bRawKey;
        private byte[] m_bRawIV;

        @Override
        protected void finalize() {
            if (this.m_bRawKey != null) {
                for (int i1 = 0; i1 < this.m_bRawKey.length; ++i1) {
                    this.m_bRawKey[i1] = 0;
                }
            }
            if (this.m_bRawIV != null) {
                for (int i2 = 0; i2 < this.m_bRawIV.length; ++i2) {
                    this.m_bRawIV[i2] = 0;
                }
            }
        }

        public static PwdSymmetricCipher getInstance(final String sAlgorithm, final String sMode, final String sPadding) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
            return new PwdSymmetricCipher(sAlgorithm, sMode, sPadding, null);
        }

        public static PwdSymmetricCipher getInstance(final String sAlgorithm, final String sMode, final String sPadding, final String ssRequestedProvider) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
            return new PwdSymmetricCipher(sAlgorithm, sMode, sPadding, ssRequestedProvider);
        }

        protected PwdSymmetricCipher(final String sAlgorithm, final String sMode, final String sPadding, final String ssRequestedProvider) throws NoSuchAlgorithmException, NoSuchProviderException, NoSuchPaddingException {
            this.m_sMode = "CBC";
            this.m_sPadding = "PKCS5Padding";
            this.m_bRawKey = null;
            this.m_bRawIV = null;
            PwdController.getInstance();
            final PwdAlgData algData = PwdController.getSymAlgInfo(sAlgorithm);
            this.m_sAlgorithm = algData.getJavaName();
            this.m_iKeyLen = algData.getKeyLen();
            if (sMode != null) {
                this.m_sMode = sMode;
            }
            if (sPadding != null) {
                this.m_sPadding = sPadding;
            }
            if (!PwdSymmetricCipher.fipsMode) {
                this.m_sProvider = ssRequestedProvider;
                if (this.m_sProvider != null) {
                    this.m_CipherObject = Cipher.getInstance(this.m_sAlgorithm + "/" + this.m_sMode + "/" + this.m_sPadding, this.m_sProvider);
                }
                else {
                    this.m_CipherObject = Cipher.getInstance(this.m_sAlgorithm + "/" + this.m_sMode + "/" + this.m_sPadding);
                }
           /* if (SCISymmetricCipher.LOG.debug) {
                SCISymmetricCipher.LOG.logDebug("SCISymetricCipher is provided by " + this.m_CipherObject.getProvider());
            }*/
            }
            else {
           /* if (SCISymmetricCipher.LOG.debug) {
                SCISymmetricCipher.LOG.logDebug("SCISymmetricCipher found FIPS mode, checking FIPS Proivder...");
            }*/
               PwdController.getInstance();
                this.m_sProvider = PwdController.getFIPSProvider();
            /*if (SCISymmetricCipher.LOG.debug) {
                SCISymmetricCipher.LOG.logDebug("SCISymmetricCipher FIPS mode m_sProvider " + this.m_sProvider);
            }
            if (SCISymmetricCipher.LOG.debug) {
                SCISymmetricCipher.LOG.logDebug("SCISymmetricCipher FIPS mode ssRequestedProvider " + ssRequestedProvider);
            }*/
                if (this.m_sProvider == null) {
                    throw new NoSuchProviderException("Provider must be specified in FIPS mode ");
                }
                if (ssRequestedProvider != null && !ssRequestedProvider.equalsIgnoreCase(this.m_sProvider)) {
                    this.m_CipherObject = Cipher.getInstance(this.m_sAlgorithm + "/" + this.m_sMode + "/" + this.m_sPadding, ssRequestedProvider);
                }
                else {
                    this.m_CipherObject = Cipher.getInstance(this.m_sAlgorithm + "/" + this.m_sMode + "/" + this.m_sPadding, this.m_sProvider);
                }
            /*if (SCISymmetricCipher.LOG.debug) {
                SCISymmetricCipher.LOG.logDebug("SCISymetricCipher is provided by " + this.m_CipherObject.getProvider());
            }*/
            }
        }

        public final void setRawKey(final byte[] bRawKey) {
            this.m_bRawKey = bRawKey;
        }

        public final void setRawIV(final byte[] bRawIV) {
            this.m_bRawIV = bRawIV;
        }

        public int getKeyBits() {
            return this.m_iKeyLen;
        }


        private final void initInternal(final int iMode) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException {
            final SecretKeySpec kSpec = new SecretKeySpec(this.m_bRawKey, this.m_sAlgorithm);
            AlgorithmParameters algParams = null;
            PwdCrypto.AlgorithmRestrictionCheck((Key)kSpec, this.m_sAlgorithm);
            if (this.m_sProvider != null) {
                algParams = PwdAlgorithmParameters.getInstance(this.m_sAlgorithm, this.m_sProvider);
            }
            else {
                algParams = PwdAlgorithmParameters.getInstance(this.m_sAlgorithm);
            }
            if (this.m_sAlgorithm.compareToIgnoreCase("RC2") == 0) {
                algParams.init(new RC2ParameterSpec(this.m_iKeyLen, this.m_bRawIV));
            }
            else {
                algParams.init(new IvParameterSpec(this.m_bRawIV));
            }
            this.m_CipherObject.init(iMode, kSpec, algParams);
        }

        public final void initEncryption() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException{
            if (this.m_bRawKey != null) {
                for (int i1 = 0; i1 < this.m_bRawKey.length; ++i1) {
                    this.m_bRawKey[i1] = 0;
                }
            }
            if (this.m_bRawIV != null) {
                for (int i2 = 0; i2 < this.m_bRawIV.length; ++i2) {
                    this.m_bRawIV[i2] = 0;
                }
            }
            final SecureRandom sRand =new SecureRandom();
            sRand.nextBytes(this.m_bRawIV = new byte[this.m_CipherObject.getBlockSize()]);
            sRand.nextBytes(this.m_bRawKey = new byte[this.m_iKeyLen / 8]);
            this.initInternal(1);
        }

        public final void initEncryption(final byte[] bIV, final byte[] bKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException{
            if (this.m_bRawKey != null) {
                for (int i1 = 0; i1 < this.m_bRawKey.length; ++i1) {
                    this.m_bRawKey[i1] = 0;
                }
            }
            if (this.m_bRawIV != null) {
                for (int i2 = 0; i2 < this.m_bRawIV.length; ++i2) {
                    this.m_bRawIV[i2] = 0;
                }
            }
            this.m_bRawIV = bIV;
            this.m_bRawKey = bKey;
            this.initInternal(1);
        }

        public final void initDecryption() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException{
            this.initInternal(2);
        }

        public final void initDecryption(final byte[] bIV, final byte[] bKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException{
            if (this.m_bRawKey != null) {
                for (int i1 = 0; i1 < this.m_bRawKey.length; ++i1) {
                    this.m_bRawKey[i1] = 0;
                }
            }
            if (this.m_bRawIV != null) {
                for (int i2 = 0; i2 < this.m_bRawIV.length; ++i2) {
                    this.m_bRawIV[i2] = 0;
                }
            }
            this.m_bRawIV = bIV;
            this.m_bRawKey = bKey;
            this.initInternal(2);
        }

        public final void initKeyWrap() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException{
            if (this.m_bRawKey != null) {
                for (int i1 = 0; i1 < this.m_bRawKey.length; ++i1) {
                    this.m_bRawKey[i1] = 0;
                }
            }
            if (this.m_bRawIV != null) {
                for (int i2 = 0; i2 < this.m_bRawIV.length; ++i2) {
                    this.m_bRawIV[i2] = 0;
                }
            }

            final SecureRandom sRand = new SecureRandom();
            sRand.nextBytes(this.m_bRawIV = new byte[this.m_CipherObject.getBlockSize()]);
            sRand.nextBytes(this.m_bRawKey = new byte[this.m_iKeyLen / 8]);
            this.initInternal(3);
        }

        public final void initKeyWrap(final byte[] bIV, final byte[] bKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException{
            if (this.m_bRawKey != null) {
                for (int i1 = 0; i1 < this.m_bRawKey.length; ++i1) {
                    this.m_bRawKey[i1] = 0;
                }
            }
            if (this.m_bRawIV != null) {
                for (int i2 = 0; i2 < this.m_bRawIV.length; ++i2) {
                    this.m_bRawIV[i2] = 0;
                }
            }
            this.m_bRawIV = bIV;
            this.m_bRawKey = bKey;
            this.initInternal(3);
        }

        public final void initKeyUnwrap() throws NoSuchAlgorithmException, NoSuchProviderException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException{
            this.initInternal(4);
        }

        public final void initKeyUnwrap(final byte[] bIV, final byte[] bKey) throws NoSuchAlgorithmException, NoSuchProviderException, InvalidParameterSpecException, InvalidKeyException, InvalidAlgorithmParameterException {
            if (this.m_bRawKey != null) {
                for (int i1 = 0; i1 < this.m_bRawKey.length; ++i1) {
                    this.m_bRawKey[i1] = 0;
                }
            }
            if (this.m_bRawIV != null) {
                for (int i2 = 0; i2 < this.m_bRawIV.length; ++i2) {
                    this.m_bRawIV[i2] = 0;
                }
            }
            this.m_bRawIV = bIV;
            this.m_bRawKey = bKey;
            this.initInternal(4);
        }

        public final Provider getProvider() {
            return this.m_CipherObject.getProvider();
        }

        public final String getAlgorithm() {
            return this.m_CipherObject.getAlgorithm();
        }

        public final int getBlockSize() {
            return this.m_CipherObject.getBlockSize();
        }

        public final int getOutputSize(final int inputLen) throws IllegalStateException {
            return this.m_CipherObject.getOutputSize(inputLen);
        }

        public final byte[] getIV() {
            return this.m_CipherObject.getIV();
        }

        public final AlgorithmParameters getParameters() {
            return this.m_CipherObject.getParameters();
        }

        public final ExemptionMechanism getExemptionMechanism() {
            return this.m_CipherObject.getExemptionMechanism();
        }

        public final void init(final int opmode, final Key key) throws InvalidKeyException{
            PwdCrypto.AlgorithmRestrictionCheck(key, this.getAlgorithm());
            this.m_CipherObject.init(opmode, key);
        }

        public final void init(final int opmode, final Key key, final SecureRandom random) throws InvalidKeyException{
            PwdCrypto.AlgorithmRestrictionCheck(key, this.getAlgorithm());
            this.m_CipherObject.init(opmode, key, random);
        }

        public final void init(final int opmode, final Key key, final AlgorithmParameterSpec params) throws InvalidKeyException, InvalidAlgorithmParameterException {
            PwdCrypto.AlgorithmRestrictionCheck(key, this.getAlgorithm());
            this.m_CipherObject.init(opmode, key, params);
        }

        public final void init(final int opmode, final Key key, final AlgorithmParameterSpec params, final SecureRandom random) throws InvalidKeyException, InvalidAlgorithmParameterException{
            PwdCrypto.AlgorithmRestrictionCheck(key, this.getAlgorithm());
            this.m_CipherObject.init(opmode, key, params, random);
        }

        public final void init(final int opmode, final Key key, final AlgorithmParameters params) throws InvalidKeyException, InvalidAlgorithmParameterException{
            PwdCrypto.AlgorithmRestrictionCheck(key, this.getAlgorithm());
            this.m_CipherObject.init(opmode, key, params);
        }

        public final void init(final int opmode, final Key key, final AlgorithmParameters params, final SecureRandom random) throws InvalidKeyException, InvalidAlgorithmParameterException{
            PwdCrypto.AlgorithmRestrictionCheck(key, this.getAlgorithm());
            this.m_CipherObject.init(opmode, key, params, random);
        }

        public final void init(final int opmode, final java.security.cert.Certificate certificate) throws InvalidKeyException {
            PwdCrypto.AlgorithmRestrictionCheck(certificate);
            this.m_CipherObject.init(opmode, certificate);
        }

        public final void init(final int opmode, final Certificate certificate, final SecureRandom random) throws InvalidKeyException{
            PwdCrypto.AlgorithmRestrictionCheck(certificate);
            this.m_CipherObject.init(opmode, certificate, random);
        }

        public final byte[] update(final byte[] input) throws IllegalStateException {
            return this.m_CipherObject.update(input);
        }

        public final byte[] update(final byte[] input, final int inputOffset, final int inputLen) throws IllegalStateException {
            return this.m_CipherObject.update(input, inputOffset, inputLen);
        }

        public final int update(final byte[] input, final int inputOffset, final int inputLen, final byte[] output) throws IllegalStateException, ShortBufferException {
            return this.m_CipherObject.update(input, inputOffset, inputLen, output);
        }

        public final int update(final byte[] input, final int inputOffset, final int inputLen, final byte[] output, final int outputOffset) throws IllegalStateException, ShortBufferException {
            return this.m_CipherObject.update(input, inputOffset, inputLen, output, outputOffset);
        }

        public final byte[] doFinal() throws IllegalStateException, IllegalBlockSizeException, BadPaddingException {
            return this.m_CipherObject.doFinal();
        }

        public final int doFinal(final byte[] output, final int outputOffset) throws IllegalStateException, IllegalBlockSizeException, ShortBufferException, BadPaddingException {
            return this.m_CipherObject.doFinal(output, outputOffset);
        }

        public final byte[] doFinal(final byte[] input) throws IllegalStateException, IllegalBlockSizeException, BadPaddingException {
            return this.m_CipherObject.doFinal(input);
        }

        public final byte[] doFinal(final byte[] input, final int inputOffset, final int inputLen) throws IllegalStateException, IllegalBlockSizeException, BadPaddingException {
            return this.m_CipherObject.doFinal(input, inputOffset, inputLen);
        }

        public final int doFinal(final byte[] input, final int inputOffset, final int inputLen, final byte[] output) throws IllegalStateException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {
            return this.m_CipherObject.doFinal(input, inputOffset, inputLen, output);
        }

        public final int doFinal(final byte[] input, final int inputOffset, final int inputLen, final byte[] output, final int outputOffset) throws IllegalStateException, ShortBufferException, IllegalBlockSizeException, BadPaddingException {
            return this.m_CipherObject.doFinal(input, inputOffset, inputLen, output, outputOffset);
        }

        public final byte[] wrap(final Key key) throws IllegalStateException, IllegalBlockSizeException, InvalidKeyException {
            return this.m_CipherObject.wrap(key);
        }

        public final Key unwrap(final byte[] wrappedKey, final String wrappedKeyAlgorithm, final int wrappedKeyType) throws IllegalStateException, InvalidKeyException, NoSuchAlgorithmException {
            return this.m_CipherObject.unwrap(wrappedKey, wrappedKeyAlgorithm, wrappedKeyType);
        }

        public Cipher getM_CipherObject() {
            return this.m_CipherObject;
        }

        static {
            fipsMode = PwdController.getFIPSMode();
        }
    }
