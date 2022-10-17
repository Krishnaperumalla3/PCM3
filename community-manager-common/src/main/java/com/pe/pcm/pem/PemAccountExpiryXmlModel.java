package com.pe.pcm.pem;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.sql.Date;
import java.util.StringJoiner;

@XmlAccessorType(XmlAccessType.FIELD)
public class PemAccountExpiryXmlModel implements Serializable {

    @XmlElement
    private String userName;
    @XmlElement
    private String certName;
    @XmlElement
    private String profileName;
    @XmlElement
    private String profileId;
    @XmlElement
    private String emailId;
    @XmlElement
    private String pemIdentifier;
    @XmlElement
    private String certType;
    @XmlElement
    private String pass;
    @XmlElement
    private String pwdLastUpdatedDate;
    @XmlElement
    private String protocol;
    @XmlElement
    private Date notAfter;
    @XmlElement
    private Date notBefore;
    @XmlElement
    private String expiresOn;
    @XmlElement
    private String nonProd;
    @XmlElement
    private String prod;

    public String getUserName() {
        return userName;
    }

    public PemAccountExpiryXmlModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getCertName() {
        return certName;
    }

    public PemAccountExpiryXmlModel setCertName(String certName) {
        this.certName = certName;
        return this;
    }

    public String getProfileName() {
        return profileName;
    }

    public PemAccountExpiryXmlModel setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public String getProfileId() {
        return profileId;
    }

    public PemAccountExpiryXmlModel setProfileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public String getEmailId() {
        return emailId;
    }

    public PemAccountExpiryXmlModel setEmailId(String emailId) {
        this.emailId = emailId;
        return this;
    }

    public String getPemIdentifier() {
        return pemIdentifier;
    }

    public PemAccountExpiryXmlModel setPemIdentifier(String pemIdentifier) {
        this.pemIdentifier = pemIdentifier;
        return this;
    }

    public String getCertType() {
        return certType;
    }

    public PemAccountExpiryXmlModel setCertType(String certType) {
        this.certType = certType;
        return this;
    }

    public String getPass() {
        return pass;
    }

    public PemAccountExpiryXmlModel setPass(String pass) {
        this.pass = pass;
        return this;
    }

    public String getPwdLastUpdatedDate() {
        return pwdLastUpdatedDate;
    }

    public PemAccountExpiryXmlModel setPwdLastUpdatedDate(String pwdLastUpdatedDate) {
        this.pwdLastUpdatedDate = pwdLastUpdatedDate;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public PemAccountExpiryXmlModel setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public Date getNotAfter() {
        return notAfter;
    }

    public PemAccountExpiryXmlModel setNotAfter(Date notAfter) {
        this.notAfter = notAfter;
        return this;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public PemAccountExpiryXmlModel setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
        return this;
    }

    public String getExpiresOn() {
        return expiresOn;
    }

    public PemAccountExpiryXmlModel setExpiresOn(String expiresOn) {
        this.expiresOn = expiresOn;
        return this;
    }

    public String getNonProd() {
        return nonProd;
    }

    public PemAccountExpiryXmlModel setNonProd(String nonProd) {
        this.nonProd = nonProd;
        return this;
    }

    public String getProd() {
        return prod;
    }

    public PemAccountExpiryXmlModel setProd(String prod) {
        this.prod = prod;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PemAccountExpiryXmlModel.class.getSimpleName() + "[", "]")
                .add("userName='" + userName + "'")
                .add("certName='" + certName + "'")
                .add("profileName='" + profileName + "'")
                .add("profileId='" + profileId + "'")
                .add("emailId='" + emailId + "'")
                .add("pemIdentifier='" + pemIdentifier + "'")
                .add("certType='" + certType + "'")
                .add("pass='" + pass + "'")
                .add("pwdLastUpdatedDate='" + pwdLastUpdatedDate + "'")
                .add("protocol='" + protocol + "'")
                .add("notAfter=" + notAfter)
                .add("notBefore=" + notBefore)
                .add("expiresOn='" + expiresOn + "'")
                .add("nonProd='" + nonProd + "'")
                .add("prod='" + prod + "'")
                .toString();
    }
}
