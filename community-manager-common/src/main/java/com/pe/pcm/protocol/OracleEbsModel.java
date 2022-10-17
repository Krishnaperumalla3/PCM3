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

package com.pe.pcm.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pe.pcm.profile.ProfileModel;

/**
 * @author Shameer.
 */

@JsonIgnoreProperties(ignoreUnknown = true)
public class OracleEbsModel extends ProfileModel {

    private String name;
    private String tpContractSend;
    private String dateTimeOag;
    private String timeoutBod;
    private String bpRecMsgs;
    private String bpUnknownBods;
    private String autoSendBodRecMsgs;
    private String nameBod;
    private String directoryBod;
    private String httpEndpoint;
    private String requestType;
    private String orProtocol;
    private String userName;
    private String password;

    public String getName() {
        return name;
    }

    public OracleEbsModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getTpContractSend() {
        return tpContractSend;
    }

    public OracleEbsModel setTpContractSend(String tpContractSend) {
        this.tpContractSend = tpContractSend;
        return this;
    }

    public String getDateTimeOag() {
        return dateTimeOag;
    }

    public OracleEbsModel setDateTimeOag(String dateTimeOag) {
        this.dateTimeOag = dateTimeOag;
        return this;
    }

    public String getTimeoutBod() {
        return timeoutBod;
    }

    public OracleEbsModel setTimeoutBod(String timeoutBod) {
        this.timeoutBod = timeoutBod;
        return this;
    }

    public String getBpRecMsgs() {
        return bpRecMsgs;
    }

    public OracleEbsModel setBpRecMsgs(String bpRecMsgs) {
        this.bpRecMsgs = bpRecMsgs;
        return this;
    }

    public String getBpUnknownBods() {
        return bpUnknownBods;
    }

    public OracleEbsModel setBpUnknownBods(String bpUnknownBods) {
        this.bpUnknownBods = bpUnknownBods;
        return this;
    }

    public String getAutoSendBodRecMsgs() {
        return autoSendBodRecMsgs;
    }

    public OracleEbsModel setAutoSendBodRecMsgs(String autoSendBodRecMsgs) {
        this.autoSendBodRecMsgs = autoSendBodRecMsgs;
        return this;
    }

    public String getNameBod() {
        return nameBod;
    }

    public OracleEbsModel setNameBod(String nameBod) {
        this.nameBod = nameBod;
        return this;
    }

    public String getDirectoryBod() {
        return directoryBod;
    }

    public OracleEbsModel setDirectoryBod(String directoryBod) {
        this.directoryBod = directoryBod;
        return this;
    }

    public String getHttpEndpoint() {
        return httpEndpoint;
    }

    public OracleEbsModel setHttpEndpoint(String httpEndpoint) {
        this.httpEndpoint = httpEndpoint;
        return this;
    }

    public String getRequestType() {
        return requestType;
    }

    public OracleEbsModel setRequestType(String requestType) {
        this.requestType = requestType;
        return this;
    }

    public String getOrProtocol() {
        return orProtocol;
    }

    public OracleEbsModel setOrProtocol(String orProtocol) {
        this.orProtocol = orProtocol;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public OracleEbsModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public OracleEbsModel setPassword(String password) {
        this.password = password;
        return this;
    }

}
