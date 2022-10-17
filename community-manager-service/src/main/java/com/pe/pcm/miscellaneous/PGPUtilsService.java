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

package com.pe.pcm.miscellaneous;

import com.pe.pcm.exception.GlobalExceptionHandler;
import org.apache.commons.io.FileUtils;
import org.bouncycastle.bcpg.ArmoredOutputStream;
import org.bouncycastle.jce.provider.BouncyCastleProvider;
import org.bouncycastle.openpgp.*;
import org.bouncycastle.openpgp.jcajce.JcaPGPObjectFactory;
import org.bouncycastle.openpgp.operator.PBESecretKeyDecryptor;
import org.bouncycastle.openpgp.operator.PublicKeyDataDecryptorFactory;
import org.bouncycastle.openpgp.operator.jcajce.*;
import org.springframework.stereotype.Service;

import java.io.*;
import java.security.SecureRandom;
import java.security.Security;
import java.util.Iterator;

@Service
public class PGPUtilsService {

    public void decryptFile(InputStream in, InputStream keyIn, char[] passwd, File fileToSave) throws IOException,
            PGPException {
        Security.addProvider(new BouncyCastleProvider());
        in = org.bouncycastle.openpgp.PGPUtil.getDecoderStream(in);
        JcaPGPObjectFactory pgpF = new JcaPGPObjectFactory(in);
        //PGPObjectFactory pgpF = new PGPObjectFactory(in)

        PGPEncryptedDataList enc;
        Object o = pgpF.nextObject();
        // the first object might be a PGP marker packet.
        if (o instanceof PGPEncryptedDataList) {
            enc = (PGPEncryptedDataList) o;
        } else {
            enc = (PGPEncryptedDataList) pgpF.nextObject();
        }

        // find the secret key
        Iterator<PGPEncryptedData> it = enc.getEncryptedDataObjects();
        PGPPrivateKey sKey = null;
        PGPPublicKeyEncryptedData pbe = null;

        while (sKey == null && it.hasNext()) {
            pbe = (PGPPublicKeyEncryptedData) it.next();
            sKey = findSecretKey(keyIn, pbe.getKeyID(), passwd);
        }

        if (sKey == null) {
            throw new IllegalArgumentException("Secret key for message not found.");
        }

        PublicKeyDataDecryptorFactory publicKeyDataDecryptorFactory = new JcePublicKeyDataDecryptorFactoryBuilder()
                .setProvider("BC").setContentProvider("BC").build(sKey);
        InputStream clear = pbe.getDataStream(publicKeyDataDecryptorFactory);
        JcaPGPObjectFactory plainFact = new JcaPGPObjectFactory(clear);
        //PGPObjectFactory plainFact = new PGPObjectFactory(clear)

        Object message = plainFact.nextObject();
        if (message instanceof PGPCompressedData) {
            PGPCompressedData cData = (PGPCompressedData) message;
            JcaPGPObjectFactory pgpFact = new JcaPGPObjectFactory(cData.getDataStream());
            //PGPObjectFactory pgpFact = new PGPObjectFactory(cData.getDataStream())
            message = pgpFact.nextObject();
        }

        if (message instanceof PGPLiteralData) {
            PGPLiteralData ld = (PGPLiteralData) message;
            try (InputStream inputStream = ld.getInputStream()) {
                if (fileToSave != null) {
                    FileUtils.copyInputStreamToFile(inputStream, fileToSave);
                } else {
                    throw GlobalExceptionHandler.internalServerError("destination file should not be null.");
                }
            }
        } else if (message instanceof PGPOnePassSignatureList) {
            throw new PGPException("Encrypted message contains a signed message - not literal data.");
        } else {
            throw new PGPException("Message is not a simple encrypted file - type unknown.");
        }

        if (pbe.isIntegrityProtected() && !pbe.verify()) {
            throw new PGPException("Message failed integrity check");
        }
    }

    public void encryptFile(OutputStream out, String fileName, PGPPublicKey encKey, boolean armor,
                            boolean withIntegrityCheck) throws IOException, PGPException {
        Security.addProvider(new BouncyCastleProvider());

        if (armor) {
            out = new ArmoredOutputStream(out);
        }

        ByteArrayOutputStream bOut = new ByteArrayOutputStream();
        PGPCompressedDataGenerator comData = new PGPCompressedDataGenerator(PGPCompressedData.ZIP);
        PGPUtil.writeFileToLiteralData(comData.open(bOut), PGPLiteralData.BINARY,
                new File(fileName));
        comData.close();

        JcePGPDataEncryptorBuilder jcePGPDataEncryptorBuilder = new JcePGPDataEncryptorBuilder(PGPEncryptedData.CAST5)
                .setWithIntegrityPacket(withIntegrityCheck).setSecureRandom(new SecureRandom()).setProvider("BC");

        PGPEncryptedDataGenerator cPk = new PGPEncryptedDataGenerator(jcePGPDataEncryptorBuilder);
        JcePublicKeyKeyEncryptionMethodGenerator jcePublicKeyKeyEncryptionMethodGenerator =
                new JcePublicKeyKeyEncryptionMethodGenerator(encKey).setProvider(new BouncyCastleProvider())
                        .setSecureRandom(new SecureRandom());
        cPk.addMethod(jcePublicKeyKeyEncryptionMethodGenerator);

        byte[] bytes = bOut.toByteArray();

        try (OutputStream cOut = cPk.open(out, bytes.length)) {
            cOut.write(bytes);
        }
        out.close();
    }

    public PGPPublicKey readPublicKey(InputStream in) throws IOException, PGPException {
        in = org.bouncycastle.openpgp.PGPUtil.getDecoderStream(in);
        PGPPublicKeyRingCollection pgpPub = new PGPPublicKeyRingCollection(in, new JcaKeyFingerprintCalculator());

        //
        // we just loop through the collection till we find a key suitable for
        // encryption, in the real
        // world you would probably want to be a bit smarter about this.
        //
        PGPPublicKey key = null;

        //
        // iterate through the key rings.
        //
        Iterator<PGPPublicKeyRing> rIt = pgpPub.getKeyRings();

        while (key == null && rIt.hasNext()) {
            PGPPublicKeyRing kRing = rIt.next();
            Iterator<PGPPublicKey> kIt = kRing.getPublicKeys();
            while (key == null && kIt.hasNext()) {
                PGPPublicKey k = kIt.next();

                if (k.isEncryptionKey()) {
                    key = k;
                }
            }
        }
        if (key == null) {
            throw new IllegalArgumentException("Can't find encryption key in key ring.");
        }
        return key;
    }

    private static PGPPrivateKey findSecretKey(InputStream keyIn, long keyID, char[] pass)
            throws IOException, PGPException {
        PGPSecretKeyRingCollection pgpSec = new PGPSecretKeyRingCollection(
                PGPUtil.getDecoderStream(keyIn), new JcaKeyFingerprintCalculator());

        PGPSecretKey pgpSecKey = pgpSec.getSecretKey(keyID);
        if (pgpSecKey == null) {
            return null;
        }
        PBESecretKeyDecryptor pbeSecretKeyDecryptor = new JcePBESecretKeyDecryptorBuilder(
                new JcaPGPDigestCalculatorProviderBuilder().setProvider("BC").build()).setProvider("BC").build(pass);

        return pgpSecKey.extractPrivateKey(pbeSecretKeyDecryptor);
    }

}
