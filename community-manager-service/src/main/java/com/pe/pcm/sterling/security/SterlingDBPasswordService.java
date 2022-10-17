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

package com.pe.pcm.sterling.security;

import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.si.pwd.security.PwdPBEWrapper;
import com.pe.pcm.si.pwd.security.PwdSecurityFlatUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.nio.charset.StandardCharsets;
import java.security.SecureRandom;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.CommonFunctions.isNotNull;
import static com.pe.pcm.utils.CommonFunctions.removeENC;

@Service
public class SterlingDBPasswordService {
    private static final Logger LOGGER = LoggerFactory.getLogger(SterlingDBPasswordService.class);

    private byte[] rootPassphrase;
    private String stringPassphrase;
    private final PasswordUtilityService passwordUtilityService;

    @Autowired
    public SterlingDBPasswordService(@Value("${sterling-b2bi.user.cmks}") String stringPassphrase, PasswordUtilityService passwordUtilityService) {
        this.stringPassphrase = stringPassphrase;
        this.passwordUtilityService = passwordUtilityService;
    }

    public String encrypt(final String str) {
        final byte[] b = encryptPassphrase(str.toCharArray());
        return encodeToString(b);
    }

    public String decrypt(final String str) {
        final byte[] b = decodeToBytes(str);
        return new String(decryptPassphrase(b));
    }

    private byte[] encryptPassphrase(final char[] bInput) {
        try {
            final PwdPBEWrapper wrapper = new PwdPBEWrapper();
            final SecureRandom random = new SecureRandom();
            final byte[] bSalt = new byte[8];
            random.nextBytes(bSalt);
            return wrapper.encrypt(101, rootPassphrase, bSalt, new String(bInput).getBytes(StandardCharsets.UTF_8));
        } catch (Exception e) {
            LOGGER.error("Exception encrypting passphrase ", e);
            throw internalServerError(e.getMessage());
        }
    }

    private char[] decryptPassphrase(final byte[] bInput) {
        try {
            final PwdPBEWrapper wrapper = new PwdPBEWrapper(bInput);
            final byte[] decrypted = wrapper.decrypt(rootPassphrase);
            return new String(decrypted, StandardCharsets.UTF_8).toCharArray();
        } catch (Exception e) {
            LOGGER.error("Exception decrypting passphrase ", e);
            throw internalServerError(e.getMessage());
        }
    }

    private byte[] decodeToBytes(final String ssInput) {
        try {
            final ByteArrayInputStream iStream = new ByteArrayInputStream(ssInput.getBytes(StandardCharsets.US_ASCII));
            final ByteArrayOutputStream oStream = new ByteArrayOutputStream();
            PwdSecurityFlatUtil.base64Decode(iStream, oStream);
            return oStream.toByteArray();
        } catch (Exception e) {
            LOGGER.error("Exception decoding string {}", ssInput, e);
            throw internalServerError(e.getMessage());
        }
    }

    private String encodeToString(final byte[] bInput) {
        final int tmpNum = 3;
        final int tNum = 4;
        try {
            int iLen = bInput.length / tmpNum * tNum;
            if (bInput.length % tmpNum != 0) {
                iLen += tNum;
            }
            final byte[] bOutput = new byte[iLen];
            PwdSecurityFlatUtil.base64EncodeLine(bInput, bOutput, bInput.length);
            return new String(bOutput, StandardCharsets.US_ASCII);
        } catch (Exception e) {
            LOGGER.error("Exception encoding bytes ", e);
            throw internalServerError(e.getMessage());
        }
    }

    @PostConstruct
    private void getPassphrase() {
        if (isNotNull(stringPassphrase)) {
            if (stringPassphrase.startsWith("ENC")) {
                stringPassphrase = passwordUtilityService.decrypt(removeENC(stringPassphrase));
                rootPassphrase = stringPassphrase.getBytes(StandardCharsets.US_ASCII);
            } else {
                rootPassphrase = stringPassphrase.getBytes(StandardCharsets.US_ASCII);
            }
        } else {
            LOGGER.info("Please Pass the Value for sterling.user.cmks");
        }
    }

    private void passphraseValidation() {

    }
}

