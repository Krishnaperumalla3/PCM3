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

import java.io.*;
import java.text.MessageFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Properties;
import java.util.Random;

public class PwdSecurityFlatUtil {

    public static final String LINE_SEP;
    protected static String serverName;
    protected static Date oldDate;
    protected static final SimpleDateFormat sdf;
    protected static SimpleDateFormat formatter;
    public static String errorStr;

    public static int base64DecodeLine(final byte[] bInputBuffer, final byte[] bOutputBuffer, final int iInputLength) {
        int i = 0;
        int iCounter = 0;
        int iReturn = -1;
        byte bTemp1 = 0;
        byte bTemp2 = 0;
        int iInputByteOffset = 0;
        int iOutputByteOffset = 0;
        for (iCounter = iInputLength / 4, i = 0; i < iCounter; ++i) {
            bTemp1 = (byte)(bInputBuffer[iInputByteOffset] & 0x7F);
            ++iInputByteOffset;
            bTemp2 = (byte)(bInputBuffer[iInputByteOffset] & 0x7F);
            ++iInputByteOffset;
            bTemp1 = GetIndex(bTemp1);
            bTemp2 = GetIndex(bTemp2);
            if (bTemp1 >= 0 && bTemp2 >= 0) {
                bOutputBuffer[iOutputByteOffset] = (byte)((bTemp1 << 2 & 0xFC) + (bTemp2 >> 4 & 0x3));
                ++iOutputByteOffset;
            }
            bTemp1 = bTemp2;
            bTemp2 = (byte)(bInputBuffer[iInputByteOffset] & 0x7F);
            ++iInputByteOffset;
            bTemp2 = GetIndex(bTemp2);
            if (bTemp1 >= 0 && bTemp2 >= 0) {
                bOutputBuffer[iOutputByteOffset] = (byte)((bTemp1 << 4 & 0xF0) + (bTemp2 >> 2 & 0xF));
                ++iOutputByteOffset;
            }
            bTemp1 = bTemp2;
            bTemp2 = (byte)(bInputBuffer[iInputByteOffset] & 0x7F);
            ++iInputByteOffset;
            bTemp2 = GetIndex(bTemp2);
            if (bTemp1 >= 0 && bTemp2 >= 0) {
                bOutputBuffer[iOutputByteOffset] = (byte)((bTemp1 << 6 & 0xC0) + (bTemp2 & 0x3F));
                ++iOutputByteOffset;
            }
        }
        iReturn = iOutputByteOffset;
        return iReturn;
    }
//TODO Used
    public static int base64Decode(final InputStream iStream, final OutputStream oStream) throws Exception {
        final int iReturn = 0;
        byte bTemp1 = 0;
        byte bTemp2 = 0;
        int iRead = 0;
        final byte[] b4 = new byte[4];
        int b4Counter = 0;
        final boolean bGot4 = false;
        boolean bEndOfStream = false;
        while (!bEndOfStream) {
            b4Counter = 0;
            b4[1] = (b4[0] = 0);
            b4[3] = (b4[2] = 0);
            while (!bGot4) {
                iRead = iStream.read();
                if (iRead < 0) {
                    bEndOfStream = true;
                    break;
                }
                if (GetIndex((byte)iRead) >= 0) {
                    b4[b4Counter] = (byte)iRead;
                    ++b4Counter;
                }
                if (b4Counter > 3) {
                    break;
                }
            }
            if (!bEndOfStream || b4Counter > 0) {
                bTemp1 = (byte)(b4[0] & 0x7F);
                bTemp2 = (byte)(b4[1] & 0x7F);
                bTemp1 = GetIndex((byte)(b4[0] & 0x7F));
                bTemp2 = GetIndex((byte)(b4[1] & 0x7F));
                if (bTemp1 >= 0 && bTemp2 >= 0) {
                    oStream.write((byte)((bTemp1 << 2 & 0xFC) + (bTemp2 >> 4 & 0x3)));
                }
                bTemp1 = bTemp2;
                bTemp2 = (byte)(b4[2] & 0x7F);
                bTemp2 = GetIndex((byte)(b4[2] & 0x7F));
                if (bTemp1 >= 0 && bTemp2 >= 0) {
                    oStream.write((byte)((bTemp1 << 4 & 0xF0) + (bTemp2 >> 2 & 0xF)));
                }
                bTemp1 = bTemp2;
                bTemp2 = (byte)(b4[3] & 0x7F);
                bTemp2 = GetIndex((byte)(b4[3] & 0x7F));
                if (bTemp1 < 0 || bTemp2 < 0) {
                    continue;
                }
                oStream.write((byte)((bTemp1 << 6 & 0xC0) + (bTemp2 & 0x3F)));
            }
        }
        return iReturn;
    }

