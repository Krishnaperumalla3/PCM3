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

package com.pe.pcm.b2b.certificate;

import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author Chenchu Kiran.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
public class SystemDigitalCertModel implements Serializable {

    private String certData;
    private String certName;
    private String certType;
    private String keyStorePassword;
    private String privateKeyPassword;
    private Boolean verifyAuthChain;
    private Boolean verifyValidity;
    private Boolean crlCache;

    public String getCertData() {
        return certData;
    }

    public SystemDigitalCertModel setCertData(String certData) {
        this.certData = certData;
        return this;
    }

    public String getCertName() {
        return certName;
    }

    public SystemDigitalCertModel setCertName(String certName) {
        this.certName = certName;
        return this;
    }

    public String getCertType() {
        return certType;
    }

    public SystemDigitalCertModel setCertType(String certType) {
        this.certType = certType;
        return this;
    }

    public String getKeyStorePassword() {
        return keyStorePassword;
    }

    public SystemDigitalCertModel setKeyStorePassword(String keyStorePassword) {
        this.keyStorePassword = keyStorePassword;
        return this;
    }

    public String getPrivateKeyPassword() {
        return privateKeyPassword;
    }

    public SystemDigitalCertModel setPrivateKeyPassword(String privateKeyPassword) {
        this.privateKeyPassword = privateKeyPassword;
        return this;
    }

    public Boolean getVerifyAuthChain() {
        return verifyAuthChain;
    }

    public SystemDigitalCertModel setVerifyAuthChain(Boolean verifyAuthChain) {
        this.verifyAuthChain = verifyAuthChain;
        return this;
    }

    public Boolean getVerifyValidity() {
        return verifyValidity;
    }

    public SystemDigitalCertModel setVerifyValidity(Boolean verifyValidity) {
        this.verifyValidity = verifyValidity;
        return this;
    }

    public Boolean getCrlCache() {
        return crlCache;
    }

    public SystemDigitalCertModel setCrlCache(Boolean crlCache) {
        this.crlCache = crlCache;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SystemDigitalCertModel.class.getSimpleName() + "[", "]")
                .add("certData='" + certData + "'")
                .add("certName='" + certName + "'")
                .add("certType='" + certType + "'")
                .add("keyStorePassword='" + keyStorePassword + "'")
                .add("privateKeyPassword='" + privateKeyPassword + "'")
                .add("verifyAuthChain=" + verifyAuthChain)
                .add("verifyValidity=" + verifyValidity)
                .add("crlCache=" + crlCache)
                .toString();
    }
}
