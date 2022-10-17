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

package com.pe.pcm.profile;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.pe.pcm.annotations.constraint.Required;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

/**
 * @author Chenchu Kiran Reddy.
 */

@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
public class ProfileModel implements Serializable {

    private String pkId;

    @XmlElement(name = "PROFILE_NAME")
    @Required(customMessage = "profileName")
    private String profileName;

    @XmlElement(name = "CUSTOM_PROFILE_NAME")
    private String customProfileName;

    @XmlElement(name = "PGP_INFO")
    private String pgpInfo;

    @XmlElement(name = "IP_WHITE_LIST")
    private String ipWhiteList;

    @XmlElement(name = "PROFILE_ID")
    @Required(customMessage = "profileId")
    private String profileId;

    @XmlElement(name = "EMAIL_ID")
    @Required(customMessage = "emailId")
    private String emailId;

    @XmlElement(name = "PHONE")
    @Required(customMessage = "phone")
    private String phone;

    @XmlElement(name = "PROTOCOL")
    @Required(customMessage = "protocol")
    private String protocol;

    @XmlElement(name = "ADDRESS_LINE1")
    private String addressLine1;

    @XmlElement(name = "ADDRESS_LINE2")
    private String addressLine2;

    @XmlElement(name = "STATUS")
    @Required(customMessage = "status")
    private Boolean status;

    @XmlElement(name = "HUB_INFO")
    private Boolean hubInfo;

    @XmlElement(name = "LIKE")
    private Boolean isLike;

    @XmlElement(name = "ONLY_PCM")
    private boolean onlyPCM;

    @XmlElement(name = "PEM_IDENTIFIER")
    private String pemIdentifier;


    @JacksonXmlProperty(localName = "PK_ID")
    public String getPkId() {
        return pkId;
    }

    public ProfileModel setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    @JacksonXmlProperty(localName = "PROFILE_NAME")
    public String getProfileName() {
        return profileName;
    }

    public ProfileModel setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    @JacksonXmlProperty(localName = "PROFILE_ID")
    public String getProfileId() {
        return profileId;
    }

    public ProfileModel setProfileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    @JacksonXmlProperty(localName = "EMAIL_ID")
    public String getEmailId() {
        return emailId;
    }

    public ProfileModel setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    @JacksonXmlProperty(localName = "PHONE")
    public String getPhone() {
        return phone;
    }

    public ProfileModel setPhone(String phone) {
        this.phone = phone;
        return this;
    }

    @JacksonXmlProperty(localName = "PROTOCOL")
    public String getProtocol() {
        return protocol;
    }

    public ProfileModel setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    @JacksonXmlProperty(localName = "ADDRESS_LINE1")
    public String getAddressLine1() {
        return addressLine1;
    }

    public ProfileModel setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
        return this;
    }

    @JacksonXmlProperty(localName = "ADDRESS_LINE2")
    public String getAddressLine2() {
        return addressLine2;
    }

    public ProfileModel setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
        return this;
    }

    @JacksonXmlProperty(localName = "STATUS")
    public Boolean getStatus() {
        return status;
    }

    public ProfileModel setStatus(Boolean status) {
        this.status = status;
        return this;
    }

    @JacksonXmlProperty(localName = "HUB_INFO")
    public Boolean getHubInfo() {
        return hubInfo;
    }

    public ProfileModel setHubInfo(Boolean hubInfo) {
        this.hubInfo = hubInfo;
        return this;
    }

    @JacksonXmlProperty(localName = "CUSTOM_PROFILE_NAME")
    public String getCustomProfileName() {
        return customProfileName;
    }

    public ProfileModel setCustomProfileName(String customProfileName) {
        this.customProfileName = customProfileName;
        return this;
    }

    @JacksonXmlProperty(localName = "PGP_INFO")
    public String getPgpInfo() {
        return pgpInfo;
    }

    public ProfileModel setPgpInfo(String pgpInfo) {
        this.pgpInfo = pgpInfo;
        return this;
    }

    @JacksonXmlProperty(localName = "IP_WHITE_LIST")
    public String getIpWhiteList() {
        return ipWhiteList;
    }

    public ProfileModel setIpWhiteList(String ipWhiteList) {
        this.ipWhiteList = ipWhiteList;
        return this;
    }

    public boolean isOnlyPCM() {
        return onlyPCM;
    }

    public ProfileModel setOnlyPCM(boolean onlyPCM) {
        this.onlyPCM = onlyPCM;
        return this;
    }

    public Boolean getLike() {
        return isLike;
    }

    public ProfileModel setLike(Boolean like) {
        isLike = like;
        return this;
    }

    @JacksonXmlProperty(localName = "PEM_IDENTIFIER")
    public String getPemIdentifier() {
        return pemIdentifier;
    }

    public ProfileModel setPemIdentifier(String pemIdentifier) {
        this.pemIdentifier = pemIdentifier;
        return this;
    }
}