    public static byte[] base64Decode(final byte[] bInput) throws Exception {
        final ByteArrayInputStream iStream = new ByteArrayInputStream(bInput);
        final ByteArrayOutputStream oStream = new ByteArrayOutputStream();
        base64Decode(iStream, oStream);
        return oStream.toByteArray();
    }

    //todo-USED
    public static int base64EncodeLine(final byte[] bInputBuffer, final byte[] bOutputBuffer, final int iInputSize) {
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
        for (i = 0; i < iCounter; ++i) {
            bTemp1 = (byte)(bInputBuffer[iInputByteOffset] >> 2 & 0x3F);
            bOutputBuffer[iOutputByteOffset] = PwdSecurityGlobals.BASE64Index[bTemp1];
            ++iOutputByteOffset;
            bTemp1 = (byte)(bInputBuffer[iInputByteOffset] << 4 & 0x30);
            ++iInputByteOffset;
            bTemp2 = (byte)(bInputBuffer[iInputByteOffset] >> 4 & 0xF);
            bOutputBuffer[iOutputByteOffset] = PwdSecurityGlobals.BASE64Index[bTemp1 + bTemp2];
            ++iOutputByteOffset;
            bTemp1 = (byte)(bInputBuffer[iInputByteOffset] << 2 & 0x3C);
            ++iInputByteOffset;
            bTemp2 = (byte)(bInputBuffer[iInputByteOffset] >> 6 & 0x3);
            bOutputBuffer[iOutputByteOffset] = PwdSecurityGlobals.BASE64Index[bTemp1 + bTemp2];
            ++iOutputByteOffset;
            bTemp1 = (byte)(bInputBuffer[iInputByteOffset] & 0x3F);
            bOutputBuffer[iOutputByteOffset] = PwdSecurityGlobals.BASE64Index[bTemp1];
            ++iOutputByteOffset;
            ++iInputByteOffset;
        }
        switch (iPad) {
            case 1:
                bTemp1 = (byte)(bInputBuffer[iInputByteOffset] >> 2 & 0x3F);
                bOutputBuffer[iOutputByteOffset] = PwdSecurityGlobals.BASE64Index[bTemp1];
                ++iOutputByteOffset;
                bTemp1 = (byte)(bInputBuffer[iInputByteOffset] << 4 & 0x30);
                bTemp2 = 0;
                bOutputBuffer[iOutputByteOffset] = PwdSecurityGlobals.BASE64Index[bTemp1 + bTemp2];
                ++iOutputByteOffset;
                bOutputBuffer[iOutputByteOffset] = 61;
                ++iOutputByteOffset;
                bOutputBuffer[iOutputByteOffset] = 61;
                ++iOutputByteOffset;
                break;
            case 2:
                bTemp1 = (byte)(bInputBuffer[iInputByteOffset] >> 2 & 0x3F);
                bOutputBuffer[iOutputByteOffset] = PwdSecurityGlobals.BASE64Index[bTemp1];
                ++iOutputByteOffset;
                bTemp1 = (byte)(bInputBuffer[iInputByteOffset] << 4 & 0x30);
                ++iInputByteOffset;
                bTemp2 = (byte)(bInputBuffer[iInputByteOffset] >> 4 & 0xF);
                bOutputBuffer[iOutputByteOffset] = PwdSecurityGlobals.BASE64Index[bTemp1 + bTemp2];
                ++iOutputByteOffset;
                bTemp1 = (byte)(bInputBuffer[iInputByteOffset] << 2 & 0x3C);
                bTemp2 = 0;
                bOutputBuffer[iOutputByteOffset] = PwdSecurityGlobals.BASE64Index[bTemp1 + bTemp2];
                ++iOutputByteOffset;
                bOutputBuffer[iOutputByteOffset] = 61;
                ++iOutputByteOffset;
                break;

        }
        iReturn = iOutputByteOffset;
        return iReturn;
    }

