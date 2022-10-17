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
public class As2GenericCertModel implements Serializable {

    private String certName;
    private int certificateOrder = 1;
    private String goLiveDate;
    private String notAfterDate;

    public String getCertName() {
        return certName;
    }

    public As2GenericCertModel setCertName(String certName) {
        this.certName = certName;
        return this;
    }

    public int getCertificateOrder() {
        return certificateOrder;
    }

    public As2GenericCertModel setCertificateOrder(int certificateOrder) {
        this.certificateOrder = certificateOrder;
        return this;
    }

    public String getGoLiveDate() {
        return goLiveDate;
    }

    public As2GenericCertModel setGoLiveDate(String goLiveDate) {
        this.goLiveDate = goLiveDate;
        return this;
    }

    public String getNotAfterDate() {
        return notAfterDate;
    }

    public As2GenericCertModel setNotAfterDate(String notAfterDate) {
        this.notAfterDate = notAfterDate;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", As2GenericCertModel.class.getSimpleName() + "[", "]")
                .add("certName='" + certName + "'")
                .add("certificateOrder=" + certificateOrder)
                .add("goLiveDate='" + goLiveDate + "'")
                .add("notAfterDate='" + notAfterDate + "'")
                .toString();
    }
}
