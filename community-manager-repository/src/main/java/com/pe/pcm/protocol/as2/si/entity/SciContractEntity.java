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

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

import static com.pe.pcm.utils.PCMConstants.*;

@Entity
@Table(name = "SCI_CONTRACT")
public class SciContractEntity implements Serializable {

    @Id
    private String objectId;

    private String objectVersion = "1";

    private String objectName;

    private String startDate;

    private String endDate;

    private String status = "SIGNED";

    private String prodProfileId;

    private String consumeProfileId;

    private String workflowName;

    private String objectClass = "CONTRACT";

    @UpdateTimestamp
    private Timestamp lastModification;

    private String lastModifier = PCM_ADMIN;

    private String objectState = STRING_TRUE;

    private String contractKey;

    private int lockid = 1;

    @CreationTimestamp
    @Column(name = "CREATETS", nullable = false, updatable = false)
    private Timestamp createts;

    @UpdateTimestamp
    private Timestamp modifyts;

    private String createuserid = PCM_ADMIN;

    private String modifyuserid = PCM_ADMIN;

    private String createprogid = PCM_UI;

    private String modifyprogid = PCM_UI;

    public String getObjectId() {
        return objectId;
    }

    public SciContractEntity setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getObjectVersion() {
        return objectVersion;
    }

    public SciContractEntity setObjectVersion(String objectVersion) {
        this.objectVersion = objectVersion;
        return this;
    }

    public String getObjectName() {
        return objectName;
    }

    public SciContractEntity setObjectName(String objectName) {
        this.objectName = objectName;
        return this;
    }

    public String getStartDate() {
        return startDate;
    }

    public SciContractEntity setStartDate(String startDate) {
        this.startDate = startDate;
        return this;
    }

    public String getEndDate() {
        return endDate;
    }

    public SciContractEntity setEndDate(String endDate) {
        this.endDate = endDate;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public SciContractEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getProdProfileId() {
        return prodProfileId;
    }

    public SciContractEntity setProdProfileId(String prodProfileId) {
        this.prodProfileId = prodProfileId;
        return this;
    }

    public String getConsumeProfileId() {
        return consumeProfileId;
    }

    public SciContractEntity setConsumeProfileId(String consumeProfileId) {
        this.consumeProfileId = consumeProfileId;
        return this;
    }

    public String getWorkflowName() {
        return workflowName;
    }

    public SciContractEntity setWorkflowName(String workflowName) {
        this.workflowName = workflowName;
        return this;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public SciContractEntity setObjectClass(String objectClass) {
        this.objectClass = objectClass;
        return this;
    }

    public Timestamp getLastModification() {
        return lastModification;
    }

    public SciContractEntity setLastModification(Timestamp lastModification) {
        this.lastModification = lastModification;
        return this;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public SciContractEntity setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
        return this;
    }

    public String getObjectState() {
        return objectState;
    }

    public SciContractEntity setObjectState(String objectState) {
        this.objectState = objectState;
        return this;
    }

    public String getContractKey() {
        return contractKey;
    }

    public SciContractEntity setContractKey(String contractKey) {
        this.contractKey = contractKey;
        return this;
    }

    public int getLockid() {
        return lockid;
    }

    public SciContractEntity setLockid(int lockid) {
        this.lockid = lockid;
        return this;
    }

    public Timestamp getCreatets() {
        return createts;
    }

    public SciContractEntity setCreatets(Timestamp createts) {
        this.createts = createts;
        return this;
    }

    public Timestamp getModifyts() {
        return modifyts;
    }

    public SciContractEntity setModifyts(Timestamp modifyts) {
        this.modifyts = modifyts;
        return this;
    }

    public String getCreateuserid() {
        return createuserid;
    }

    public SciContractEntity setCreateuserid(String createuserid) {
        this.createuserid = createuserid;
        return this;
    }

    public String getModifyuserid() {
        return modifyuserid;
    }

    public SciContractEntity setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid;
        return this;
    }

    public String getCreateprogid() {
        return createprogid;
    }

    public SciContractEntity setCreateprogid(String createprogid) {
        this.createprogid = createprogid;
        return this;
    }

    public String getModifyprogid() {
        return modifyprogid;
    }

    public SciContractEntity setModifyprogid(String modifyprogid) {
        this.modifyprogid = modifyprogid;
        return this;
    }
}
