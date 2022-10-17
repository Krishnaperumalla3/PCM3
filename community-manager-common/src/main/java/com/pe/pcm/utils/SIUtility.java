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

package com.pe.pcm.utils;


import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.si.pwd.Base64Coder;
import org.springframework.util.StringUtils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.US_ASCII;
import static java.nio.charset.StandardCharsets.UTF_8;

public class SIUtility {

    private SIUtility(){}

    public static String getEncryptedPassword(String pwdString, String pwdSalt) {
        if (pwdSalt == null) {
            return hashPassword(pwdString);
        }
        return hashPassword(pwdString, pwdSalt);
    }

    private static String hashPassword(String ssPassword) {
        String out = null;

        try {
            MessageDigest digest = MessageDigest.getInstance("SHA");
            if (digest != null) {
                byte[] bOutput = digest.digest(ssPassword.getBytes());
                byte[] b64Output = new byte[28];
                Base64Coder.base64EncodeLine(bOutput, b64Output, 20);
                out = new String(b64Output);
            }

            return out;
        } catch (NoSuchAlgorithmException var5) {
            throw new CommunityManagerServiceException(var5);
        }
    }

    private static String hashPassword(String ssPassword, String salt) {
        if (!StringUtils.hasText(salt)) {
            return hashPassword(ssPassword);
        } else {
            byte[] bEncodedSalt = salt.getBytes();
            byte[] buff = new byte[bEncodedSalt.length];
            int size = Base64Coder.base64DecodeLine(bEncodedSalt, buff, bEncodedSalt.length);
            byte[] bDecodedSalt = new byte[size];
            System.arraycopy(buff, 0, bDecodedSalt, 0, size);
            return hashPassword(ssPassword, bDecodedSalt);
        }
    }

    private static String hashPassword(String ssPassword, byte[] bSalt) {

        try {
            byte[] bPassword = ssPassword.getBytes(UTF_8);
            int length = bSalt.length + bPassword.length;
            byte[] saltedPassword = new byte[length];
            System.arraycopy(bPassword, 0, saltedPassword, 0, bPassword.length);
            System.arraycopy(bSalt, 0, saltedPassword, bPassword.length, bSalt.length);
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] bOutput1 = digest.digest(saltedPassword);
            byte[] b64Output = new byte[50];
            int len = Base64Coder.base64EncodeLine(bOutput1, b64Output, bOutput1.length);
            if (len == 50) {
                throw new IllegalStateException("Base64 Overflow [" + len + "]");
            } else {
                return new String(b64Output, 0, len, US_ASCII);
            }
        } catch (NoSuchAlgorithmException var10) {
            throw new CommunityManagerServiceException(var10);
        }
    }
}

