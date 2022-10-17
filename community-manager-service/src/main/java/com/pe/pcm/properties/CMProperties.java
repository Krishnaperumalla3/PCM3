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

package com.pe.pcm.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import static java.lang.Boolean.FALSE;
import static java.lang.Boolean.TRUE;
import static org.springframework.util.StringUtils.hasText;

/**
 * @author Kiran Reddy.
 */

@Component
@ConfigurationProperties(prefix = "cm")
public class CMProperties {

    private String color;
    private Boolean cmDeployment = TRUE;
    private String cmKs;
    private Boolean apiConnectEnabled = FALSE;
    private Boolean sfgPcdReports = FALSE;

    private String tempDirectory;

    public String getColor() {
        if (hasText(color)) {
            return color;
        }
        return "black";
    }

    public CMProperties setColor(String color) {
        this.color = color;
        return this;
    }

    public Boolean getCmDeployment() {
        return cmDeployment;
    }

    public CMProperties setCmDeployment(Boolean cmDeployment) {
        if (cmDeployment == null) {
            this.cmDeployment = TRUE;
        } else {
            this.cmDeployment = cmDeployment;
        }
        return this;
    }

    public String getCmKs() {
        return cmKs;
    }

    public CMProperties setCmKs(String cmKs) {
        this.cmKs = cmKs;
        return this;
    }

    public Boolean getApiConnectEnabled() {
        return apiConnectEnabled;
    }

    public CMProperties setApiConnectEnabled(Boolean apiConnectEnabled) {
        if (apiConnectEnabled == null) {
            this.apiConnectEnabled = FALSE;
        } else {
            this.apiConnectEnabled = apiConnectEnabled;
        }
        return this;
    }

    public Boolean getSfgPcdReports() {
        return sfgPcdReports;
    }

    public CMProperties setSfgPcdReports(Boolean sfgPcdReports) {
        if (sfgPcdReports == null) {
            this.sfgPcdReports = FALSE;
        } else {
            this.sfgPcdReports = sfgPcdReports;
        }
        return this;
    }

    public String getTempDirectory() {
        if (StringUtils.hasText(tempDirectory)) {
            return tempDirectory;
        } else {
            return "cm_temp_directory";
        }
    }

    public CMProperties setTempDirectory(String tempDirectory) {
        this.tempDirectory = tempDirectory;
        return this;
    }
}
