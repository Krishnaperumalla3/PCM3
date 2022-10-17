package com.pe.pcm.certificate.entity;

import com.pe.pcm.protocol.as2.si.entity.SciAudit;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;

/**
 * @author Shameer.v.
 */
@Entity
@Table(name = "PGP_PUBLIC_KEY")
public class PgpPublicKeyEntity implements Serializable {

    private static final long serialVersionUID = -4479505501978841278L;

    @Id
    private String objectId;

    @NotNull
    private String name;

    private String keyId;

    private String username;

    private String fingerprint;

    private String usages;

    private String masterKey;

    @UpdateTimestamp
    private Date notBefore;

    @UpdateTimestamp
    private Date notAfter;

    private String keyAlgorithm;

    private String keyType;

    private String keyStrength;

    private String rawStore;

    private String verifyOnUse;


    public String getObjectId() {
        return objectId;
    }

    public PgpPublicKeyEntity setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getName() {
        return name;
    }

    public PgpPublicKeyEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getKeyId() {
        return keyId;
    }

    public PgpPublicKeyEntity setKeyId(String keyId) {
        this.keyId = keyId;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public PgpPublicKeyEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getFingerprint() {
        return fingerprint;
    }

    public PgpPublicKeyEntity setFingerprint(String fingerprint) {
        this.fingerprint = fingerprint;
        return this;
    }

    public String getUsages() {
        return usages;
    }

    public PgpPublicKeyEntity setUsages(String usages) {
        this.usages = usages;
        return this;
    }

    public String getMasterKey() {
        return masterKey;
    }

    public PgpPublicKeyEntity setMasterKey(String masterKey) {
        this.masterKey = masterKey;
        return this;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public PgpPublicKeyEntity setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
        return this;
    }

    public Date getNotAfter() {
        return notAfter;
    }

    public PgpPublicKeyEntity setNotAfter(Date notAfter) {
        this.notAfter = notAfter;
        return this;
    }

    public String getKeyAlgorithm() {
        return keyAlgorithm;
    }

    public PgpPublicKeyEntity setKeyAlgorithm(String keyAlgorithm) {
        this.keyAlgorithm = keyAlgorithm;
        return this;
    }

    public String getKeyType() {
        return keyType;
    }

    public PgpPublicKeyEntity setKeyType(String keyType) {
        this.keyType = keyType;
        return this;
    }

    public String getKeyStrength() {
        return keyStrength;
    }

    public PgpPublicKeyEntity setKeyStrength(String keyStrength) {
        this.keyStrength = keyStrength;
        return this;
    }

    public String getRawStore() {
        return rawStore;
    }

    public PgpPublicKeyEntity setRawStore(String rawStore) {
        this.rawStore = rawStore;
        return this;
    }

    public String getVerifyOnUse() {
        return verifyOnUse;
    }

    public PgpPublicKeyEntity setVerifyOnUse(String verifyOnUse) {
        this.verifyOnUse = verifyOnUse;
        return this;
    }
}
