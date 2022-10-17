/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;

import java.io.Serializable;

/**
 * @author Chenchu Kiran Reddy.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class As2AdditionalServerModel implements Serializable {

    private String endpoint;
    private Integer responseTimeout;
    private String sslType;
    private String cipherStrength;
    private String keyCertificatePassphrase;
    private String keyCertId;
    private String caCertId;

    @JacksonXmlProperty(localName = "END_POINT")
    public String getEndpoint() {
        return endpoint;
    }

    public As2AdditionalServerModel setEndpoint(String endpoint) {
        this.endpoint = endpoint;
        return this;
    }

    @JacksonXmlProperty(localName = "RESPONSE_TIMEOUT")
    public Integer getResponseTimeout() {
        return responseTimeout;
    }

    public As2AdditionalServerModel setResponseTimeout(Integer responseTimeout) {
        this.responseTimeout = responseTimeout;
        return this;
    }

    @JacksonXmlProperty(localName = "SSL_TYPE")
    public String getSslType() {
        return sslType;
    }

    public As2AdditionalServerModel setSslType(String sslType) {
        this.sslType = sslType;
        return this;
    }

    @JacksonXmlProperty(localName = "CIPHER_STRENGTH")
    public String getCipherStrength() {
        return cipherStrength;
    }

    public As2AdditionalServerModel setCipherStrength(String cipherStrength) {
        this.cipherStrength = cipherStrength;
        return this;
    }

    @JacksonXmlProperty(localName = "KEY_CERTIFICATE_PASSPHRASE")
    public String getKeyCertificatePassphrase() {
        return keyCertificatePassphrase;
    }

    public As2AdditionalServerModel setKeyCertificatePassphrase(String keyCertificatePassphrase) {
        this.keyCertificatePassphrase = keyCertificatePassphrase;
        return this;
    }

    @JacksonXmlProperty(localName = "KEY_CERT")
    public String getKeyCertId() {
        return keyCertId;
    }

    public As2AdditionalServerModel setKeyCertId(String keyCertId) {
        this.keyCertId = keyCertId;
        return this;
    }

    @JacksonXmlProperty(localName = "CA_CERT")
    public String getCaCertId() {
        return caCertId;
    }

    public As2AdditionalServerModel setCaCertId(String caCertId) {
        this.caCertId = caCertId;
        return this;
    }
}
