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

@JsonInclude(JsonInclude.Include.NON_NULL)
public class CaCertModel implements Serializable {

    private String certData;
    private String certGroups;
    private String certName;
    private boolean verifyAuthChain;
    private boolean verifyValidity;

    public String getCertData() {
        return certData;
    }

    public CaCertModel setCertData(String certData) {
        this.certData = certData;
        return this;
    }

    public String getCertGroups() {
        return certGroups;
    }

    public CaCertModel setCertGroups(String certGroups) {
        this.certGroups = certGroups;
        return this;
    }

    public String getCertName() {
        return certName;
    }

    public CaCertModel setCertName(String certName) {
        this.certName = certName;
        return this;
    }

    public boolean isVerifyAuthChain() {
        return verifyAuthChain;
    }

    public CaCertModel setVerifyAuthChain(boolean verifyAuthChain) {
        this.verifyAuthChain = verifyAuthChain;
        return this;
    }

    public boolean isVerifyValidity() {
        return verifyValidity;
    }

    public CaCertModel setVerifyValidity(boolean verifyValidity) {
        this.verifyValidity = verifyValidity;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CaCertModel.class.getSimpleName() + "[", "]")
                .add("certData='" + certData + "'")
                .add("certGroups='" + certGroups + "'")
                .add("certName='" + certName + "'")
                .add("verifyAuthChain=" + verifyAuthChain)
                .add("verifyValidity=" + verifyValidity)
                .toString();
    }
}
