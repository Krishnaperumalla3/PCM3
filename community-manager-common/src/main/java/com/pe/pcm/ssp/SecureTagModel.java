package com.pe.pcm.ssp;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;
import java.util.List;

@XmlAccessorType(XmlAccessType.FIELD)
public class SecureTagModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement
    private String ccc;
    @XmlElement
    private CipherSuiteModel cipherSuites;
    @XmlElement
    private String clientAuthentication;
    @XmlElement
    private String keyCertName;
    @XmlElement
    private String keyStoreName;
    @XmlElement
    private String protocol;
    @XmlElement
    private String trustStoreName;
    @XmlElement
    private List<TrustedCertNameModel> trustedCertNames;
    @XmlElement
    private String verifyCommonName;
    @XmlElement
    private String clientAuthenticationCD;

    public SecureTagModel() {
    }

    public SecureTagModel(String ccc, CipherSuiteModel cipherSuites, String clientAuthentication, String keyCertName, String keyStoreName, String protocol, String trustStoreName, List<TrustedCertNameModel> trustedCertNames, String verifyCommonName, String clientAuthenticationCD) {
        this.ccc = ccc;
        this.cipherSuites = cipherSuites;
        this.clientAuthentication = clientAuthentication;
        this.keyCertName = keyCertName;
        this.keyStoreName = keyStoreName;
        this.protocol = protocol;
        this.trustStoreName = trustStoreName;
        this.trustedCertNames = trustedCertNames;
        this.verifyCommonName = verifyCommonName;
        this.clientAuthenticationCD = clientAuthenticationCD;
    }

    public String getCcc() {
        return ccc;
    }

    public SecureTagModel setCcc(String ccc) {
        this.ccc = ccc;
        return this;
    }

    public CipherSuiteModel getCipherSuites() {
        return cipherSuites;
    }

    public SecureTagModel setCipherSuites(CipherSuiteModel cipherSuites) {
        this.cipherSuites = cipherSuites;
        return this;
    }

    public String getClientAuthentication() {
        return clientAuthentication;
    }

    public SecureTagModel setClientAuthentication(String clientAuthentication) {
        this.clientAuthentication = clientAuthentication;
        return this;
    }

    public String getKeyCertName() {
        return keyCertName;
    }

    public SecureTagModel setKeyCertName(String keyCertName) {
        this.keyCertName = keyCertName;
        return this;
    }

    public String getKeyStoreName() {
        return keyStoreName;
    }

    public SecureTagModel setKeyStoreName(String keyStoreName) {
        this.keyStoreName = keyStoreName;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public SecureTagModel setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getTrustStoreName() {
        return trustStoreName;
    }

    public SecureTagModel setTrustStoreName(String trustStoreName) {
        this.trustStoreName = trustStoreName;
        return this;
    }

    public List<TrustedCertNameModel> getTrustedCertNames() {
        return trustedCertNames;
    }

    public SecureTagModel setTrustedCertNames(List<TrustedCertNameModel> trustedCertNames) {
        this.trustedCertNames = trustedCertNames;
        return this;
    }

    public String getVerifyCommonName() {
        return verifyCommonName;
    }

    public SecureTagModel setVerifyCommonName(String verifyCommonName) {
        this.verifyCommonName = verifyCommonName;
        return this;
    }

    public String getClientAuthenticationCD() {
        return clientAuthenticationCD;
    }

    public SecureTagModel setClientAuthenticationCD(String clientAuthenticationCD) {
        this.clientAuthenticationCD = clientAuthenticationCD;
        return this;
    }
}
