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

import com.pe.pcm.constants.AuthoritiesConstants;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

import static com.pe.pcm.utils.PCMConstants.*;

@Entity
@Table(name = "YFS_ORGANIZATION")
public class YFSOrganizationEntity extends SciAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String organizationKey;

    private String organizationCode;

    private String resourceIdentifier;

    private String xrefOrganizationCode;

    private String isHubOrganization;

    private String isNode;

    private String isEnterprise;

    private String parentOrganizationCode;

    private String corporateAddressKey;

    private String contactAddressKey;

    private String billingAddressKey;

    private String localeCode;

    private String organizationName;

    private String creatorOrganizationKey;

    private String primaryEnterpriseKey;

    private String dunsNumber;

    private String primaryUrl;

    private String inheritConfigFromEnterprise;

    private String inheritOrgConfig;

    private String businessCalendarKey;

    private String activateFlag;

    private String pwdPolicyKey;

    private String objectId;

    private String externalObjectId;

    private String extendsObjectId;

    private String identifier;

    @Column(name = "iso_20022_type")
    private String iso20022Type;

    private String issuer;

    private String schemeNameCode;

    private String schemeNameProprietary;

    private String clearingSystemMemberId;

    private String isoCsmEntity;

    private String isoOtherEntity;

    private String isoIdEntity;

    private String isoContactEntity;

    private String vatCode;

    private String emailAddr;

    private String lookupId;

    private String lookupIdComments;

    public YFSOrganizationEntity() {
        this.setLockid(0);
        this.setCreateuserid(AuthoritiesConstants.ADMIN);
        this.setModifyuserid(AuthoritiesConstants.ADMIN);
        this.setCreateprogid(SPACE);
        this.setModifyprogid(SPACE);
        this.setCreatorOrganizationKey(DEFAULT);
        this.setPrimaryEnterpriseKey(DEFAULT);
        this.setActivateFlag(Y);

        this.setOrganizationCode(EMPTY);
        this.setResourceIdentifier(SPACE);
        this.setXrefOrganizationCode(SPACE);
        this.setIsHubOrganization(SPACE);
        this.setIsNode(SPACE);
        this.setIsEnterprise(SPACE);
        this.setParentOrganizationCode(SPACE);
        this.setCorporateAddressKey(SPACE);
        this.setContactAddressKey(SPACE);
        this.setBillingAddressKey(SPACE);
        this.setLocaleCode(SPACE);
        this.setOrganizationName(SPACE);
        this.setDunsNumber(SPACE);
        this.setPrimaryUrl(SPACE);
        this.setInheritConfigFromEnterprise(SPACE);
        this.setInheritOrgConfig(SPACE);
        this.setBusinessCalendarKey(SPACE);
        this.setActivateFlag(SPACE);
        this.setExternalObjectId(SPACE);
        this.setExtendsObjectId(SPACE);
        this.setIdentifier(SPACE);
        this.setIso20022Type(SPACE);
        this.setIssuer(SPACE);
        this.setSchemeNameCode(SPACE);
        this.setSchemeNameProprietary(SPACE);
        this.setClearingSystemMemberId(SPACE);
        this.setIsoCsmEntity(SPACE);
        this.setIsoOtherEntity(SPACE);
        this.setIsoIdEntity(SPACE);
        this.setIsoContactEntity(SPACE);
        this.setVatCode(SPACE);
        this.setEmailAddr(SPACE);
        this.setLookupId(SPACE);
        this.setLookupIdComments(SPACE);
    }

    public String getOrganizationKey() {
        return organizationKey;
    }

    public void setOrganizationKey(String organizationKey) {
        this.organizationKey = organizationKey;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public void setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
    }

    public String getResourceIdentifier() {
        return resourceIdentifier;
    }

    public void setResourceIdentifier(String resourceIdentifier) {
        this.resourceIdentifier = resourceIdentifier;
    }

    public String getXrefOrganizationCode() {
        return xrefOrganizationCode;
    }

    public void setXrefOrganizationCode(String xrefOrganizationCode) {
        this.xrefOrganizationCode = xrefOrganizationCode;
    }

    public String getIsHubOrganization() {
        return isHubOrganization;
    }

    public void setIsHubOrganization(String isHubOrganization) {
        this.isHubOrganization = isHubOrganization;
    }

    public String getIsNode() {
        return isNode;
    }

    public void setIsNode(String isNode) {
        this.isNode = isNode;
    }

    public String getIsEnterprise() {
        return isEnterprise;
    }

    public void setIsEnterprise(String isEnterprise) {
        this.isEnterprise = isEnterprise;
    }

    public String getParentOrganizationCode() {
        return parentOrganizationCode;
    }

    public void setParentOrganizationCode(String parentOrganizationCode) {
        this.parentOrganizationCode = parentOrganizationCode;
    }

    public String getCorporateAddressKey() {
        return corporateAddressKey;
    }

    public void setCorporateAddressKey(String corporateAddressKey) {
        this.corporateAddressKey = corporateAddressKey;
    }

    public String getContactAddressKey() {
        return contactAddressKey;
    }

    public void setContactAddressKey(String contactAddressKey) {
        this.contactAddressKey = contactAddressKey;
    }

    public String getBillingAddressKey() {
        return billingAddressKey;
    }

    public void setBillingAddressKey(String billingAddressKey) {
        this.billingAddressKey = billingAddressKey;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public void setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public void setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
    }

    public String getCreatorOrganizationKey() {
        return creatorOrganizationKey;
    }

    public void setCreatorOrganizationKey(String creatorOrganizationKey) {
        this.creatorOrganizationKey = creatorOrganizationKey;
    }

    public String getPrimaryEnterpriseKey() {
        return primaryEnterpriseKey;
    }

    public void setPrimaryEnterpriseKey(String primaryEnterpriseKey) {
        this.primaryEnterpriseKey = primaryEnterpriseKey;
    }

    public String getDunsNumber() {
        return dunsNumber;
    }

    public void setDunsNumber(String dunsNumber) {
        this.dunsNumber = dunsNumber;
    }

    public String getPrimaryUrl() {
        return primaryUrl;
    }

    public void setPrimaryUrl(String primaryUrl) {
        this.primaryUrl = primaryUrl;
    }

    public String getInheritConfigFromEnterprise() {
        return inheritConfigFromEnterprise;
    }

    public void setInheritConfigFromEnterprise(String inheritConfigFromEnterprise) {
        this.inheritConfigFromEnterprise = inheritConfigFromEnterprise;
    }

    public String getInheritOrgConfig() {
        return inheritOrgConfig;
    }

    public void setInheritOrgConfig(String inheritOrgConfig) {
        this.inheritOrgConfig = inheritOrgConfig;
    }

    public String getBusinessCalendarKey() {
        return businessCalendarKey;
    }

    public void setBusinessCalendarKey(String businessCalendarKey) {
        this.businessCalendarKey = businessCalendarKey;
    }

    public String getActivateFlag() {
        return activateFlag;
    }

    public void setActivateFlag(String activateFlag) {
        this.activateFlag = activateFlag;
    }

    public String getPwdPolicyKey() {
        return pwdPolicyKey;
    }

    public void setPwdPolicyKey(String pwdPolicyKey) {
        this.pwdPolicyKey = pwdPolicyKey;
    }

    public String getObjectId() {
        return objectId;
    }

    public void setObjectId(String objectId) {
        this.objectId = objectId;
    }

    public String getExternalObjectId() {
        return externalObjectId;
    }

    public void setExternalObjectId(String externalObjectId) {
        this.externalObjectId = externalObjectId;
    }

    public String getExtendsObjectId() {
        return extendsObjectId;
    }

    public void setExtendsObjectId(String extendsObjectId) {
        this.extendsObjectId = extendsObjectId;
    }

    public String getIdentifier() {
        return identifier;
    }

    public void setIdentifier(String identifier) {
        this.identifier = identifier;
    }

    public String getIso20022Type() {
        return iso20022Type;
    }

    public void setIso20022Type(String iso20022Type) {
        this.iso20022Type = iso20022Type;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSchemeNameCode() {
        return schemeNameCode;
    }

    public void setSchemeNameCode(String schemeNameCode) {
        this.schemeNameCode = schemeNameCode;
    }

    public String getSchemeNameProprietary() {
        return schemeNameProprietary;
    }

    public void setSchemeNameProprietary(String schemeNameProprietary) {
        this.schemeNameProprietary = schemeNameProprietary;
    }

    public String getClearingSystemMemberId() {
        return clearingSystemMemberId;
    }

    public void setClearingSystemMemberId(String clearingSystemMemberId) {
        this.clearingSystemMemberId = clearingSystemMemberId;
    }

    public String getIsoCsmEntity() {
        return isoCsmEntity;
    }

    public void setIsoCsmEntity(String isoCsmEntity) {
        this.isoCsmEntity = isoCsmEntity;
    }

    public String getIsoOtherEntity() {
        return isoOtherEntity;
    }

    public void setIsoOtherEntity(String isoOtherEntity) {
        this.isoOtherEntity = isoOtherEntity;
    }

    public String getIsoIdEntity() {
        return isoIdEntity;
    }

    public void setIsoIdEntity(String isoIdEntity) {
        this.isoIdEntity = isoIdEntity;
    }

    public String getIsoContactEntity() {
        return isoContactEntity;
    }

    public void setIsoContactEntity(String isoContactEntity) {
        this.isoContactEntity = isoContactEntity;
    }

    public String getVatCode() {
        return vatCode;
    }

    public void setVatCode(String vatCode) {
        this.vatCode = vatCode;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public void setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
    }

    public String getLookupId() {
        return lookupId;
    }

    public void setLookupId(String lookupId) {
        this.lookupId = lookupId;
    }

    public String getLookupIdComments() {
        return lookupIdComments;
    }

    public void setLookupIdComments(String lookupIdComments) {
        this.lookupIdComments = lookupIdComments;
    }

    @Override
    public String toString() {
        return "YFSOrganizationEntity [organizationKey=" + organizationKey + ", organizationCode=" + organizationCode
                + ", resourceIdentifier=" + resourceIdentifier + ", xrefOrganizationCode=" + xrefOrganizationCode
                + ", isHubOrganization=" + isHubOrganization + ", isNode=" + isNode + ", isEnterprise=" + isEnterprise
                + ", parentOrganizationCode=" + parentOrganizationCode + ", corporateAddressKey=" + corporateAddressKey
                + ", contactAddressKey=" + contactAddressKey + ", billingAddressKey=" + billingAddressKey
                + ", localeCode=" + localeCode + ", organizationName=" + organizationName + ", creatorOrganizationKey="
                + creatorOrganizationKey + ", primaryEnterpriseKey=" + primaryEnterpriseKey + ", dunsNumber="
                + dunsNumber + ", primaryUrl=" + primaryUrl + ", inheritConfigFromEnterprise="
                + inheritConfigFromEnterprise + ", inheritOrgConfig=" + inheritOrgConfig + ", businessCalendarKey="
                + businessCalendarKey + ", activateFlag=" + activateFlag + ", pwdPolicyKey=" + pwdPolicyKey
                + ", objectId=" + objectId + ", externalObjectId=" + externalObjectId + ", extendsObjectId="
                + extendsObjectId + ", identifier=" + identifier + ", iso20022Type=" + iso20022Type + ", issuer="
                + issuer + ", schemeNameCode=" + schemeNameCode + ", schemeNameProprietary=" + schemeNameProprietary
                + ", clearingSystemMemberId=" + clearingSystemMemberId + ", isoCsmEntity=" + isoCsmEntity
                + ", isoOtherEntity=" + isoOtherEntity + ", isoIdEntity=" + isoIdEntity + ", isoContactEntity="
                + isoContactEntity + ", vatCode=" + vatCode + ", emailAddr=" + emailAddr + ", lookupId=" + lookupId
                + ", lookupIdComments=" + lookupIdComments + "]";
    }

}
