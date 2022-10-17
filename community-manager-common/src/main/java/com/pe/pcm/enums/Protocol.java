/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc, Version 6.1 (the "License");
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

package com.pe.pcm.enums;

import java.util.Arrays;

/**
 * @author Chenchu Kiran.
 */
public enum Protocol {

    AS2("AS2"),
    CONNECT_DIRECT("CONNECT_DIRECT"),
    FILE_SYSTEM("FileSystem"),
    FTP("FTP"),
    FTPS("FTPS"),
    SFTP("SFTP"),
    HTTP("HTTP"),
    HTTPS("HTTPS"),
    MAILBOX("Mailbox"),
    MQ("MQ"),
    SAP("SAP"),
    SFG_FTP("SFGFTP"),
    SFG_FTPS("SFGFTPS"),
    SFG_SFTP("SFGSFTP"),
    WEB_SERVICE("Webservice"),
    EXISTING_CONNECTION("ExistingConnection"),
    AWS_S3("AWS_S3"),
    SMTP("SMTP"),
    SFG_CONNECT_DIRECT("SFG_CONNECT_DIRECT"),
    ORACLE_EBS("ORACLE_EBS"),
    CUSTOM_PROTOCOL("CUSTOM_PROTOCOL"),
    GOOGLE_DRIVE("GOOGLE_DRIVE");

    private String protocol;

    Protocol(String protocol) {
        this.protocol = protocol;
    }

    public String getProtocol() {
        return this.protocol;
    }


    public static Protocol findProtocol(final String protocol) {
        return Arrays.stream(values()).filter(value -> value.getProtocol().equals(protocol)).findFirst().orElse(null);
    }


}
