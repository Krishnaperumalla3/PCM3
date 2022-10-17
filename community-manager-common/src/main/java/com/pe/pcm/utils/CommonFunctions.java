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

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pe.pcm.common.CommunityManagerResponse;
import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.functions.TriFunction;
import com.pe.pcm.pem.PemStringMnplModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.LongBuffer;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.*;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.pe.pcm.exception.GlobalExceptionHandler.badRequest;
import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;
import static com.pe.pcm.utils.PCMConstants.*;
import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.springframework.http.HttpStatus.BAD_REQUEST;

/**
 * @author Kiran Reddy.
 */
public class CommonFunctions {

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonFunctions.class);

    private CommonFunctions() {
    }

    private static final List<String> defaultAccounts = Arrays.asList("admin", "apiadmin", "fg_sysadmin");

    public static Boolean convertStringToBoolean(String booleanAsString) {
        return (booleanAsString != null && booleanAsString.equalsIgnoreCase("y")) ? TRUE : FALSE;
    }

    public static Boolean convertStringToBooleanForCD(String booleanAsString) {
        return (booleanAsString != null && booleanAsString.equalsIgnoreCase("ENABLED")) ? TRUE : FALSE;
    }


    public static String convertStringToReqString(String status) {
        return (status != null && status.equalsIgnoreCase("Y")) ? "Active" : "InActive";
    }

    public static String convertBooleanToString(boolean value) {
        return value ? "Y" : "N";
    }

    public static String convertBooleanToStringForCD(boolean value) {
        return value ? "ENABLED" : "DISABLED";
    }

    public static int convertSecurityProtocolToInteger(String value) {

        switch (value) {
            case ("TLS 1.0"):
                return 0;
            case ("TLS 1.1"):
                return 1;
            case ("TLS 1.2"):
                return 2;
            default:
                throw badRequest("Invalid Input Please Enter Valid Input Ex: TLS 1.0,TLS 1.1,TLS 1.2");
        }
    }

    public static boolean isNotNull(String data) {
        return data != null && !data.replace(" ", "").isEmpty();
    }

    public static Boolean isNotNullWithZeroCheck(String data) {
        return (data != null && !data.replace(" ", "").isEmpty() && !Integer.valueOf(data).equals(0)) ? TRUE : FALSE;
    }

    public static boolean isNotNull(Object data) {
        return data != null && !String.valueOf(data).replace(" ", "").isEmpty();
    }

    public static Integer convertPoolingIntervalToMinutes(String poolingInterval) {

        if (poolingInterval.contains("H")) {
            return Integer.parseInt(poolingInterval.replace("H", "").trim()) * 60;
        } else if (poolingInterval.contains("D")) {
            return Integer.parseInt(poolingInterval.replace("D", "").trim()) * 60 * 24;
        } else if (poolingInterval.contains("W")) {
            return Integer.parseInt(poolingInterval.replace("W", "").trim()) * 60 * 24 * 7;
        } else if (poolingInterval.contains("M")) {
            return Integer.parseInt(poolingInterval.replace("M", "").trim()) * 60 * 24 * 7 * 30;
        } else {
            return Integer.valueOf(poolingInterval);
        }
    }

    public static String convertPoolingIntervalToDisplayValue(String poolingInterval) {

        if (poolingInterval.contains("H")) {
            return poolingInterval.replace("H", " Hour(s)");
        } else if (poolingInterval.contains("D")) {
            return poolingInterval.replace("D", " Day(s)");
        } else if (poolingInterval.contains("W")) {
            return poolingInterval.replace("W", " Week(s)");
        } else if (poolingInterval.contains("M")) {
            return poolingInterval.replace("M", " Month(s)");
        } else {
            return poolingInterval + " Minute(s)";
        }
    }

    public static void outStreamResponse(HttpServletResponse httpServletResponse, String dataToStream) {
        httpServletResponse.addHeader("ContentType", "application/xml");
        httpServletResponse.addHeader(
                "Content-Disposition",
                "attachment; filename=\" WorkFlow_Export_"
                        + System.currentTimeMillis()
                        + ".xml");
        try {
            httpServletResponse.getOutputStream().write(
                    dataToStream.getBytes());
            httpServletResponse.getOutputStream().flush();
            httpServletResponse.getOutputStream().close();
        } catch (IOException e) {
            throw internalServerError("Unable to export workflow. Please try after sometime");
        }
    }

    public static void exportAllOutStreamResponse(HttpServletResponse httpServletResponse, String dataToStream) {
        httpServletResponse.addHeader(HttpHeaders.CONTENT_TYPE, "text/csv");
        httpServletResponse.addHeader(
                HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=\"" + "PCM_AllWorkFlows_"
                        + System.currentTimeMillis()
                        + ".csv");
        try {
            httpServletResponse.getOutputStream().write(
                    dataToStream.getBytes());
            httpServletResponse.getOutputStream().flush();
            httpServletResponse.getOutputStream().close();
        } catch (IOException e) {
            throw internalServerError("Unable to export all workFlows. Please try after sometime");
        }
    }

    public static final Function<MultipartFile, String> convertFileToString = file -> {
        if (file.getOriginalFilename() != null) {
            if (StringUtils.cleanPath(file.getOriginalFilename()).contains("..")) {
                throw internalServerError("Sorry! Filename contains invalid path sequence " + file);
            }
            try {
                return new String(file.getBytes());
            } catch (IOException e) {
                throw internalServerError("Sorry! Unable to read " + file);
            }
        }
        return "";
    };

    public static final UnaryOperator<String> convertToBase64 = data ->
            org.apache.tomcat.util.codec.binary.Base64.isBase64(data.getBytes()) ? data : new String(Base64.getEncoder().encode(data.getBytes()));

    static Timestamp convertStringToTimestamp(String stringDate) {
        try {
            SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT_PCM);
            Date date = formatter.parse(stringDate);
            return new Timestamp(date.getTime());
        } catch (ParseException e) {
            throw badRequest("Date property Ex: 2018-12-02 05:15:10, format : " + DATE_FORMAT_PCM);

        }
    }

    public static String convertDateForOracle(String date) {
        try {
            SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat format2 = new SimpleDateFormat("dd-MMM-yyyy");
            Date date1 = format1.parse(date);
            return format2.format(date1);
        } catch (Exception ex) {
            if (ex.toString().contains("ParseException")) {
                throw new IllegalArgumentException("Please give Date format as YYYY-MM-DD, Ex: 2020-10-20");
            }
        }
        return "";
    }

    public static final Function<String, CommunityManagerResponse> OK = message ->
            new CommunityManagerResponse(HttpStatus.OK.value(), message);

    public static final BinaryOperator<String> isNullThrowError = (data, display) -> {
        if (!isNotNull(data)) {
            throw new CommunityManagerServiceException(BAD_REQUEST.value(), display + " should not be Null/Empty.");
        }
        return data;
    };

    public static final BinaryOperator<String> isNullThrowCustomError = (data, display) -> {
        if (!isNotNull(data)) {
            throw new CommunityManagerServiceException(BAD_REQUEST.value(), display);
        }
        return data;
    };

    public static void throwIfNullOrEmpty(Object data, String display) {
        if (!isNotNull(data)) {
            throw new CommunityManagerServiceException(BAD_REQUEST.value(), display + " should not be Null/Empty.");
        }
    }

    public static boolean notEqual(Object source, Object destination) {
        return !(isNotNull(source) ? String.valueOf(source) : "").equals(isNotNull(destination) ? String.valueOf(destination) : "");
    }

    public static void throwIfFilePathNull(String originalFileName) {
        if (originalFileName != null) {
            String filePath = org.springframework.util.StringUtils.cleanPath(originalFileName);
            if (filePath.contains(TWO_DOTS)) {
                throw internalServerError("Oops... Filename contains invalid path sequence " + filePath);
            }
        } else {
            throw internalServerError("Oops... File path is null, please try again.");
        }
    }

    public static String convertToLocalLocale(String input) {
        try {
            DateFormat inputFormat = new SimpleDateFormat(DATE_LOCALE_FORMAT, Locale.ENGLISH);
            Date date = inputFormat.parse(input);
            DateFormat outputFormat = new SimpleDateFormat(TIME_STAMP_FORMAT, Locale.ENGLISH);
            //outputFormat.setTimeZone(TimeZone.getTimeZone("GMT"))
            return outputFormat.format(date);
        } catch (Exception e) {
            throw badRequest("Date property Ex: Wed Sep 18 2019 06:09:55 GMT+05:30, format : " + DATE_LOCALE_FORMAT);
        }
    }

    private static final TriFunction<String, Boolean, Boolean, String> toLowerOrUpper = (data, toLower, toUpper) -> {
        if (toLower && !toUpper) {
            return data.toLowerCase();
        } else if (toUpper && !toLower) {
            return data.toUpperCase();
        } else {
            return data;
        }
    };

    public static final Function<PemStringMnplModel, String> convertPEMString = pemStringMnplModel -> {
        if (pemStringMnplModel.isIgnoreLowerUpper() && !pemStringMnplModel.isIgnoreSpace()) {
            return pemStringMnplModel.isRemoveSpace() ? pemStringMnplModel.getData().replace(" ", "") : pemStringMnplModel.getData();
        } else if (pemStringMnplModel.isIgnoreSpace() && !pemStringMnplModel.isIgnoreLowerUpper()) {
            return toLowerOrUpper.apply(pemStringMnplModel.getData(), pemStringMnplModel.isToLower(), pemStringMnplModel.isToUpper());
        } else {
            return pemStringMnplModel.isRemoveSpace() ? toLowerOrUpper.apply(
                    pemStringMnplModel.getData(), pemStringMnplModel.isToLower(), pemStringMnplModel.isToUpper()).replace(" ", "") :
                    toLowerOrUpper.apply(pemStringMnplModel.getData(), pemStringMnplModel.isToLower(), pemStringMnplModel.isToUpper());
        }
    };

    public static void addToList(List<String> list, String value) {
        if (isNotNull(value)) {
            list.add(value);
        }
    }

    public static final UnaryOperator<String> loadDataAsStringFromPath = filePath -> {
        StringBuilder contentBuilder;
        try (Stream<String> stream = Files.lines(Paths.get(filePath), StandardCharsets.ISO_8859_1)) { //Charset.forName("Cp1252")
            contentBuilder = new StringBuilder();
            stream.forEach(line -> contentBuilder.append(line).append("\n"));
            return contentBuilder.toString();
        } catch (NoSuchFileException noSuchFileException) {
            throw internalServerError("The file is not available in Archived Location : path =  " + filePath);
        } catch (IOException e) {
            throw internalServerError(e.getMessage());
        }

    };

    public static String findRootDirectory(String directory, boolean baseDirectoryForVirtualRoot) {

        if (baseDirectoryForVirtualRoot) {
            return "/" + directory.split("/")[1];
        }
        String[] ary = directory.split("/");

        if (ary.length > 2) {
            return directory.substring(0, directory.lastIndexOf('/'));
        }
        return directory;
    }

    public static Set<String> getRootDirectories(String directory, Set<String> rootList, AtomicReference<String> atomicReference, boolean isPermission) {
        Arrays.asList(directory.split("/")).forEach(s -> {
            if (isNotNull(s)) {
                String permission = atomicReference.get() + "/" + s;
                rootList.add(permission + (isPermission ? " " + MAILBOX : ""));
                atomicReference.set(permission);
            }
        });
        return rootList;
    }

    public static final IntFunction<String> generatePemPassword = length -> {

        if (length < 8) {
            throw internalServerError("Password length should not be less than 8.");
        }
        String numbers = "0123456789";
        String symbols = "!@#$%^&*_=+-/.?)";
        String capitalChars = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        String smallChars = "abcdefghijklmnopqrstuvwxyz";
        String values = capitalChars + smallChars + numbers + symbols;
        char[] password = new char[length];
        try {
            Random randomMethod = SecureRandom.getInstanceStrong();
            IntStream.range(0, length).forEach(
                    value -> password[value] = values.charAt(randomMethod.nextInt(values.length()))
            );
        } catch (NoSuchAlgorithmException nae) {
            throw internalServerError(nae.getMessage());
        }
        return String.valueOf(password);
    };

    public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
        Map<Object, Boolean> map = new ConcurrentHashMap<>();
        return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
    }

    public static java.sql.Date addDays(java.sql.Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, days);
        return new java.sql.Date(c.getTimeInMillis());
    }

    public static java.sql.Timestamp minusDays(java.sql.Date date, int days) {
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        c.add(Calendar.DATE, -days);
        return new java.sql.Timestamp(c.getTimeInMillis());
    }

    public static List<List<String>> getPartitions(int partitionSize, List<String> actualList) {
        List<List<String>> partitions = new ArrayList<>();
        for (int i = 0; i < actualList.size(); i += partitionSize) {
            partitions.add(actualList.subList(i,
                    Math.min(i + partitionSize, actualList.size())));
        }
        return partitions;
    }

    public static final BiFunction<String, String, Integer> convertStringToIntegerThrowError = (data, field) -> {
        try {
            if (isNotNull(data)) {
                return Integer.valueOf(data);
            }
        } catch (NumberFormatException e) {
            throw internalServerError("NumberFormatException, Unable to convert the " + field + " to number.");
        }
        return 0;
    };

    public static final IntFunction<String> getRandomSalt = index -> {

        switch (index) {
            case 0:
                return "oe4jnFLoj5i5B1Qg5+trzA==";
            case 1:
                return "qs1D35lBjA5bqB1g1OKUzQ==";
            case 2:
                return "9w6KFRv/OXg8kKKubJlcwg==";
            case 3:
                return "OGYB4q/jk8Hu43nwcb/1Mw==";
            case 4:
                return "2qfUs0BzUCY3jE8/mIoKug==";
            case 5:
                return "y/qKUeMec7oSO9mYn9sQmg==";
            case 6:
                return "sQ7uHtU3KWskMt970oq5DQ==";
            case 7:
                return "v0noVUY59WFYXy6wCHDevg==";
            case 8:
                return "jX1siQlFfLBo2340cIlhng==";
            case 9:
                return "01F4+0PoU103chqxNZxOVA==";
            default:
                return "jX1siQlFfLBo2340cIlhng==";

        }
    };

    public static String removeENC(String value) {
        value = value.replace("ENC(", "").replace("ENC (", "").trim();
        return value.substring(0, value.lastIndexOf(")"));
    }

    public static final UnaryOperator<String> formatMail = mail -> isNotNull(mail) ? mail.replace(";", ",") : mail;

    public static void checkDefaultUsers(String userName) {

        if (defaultAccounts.contains(userName.trim())) {
            throw internalServerError("admin account cannot be edited, please update from IBM Sterling B2B Integrator. userName : " + userName);
        }
    }

    public static final BiFunction<String, Integer, String> getRequiredLengthString = (data, requiredLength) -> {

        StringBuilder organizationKeySb = new StringBuilder(data);
        if (requiredLength > data.length()) {
            int len = requiredLength - data.length();
            IntStream.range(0, len).forEach(num -> organizationKeySb.append(" "));
        }
        return organizationKeySb.toString();
    };

    public static Map<String, String> getEntityMap(Object obj) {
        return new ObjectMapper().convertValue(obj,
                new TypeReference<Map<String, String>>() {
                });
    }

    public static Timestamp convertDBTimeToTimestamp(String dbTimestamp, String databaseType) {
        try {
            DateFormat df = new SimpleDateFormat(getDbTimestampFormat(databaseType));
            Date parsedDate = df.parse(dbTimestamp);
            Calendar c = Calendar.getInstance();
            c.setTime(parsedDate);
            return new java.sql.Timestamp(c.getTimeInMillis());
        } catch (ParseException pe) {
            throw internalServerError("Unable to parse the database timestamp");
        }
    }

    private static String getDbTimestampFormat(String db) {
        if (db.equals(ORACLE) || db.equals(SQL_SERVER)) {
            return DATE_FORMAT_PCM;
        } else if (db.equals(DB2)) {
            return "yyyy-MM-dd-kk.mm.ss.SSS";
        }

        return "";
    }

    public static final BiFunction<String, Boolean, String> apacheDecodeBase64 = (data, throwIfNotEncrypted) -> {
        if (isNotNull(data)) {
            try {
                return new String(org.apache.tomcat.util.codec.binary.Base64.decodeBase64(data));
            } catch (Exception e) {
                if (throwIfNotEncrypted) {
                    throw new CommunityManagerServiceException(BAD_REQUEST.value(), "Data should be encoded.");
                }
                return data;
            }
        } else {
            throw new CommunityManagerServiceException(BAD_REQUEST.value(), "Data should not be Null/Empty.");
        }
    };

    public static final Function<Boolean, String> convertBoolToIMPSSLStr = implicitSSLBool ->
            implicitSSLBool != null ? (implicitSSLBool ? "SSL_IMPLICIT" : "SSL_EXPLICIT") : "SSL_EXPLICIT";

    public static final Function<String, Boolean> convertIMPSSLStrToBool = implicitSSLStr ->
            isNotNull(implicitSSLStr) ? (implicitSSLStr.equalsIgnoreCase("SSL_IMPLICIT") ? TRUE : FALSE) : FALSE;

    public static String getJarPath() {
        String currPath = new File(".").getAbsolutePath();
        return currPath.substring(0, currPath.length() - 1);
    }

    public static String getUniqueString(int capacity) {
        UUID uuid = UUID.randomUUID();
        ByteBuffer uuidBuffer = ByteBuffer.allocate(capacity);
        LongBuffer longBuffer = uuidBuffer.asLongBuffer();
        longBuffer.put(uuid.getMostSignificantBits());
        longBuffer.put(uuid.getLeastSignificantBits());
        return new String(Base64.getEncoder().encode(uuidBuffer.array()),
                StandardCharsets.US_ASCII).replace('+', 'S')
                .replace('/', 'W')
                .replace('=', 'K').toUpperCase();
    }

    public static void deleteIfExists(File deleteFile) {
        try {
            LOGGER.info("file deleted: {}", Files.deleteIfExists(deleteFile.toPath()));
        } catch (IOException e) {
            LOGGER.error("Unable to delete the file");
        }
    }

}
