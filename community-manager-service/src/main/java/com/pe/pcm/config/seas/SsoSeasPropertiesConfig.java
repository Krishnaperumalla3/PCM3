/*
 *
 *  * Copyright (c) 2020 Pragma Edge Inc
 *  *
 *  * Licensed under the Pragma Edge Inc
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * https://pragmaedge.com/licenseagreement
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.pe.pcm.config.seas;

import com.pe.pcm.exception.CommunityManagerServiceException;
import com.pe.pcm.miscellaneous.PasswordUtilityService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;

import static com.pe.pcm.constants.ProfilesConstants.SSO_SSP_SEAS;
import static com.pe.pcm.utils.CommonFunctions.removeENC;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */
@Component
@Profile(SSO_SSP_SEAS)
@ConfigurationProperties(prefix = SSO_SSP_SEAS)
public class SsoSeasPropertiesConfig {
    private static final Logger LOGGER = LoggerFactory.getLogger(SsoSeasPropertiesConfig.class);

    private SspProperties ssp;
    private UserRequest userRequest;
    private SeasProperties seas;

    public SspProperties getSsp() {
        return ssp;
    }

    public SsoSeasPropertiesConfig setSsp(SspProperties ssp) {
        this.ssp = ssp;
        return this;
    }

    public UserRequest getUserRequest() {
        return userRequest;
    }

    public SsoSeasPropertiesConfig setUserRequest(UserRequest userRequest) {
        this.userRequest = userRequest;
        return this;
    }

    public SeasProperties getSeas() {
        return seas;
    }

    public SsoSeasPropertiesConfig setSeas(SeasProperties seas) {
        this.seas = seas;
        return this;
    }

    public static class SspProperties {
        private String userHeaderName = "SM_USER";
        private String tokenCookieName = "SSOTOKEN";
        private String logoutEndpoint = "/Signon/logout.html";

        public String getUserHeaderName() {
            return userHeaderName;
        }

        public SspProperties setUserHeaderName(String userHeaderName) {
            if (hasText(userHeaderName)) {
                this.userHeaderName = userHeaderName;
            }
            return this;
        }

        public String getTokenCookieName() {
            return tokenCookieName;
        }

        public SspProperties setTokenCookieName(String tokenCookieName) {
            if (hasText(tokenCookieName)) {
                this.tokenCookieName = tokenCookieName;
            }
            return this;
        }

        public String getLogoutEndpoint() {
            return logoutEndpoint;
        }

        public SspProperties setLogoutEndpoint(String logoutEndpoint) {
            if (hasText(logoutEndpoint)) {
                if (logoutEndpoint.startsWith("/")) {
                    this.logoutEndpoint = logoutEndpoint;
                } else {
                    this.logoutEndpoint = "/" + logoutEndpoint;
                }
            }
            return this;
        }

        @Override
        public String toString() {
            return "SspProperties{" +
                    "userHeaderName='" + userHeaderName + '\'' +
                    ", tokenCookieName='" + tokenCookieName + '\'' +
                    ", logoutEndpoint='" + logoutEndpoint + '\'' +
                    '}';
        }
    }

    public static class SeasProperties {
        private String authProfile = "communityManager";
        private String host;
        private int port;
        private SeasSSL ssl;

        public String getAuthProfile() {
            return authProfile;
        }

        public SeasProperties setAuthProfile(String authProfile) {
            if (hasText(authProfile)) {
                this.authProfile = authProfile;
            }
            return this;
        }

        public String getHost() {
            return host;
        }

        public SeasProperties setHost(String host) {
            this.host = host;
            return this;
        }

        public int getPort() {
            return port;
        }

        public SeasProperties setPort(int port) {
            this.port = port;
            return this;
        }

        public SeasSSL getSsl() {
            return ssl;
        }

        public SeasProperties setSsl(SeasSSL ssl) {
            this.ssl = ssl;
            return this;
        }

        @Override
        public String toString() {
            return "SeasProperties{" +
                    ", authProfile='" + authProfile + '\'' +
                    ", host='" + host + '\'' +
                    ", port=" + port +
                    ", ssl=" + ssl +
                    '}';
        }
    }

    public static class SeasSSL {
        private boolean enabled;
        private String protocol;
        private String cipherSuits;
        private SeasTrustStore trustStore;
        private SeasKeyStore keyStore;

        public boolean isEnabled() {
            return enabled;
        }

        public SeasSSL setEnabled(boolean enabled) {
            this.enabled = enabled;
            return this;
        }

        public String getProtocol() {
            return protocol;
        }

