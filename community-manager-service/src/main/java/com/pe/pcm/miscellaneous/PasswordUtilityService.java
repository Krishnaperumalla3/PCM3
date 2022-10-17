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

import com.pe.pcm.config.FileArchiveProperties;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.PBEKeySpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.Base64;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.loadDataAsStringFromPath;
import static com.pe.pcm.utils.PCMConstants.AES;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Service
public class PasswordUtilityService {
    private static final Logger LOGGER = LoggerFactory.getLogger(PasswordUtilityService.class);

    private static final String KEY = "PS)^@.]7J(Fx#E";
    private static final String SALT = "SI.Sal@Enc-Ks@J)";
    private static final byte[] IV = {0, 0, 5, 0, 0, 6, 0, 1, 0, 8, 0, 0, 0, 5, 5, 0};

    private FileArchiveProperties fileArchiveProperties;

    @Autowired
    public PasswordUtilityService(FileArchiveProperties fileArchiveProperties) {
        this.fileArchiveProperties = fileArchiveProperties;
    }

    public PasswordUtilityService() {
    }


    public String decryptFileWithJava8(String filePath) {
        try {
            return new String(getCipher(DECRYPT_MODE, TRUE).doFinal(Base64.getDecoder().decode(loadDataAsStringFromPath.apply(filePath))));
        } catch (Exception e) {
            throw internalServerError(e.getMessage());
        }
    }

    public String decryptValueWithJava8(String data) {
        try {
            return new String(getCipher(DECRYPT_MODE, FALSE).doFinal(Base64.getDecoder().decode(data)));
        } catch (Exception e) {
            return data;
        }
    }

    public CipherInputStream decryptFileWithJava8(InputStream inputStream) {
        try {
            Cipher cipher = getCipher(DECRYPT_MODE, TRUE);
            return new CipherInputStream(inputStream, cipher);
        } catch (Exception e) {
            throw internalServerError(e.getMessage());
        }
    }

//    public String decryptFileWithJava7(String filePath)
//            return new String(getCipher(DECRYPT_MODE, TRUE).doFinal(new BASE64Decoder().decodeBuffer(loadDataAsStringFromPath.apply(filePath))), StandardCharsets.UTF_8)

    public String encryptFileWithJava8(String strToEncrypt) {
        try {
            return Base64.getEncoder().encodeToString(getCipher(ENCRYPT_MODE, FALSE).doFinal(strToEncrypt.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw internalServerError(e.getMessage());
        }
    }

    public String decrypt(String value) {
        try {
            return new String(getCipher(DECRYPT_MODE, FALSE).doFinal(Base64.getDecoder().decode(value)));
        } catch (IllegalBlockSizeException ie) {
            LOGGER.info("Error While Decrypting(IllegalBlockSizeException) : ", ie);
            throw internalServerError("IllegalBlockSizeException");
        } catch (Exception e) {
            LOGGER.info("Error While Decrypting : {}", e.getMessage());
            throw internalServerError(e.getMessage());
        }
    }

    public String encrypt(String value) {
        try {
            return Base64.getEncoder().encodeToString(getCipher(ENCRYPT_MODE, FALSE).doFinal(value.getBytes(StandardCharsets.UTF_8)));
        } catch (Exception e) {
            throw internalServerError(e.getMessage());
        }
    }

    public String getEncryptedValue(String value) {
        if (hasText(value)) {
            try {
                decrypt(value);
            } catch (Exception e) {
                return encrypt(value);
            }
        }
        return value;
    }

    public String getDecryptedValue(String value) {
        if (hasText(value)) {
            try {
                return decrypt(value);
            } catch (Exception e) {
                //It can be empty
            }
        }
        return value;
    }

    private Cipher getCipher(int flowMode, Boolean isFile) throws InvalidKeySpecException, NoSuchAlgorithmException, NoSuchPaddingException,
            InvalidKeyException, InvalidAlgorithmParameterException {

        SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA256");
        SecretKey secretKey = factory.generateSecret(new PBEKeySpec(isFile ? decrypt(fileArchiveProperties.getAes().getSecretKey()).toCharArray() : KEY.toCharArray(),
                isFile ? decrypt(fileArchiveProperties.getAes().getSalt()).getBytes() : SALT.getBytes(), 65536, 256));

        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING"); //AES/GCM/NoPadding
        cipher.init(flowMode, new SecretKeySpec(secretKey.getEncoded(), AES), new IvParameterSpec(IV));
        return cipher;
    }

}
