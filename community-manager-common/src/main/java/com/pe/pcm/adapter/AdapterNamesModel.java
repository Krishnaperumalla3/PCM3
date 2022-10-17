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

package com.pe.pcm.adapter;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author Kiran Reddy.
 */
public class AdapterNamesModel implements Serializable {

    private String ftpServerAdapterName;
    private String ftpClientAdapterName;
    private String ftpsServerAdapterName;
    private String ftpsClientAdapterName;
    private String sftpServerAdapterName;
    private String sftpClientAdapterName;
    private String as2ServerAdapterName;
    private String as2ClientAdapterName;
    private String as2HttpClientAdapter;
    private String cdClientAdapterName;
    private String httpServerAdapterName;
    private String httpsServerAdapterName;
    private String mqAdapterName;
    private String wsServerAdapterName;
    private String fsAdapter;
    private String sfgSftpClientAdapterName;
    private String sfgSftpServerAdapterName;
    private String sfgFtpClientAdapterName;
    private String sfgFtpServerAdapterName;
    private String sfgFtpsClientAdapterName;
    private String sfgFtpsServerAdapterName;

    public String getFtpServerAdapterName() {
        return ftpServerAdapterName;
    }

    public AdapterNamesModel setFtpServerAdapterName(String ftpServerAdapterName) {
        this.ftpServerAdapterName = ftpServerAdapterName;
        return this;
    }

    public String getFtpClientAdapterName() {
        return ftpClientAdapterName;
    }

    public AdapterNamesModel setFtpClientAdapterName(String ftpClientAdapterName) {
        this.ftpClientAdapterName = ftpClientAdapterName;
        return this;
    }

    public String getFtpsServerAdapterName() {
        return ftpsServerAdapterName;
    }

    public AdapterNamesModel setFtpsServerAdapterName(String ftpsServerAdapterName) {
        this.ftpsServerAdapterName = ftpsServerAdapterName;
        return this;
    }

    public String getFtpsClientAdapterName() {
        return ftpsClientAdapterName;
    }

    public AdapterNamesModel setFtpsClientAdapterName(String ftpsClientAdapterName) {
        this.ftpsClientAdapterName = ftpsClientAdapterName;
        return this;
    }

    public String getSftpServerAdapterName() {
        return sftpServerAdapterName;
    }

    public AdapterNamesModel setSftpServerAdapterName(String sftpServerAdapterName) {
        this.sftpServerAdapterName = sftpServerAdapterName;
        return this;
    }

    public String getSftpClientAdapterName() {
        return sftpClientAdapterName;
    }

    public AdapterNamesModel setSftpClientAdapterName(String sftpClientAdapterName) {
        this.sftpClientAdapterName = sftpClientAdapterName;
        return this;
    }

    public String getAs2ServerAdapterName() {
        return as2ServerAdapterName;
    }

    public AdapterNamesModel setAs2ServerAdapterName(String as2ServerAdapterName) {
        this.as2ServerAdapterName = as2ServerAdapterName;
        return this;
    }

    public String getAs2ClientAdapterName() {
        return as2ClientAdapterName;
    }

    public AdapterNamesModel setAs2ClientAdapterName(String as2ClientAdapterName) {
        this.as2ClientAdapterName = as2ClientAdapterName;
        return this;
    }

    public String getAs2HttpClientAdapter() {
        return as2HttpClientAdapter;
    }

    public AdapterNamesModel setAs2HttpClientAdapter(String as2HttpClientAdapter) {
        this.as2HttpClientAdapter = as2HttpClientAdapter;
        return this;
    }

    public String getCdClientAdapterName() {
        return cdClientAdapterName;
    }

    public AdapterNamesModel setCdClientAdapterName(String cdClientAdapterName) {
        this.cdClientAdapterName = cdClientAdapterName;
        return this;
    }

    public String getHttpServerAdapterName() {
        return httpServerAdapterName;
    }

    public AdapterNamesModel setHttpServerAdapterName(String httpServerAdapterName) {
        this.httpServerAdapterName = httpServerAdapterName;
        return this;
    }

    public String getHttpsServerAdapterName() {
        return httpsServerAdapterName;
    }

    public AdapterNamesModel setHttpsServerAdapterName(String httpsServerAdapterName) {
        this.httpsServerAdapterName = httpsServerAdapterName;
        return this;
    }

