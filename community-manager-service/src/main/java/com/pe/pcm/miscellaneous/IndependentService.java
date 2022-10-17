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

package com.pe.pcm.miscellaneous;

import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import static com.pe.pcm.exception.GlobalExceptionHandler.internalServerError;

/**
 * @author Kiran Reddy.
 */
@Service
public class IndependentService {
    private final Environment environment;

    public IndependentService(Environment environment) {
        this.environment = environment;
    }

    public String getActiveProfile() {
        try {
            return environment.getActiveProfiles()[0];
        } catch (IndexOutOfBoundsException ex) {
            throw internalServerError("Please Start the application on pcm or cm-api or cm or saml profile");
        }
    }

    public String getDbType() {
        try {
            return environment.getProperty("dbType");
        } catch (Exception ex) {
            throw internalServerError("Please contact administration team.");
        }
    }

}
