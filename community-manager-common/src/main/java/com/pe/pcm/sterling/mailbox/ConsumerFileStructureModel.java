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

package com.pe.pcm.sterling.mailbox;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerFileStructureModel implements Serializable {

    private String consumerLayerName;
    private String fileNameFormat;
    private Boolean encryptionRequired;
    private Boolean signatureRequired;

    public String getConsumerLayerName() {
        return consumerLayerName;
    }

    public ConsumerFileStructureModel setConsumerLayerName(String consumerLayerName) {
        this.consumerLayerName = consumerLayerName;
        return this;
    }

    public String getFileNameFormat() {
        return fileNameFormat;
    }

    public ConsumerFileStructureModel setFileNameFormat(String fileNameFormat) {
        this.fileNameFormat = fileNameFormat;
        return this;
    }

    public Boolean getEncryptionRequired() {
        return encryptionRequired;
    }

    public ConsumerFileStructureModel setEncryptionRequired(Boolean encryptionRequired) {
        this.encryptionRequired = encryptionRequired;
        return this;
    }

    public Boolean getSignatureRequired() {
        return signatureRequired;
    }

    public ConsumerFileStructureModel setSignatureRequired(Boolean signatureRequired) {
        this.signatureRequired = signatureRequired;
        return this;
    }
}