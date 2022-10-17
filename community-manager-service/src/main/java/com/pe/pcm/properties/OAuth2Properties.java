package com.pe.pcm.properties;

import com.pe.pcm.miscellaneous.PasswordUtilityService;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import static com.pe.pcm.utils.CommonFunctions.removeENC;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Component
@ConfigurationProperties(prefix = "oauth2")
public class OAuth2Properties {
    private String tokenUrl;
    private String grantType;
    private String clientId;
    private String clientSecret;
    private String scope;
    private String resource;
    private String username;
    private String cmks;
    private Token token;


    public String getTokenUrl() {
        return tokenUrl;
    }

    public OAuth2Properties setTokenUrl(String tokenUrl) {
        this.tokenUrl = tokenUrl;
        return this;
    }

    public String getGrantType() {
        return grantType;
    }

    public OAuth2Properties setGrantType(String grantType) {
        this.grantType = grantType;
        return this;
    }

    public String getClientId() {
        return clientId;
    }

    public OAuth2Properties setClientId(String clientId) {
        this.clientId = clientId;
        return this;
    }

    public String getClientSecret() {
        return clientSecret;
    }

    public OAuth2Properties setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
        return this;
    }

    public String getScope() {
        return scope;
    }

    public OAuth2Properties setScope(String scope) {
        this.scope = scope;
        return this;
    }

    public String getResource() {
        return resource;
    }

    public OAuth2Properties setResource(String resource) {
        this.resource = resource;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public OAuth2Properties setUsername(String username) {
        this.username = username;
        return this;
    }

    public String getCmks() {
        return cmks;
    }

    public OAuth2Properties setCmks(String cmks) {
        if (hasText(cmks) && cmks.startsWith("ENC")) {
            this.cmks = new PasswordUtilityService().decrypt(removeENC(cmks));
        } else {
            this.cmks = cmks;
        }
        return this;
    }

    public Token getToken() {
        return token;
    }

    public OAuth2Properties setToken(Token token) {
        this.token = token;
        return this;
    }

    public static class Token {
        private String responseParser;
        private String prefix;
        private String header;

        public String getResponseParser() {
            if (hasText(responseParser)) {
                return responseParser;
            }
            return "access_token";
        }

        public Token setResponseParser(String responseParser) {
            this.responseParser = responseParser;
            return this;
        }

        public String getPrefix() {
            if (hasText(prefix)) {
                return prefix;
            }
            return "Bearer";
        }

        public Token setPrefix(String prefix) {
            this.prefix = prefix;
            return this;
        }

        public String getHeader() {
            if (hasText(header)) {
                return header;
            }
            return "Authorization";
        }

        public Token setHeader(String header) {
            this.header = header;
            return this;
        }
    }
}
