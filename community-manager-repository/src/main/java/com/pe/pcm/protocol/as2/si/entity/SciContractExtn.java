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

package com.pe.pcm.protocol.as2.si.entity;

import com.pe.pcm.utils.PCMConstants;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "SCI_CONTRACT_EXTN")
public class SciContractExtn implements Serializable {

    @Id
    private String objectId;

    private String objectVersion = "1";

    private String objectName;

    private String contractId;

    private String value;

    private String objectClass = "CONTRACT_EXTN";

    private String lastModification;

    private String lastModifier = PCMConstants.PCM_ADMIN;

    private String objectState = "true";

    public String getObjectId() {
        return objectId;
    }

    public SciContractExtn setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getObjectVersion() {
        return objectVersion;
    }

    public SciContractExtn setObjectVersion(String objectVersion) {
        this.objectVersion = objectVersion;
        return this;
    }

    public String getObjectName() {
        return objectName;
    }

    public SciContractExtn setObjectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public String getContractId() {
        return contractId;
    }

    public SciContractExtn setContractId(String contractId) {
        this.contractId = contractId;
        return this;
    }

    public String getValue() {
        return value;
    }

    public SciContractExtn setValue(String value) {
        this.value = value;
        return this;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public SciContractExtn setObjectClass(String objectClass) {
        this.objectClass = objectClass;
        return this;
    }

    public String getLastModification() {
        return lastModification;
    }

    public SciContractExtn setLastModification(String lastModification) {
        this.lastModification = lastModification;
        return this;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public SciContractExtn setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
        return this;
    }

    public String getObjectState() {
        return objectState;
    }

    public SciContractExtn setObjectState(String objectState) {
        this.objectState = objectState;
        return this;
    }
}