    public String getMqAdapterName() {
        return mqAdapterName;
    }

    public AdapterNamesModel setMqAdapterName(String mqAdapterName) {
        this.mqAdapterName = mqAdapterName;
        return this;
    }

    public String getWsServerAdapterName() {
        return wsServerAdapterName;
    }

    public AdapterNamesModel setWsServerAdapterName(String wsServerAdapterName) {
        this.wsServerAdapterName = wsServerAdapterName;
        return this;
    }

    public String getFsAdapter() {
        return fsAdapter;
    }

    public AdapterNamesModel setFsAdapter(String fsAdapter) {
        this.fsAdapter = fsAdapter;
        return this;
    }

    public String getSfgSftpClientAdapterName() {
        return sfgSftpClientAdapterName;
    }

    public AdapterNamesModel setSfgSftpClientAdapterName(String sfgSftpClientAdapterName) {
        this.sfgSftpClientAdapterName = sfgSftpClientAdapterName;
        return this;
    }

    public String getSfgSftpServerAdapterName() {
        return sfgSftpServerAdapterName;
    }

    public AdapterNamesModel setSfgSftpServerAdapterName(String sfgSftpServerAdapterName) {
        this.sfgSftpServerAdapterName = sfgSftpServerAdapterName;
        return this;
    }

    public String getSfgFtpClientAdapterName() {
        return sfgFtpClientAdapterName;
    }

    public AdapterNamesModel setSfgFtpClientAdapterName(String sfgFtpClientAdapterName) {
        this.sfgFtpClientAdapterName = sfgFtpClientAdapterName;
        return this;
    }

    public String getSfgFtpServerAdapterName() {
        return sfgFtpServerAdapterName;
    }

    public AdapterNamesModel setSfgFtpServerAdapterName(String sfgFtpServerAdapterName) {
        this.sfgFtpServerAdapterName = sfgFtpServerAdapterName;
        return this;
    }

    public String getSfgFtpsClientAdapterName() {
        return sfgFtpsClientAdapterName;
    }

    public AdapterNamesModel setSfgFtpsClientAdapterName(String sfgFtpsClientAdapterName) {
        this.sfgFtpsClientAdapterName = sfgFtpsClientAdapterName;
        return this;
    }

    public String getSfgFtpsServerAdapterName() {
        return sfgFtpsServerAdapterName;
    }

    public AdapterNamesModel setSfgFtpsServerAdapterName(String sfgFtpsServerAdapterName) {
        this.sfgFtpsServerAdapterName = sfgFtpsServerAdapterName;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AdapterNamesModel.class.getSimpleName() + "[", "]")
                .add("ftpServerAdapterName='" + ftpServerAdapterName + "'")
                .add("ftpClientAdapterName='" + ftpClientAdapterName + "'")
                .add("ftpsServerAdapterName='" + ftpsServerAdapterName + "'")
                .add("ftpsClientAdapterName='" + ftpsClientAdapterName + "'")
                .add("sftpServerAdapterName='" + sftpServerAdapterName + "'")
                .add("sftpClientAdapterName='" + sftpClientAdapterName + "'")
                .add("as2ServerAdapterName='" + as2ServerAdapterName + "'")
                .add("as2ClientAdapterName='" + as2ClientAdapterName + "'")
                .add("as2HttpClientAdapter='" + as2HttpClientAdapter + "'")
                .add("cdClientAdapterName='" + cdClientAdapterName + "'")
                .add("httpServerAdapterName='" + httpServerAdapterName + "'")
                .add("httpsServerAdapterName='" + httpsServerAdapterName + "'")
                .add("mqAdapterName='" + mqAdapterName + "'")
                .add("wsServerAdapterName='" + wsServerAdapterName + "'")
                .add("fsAdapter='" + fsAdapter + "'")
                .add("sfgSftpClientAdapterName='" + sfgSftpClientAdapterName + "'")
                .add("sfgSftpServerAdapterName='" + sfgSftpServerAdapterName + "'")
                .add("sfgFtpClientAdapterName='" + sfgFtpClientAdapterName + "'")
                .add("sfgFtpServerAdapterName='" + sfgFtpServerAdapterName + "'")
                .add("sfgFtpsClientAdapterName='" + sfgFtpsClientAdapterName + "'")
                .add("sfgFtpsServerAdapterName='" + sfgFtpsServerAdapterName + "'")
                .toString();
    }
}
