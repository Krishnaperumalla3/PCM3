/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.sterling.mailbox;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProvisioningFactModel implements Serializable {

    private String factName;
    private String displayLabel;
    private String description;

    public String getFactName() {
        return factName;
    }

    public ProvisioningFactModel setFactName(String factName) {
        this.factName = factName;
        return this;
    }

    public String getDisplayLabel() {
        return displayLabel;
    }

    public ProvisioningFactModel setDisplayLabel(String displayLabel) {
        this.displayLabel = displayLabel;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public ProvisioningFactModel setDescription(String description) {
        this.description = description;
        return this;
    }
}
