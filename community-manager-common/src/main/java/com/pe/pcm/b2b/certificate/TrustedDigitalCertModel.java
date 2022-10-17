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

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author Chenchu Kiran.
 */
public class TrustedDigitalCertModel implements Serializable {

    private String certName;
    private String certData;
    private Boolean verifyAuthChain;
    private Boolean verifyValidity;

    public String getCertName() {
        return certName;
    }

    public TrustedDigitalCertModel setCertName(String certName) {
        this.certName = certName;
        return this;
    }

    public String getCertData() {
        return certData;
    }

    public TrustedDigitalCertModel setCertData(String certData) {
        this.certData = certData;
        return this;
    }

    public Boolean getVerifyAuthChain() {
        return verifyAuthChain;
    }

    public TrustedDigitalCertModel setVerifyAuthChain(Boolean verifyAuthChain) {
        this.verifyAuthChain = verifyAuthChain;
        return this;
    }

    public Boolean getVerifyValidity() {
        return verifyValidity;
    }

    public TrustedDigitalCertModel setVerifyValidity(Boolean verifyValidity) {
        this.verifyValidity = verifyValidity;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TrustedDigitalCertModel.class.getSimpleName() + "[", "]")
                .add("certName='" + certName + "'")
                .add("certData='" + certData + "'")
                .add("verifyAuthChain=" + verifyAuthChain)
                .add("verifyValidity=" + verifyValidity)
                .toString();
    }
}
