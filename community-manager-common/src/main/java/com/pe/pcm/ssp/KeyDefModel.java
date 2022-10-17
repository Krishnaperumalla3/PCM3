package com.pe.pcm.ssp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class KeyDefModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String description;
    @XmlElement
    private String enabled;
    @XmlElement
    private String keyData;
    @XmlElement
    private String keyType;
    @XmlElement
    private String name;
    @XmlElement
    private String forceToUnlock;
    @XmlElement
    private String formatVersion;
    @XmlElement
    private String keyDataFormat;
    @XmlElement
    private String keyInHSM;
    @XmlElement
    private String password;
    @XmlElement
    private String serialNum;
    @XmlElement
    private String validFrom;
    @XmlElement
    private String validTo;
    @XmlElement
    private String verStamp;
    @XmlElement
    private String versionNum;




    public KeyDefModel() {
    }

    public KeyDefModel(String description, String enabled, String keyData, String keyType, String name, String forceToUnlock, String formatVersion, String keyDataFormat, String keyInHSM, String password, String serialNum, String validFrom, String validTo, String verStamp, String versionNum) {
        this.description = description;
        this.enabled = enabled;
        this.keyData = keyData;
        this.keyType = keyType;
        this.name = name;
        this.forceToUnlock = forceToUnlock;
        this.formatVersion = formatVersion;
        this.keyDataFormat = keyDataFormat;
        this.keyInHSM = keyInHSM;
        this.password = password;
        this.serialNum = serialNum;
        this.validFrom = validFrom;
        this.validTo = validTo;
        this.verStamp = verStamp;
        this.versionNum = versionNum;
    }

    public String getDescription() {
        return description;
    }

    public KeyDefModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getEnabled() {
        return enabled;
    }

    public KeyDefModel setEnabled(String enabled) {
        this.enabled = enabled;
        return this;
    }

    public String getKeyData() {
        return keyData;
    }

    public KeyDefModel setKeyData(String keyData) {
        this.keyData = keyData;
        return this;
    }

    public String getKeyType() {
        return keyType;
    }

    public KeyDefModel setKeyType(String keyType) {
        this.keyType = keyType;
        return this;
    }

    public String getName() {
        return name;
    }

    public KeyDefModel setName(String name) {
        this.name = name;
        return this;
    }

    public String getForceToUnlock() {
        return forceToUnlock;
    }

    public KeyDefModel setForceToUnlock(String forceToUnlock) {
        this.forceToUnlock = forceToUnlock;
        return this;
    }

    public String getFormatVersion() {
        return formatVersion;
    }

    public KeyDefModel setFormatVersion(String formatVersion) {
        this.formatVersion = formatVersion;
        return this;
    }

    public String getKeyDataFormat() {
        return keyDataFormat;
    }

    public KeyDefModel setKeyDataFormat(String keyDataFormat) {
        this.keyDataFormat = keyDataFormat;
        return this;
    }

    public String getKeyInHSM() {
        return keyInHSM;
    }

    public KeyDefModel setKeyInHSM(String keyInHSM) {
        this.keyInHSM = keyInHSM;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public KeyDefModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public String getSerialNum() {
        return serialNum;
    }

    public KeyDefModel setSerialNum(String serialNum) {
        this.serialNum = serialNum;
        return this;
    }

    public String getValidFrom() {
        return validFrom;
    }

    public KeyDefModel setValidFrom(String validFrom) {
        this.validFrom = validFrom;
        return this;
    }

    public String getValidTo() {
        return validTo;
    }

    public KeyDefModel setValidTo(String validTo) {
        this.validTo = validTo;
        return this;
    }

    public String getVerStamp() {
        return verStamp;
    }

    public KeyDefModel setVerStamp(String verStamp) {
        this.verStamp = verStamp;
        return this;
    }

    public String getVersionNum() {
        return versionNum;
    }

    public KeyDefModel setVersionNum(String versionNum) {
        this.versionNum = versionNum;
        return this;
    }
}
