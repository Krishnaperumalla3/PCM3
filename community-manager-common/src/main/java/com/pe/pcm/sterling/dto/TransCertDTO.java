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

package com.pe.pcm.sterling.dto;

import java.io.Serializable;
import java.util.Date;

/**
 * @author Chenchu Kiran.
 */
public class TransCertDTO implements Serializable {

    private String certificateKey;
    private String transportId;
    private String keyCertId;
    private Date goLiveDate;
    private Date notAfterDate;
    private Integer certStatus;
    private Integer certOrder;

    public String getCertificateKey() {
        return certificateKey;
    }

    public TransCertDTO setCertificateKey(String certificateKey) {
        this.certificateKey = certificateKey;
        return this;
    }

    public String getTransportId() {
        return transportId;
    }

    public TransCertDTO setTransportId(String transportId) {
        this.transportId = transportId;
        return this;
    }

    public String getKeyCertId() {
        return keyCertId;
    }

    public TransCertDTO setKeyCertId(String keyCertId) {
        this.keyCertId = keyCertId;
        return this;
    }

    public Date getGoLiveDate() {
        return goLiveDate;
    }

    public TransCertDTO setGoLiveDate(Date goLiveDate) {
        this.goLiveDate = goLiveDate;
        return this;
    }

    public Date getNotAfterDate() {
        return notAfterDate;
    }

    public TransCertDTO setNotAfterDate(Date notAfterDate) {
        this.notAfterDate = notAfterDate;
        return this;
    }

    public Integer getCertStatus() {
        return certStatus;
    }

    public TransCertDTO setCertStatus(Integer certStatus) {
        this.certStatus = certStatus;
        return this;
    }

    public Integer getCertOrder() {
        return certOrder;
    }

    public TransCertDTO setCertOrder(Integer certOrder) {
        this.certOrder = certOrder;
        return this;
    }
}
