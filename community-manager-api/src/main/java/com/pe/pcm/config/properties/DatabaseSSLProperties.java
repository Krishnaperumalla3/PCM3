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

package com.pe.pcm.config.properties;

import com.pe.pcm.miscellaneous.PasswordUtilityService;
import com.pe.pcm.utils.CommonFunctions;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static com.pe.pcm.utils.CommonFunctions.removeENC;
import static org.springframework.util.StringUtils.*;

@Component
@ConfigurationProperties(prefix = "spring.datasource.ssl")
public class DatabaseSSLProperties {
    private Boolean enabled;
    private String trustStore;
    private String trustStoreType;
    private String trustStoreCmks;

    public Boolean getEnabled() {
        return enabled != null && enabled;
    }

    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    public String getTrustStore() {
        return trustStore;
    }

    public void setTrustStore(String trustStore) {
        this.trustStore = trustStore;
    }

    public String getTrustStoreType() {
        return trustStoreType;
    }

    public void setTrustStoreType(String trustStoreType) {
        this.trustStoreType = trustStoreType;
    }

    public String getTrustStoreCmks() {
        return trustStoreCmks;
    }

    public void setTrustStoreCmks(String trustStoreCmks) {
        if (hasText(trustStoreCmks) && trustStoreCmks.startsWith("ENC(") && trustStoreCmks.endsWith(")")) {
            this.trustStoreCmks = new PasswordUtilityService().decrypt(removeENC(trustStoreCmks));
        } else {
            this.trustStoreCmks = trustStoreCmks;
        }
    }

}