        public SeasSSL setProtocol(String protocol) {
            this.protocol = protocol;
            return this;
        }

        public String getCipherSuits() {
            return cipherSuits;
        }

        public SeasSSL setCipherSuits(String cipherSuits) {
            this.cipherSuits = cipherSuits;
            return this;
        }

        public SeasTrustStore getTrustStore() {
            return trustStore;
        }

        public SeasSSL setTrustStore(SeasTrustStore trustStore) {
            this.trustStore = trustStore;
            return this;
        }

        public SeasKeyStore getKeyStore() {
            return keyStore;
        }

        public SeasSSL setKeyStore(SeasKeyStore keyStore) {
            this.keyStore = keyStore;
            return this;
        }

        @Override
        public String toString() {
            return "SeasSSL{" +
                    "enabled=" + enabled +
                    ", protocol='" + protocol + '\'' +
                    ", cipherSuits='" + cipherSuits + '\'' +
                    ", trustStore=" + trustStore +
                    ", keyStore=" + keyStore +
                    '}';
        }
    }

    public static class SeasTrustStore {
        private String name;
        private String cmks;
        private String alias;
        private String type;

        public String getName() {
            return name;
        }

        public SeasTrustStore setName(String name) {
            this.name = name;
            return this;
        }

        public String getCmks() {
            return cmks;
        }

        public SeasTrustStore setCmks(String cmks) {
            this.cmks = cmks;
            return this;
        }

        public String getAlias() {
            return alias;
        }

        public SeasTrustStore setAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public String getType() {
            return type;
        }

        public SeasTrustStore setType(String type) {
            this.type = type;
            return this;
        }

