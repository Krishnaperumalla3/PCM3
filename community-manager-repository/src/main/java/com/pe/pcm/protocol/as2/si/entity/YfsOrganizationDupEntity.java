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

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "YFS_ORGANIZATION")
public class YfsOrganizationDupEntity extends SciAudit implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private String objectId;
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

    public YfsOrganizationDupEntity() {
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

    public String getObjectId() {
        return objectId;
    }

    public YfsOrganizationDupEntity setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getOrganizationKey() {
        return organizationKey;
    }

    public YfsOrganizationDupEntity setOrganizationKey(String organizationKey) {
        this.organizationKey = organizationKey;
        return this;
    }

    public String getOrganizationCode() {
        return organizationCode;
    }

    public YfsOrganizationDupEntity setOrganizationCode(String organizationCode) {
        this.organizationCode = organizationCode;
        return this;
    }

    public String getResourceIdentifier() {
        return resourceIdentifier;
    }

    public YfsOrganizationDupEntity setResourceIdentifier(String resourceIdentifier) {
        this.resourceIdentifier = resourceIdentifier;
        return this;
    }

    public String getXrefOrganizationCode() {
        return xrefOrganizationCode;
    }

    public YfsOrganizationDupEntity setXrefOrganizationCode(String xrefOrganizationCode) {
        this.xrefOrganizationCode = xrefOrganizationCode;
        return this;
    }

    public String getIsHubOrganization() {
        return isHubOrganization;
    }

    public YfsOrganizationDupEntity setIsHubOrganization(String isHubOrganization) {
        this.isHubOrganization = isHubOrganization;
        return this;
    }

    public String getIsNode() {
        return isNode;
    }

    public YfsOrganizationDupEntity setIsNode(String isNode) {
        this.isNode = isNode;
        return this;
    }

    public String getIsEnterprise() {
        return isEnterprise;
    }

    public YfsOrganizationDupEntity setIsEnterprise(String isEnterprise) {
        this.isEnterprise = isEnterprise;
        return this;
    }

    public String getParentOrganizationCode() {
        return parentOrganizationCode;
    }

    public YfsOrganizationDupEntity setParentOrganizationCode(String parentOrganizationCode) {
        this.parentOrganizationCode = parentOrganizationCode;
        return this;
    }

    public String getCorporateAddressKey() {
        return corporateAddressKey;
    }

    public YfsOrganizationDupEntity setCorporateAddressKey(String corporateAddressKey) {
        this.corporateAddressKey = corporateAddressKey;
        return this;
    }

    public String getContactAddressKey() {
        return contactAddressKey;
    }

    public YfsOrganizationDupEntity setContactAddressKey(String contactAddressKey) {
        this.contactAddressKey = contactAddressKey;
        return this;
    }

    public String getBillingAddressKey() {
        return billingAddressKey;
    }

    public YfsOrganizationDupEntity setBillingAddressKey(String billingAddressKey) {
        this.billingAddressKey = billingAddressKey;
        return this;
    }

    public String getLocaleCode() {
        return localeCode;
    }

    public YfsOrganizationDupEntity setLocaleCode(String localeCode) {
        this.localeCode = localeCode;
        return this;
    }

    public String getOrganizationName() {
        return organizationName;
    }

    public YfsOrganizationDupEntity setOrganizationName(String organizationName) {
        this.organizationName = organizationName;
        return this;
    }

    public String getCreatorOrganizationKey() {
        return creatorOrganizationKey;
    }

    public YfsOrganizationDupEntity setCreatorOrganizationKey(String creatorOrganizationKey) {
        this.creatorOrganizationKey = creatorOrganizationKey;
        return this;
    }

    public String getPrimaryEnterpriseKey() {
        return primaryEnterpriseKey;
    }

    public YfsOrganizationDupEntity setPrimaryEnterpriseKey(String primaryEnterpriseKey) {
        this.primaryEnterpriseKey = primaryEnterpriseKey;
        return this;
    }

    public String getDunsNumber() {
        return dunsNumber;
    }

    public YfsOrganizationDupEntity setDunsNumber(String dunsNumber) {
        this.dunsNumber = dunsNumber;
        return this;
    }

    public String getPrimaryUrl() {
        return primaryUrl;
    }

    public YfsOrganizationDupEntity setPrimaryUrl(String primaryUrl) {
        this.primaryUrl = primaryUrl;
        return this;
    }

    public String getInheritConfigFromEnterprise() {
        return inheritConfigFromEnterprise;
    }

    public YfsOrganizationDupEntity setInheritConfigFromEnterprise(String inheritConfigFromEnterprise) {
        this.inheritConfigFromEnterprise = inheritConfigFromEnterprise;
        return this;
    }

    public String getInheritOrgConfig() {
        return inheritOrgConfig;
    }

    public YfsOrganizationDupEntity setInheritOrgConfig(String inheritOrgConfig) {
        this.inheritOrgConfig = inheritOrgConfig;
        return this;
    }

    public String getBusinessCalendarKey() {
        return businessCalendarKey;
    }

    public YfsOrganizationDupEntity setBusinessCalendarKey(String businessCalendarKey) {
        this.businessCalendarKey = businessCalendarKey;
        return this;
    }

    public String getActivateFlag() {
        return activateFlag;
    }

    public YfsOrganizationDupEntity setActivateFlag(String activateFlag) {
        this.activateFlag = activateFlag;
        return this;
    }

    public String getPwdPolicyKey() {
        return pwdPolicyKey;
    }

    public YfsOrganizationDupEntity setPwdPolicyKey(String pwdPolicyKey) {
        this.pwdPolicyKey = pwdPolicyKey;
        return this;
    }

    public String getExternalObjectId() {
        return externalObjectId;
    }

    public YfsOrganizationDupEntity setExternalObjectId(String externalObjectId) {
        this.externalObjectId = externalObjectId;
        return this;
    }

    public String getExtendsObjectId() {
        return extendsObjectId;
    }

    public YfsOrganizationDupEntity setExtendsObjectId(String extendsObjectId) {
        this.extendsObjectId = extendsObjectId;
        return this;
    }

    public String getIdentifier() {
        return identifier;
    }

    public YfsOrganizationDupEntity setIdentifier(String identifier) {
        this.identifier = identifier;
        return this;
    }

    public String getIso20022Type() {
        return iso20022Type;
    }

    public YfsOrganizationDupEntity setIso20022Type(String iso20022Type) {
        this.iso20022Type = iso20022Type;
        return this;
    }

    public String getIssuer() {
        return issuer;
    }

    public YfsOrganizationDupEntity setIssuer(String issuer) {
        this.issuer = issuer;
        return this;
    }

    public String getSchemeNameCode() {
        return schemeNameCode;
    }

    public YfsOrganizationDupEntity setSchemeNameCode(String schemeNameCode) {
        this.schemeNameCode = schemeNameCode;
        return this;
    }

    public String getSchemeNameProprietary() {
        return schemeNameProprietary;
    }

    public YfsOrganizationDupEntity setSchemeNameProprietary(String schemeNameProprietary) {
        this.schemeNameProprietary = schemeNameProprietary;
        return this;
    }

    public String getClearingSystemMemberId() {
        return clearingSystemMemberId;
    }

    public YfsOrganizationDupEntity setClearingSystemMemberId(String clearingSystemMemberId) {
        this.clearingSystemMemberId = clearingSystemMemberId;
        return this;
    }

    public String getIsoCsmEntity() {
        return isoCsmEntity;
    }

    public YfsOrganizationDupEntity setIsoCsmEntity(String isoCsmEntity) {
        this.isoCsmEntity = isoCsmEntity;
        return this;
    }

    public String getIsoOtherEntity() {
        return isoOtherEntity;
    }

    public YfsOrganizationDupEntity setIsoOtherEntity(String isoOtherEntity) {
        this.isoOtherEntity = isoOtherEntity;
        return this;
    }

    public String getIsoIdEntity() {
        return isoIdEntity;
    }

    public YfsOrganizationDupEntity setIsoIdEntity(String isoIdEntity) {
        this.isoIdEntity = isoIdEntity;
        return this;
    }

    public String getIsoContactEntity() {
        return isoContactEntity;
    }

    public YfsOrganizationDupEntity setIsoContactEntity(String isoContactEntity) {
        this.isoContactEntity = isoContactEntity;
        return this;
    }

    public String getVatCode() {
        return vatCode;
    }

    public YfsOrganizationDupEntity setVatCode(String vatCode) {
        this.vatCode = vatCode;
        return this;
    }

    public String getEmailAddr() {
        return emailAddr;
    }

    public YfsOrganizationDupEntity setEmailAddr(String emailAddr) {
        this.emailAddr = emailAddr;
        return this;
    }

    public String getLookupId() {
        return lookupId;
    }

    public YfsOrganizationDupEntity setLookupId(String lookupId) {
        this.lookupId = lookupId;
        return this;
    }

    public String getLookupIdComments() {
        return lookupIdComments;
    }

    public YfsOrganizationDupEntity setLookupIdComments(String lookupIdComments) {
        this.lookupIdComments = lookupIdComments;
        return this;
    }


}

