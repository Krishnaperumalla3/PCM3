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

import com.pe.pcm.enums.Protocol;
import com.pe.pcm.exception.GlobalExceptionHandler;

import java.util.Optional;
import java.util.function.BiFunction;

import static com.pe.pcm.exception.GlobalExceptionHandler.notFound;
import static com.pe.pcm.utils.PCMConstants.*;
import static org.apache.commons.lang3.RandomStringUtils.randomAlphanumeric;

/**
 * @author Chenchu Kiran.
 */
public class KeyGeneratorUtil {

    private KeyGeneratorUtil() {
        //Not to create object for this class
    }

    public static final BiFunction<String, Integer, String> getPrimaryKey = (preAppend, length) -> preAppend + randomAlphanumeric(length);

    public static String getChildPrimaryKey(String protocolString) {
        Protocol protocol = Optional.ofNullable(Protocol.findProtocol(protocolString)).orElseThrow(GlobalExceptionHandler::unknownProtocol);
        switch (protocol) {
            case FTP:
            case SFG_FTP:
                return getPrimaryKey.apply(PROTOCOL_FTP_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
            case FTPS:
            case SFG_FTPS:
                return getPrimaryKey.apply(PROTOCOL_FTPS_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
            case SFTP:
            case SFG_SFTP:
                return getPrimaryKey.apply(PROTOCOL_SFTP_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
            case AS2:
                return getPrimaryKey.apply(PROTOCOL_AS2_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
            case HTTP:
                return getPrimaryKey.apply(PROTOCOL_HTTP_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
            case HTTPS:
                return getPrimaryKey.apply(PROTOCOL_HTTPS_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
            case MQ:
                return getPrimaryKey.apply(PROTOCOL_MQ_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
            case MAILBOX:
                return getPrimaryKey.apply(PROTOCOL_MAILBOX_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
            case SAP:
                return getPrimaryKey.apply(PROTOCOL_SAP_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
            case WEB_SERVICE:
                return getPrimaryKey.apply(PROTOCOL_WEBSERVICE_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
            case FILE_SYSTEM:
                return getPrimaryKey.apply(PROTOCOL_FILESYSTEM_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
            case CONNECT_DIRECT:
                return getPrimaryKey.apply(PROTOCOL_CD_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
            case EXISTING_CONNECTION:
                return getPrimaryKey.apply(PROTOCOL_EC_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
            case SMTP:
                return getPrimaryKey.apply(PROTOCOL_SMTP_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
            case ORACLE_EBS:
                return getPrimaryKey.apply(PROTOCOL_ORA_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
            case AWS_S3:
                return getPrimaryKey.apply(PROTOCOL_AWS_S3_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
            case SFG_CONNECT_DIRECT:
                return getPrimaryKey.apply(PROTOCOL_SFG_CD_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);
            case CUSTOM_PROTOCOL:
                return getPrimaryKey.apply(PROTOCOL_CUSTOM_PROTOCOL_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);

            case GOOGLE_DRIVE:
                return getPrimaryKey.apply(PROTOCOL_GOOGLE_DRIVE_PROTOCOL_PKEY_PRE_APPEND, PROTOCOL_PKEY_RANDOM_COUNT);

            default:
                throw notFound("Given Protocol not in our List," + protocol);
        }

    }

    public static String generatePrimaryKey(String appender, int length) {
        return getPrimaryKey.apply(appender, length);
    }

}
