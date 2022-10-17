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
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Date;

import static com.pe.pcm.utils.PCMConstants.*;

@Entity
@Table(name = "SCI_PROFILE")
public class SciProfileEntity extends SciAudit implements Serializable {

    @Id
    private String objectId;

    private String externalObjectId;

    private String objectVersion;

    private String objectName;

    private String entityId;

    private String delivChannelId;

    private String packagingId;

    private String svcProviderId;

    private String profileType;

    private String roleName;

    private String roleHref;

    private String service;

    private String serviceType;

    private String action;

    private String objectClass;

    @UpdateTimestamp
    private Date lastModification;

    private String lastModifier;

    private String objectState;

    private Integer roleNameInh;

    private Integer roleHrefInh;

    private Integer serviceInh;

    private Integer serviceTypeInh;

    private Integer actionInh;

    private Integer profileTypeInh;

    private Integer svcProvideridInh;

    private Integer profileWfsInh;

    private String extendsObjectId;

    private Integer extObjectVersion;

    private String rnProfileId;

    private String gln;

    private Integer glnInh;

    private String profileKey;

    public SciProfileEntity() {
        this.setLockid(1);
        this.setCreateuserid(AuthoritiesConstants.ADMIN);
        this.setModifyuserid(AuthoritiesConstants.ADMIN);
        this.setCreateprogid(UI);
        this.setModifyprogid(UI);
        this.setObjectVersion("1");
        this.setObjectName("database");
        this.setObjectClass("PROFILE");
        this.setObjectState(STRING_TRUE);
        this.setExtObjectVersion(0);
        this.setSvcProviderId(NONE);
        this.setProfileType("AS2");
        this.setLastModifier(AuthoritiesConstants.ADMIN);
        this.setRoleNameInh(0);
        this.setRoleHrefInh(0);
        this.setServiceInh(0);
        this.setServiceTypeInh(0);
        this.setActionInh(0);
        this.setProfileTypeInh(0);
        this.setSvcProvideridInh(0);
        this.setProfileWfsInh(0);
        this.setGlnInh(0);
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

    public String getObjectVersion() {
        return objectVersion;
    }

    public void setObjectVersion(String objectVersion) {
        this.objectVersion = objectVersion;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getEntityId() {
        return entityId;
    }

    public void setEntityId(String entityId) {
        this.entityId = entityId;
    }

    public String getDelivChannelId() {
        return delivChannelId;
    }

    public void setDelivChannelId(String delivChannelId) {
        this.delivChannelId = delivChannelId;
    }

    public String getPackagingId() {
        return packagingId;
    }

    public void setPackagingId(String packagingId) {
        this.packagingId = packagingId;
    }

    public String getSvcProviderId() {
        return svcProviderId;
    }

    public void setSvcProviderId(String svcProviderId) {
        this.svcProviderId = svcProviderId;
    }

    public String getProfileType() {
        return profileType;
    }

    public void setProfileType(String profileType) {
        this.profileType = profileType;
    }

    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleHref() {
        return roleHref;
    }

    public void setRoleHref(String roleHref) {
        this.roleHref = roleHref;
    }

    public String getService() {
        return service;
    }

    public void setService(String service) {
        this.service = service;
    }

    public String getServiceType() {
        return serviceType;
    }

    public void setServiceType(String serviceType) {
        this.serviceType = serviceType;
    }

    public String getAction() {
        return action;
    }

    public void setAction(String action) {
        this.action = action;
    }

    public String getObjectClass() {
        return objectClass;
    }

    public void setObjectClass(String objectClass) {
        this.objectClass = objectClass;
    }

    public Date getLastModification() {
        return lastModification;
    }

    public void setLastModification(Date lastModification) {
        this.lastModification = lastModification;
    }

    public String getLastModifier() {
        return lastModifier;
    }

    public void setLastModifier(String lastModifier) {
        this.lastModifier = lastModifier;
    }

    public String getObjectState() {
        return objectState;
    }

    public void setObjectState(String objectState) {
        this.objectState = objectState;
    }

    public Integer getRoleNameInh() {
        return roleNameInh;
    }

    public void setRoleNameInh(Integer roleNameInh) {
        this.roleNameInh = roleNameInh;
    }

    public Integer getRoleHrefInh() {
        return roleHrefInh;
    }

    public void setRoleHrefInh(Integer roleHrefInh) {
        this.roleHrefInh = roleHrefInh;
    }

    public Integer getServiceInh() {
        return serviceInh;
    }

    public void setServiceInh(Integer serviceInh) {
        this.serviceInh = serviceInh;
    }

    public Integer getServiceTypeInh() {
        return serviceTypeInh;
    }

    public void setServiceTypeInh(Integer serviceTypeInh) {
        this.serviceTypeInh = serviceTypeInh;
    }

    public Integer getActionInh() {
        return actionInh;
    }

    public void setActionInh(Integer actionInh) {
        this.actionInh = actionInh;
    }

    public Integer getProfileTypeInh() {
        return profileTypeInh;
    }

    public void setProfileTypeInh(Integer profileTypeInh) {
        this.profileTypeInh = profileTypeInh;
    }

    public Integer getSvcProvideridInh() {
        return svcProvideridInh;
    }

    public void setSvcProvideridInh(Integer svcProvideridInh) {
        this.svcProvideridInh = svcProvideridInh;
    }

    public Integer getProfileWfsInh() {
        return profileWfsInh;
    }

    public void setProfileWfsInh(Integer profileWfsInh) {
        this.profileWfsInh = profileWfsInh;
    }

    public String getExtendsObjectId() {
        return extendsObjectId;
    }

    public void setExtendsObjectId(String extendsObjectId) {
        this.extendsObjectId = extendsObjectId;
    }

    public Integer getExtObjectVersion() {
        return extObjectVersion;
    }

    public void setExtObjectVersion(Integer extObjectVersion) {
        this.extObjectVersion = extObjectVersion;
    }

    public String getRnProfileId() {
        return rnProfileId;
    }

    public void setRnProfileId(String rnProfileId) {
        this.rnProfileId = rnProfileId;
    }

    public String getGln() {
        return gln;
    }

    public void setGln(String gln) {
        this.gln = gln;
    }

    public Integer getGlnInh() {
        return glnInh;
    }

    public void setGlnInh(Integer glnInh) {
        this.glnInh = glnInh;
    }

    public String getProfileKey() {
        return profileKey;
    }

    public void setProfileKey(String profileKey) {
        this.profileKey = profileKey;
    }

    @Override
    public String toString() {
        return "SciProfileEntity [objectId=" + objectId + ", externalObjectId=" + externalObjectId + ", objectVersion="
                + objectVersion + ", objectName=" + objectName + ", entityId=" + entityId + ", delivChannelId="
                + delivChannelId + ", packagingId=" + packagingId + ", svcProviderId=" + svcProviderId
                + ", profileType=" + profileType + ", roleName=" + roleName + ", roleHref=" + roleHref + ", service="
                + service + ", serviceType=" + serviceType + ", action=" + action + ", objectClass=" + objectClass
                + ", lastModification=" + lastModification + ", lastModifier=" + lastModifier + ", objectState="
                + objectState + ", roleNameInh=" + roleNameInh + ", roleHrefInh=" + roleHrefInh + ", serviceInh="
                + serviceInh + ", serviceTypeInh=" + serviceTypeInh + ", actionInh=" + actionInh + ", profileTypeInh="
                + profileTypeInh + ", svcProvideridInh=" + svcProvideridInh + ", profileWfsInh=" + profileWfsInh
                + ", extendsObjectId=" + extendsObjectId + ", extObjectVersion=" + extObjectVersion + ", rnProfileId="
                + rnProfileId + ", gln=" + gln + ", glnInh=" + glnInh + ", profileKey=" + profileKey + "]";
    }

}