    public static int base64Encode(final InputStream iStream, final OutputStream oStream) throws Exception {
        int iReturn = 0;
        int iRead = 0;
        int iEncoded = 0;
        final boolean bStop = false;
        final byte[] bToEncode = new byte[54];
        final byte[] bEncoded = new byte[128];
        while (!bStop) {
            iRead = iStream.read(bToEncode, 0, 54);
            if (iRead == -1) {
                break;
            }
            iEncoded = base64EncodeLine(bToEncode, bEncoded, iRead);
            oStream.write(bEncoded, 0, iEncoded);
            oStream.write(13);
            oStream.write(10);
            iReturn += iEncoded;
        }
        return iReturn;
    }

    public static byte[] base64Encode(final byte[] bInput) throws Exception {
        final ByteArrayInputStream iStream = new ByteArrayInputStream(bInput);
        final ByteArrayOutputStream oStream = new ByteArrayOutputStream();
        base64Encode(iStream, oStream);
        return oStream.toByteArray();
    }

    public static byte GetIndex(final byte b) {
        byte iReturn = -1;
        byte iCounter;
        for (iCounter = 0, iCounter = 0; iCounter < 64; ++iCounter) {
            if (PwdSecurityGlobals.BASE64Index[iCounter] == b) {
                iReturn = iCounter;
                break;
            }
        }
        return iReturn;
    }

    public static int base64EncodeNoEOL(final InputStream iStream, final OutputStream oStream) {
        int iReturn = 0;
        int iRead = 0;
        int iEncoded = 0;
        final boolean bStop = false;
        final byte[] bToEncode = new byte[54];
        final byte[] bEncoded = new byte[128];
        try {
            while (!bStop) {
                iRead = iStream.read(bToEncode, 0, 54);
                if (iRead == -1) {
                    break;
                }
                iEncoded = base64EncodeLine(bToEncode, bEncoded, iRead);
                oStream.write(bEncoded, 0, iEncoded);
                iReturn += iEncoded;
            }
        }
        catch (Exception e) {
            System.out.println("EncoderDecoder: Exception caught"+ e);
            iReturn = -1;
        }
        return iReturn;
    }

   /* public static String createXMLSafeGUID() {
        return makeXMLSafeGUID(createGUID());
    }*/

    public static String makeXMLSafeGUID(final String guid) {
        final StringBuffer buffer = new StringBuffer(guid);
        int i = buffer.length();
        while (--i >= 0) {
            if (buffer.charAt(i) == ':') {
                buffer.setCharAt(i, '-');
            }
        }
        if (Character.isDigit(buffer.charAt(0))) {
            return "si" + buffer.toString();
        }
        return buffer.toString();
    }

    public static synchronized String createYantraUniqueKey() {
        try {
            Thread.sleep(1L);
        }
        catch (Exception ex) {}
        String toReturn = null;
        final String tmpString = System.currentTimeMillis() + "-" + new Random().nextInt();
        if (tmpString.length() > 24) {
            toReturn = tmpString.substring(0, 24);
        }
        else {
            toReturn = tmpString;
        }
        return toReturn.trim();
    }

