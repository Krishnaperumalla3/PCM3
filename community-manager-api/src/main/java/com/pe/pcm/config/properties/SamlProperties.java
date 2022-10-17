package com.pe.pcm.config.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.StringJoiner;

import static com.pe.pcm.constants.ProfilesConstants.SAML;

/**
 * @author Kiran Reddy.
 */
@Component
@ConfigurationProperties(prefix = SAML)
public class SamlProperties {

    private Jwt jwt;
    private Idp idp;
    private String scheme;
    private String host;
    private Url url;
    private Ssl ssl;

    public Jwt getJwt() {
        return jwt;
    }

    public SamlProperties setJwt(Jwt jwt) {
        this.jwt = jwt;
        return this;
    }

    public Idp getIdp() {
        return idp;
    }

    public SamlProperties setIdp(Idp idp) {
        this.idp = idp;
        return this;
    }

    public String getScheme() {
        return scheme;
    }

    public SamlProperties setScheme(String scheme) {
        this.scheme = scheme;
        return this;
    }

    public String getHost() {
        return host;
    }

    public SamlProperties setHost(String host) {
        this.host = host;
        return this;
    }

    public Url getUrl() {
        return url;
    }

    public SamlProperties setUrl(Url url) {
        this.url = url;
        return this;
    }

    public Ssl getSsl() {
        return ssl;
    }

    public SamlProperties setSsl(Ssl ssl) {
        this.ssl = ssl;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SamlProperties.class.getSimpleName() + "[", "]")
                .add("jwt=" + jwt)
                .add("idp=" + idp)
                .add("scheme='" + scheme + "'")
                .add("host='" + host + "'")
                .add("url=" + url)
                .add("ssl=" + ssl)
                .toString();
    }

    public static class Jwt {
        private String secretKey;
        private Integer sessionExpire;

        public String getSecretKey() {
            return secretKey;
        }

        public Jwt setSecretKey(String secretKey) {
            this.secretKey = secretKey;
            return this;
        }

        public Integer getSessionExpire() {
            return sessionExpire;
        }

        public Jwt setSessionExpire(Integer sessionExpire) {
            this.sessionExpire = sessionExpire;
            return this;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Jwt.class.getSimpleName() + "[", "]")
                    .add("secretKey='" + secretKey + "'")
                    .add("sessionExpire=" + sessionExpire)
                    .toString();
        }
    }

    public static class Idp {
        private String metadata;
        private String entityId;

        public String getMetadata() {
            return metadata;
        }

        public Idp setMetadata(String metadata) {
            this.metadata = metadata;
            return this;
        }

        public String getEntityId() {
            return entityId;
        }

        public Idp setEntityId(String entityId) {
            this.entityId = entityId;
            return this;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Idp.class.getSimpleName() + "[", "]")
                    .add("metadata='" + metadata + "'")
                    .add("entityId='" + entityId + "'")
                    .toString();
        }
    }

    public static class Url {
        private String client;
        private String entity;

        public String getClient() {
            return client;
        }

        public Url setClient(String client) {
            this.client = client;
            return this;
        }

        public String getEntity() {
            return entity;
        }

        public Url setEntity(String entity) {
            this.entity = entity;
            return this;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Url.class.getSimpleName() + "[", "]")
                    .add("client='" + client + "'")
                    .add("entity='" + entity + "'")
                    .toString();
        }
    }

    public static class Ssl {
        private String keyStore;
        private String keyCmks;
        private String storeCmks;
        private String keyAlias;

        public String getKeyStore() {
            return keyStore;
        }

        public Ssl setKeyStore(String keyStore) {
            this.keyStore = keyStore;
            return this;
        }

        public String getKeyCmks() {
            return keyCmks;
        }

        public Ssl setKeyCmks(String keyCmks) {
            this.keyCmks = keyCmks;
            return this;
        }

        public String getStoreCmks() {
            return storeCmks;
        }

        public Ssl setStoreCmks(String storeCmks) {
            this.storeCmks = storeCmks;
            return this;
        }

        public String getKeyAlias() {
            return keyAlias;
        }

        public Ssl setKeyAlias(String keyAlias) {
            this.keyAlias = keyAlias;
            return this;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Ssl.class.getSimpleName() + "[", "]")
                    .add("keyStore='" + keyStore + "'")
                    .add("keyCmks='" + keyCmks + "'")
                    .add("storeCmks='" + storeCmks + "'")
                    .add("keyAlias='" + keyAlias + "'")
                    .toString();
        }
    }
}