        @Override
        public String toString() {
            return "SeasTrustStore{" +
                    "name='" + name + '\'' +
                    ", cmks='" + cmks + '\'' +
                    ", alias='" + alias + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    public static class SeasKeyStore {
        private String name;
        private String cmks;
        private String alias;
        private String type;

        public String getName() {
            return name;
        }

        public SeasKeyStore setName(String name) {
            this.name = name;
            return this;
        }

        public String getCmks() {
            return cmks;
        }

        public SeasKeyStore setCmks(String cmks) {
            this.cmks = cmks;
            return this;
        }

        public String getAlias() {
            return alias;
        }

        public SeasKeyStore setAlias(String alias) {
            this.alias = alias;
            return this;
        }

        public String getType() {
            return type;
        }

        public SeasKeyStore setType(String type) {
            this.type = type;
            return this;
        }

        @Override
        public String toString() {
            return "SeasKeyStore{" +
                    "name='" + name + '\'' +
                    ", cmks='" + cmks + '\'' +
                    ", alias='" + alias + '\'' +
                    ", type='" + type + '\'' +
                    '}';
        }
    }

    public static class UserRequest {
        private String roleDelimiter;
        private UserCustomProperties user;
        private LdapUserRolesMapConfig userRoles;

        public String getRoleDelimiter() {
            if (hasText(roleDelimiter)) {
                return roleDelimiter;
            }
            return ",";
        }

        public UserRequest setRoleDelimiter(String roleDelimiter) {
            this.roleDelimiter = roleDelimiter;
            return this;
        }

        public UserCustomProperties getUser() {
            return user;
        }

        public UserRequest setUser(UserCustomProperties user) {
            this.user = user;
            return this;
        }

        public LdapUserRolesMapConfig getUserRoles() {
            return userRoles;
        }

        public UserRequest setUserRoles(LdapUserRolesMapConfig userRoles) {
            this.userRoles = userRoles;
            return this;
        }

        @Override
        public String toString() {
            return "UserRequest{" +
                    "roleDelimiter='" + roleDelimiter + '\'' +
                    ", user=" + user +
                    ", userRoles=" + userRoles +
                    '}';
        }
    }

    public static class UserCustomProperties {
        private String email;
        private String role;
        private String firstName;
        private String lastName;
        private String phone;
        private String externalId;
        private String preferredLanguage;

        public String getEmail() {
            return email;
        }

        public UserCustomProperties setEmail(String email) {
            this.email = email;
            return this;
        }

        public String getRole() {
            return role;
        }

        public UserCustomProperties setRole(String role) {
            this.role = role;
            return this;
        }

        public String getFirstName() {
            return firstName;
        }

        public UserCustomProperties setFirstName(String firstName) {
            this.firstName = firstName;
            return this;
        }

        public String getLastName() {
            return lastName;
        }

        public UserCustomProperties setLastName(String lastName) {
            this.lastName = lastName;
            return this;
        }

        public String getPhone() {
            return phone;
        }

        public UserCustomProperties setPhone(String phone) {
            this.phone = phone;
            return this;
        }

        public String getExternalId() {
            return externalId;
        }

        public UserCustomProperties setExternalId(String externalId) {
            this.externalId = externalId;
            return this;
        }

        public String getPreferredLanguage() {
            if (!hasText(preferredLanguage)) {
                return "preferredLanguage";
            }
            return preferredLanguage;
        }

        public UserCustomProperties setPreferredLanguage(String preferredLanguage) {
            this.preferredLanguage = preferredLanguage;
            return this;
        }

        @Override
        public String toString() {
            return "UserCustomProperties{" +
                    "email='" + email + '\'' +
                    ", role='" + role + '\'' +
                    ", firstName='" + firstName + '\'' +
                    ", lastName='" + lastName + '\'' +
                    ", phone='" + phone + '\'' +
                    ", externalId='" + externalId + '\'' +
                    ", preferredLanguage='" + preferredLanguage + '\'' +
                    '}';
        }
    }

    public static class LdapUserRolesMapConfig {
        private String super_admin;
        private String admin;
        private String on_boarder;
        private String business_admin;
        private String business_user;
        private String data_processor;
        private String data_processor_restricted;

        public String getSuper_admin() {
            return super_admin;
        }

        public LdapUserRolesMapConfig setSuper_admin(String super_admin) {
            this.super_admin = super_admin;
            return this;
        }

        public String getAdmin() {
            return admin;
        }

        public LdapUserRolesMapConfig setAdmin(String admin) {
            this.admin = admin;
            return this;
        }

        public String getOn_boarder() {
            return on_boarder;
        }

        public LdapUserRolesMapConfig setOn_boarder(String on_boarder) {
            this.on_boarder = on_boarder;
            return this;
        }

        public String getBusiness_admin() {
            return business_admin;
        }

        public LdapUserRolesMapConfig setBusiness_admin(String business_admin) {
            this.business_admin = business_admin;
            return this;
        }

        public String getBusiness_user() {
            return business_user;
        }

        public LdapUserRolesMapConfig setBusiness_user(String business_user) {
            this.business_user = business_user;
            return this;
        }

        public String getData_processor() {
            return data_processor;
        }

        public LdapUserRolesMapConfig setData_processor(String data_processor) {
            this.data_processor = data_processor;
            return this;
        }

        public String getData_processor_restricted() {
            return data_processor_restricted;
        }

        public LdapUserRolesMapConfig setData_processor_restricted(String data_processor_restricted) {
            this.data_processor_restricted = data_processor_restricted;
            return this;
        }

        @Override
        public String toString() {
            return "LdapUserRolesMapConfig{" +
                    "super_admin='" + super_admin + '\'' +
                    ", admin='" + admin + '\'' +
                    ", on_boarder='" + on_boarder + '\'' +
                    ", business_admin='" + business_admin + '\'' +
                    ", business_user='" + business_user + '\'' +
                    ", data_processor='" + data_processor + '\'' +
                    ", data_processor_restricted='" + data_processor_restricted + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "SsoSeasPropertiesConfig{" +
                "ssp=" + ssp +
                ", userRequest=" + userRequest +
                ", seas=" + seas +
                '}';
    }

    @PostConstruct
    void pwdEnc() {
        // Roles and other params validation done in ManageLdapUserService.java class
        if (hasText(getSeas().getSsl().getTrustStore().getCmks())) {
            if (getSeas().getSsl().getTrustStore().getCmks().startsWith("ENC")) {
                try {
                    getSeas().getSsl().getTrustStore().setCmks(new PasswordUtilityService().decrypt(removeENC(getSeas().getSsl().getTrustStore().getCmks())));
                } catch (CommunityManagerServiceException e) {
                    LOGGER.error("Unable to Decrypt the Value of sso-ssp-seas.seas.ssl.trust-store.cmks, Please Provide the Proper Encrypted Value");
                    System.exit(0);
                }

            }
        }

        if (hasText(getSeas().getSsl().getKeyStore().getCmks())) {
            if (getSeas().getSsl().getKeyStore().getCmks().startsWith("ENC")) {
                try {
                    getSeas().getSsl().getKeyStore().setCmks(new PasswordUtilityService().decrypt(removeENC(getSeas().getSsl().getKeyStore().getCmks())));
                } catch (CommunityManagerServiceException e) {
                    LOGGER.error("Unable to Decrypt the Value of sso-ssp-seas.seas.ssl.key-store.cmks, Please Provide the Proper Encrypted Value");
                    System.exit(0);
                }

            }
        }
    }

}
