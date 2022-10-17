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


import java.io.ByteArrayOutputStream;
import java.math.BigInteger;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static java.math.BigInteger.ONE;

public class PwdPKCS12KeyIV {

    private PwdPKCS12KeyIV() {
    }

    private static int getLength(final double length) {
        final double size = 64.0;
        return (int) size * (int) Math.ceil(length / size);
    }

    private static void copy(final byte[] r, final byte[] b) {
        final int bse = 64;
        final int s = 20;
        for (int i = 1; i <= bse / s; ++i) {
            copyAll(r, 0, b, s * (i - 1), r.length);
        }
        copyAll(r, 0, b, bse / s * s, bse % s);
    }

    private static void pad(final byte[] saltPass, final byte[] initialInt) {
        byte[] saltPassBlock;
        BigInteger saltPassIntSum;
        for (int j = 1; j < saltPass.length - 1; j += 64) {
            saltPassBlock = new byte[64];
            copyAll(saltPass, j - 1, saltPassBlock, 0, 64);
            saltPassIntSum = new BigInteger(initialInt);
            saltPassIntSum = saltPassIntSum.add(ONE).add(new BigInteger(saltPassBlock));
            saltPassBlock = trim(saltPassIntSum.toByteArray(), 64);
            copyAll(saltPassBlock, 0, saltPass, j - 1, 64);
        }
    }

    private static byte[] trim(final byte[] b, final int size) {
        if (b == null) {
            return new byte[0];
        }
        if (b.length == size) {
            return b;
        }
        if (b.length < size) {
            final byte[] t = new byte[size];
            int i;
            for (i = 0; i < size / b.length; ++i) {
                copyAll(b, 0, t, i, b.length);
            }
            copyAll(b, 0, t, i * b.length, size % b.length);
            return t;
        } else {
            final byte[] t = new byte[size];
            copyAll(b, 0, t, 0, size);
            return t;
        }
    }

    public static byte[] genKeyBytes(final PwdMessageDigest md, final byte[] password, final byte[] salt, final int ic, final int keyLen) {
        return genBytes(1, md, password, salt, ic, keyLen);
    }

    public static byte[] genIVBytes(final PwdMessageDigest md, final byte[] password, final byte[] salt, final int ic, final int keyLen) {
        return genBytes(2, md, password, salt, ic, keyLen);
    }

    public static byte[] genBytes(final int opmode, final PwdMessageDigest md, final byte[] password, final byte[] salt, final int ic, final int keyLen) {
        if (password == null || salt == null) {
            throw internalServerError("CMPKCS12KeyIV: Error in genBytes!!!");
        }
        if (opmode != 1 && opmode != 2) {
            throw internalServerError("CMPKCS12KeyIV: Error in genBytes!!!");
        }
        try {
            final ByteArrayOutputStream bos = new ByteArrayOutputStream();
            final byte[] opmodeBytes = new byte[64];
            for (int i = 0; i < 64; ++i) {
                opmodeBytes[i] = (byte) opmode;
            }
            if (salt.length != 0) {
                final int saltLen = getLength(salt.length);
                for (int j = 0; j < saltLen / salt.length; ++j) {
                    bos.write(salt);
                }
                bos.write(salt, 0, saltLen % salt.length);
            }
            if (password.length != 0) {
                final int passLen = getLength(password.length);
                for (int j = 0; j < passLen / password.length; ++j) {
                    bos.write(password);
                }
                bos.write(password, 0, passLen % password.length);
            }
            final byte[] saltPass = bos.toByteArray();
            final int keyIteration = (int) Math.ceil(keyLen / 20.0);
            final byte[] finishedBytes = new byte[1280];
            byte[] bigInt;
            for (int k = 0; k < keyIteration; ++k) {
                md.reset();
                md.update(opmodeBytes);
                md.update(saltPass);
                for (int l = 1; l < ic; ++l) {
                    final byte[] hashResult = md.digest();
                    md.reset();
                    md.update(hashResult);
                }
                final byte[] result = md.digest();
                bigInt = new byte[64];
                copy(result, bigInt);
                copyAll(bigInt, 0, finishedBytes, k * 20, 20);
                pad(saltPass, bigInt);
            }
            final byte[] result2 = new byte[keyLen];
            copyAll(finishedBytes, 0, result2, 0, keyLen);
            return result2;
        } catch (Exception e) {
            throw internalServerError(e.getMessage());
        }
    }

    private static void copyAll(final byte[] s, final int soff, final byte[] d, final int doff, final int length) {
        System.arraycopy(s, soff, d, doff, length);
    }
}

