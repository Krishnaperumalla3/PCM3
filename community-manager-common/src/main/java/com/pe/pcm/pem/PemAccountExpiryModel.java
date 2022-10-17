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

package com.pe.pcm.pem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;
import java.util.StringJoiner;

/**
 * @author Kiran Reddy.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PemAccountExpiryModel implements Serializable {

    private String userName;
    private String certName;
    private String profileName;
    private String profileId;
    private String emailId;
    private String pemIdentifier;
    private String certType;
    private String pass;
    private Timestamp pwdLastUpdatedDate;
    private String protocol;
    private Date notAfter;
    private Date notBefore;
    private String expiresOn;
    private String keyName;
    private Timestamp createDate;

    public String getUserName() {
        return userName;
    }

    public PemAccountExpiryModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getCertName() {
        return certName;
    }

    public PemAccountExpiryModel setCertName(String certName) {
        this.certName = certName;
        return this;
    }

    public String getProfileName() {
        return profileName;
    }

    public PemAccountExpiryModel setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public String getProfileId() {
        return profileId;
    }

    public PemAccountExpiryModel setProfileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public String getEmailId() {
        return emailId;
    }

    public PemAccountExpiryModel setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public String getPemIdentifier() {
        return pemIdentifier;
    }

    public PemAccountExpiryModel setPemIdentifier(String pemIdentifier) {
        this.pemIdentifier = pemIdentifier;
        return this;
    }

    public String getCertType() {
        return certType;
    }

    public PemAccountExpiryModel setCertType(String certType) {
        this.certType = certType;
        return this;
    }

    public String getPass() {
        return pass;
    }

    public PemAccountExpiryModel setPass(String pass) {
        this.pass = pass;
        return this;
    }

    public Timestamp getPwdLastUpdatedDate() {
        return pwdLastUpdatedDate;
    }

    public PemAccountExpiryModel setPwdLastUpdatedDate(Timestamp pwdLastUpdatedDate) {
        this.pwdLastUpdatedDate = pwdLastUpdatedDate;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public PemAccountExpiryModel setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public Date getNotAfter() {
        return notAfter;
    }

    public PemAccountExpiryModel setNotAfter(Date notAfter) {
        this.notAfter = notAfter;
        return this;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public PemAccountExpiryModel setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
        return this;
    }

    public String getExpiresOn() {
        return expiresOn;
    }

    public PemAccountExpiryModel setExpiresOn(String expiresOn) {
        this.expiresOn = expiresOn;
        return this;
    }

    public String getKeyName() {
        return keyName;
    }

    public PemAccountExpiryModel setKeyName(String keyName) {
        this.keyName = keyName;
        return this;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public PemAccountExpiryModel setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PemAccountExpiryModel.class.getSimpleName() + "[", "]")
                .add("userName='" + userName + "'")
                .add("certName='" + certName + "'")
                .add("profileName='" + profileName + "'")
                .add("profileId='" + profileId + "'")
                .add("emailId='" + emailId + "'")
                .add("pemIdentifier='" + pemIdentifier + "'")
                .add("certType='" + certType + "'")
                .add("pass='" + pass + "'")
                .add("pwdLastUpdatedDate=" + pwdLastUpdatedDate)
                .add("protocol='" + protocol + "'")
                .add("notAfter=" + notAfter)
                .add("notBefore=" + notBefore)
                .add("expiresOn='" + expiresOn + "'")
                .toString();
    }
}
