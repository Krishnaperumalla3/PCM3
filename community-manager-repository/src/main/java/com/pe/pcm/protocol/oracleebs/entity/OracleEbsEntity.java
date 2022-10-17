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

package com.pe.pcm.protocol.oracleebs.entity;

import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Shameer.
 *
 */

@Entity
@Table(name="PETPE_ORACLE")
public class OracleEbsEntity {

    @Id
    private String pkId;
    private String subscriberType;
    private String subscriberId;
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
    private String protocol;
    private String userName;
    private String password;

    public String getPkId() {
        return pkId;
    }

    public OracleEbsEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getSubscriberType() {
        return subscriberType;
    }

    public OracleEbsEntity setSubscriberType(String subscriberType) {
        this.subscriberType = subscriberType;
        return this;
    }

    public String getSubscriberId() {
        return subscriberId;
    }

    public OracleEbsEntity setSubscriberId(String subscriberId) {
        this.subscriberId = subscriberId;
        return this;
    }

    public String getName() {
        return name;
    }

    public OracleEbsEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getTpContractSend() {
        return tpContractSend;
    }

    public OracleEbsEntity setTpContractSend(String tpContractSend) {
        this.tpContractSend = tpContractSend;
        return this;
    }

    public String getDateTimeOag() {
        return dateTimeOag;
    }

    public OracleEbsEntity setDateTimeOag(String dateTimeOag) {
        this.dateTimeOag = dateTimeOag;
        return this;
    }

    public String getTimeoutBod() {
        return timeoutBod;
    }

    public OracleEbsEntity setTimeoutBod(String timeoutBod) {
        this.timeoutBod = timeoutBod;
        return this;
    }

    public String getBpRecMsgs() {
        return bpRecMsgs;
    }

    public OracleEbsEntity setBpRecMsgs(String bpRecMsgs) {
        this.bpRecMsgs = bpRecMsgs;
        return this;
    }

    public String getBpUnknownBods() {
        return bpUnknownBods;
    }

    public OracleEbsEntity setBpUnknownBods(String bpUnknownBods) {
        this.bpUnknownBods = bpUnknownBods;
        return this;
    }

    public String getAutoSendBodRecMsgs() {
        return autoSendBodRecMsgs;
    }

    public OracleEbsEntity setAutoSendBodRecMsgs(String autoSendBodRecMsgs) {
        this.autoSendBodRecMsgs = autoSendBodRecMsgs;
        return this;
    }

    public String getNameBod() {
        return nameBod;
    }

    public OracleEbsEntity setNameBod(String nameBod) {
        this.nameBod = nameBod;
        return this;
    }

    public String getDirectoryBod() {
        return directoryBod;
    }

    public OracleEbsEntity setDirectoryBod(String directoryBod) {
        this.directoryBod = directoryBod;
        return this;
    }

    public String getHttpEndpoint() {
        return httpEndpoint;
    }

    public OracleEbsEntity setHttpEndpoint(String httpEndpoint) {
        this.httpEndpoint = httpEndpoint;
        return this;
    }

    public String getRequestType() {
        return requestType;
    }

    public OracleEbsEntity setRequestType(String requestType) {
        this.requestType = requestType;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public OracleEbsEntity setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public OracleEbsEntity setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public OracleEbsEntity setPassword(String password) {
        this.password = password;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("pkId", pkId)
                .append("subscriberType", subscriberType)
                .append("subscriberId", subscriberId)
                .append("name", name)
                .append("tpContractSend", tpContractSend)
                .append("dateTimeOag", dateTimeOag)
                .append("timeoutBod", timeoutBod)
                .append("bpRecMsgs", bpRecMsgs)
                .append("bpUnknownBods", bpUnknownBods)
                .append("autoSendBodRecMsgs", autoSendBodRecMsgs)
                .append("nameBod", nameBod)
                .append("directoryBod", directoryBod)
                .append("httpEndpoint", httpEndpoint)
                .append("requestType", requestType)
                .append("protocol", protocol)
                .append("userName", userName)
                .append("password", password)
                .toString();
    }
}
