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

package com.pe.pcm.config.si;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotEmpty;
import java.util.StringJoiner;

/**
 * @author Chenchu Kiran.
 */
@Component
@ConfigurationProperties(prefix = "b2b")
public class B2bModuleProperties {


    private boolean active;

    private As2 as2;

    private Api api;


    private static class Api {
        @NotEmpty
        private String userName;
        private String password;
        private String baseUrl;

        public String getUserName() {
            return userName;
        }

        public void setUserName(String userName) {
            this.userName = userName;
        }

        public String getPassword() {
            return password;
        }

        public void setPassword(String password) {
            this.password = password;
        }

        public String getBaseUrl() {
            return baseUrl;
        }

        public void setBaseUrl(String baseUrl) {
            this.baseUrl = baseUrl;
        }

        @Override
        public String toString() {
            return new StringJoiner(", ", Api.class.getSimpleName() + "[", "]")
                    .add("userName='" + userName + "'")
                    .add("password='" + password + "'")
                    .add("baseUrl='" + baseUrl + "'")
                    .toString();
        }
    }

    public static class As2 {
        private boolean active = Boolean.FALSE;

        public boolean isActive() {
            return active;
        }

        public void setActive(boolean active) {
            this.active = active;
        }
    }

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public As2 getAs2() {
        return as2;
    }

    public void setAs2(As2 as2) {
        this.as2 = as2;
    }

    public Api getApi() {
        return api;
    }

    public void setApi(Api api) {
        this.api = api;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", B2bModuleProperties.class.getSimpleName() + "[", "]")
                .add("active=" + active)
                .add("as2=" + as2)
                .add("api=" + api)
                .toString();
    }
}
