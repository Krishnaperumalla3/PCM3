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

package com.pe.pcm.config.b2bi;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.LinkedHashMap;
import java.util.Map;

@Component
@ConfigurationProperties(prefix = "sterling-b2bi")
public class SterlingB2biPropertiesConfig {

    private B2BiApi b2biApi;

    public B2BiApi getB2biApi() {
        return b2biApi;
    }

    public SterlingB2biPropertiesConfig setB2biApi(B2BiApi b2biApi) {
        this.b2biApi = b2biApi;
        return this;
    }

    public static class B2BiApi {
        Map<String, Integer> authHost;

        public Map<String, Integer> getAuthHost() {
            if (authHost == null) {
                return new LinkedHashMap<>();
            }
            return authHost;
        }

        public B2BiApi setAuthHost(Map<String, Integer> authHost) {
            this.authHost = authHost;
            return this;
        }
    }
}
