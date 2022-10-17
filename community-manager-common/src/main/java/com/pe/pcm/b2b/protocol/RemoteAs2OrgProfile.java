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

package com.pe.pcm.b2b.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class RemoteAs2OrgProfile {

    private String AS2Identifier;
    private String emailAddress;
    private String emailHost;
    private String emailPort;
    private String exchangeCert;
    private String exchangeCertSelectionPolicy;
    private String identityName;
    private String profileName;
    private boolean selectNewIdentity = false;
    private String signingCert;
    private String signingCertSelectionPolicy;
    private String useExistingIdentity;

    public String getAS2Identifier() {
        return AS2Identifier;
    }

    public RemoteAs2OrgProfile setAS2Identifier(String AS2Identifier) {
        this.AS2Identifier = AS2Identifier;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public RemoteAs2OrgProfile setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getEmailHost() {
        return emailHost;
    }

    public RemoteAs2OrgProfile setEmailHost(String emailHost) {
        this.emailHost = emailHost;
        return this;
    }

    public String getEmailPort() {
        return emailPort;
    }

    public RemoteAs2OrgProfile setEmailPort(String emailPort) {
        this.emailPort = emailPort;
        return this;
    }

    public String getExchangeCert() {
        return exchangeCert;
    }

    public RemoteAs2OrgProfile setExchangeCert(String exchangeCert) {
        this.exchangeCert = exchangeCert;
        return this;
    }

    public String getExchangeCertSelectionPolicy() {
        return exchangeCertSelectionPolicy;
    }

    public RemoteAs2OrgProfile setExchangeCertSelectionPolicy(String exchangeCertSelectionPolicy) {
        this.exchangeCertSelectionPolicy = exchangeCertSelectionPolicy;
        return this;
    }

    public String getIdentityName() {
        return identityName;
    }

    public RemoteAs2OrgProfile setIdentityName(String identityName) {
        this.identityName = identityName;
        return this;
    }

    public String getProfileName() {
        return profileName;
    }

    public RemoteAs2OrgProfile setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public boolean isSelectNewIdentity() {
        return selectNewIdentity;
    }

    public RemoteAs2OrgProfile setSelectNewIdentity(boolean selectNewIdentity) {
        this.selectNewIdentity = selectNewIdentity;
        return this;
    }

    public String getSigningCert() {
        return signingCert;
    }

    public RemoteAs2OrgProfile setSigningCert(String signingCert) {
        this.signingCert = signingCert;
        return this;
    }

    public String getSigningCertSelectionPolicy() {
        return signingCertSelectionPolicy;
    }

    public RemoteAs2OrgProfile setSigningCertSelectionPolicy(String signingCertSelectionPolicy) {
        this.signingCertSelectionPolicy = signingCertSelectionPolicy;
        return this;
    }

    public String getUseExistingIdentity() {
        return useExistingIdentity;
    }

    public RemoteAs2OrgProfile setUseExistingIdentity(String useExistingIdentity) {
        this.useExistingIdentity = useExistingIdentity;
        return this;
    }
}

