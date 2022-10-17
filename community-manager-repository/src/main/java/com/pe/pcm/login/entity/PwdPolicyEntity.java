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

package com.pe.pcm.login.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "PWD_POLICY")
public class PwdPolicyEntity implements Serializable {

    private static final long serialVersionUID = 6127086631263597810L;

    @Id
    private String policyId;
    private String policyName;
    private String numDaysValid;
    private String minLength;
    private String maxLentgh;
    private String numHistEntries;
    private String specialCharsReq;
    private String firstAttempt;
    private String status;
    private String lockid;
    private String createts;
    private String modifyts;
    private String createuserid;
    private String modifyuserid;
    private String createprogid;
    private String modifyprogid;


    public String getPolicyId() {
        return policyId;
    }

    public PwdPolicyEntity setPolicyId(String policyId) {
        this.policyId = policyId;
        return this;
    }

    public String getPolicyName() {
        return policyName;
    }

    public PwdPolicyEntity setPolicyName(String policyName) {
        this.policyName = policyName;
        return this;
    }

    public String getNumDaysValid() {
        return numDaysValid;
    }

    public PwdPolicyEntity setNumDaysValid(String numDaysValid) {
        this.numDaysValid = numDaysValid;
        return this;
    }

    public String getMinLength() {
        return minLength;
    }

    public PwdPolicyEntity setMinLength(String minLength) {
        this.minLength = minLength;
        return this;
    }

    public String getMaxLentgh() {
        return maxLentgh;
    }

    public PwdPolicyEntity setMaxLentgh(String maxLentgh) {
        this.maxLentgh = maxLentgh;
        return this;
    }

    public String getNumHistEntries() {
        return numHistEntries;
    }

    public PwdPolicyEntity setNumHistEntries(String numHistEntries) {
        this.numHistEntries = numHistEntries;
        return this;
    }

    public String getSpecialCharsReq() {
        return specialCharsReq;
    }

    public PwdPolicyEntity setSpecialCharsReq(String specialCharsReq) {
        this.specialCharsReq = specialCharsReq;
        return this;
    }

    public String getFirstAttempt() {
        return firstAttempt;
    }

    public PwdPolicyEntity setFirstAttempt(String firstAttempt) {
        this.firstAttempt = firstAttempt;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public PwdPolicyEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getLockid() {
        return lockid;
    }

    public PwdPolicyEntity setLockid(String lockid) {
        this.lockid = lockid;
        return this;
    }

    public String getCreatets() {
        return createts;
    }

    public PwdPolicyEntity setCreatets(String createts) {
        this.createts = createts;
        return this;
    }

    public String getModifyts() {
        return modifyts;
    }

    public PwdPolicyEntity setModifyts(String modifyts) {
        this.modifyts = modifyts;
        return this;
    }

    public String getCreateuserid() {
        return createuserid;
    }

    public PwdPolicyEntity setCreateuserid(String createuserid) {
        this.createuserid = createuserid;
        return this;
    }

    public String getModifyuserid() {
        return modifyuserid;
    }

    public PwdPolicyEntity setModifyuserid(String modifyuserid) {
        this.modifyuserid = modifyuserid;
        return this;
    }

    public String getCreateprogid() {
        return createprogid;
    }

    public PwdPolicyEntity setCreateprogid(String createprogid) {
        this.createprogid = createprogid;
        return this;
    }

    public String getModifyprogid() {
        return modifyprogid;
    }

    public PwdPolicyEntity setModifyprogid(String modifyprogid) {
        this.modifyprogid = modifyprogid;
        return this;
    }
}