    /*public static String getUniqueFileName(String fileFormat) {
        final int i = fileFormat.indexOf("%^");
        if (i >= 0) {
            final StringBuffer sb = new StringBuffer(80);
            if (i > 0) {
                sb.append(fileFormat.substring(0, i));
            }
            if (SecurityFlatUtil.serverName == null) {
                SecurityFlatUtil.serverName = Manager.getProperty("servername") + "_";
            }
            sb.append(SecurityFlatUtil.serverName);
            synchronized (SecurityFlatUtil.oldDate) {
                long now;
                for (now = System.currentTimeMillis(); SecurityFlatUtil.oldDate.getTime() == now; now = System.currentTimeMillis()) {}
                SecurityFlatUtil.oldDate.setTime(now);
                sb.append(SecurityFlatUtil.sdf.format(SecurityFlatUtil.oldDate));
            }
            final int suffix = i + 2;
            if (suffix < fileFormat.length()) {
                sb.append(fileFormat.substring(suffix));
            }
            fileFormat = sb.toString();
        }
        return fileFormat;
    }*/

    public static Properties loadPropertiesFile(final String propFilePath) throws FileNotFoundException, SecurityException, IOException {
        Properties props = null;
        FileInputStream fis = null;
        try {
            fis = new FileInputStream(propFilePath);
            props = new Properties();
            props.load(fis);
        }
        finally {
            if (fis != null) {
                try {
                    fis.close();
                }
                catch (IOException ioe) {
                    ioe.printStackTrace();
                }
            }
        }
        return props;
    }

    public static String now() {
        return "[" + new Date(System.currentTimeMillis()).toString() + "]";
    }

    public static String classname() {
        return "[" + getCaller(3) + "]";
    }

    public static String me() {
        return "[" + extractShortClassName(getCaller(3)) + "]";
    }

    public static String here() {
        return "[" + extractShortClassName(getCaller(4)) + "->" + extractShortClassName(getCaller(3)) + "]";
    }

    private static String extractShortClassName(final String ClassString) {
        int CurrentIndex = 0;
        int OldIndex = 0;
        while (CurrentIndex > -1) {
            OldIndex = CurrentIndex;
            CurrentIndex = ClassString.indexOf(46, CurrentIndex);
        }
        return ClassString.substring(OldIndex, ClassString.length());
    }

    public static String getCaller(final int callerID) {
        final int stack_base = callerID;
        final StringWriter sw = new StringWriter();
        new Throwable().printStackTrace(new PrintWriter(sw));
        final String trace = sw.toString();
        int linestart = -1;
        for (int i = 0; i < stack_base; ++i) {
            linestart = trace.indexOf("\n", linestart + 1);
        }
        return trace.substring(linestart + 5, trace.indexOf("(", linestart + 5));
    }

    private static int countChars(final char c, final String str) {
        int count = 0;
        for (int i = 0; i < str.length(); ++i) {
            if (str.charAt(i) == c) {
                ++count;
            }
        }
        return count;
    }

    public static String langLookup(final String scope, final String subsystem, final String name) {
        return langLookup(scope, subsystem, name, null);
    }

    public static String langLookup(final String scope, final String subsystem, final String name, final Object[] params) {
        String ret = null;
        final StringBuffer key = new StringBuffer().append(scope).append(".").append(subsystem).append(".").append(name);
        ret = key.toString();
        if (params != null && ret != null) {
            ret = MessageFormat.format(ret, params);
        }
        return ret;
    }

    static {
        LINE_SEP = System.getProperty("line.separator");
        PwdSecurityFlatUtil.serverName = null;
        PwdSecurityFlatUtil.oldDate = new Date();
        sdf = new SimpleDateFormat("yyyyMMddHHmmssSSS");
        PwdSecurityFlatUtil.formatter = new SimpleDateFormat("yyyyMMdd");
        PwdSecurityFlatUtil.errorStr = null;
    }
}
