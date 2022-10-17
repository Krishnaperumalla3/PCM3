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

package com.pe.pcm.si.pwd;

import com.pe.pcm.exception.CommunityManagerServiceException;

import java.io.*;


public class Base64Coder
        implements Base64Xerces {
    private static final byte[] BASE64Index = {65, 66, 67, 68, 69, 70, 71, 72, 73, 74, 75, 76, 77, 78, 79, 80, 81, 82, 83, 84, 85, 86, 87, 88, 89, 90, 97, 98, 99, 100, 101, 102, 103, 104, 105, 106, 107, 108, 109, 110, 111, 112, 113, 114, 115, 116, 117, 118, 119, 120, 121, 122, 48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 43, 47};


    private static final byte[] Base64Indicies = new byte[128];

    static {
        for (int i = 0; i < 128; i++) {
            Base64Indicies[i] = -1;
        }

        Base64Indicies[65] = 0;
        Base64Indicies[66] = 1;
        Base64Indicies[67] = 2;
        Base64Indicies[68] = 3;
        Base64Indicies[69] = 4;
        Base64Indicies[70] = 5;
        Base64Indicies[71] = 6;
        Base64Indicies[72] = 7;
        Base64Indicies[73] = 8;
        Base64Indicies[74] = 9;
        Base64Indicies[75] = 10;
        Base64Indicies[76] = 11;
        Base64Indicies[77] = 12;
        Base64Indicies[78] = 13;
        Base64Indicies[79] = 14;

        Base64Indicies[80] = 15;
        Base64Indicies[81] = 16;
        Base64Indicies[82] = 17;
        Base64Indicies[83] = 18;
        Base64Indicies[84] = 19;
        Base64Indicies[85] = 20;
        Base64Indicies[86] = 21;
        Base64Indicies[87] = 22;
        Base64Indicies[88] = 23;
        Base64Indicies[89] = 24;
        Base64Indicies[90] = 25;

        Base64Indicies[97] = 26;
        Base64Indicies[98] = 27;
        Base64Indicies[99] = 28;
        Base64Indicies[100] = 29;
        Base64Indicies[101] = 30;
        Base64Indicies[102] = 31;
        Base64Indicies[103] = 32;
        Base64Indicies[104] = 33;
        Base64Indicies[105] = 34;
        Base64Indicies[106] = 35;
        Base64Indicies[107] = 36;
        Base64Indicies[108] = 37;
        Base64Indicies[109] = 38;
        Base64Indicies[110] = 39;
        Base64Indicies[111] = 40;

        Base64Indicies[112] = 41;
        Base64Indicies[113] = 42;
        Base64Indicies[114] = 43;
        Base64Indicies[115] = 44;
        Base64Indicies[116] = 45;
        Base64Indicies[117] = 46;
        Base64Indicies[118] = 47;
        Base64Indicies[119] = 48;
        Base64Indicies[120] = 49;
        Base64Indicies[121] = 50;
        Base64Indicies[122] = 51;

        Base64Indicies[48] = 52;
        Base64Indicies[49] = 53;
        Base64Indicies[50] = 54;
        Base64Indicies[51] = 55;
        Base64Indicies[52] = 56;
        Base64Indicies[53] = 57;
        Base64Indicies[54] = 58;
        Base64Indicies[55] = 59;
        Base64Indicies[56] = 60;
        Base64Indicies[57] = 61;

        Base64Indicies[43] = 62;
        Base64Indicies[47] = 63;
    }

    public static byte getIndex(byte b) {
        if ((b >= 0) && (b < 128)) {
            return Base64Indicies[b];
        }
        return -1;
    }


    public static int base64DecodeLine(byte[] bInputBuffer, byte[] bOutputBuffer, int iInputLength) {
        int i = 0;
        int iCounter = 0;
        int iReturn = -1;

        byte bTemp1 = 0;
        byte bTemp2 = 0;

        int iInputByteOffset = 0;
        int iOutputByteOffset = 0;


        iCounter = iInputLength / 4;

        for (i = 0; i < iCounter; i++) {

            if ((bInputBuffer[iInputByteOffset] == 32) || (bInputBuffer[iInputByteOffset] == 9)) {
                iInputByteOffset++;

            } else {

                bTemp1 = (byte) (bInputBuffer[iInputByteOffset] & 0x7F);
                iInputByteOffset++;


                bTemp2 = (byte) (bInputBuffer[iInputByteOffset] & 0x7F);
                iInputByteOffset++;

                bTemp1 = getIndex(bTemp1);
                bTemp2 = getIndex(bTemp2);

                if ((bTemp1 >= 0) && (bTemp2 >= 0)) {
                    bOutputBuffer[iOutputByteOffset] = ((byte) ((bTemp1 << 2 & 0xFC) + (bTemp2 >> 4 & 0x3)));
                    iOutputByteOffset++;
                }

                bTemp1 = bTemp2;
                bTemp2 = (byte) (bInputBuffer[iInputByteOffset] & 0x7F);
                iInputByteOffset++;

                bTemp2 = getIndex(bTemp2);

                if ((bTemp1 >= 0) && (bTemp2 >= 0)) {
                    bOutputBuffer[iOutputByteOffset] = ((byte) ((bTemp1 << 4 & 0xF0) + (bTemp2 >> 2 & 0xF)));
                    iOutputByteOffset++;
                }

                bTemp1 = bTemp2;
                bTemp2 = (byte) (bInputBuffer[iInputByteOffset] & 0x7F);
                iInputByteOffset++;

                bTemp2 = getIndex(bTemp2);

                if ((bTemp1 >= 0) && (bTemp2 >= 0)) {
                    bOutputBuffer[iOutputByteOffset] = ((byte) ((bTemp1 << 6 & 0xC0) + (bTemp2 & 0x3F)));
                    iOutputByteOffset++;
                }
            }
        }
        iReturn = iOutputByteOffset;

        return iReturn;
    }


    public static int base64EncodeLine(byte[] bInputBuffer, byte[] bOutputBuffer, int iInputSize) {
        int i = 0;
        int iCounter = 0;
        int iPad = 0;
        int iReturn = -1;
        int iInputByteOffset = 0;
        int iOutputByteOffset = 0;
        byte bTemp1 = 0;
        byte bTemp2 = 0;

        iCounter = iInputSize / 3;
        iPad = iInputSize % 3;


        for (i = 0; i < iCounter; i++) {


            bTemp1 = (byte) (bInputBuffer[iInputByteOffset] >> 2 & 0x3F);
            bOutputBuffer[iOutputByteOffset] = BASE64Index[bTemp1];
            iOutputByteOffset++;


            bTemp1 = (byte) (bInputBuffer[iInputByteOffset] << 4 & 0x30);
            iInputByteOffset++;
            bTemp2 = (byte) (bInputBuffer[iInputByteOffset] >> 4 & 0xF);
            bOutputBuffer[iOutputByteOffset] = BASE64Index[(bTemp1 + bTemp2)];
            iOutputByteOffset++;


            bTemp1 = (byte) (bInputBuffer[iInputByteOffset] << 2 & 0x3C);
            iInputByteOffset++;
            bTemp2 = (byte) (bInputBuffer[iInputByteOffset] >> 6 & 0x3);
            bOutputBuffer[iOutputByteOffset] = BASE64Index[(bTemp1 + bTemp2)];
            iOutputByteOffset++;


            bTemp1 = (byte) (bInputBuffer[iInputByteOffset] & 0x3F);
            bOutputBuffer[iOutputByteOffset] = BASE64Index[bTemp1];
            iOutputByteOffset++;
            iInputByteOffset++;
        }

        switch (iPad) {


            case 1:
                bTemp1 = (byte) (bInputBuffer[iInputByteOffset] >> 2 & 0x3F);
                bOutputBuffer[iOutputByteOffset] = BASE64Index[bTemp1];
                iOutputByteOffset++;


                bTemp1 = (byte) (bInputBuffer[iInputByteOffset] << 4 & 0x30);
                bTemp2 = 0;
                bOutputBuffer[iOutputByteOffset] = BASE64Index[(bTemp1 + bTemp2)];
                iOutputByteOffset++;
                bOutputBuffer[iOutputByteOffset] = 61;
                iOutputByteOffset++;
                bOutputBuffer[iOutputByteOffset] = 61;
                iOutputByteOffset++;
                break;


            case 2:
                bTemp1 = (byte) (bInputBuffer[iInputByteOffset] >> 2 & 0x3F);
                bOutputBuffer[iOutputByteOffset] = BASE64Index[bTemp1];
                iOutputByteOffset++;


                bTemp1 = (byte) (bInputBuffer[iInputByteOffset] << 4 & 0x30);
                iInputByteOffset++;
                bTemp2 = (byte) (bInputBuffer[iInputByteOffset] >> 4 & 0xF);
                bOutputBuffer[iOutputByteOffset] = BASE64Index[(bTemp1 + bTemp2)];
                iOutputByteOffset++;


                bTemp1 = (byte) (bInputBuffer[iInputByteOffset] << 2 & 0x3C);
                bTemp2 = 0;
                bOutputBuffer[iOutputByteOffset] = BASE64Index[(bTemp1 + bTemp2)];
                iOutputByteOffset++;
                bOutputBuffer[iOutputByteOffset] = 61;
                iOutputByteOffset++;
                break;

            default:
                // No Implementations Needed.
        }


        iReturn = iOutputByteOffset;
        return iReturn;
    }

    static int base64Decode(InputStream iStream, OutputStream oStream)
            throws IOException {
        int iReturn = 0;
        byte bTemp1 = 0;
        byte bTemp2 = 0;
        int iRead = 0;
        byte[] b4 = new byte[4];
        int b4Counter = 0;
        boolean bGot4 = false;
        boolean bEndOfStream = false;

        while (!bEndOfStream) {
            b4Counter = 0;
            b4[0] = 0;
            b4[1] = 0;
            b4[2] = 0;
            b4[3] = 0;

            while (!bGot4) {
                iRead = iStream.read();
                if (iRead < 0) {
                    bEndOfStream = true;
                } else {
                    if (getIndex((byte) iRead) >= 0) {
                        b4[b4Counter] = ((byte) iRead);
                        b4Counter++;
                    }
                    if (b4Counter > 3)
                        break;
                }
            }
            if ((!bEndOfStream) || (b4Counter > 0)) {


                bTemp1 = (byte) (b4[0] & 0x7F);


                bTemp2 = (byte) (b4[1] & 0x7F);

                bTemp1 = getIndex((byte) (b4[0] & 0x7F));
                bTemp2 = getIndex((byte) (b4[1] & 0x7F));

                if ((bTemp1 >= 0) && (bTemp2 >= 0)) {
                    oStream.write((byte) ((bTemp1 << 2 & 0xFC) + (bTemp2 >> 4 & 0x3)));
                }

                bTemp1 = bTemp2;
                bTemp2 = (byte) (b4[2] & 0x7F);

                bTemp2 = getIndex((byte) (b4[2] & 0x7F));

                if ((bTemp1 >= 0) && (bTemp2 >= 0)) {
                    oStream.write((byte) ((bTemp1 << 4 & 0xF0) + (bTemp2 >> 2 & 0xF)));
                }

                bTemp1 = bTemp2;
                bTemp2 = (byte) (b4[3] & 0x7F);

                bTemp2 = getIndex((byte) (b4[3] & 0x7F));

                if ((bTemp1 >= 0) && (bTemp2 >= 0)) {
                    oStream.write((byte) ((bTemp1 << 6 & 0xC0) + (bTemp2 & 0x3F)));
                }
            }
        }
        return iReturn;
    }

    public byte[] decode(byte[] content) {
        if (content.length == 0) {
            return new byte[0];
        }
        InputStream in = new ByteArrayInputStream(content);
        ByteArrayOutputStream out = new ByteArrayOutputStream(content.length);
        try {
            base64Decode(in, out);
        } catch (IOException e) {
            throw new CommunityManagerServiceException(e);
        }
        return out.toByteArray();
    }

    public byte[] encode(byte[] content) {
        if (content.length == 0) {
            return new byte[0];
        }
        int iCounter = content.length / 3;
        int iPad = content.length % 3;

        int size = iCounter * 4;
        if (iPad > 0) {
            size += 4;
        }

        byte[] out = new byte[size];
        base64EncodeLine(content, out, content.length);
        return out;
    }

    public Base64Coder() {
        // Allowing to create Object with Default Constructor/No Argument Constructor
    }
}
