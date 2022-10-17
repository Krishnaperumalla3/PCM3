/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc, Version 6.1 (the "License");
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

package com.pe.pcm.envelope;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import org.apache.commons.lang3.builder.ToStringBuilder;

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EdiProperties {


    @NotNull
    private String partnerPkId;
    private String partnerName;
    private String direction;
    private String validateInput;
    private String validateOutput;
    private String useIndicator;


    public String getPartnerPkId() {
        return partnerPkId;
    }

    public EdiProperties setPartnerPkId(String partnerPkId) {
        this.partnerPkId = partnerPkId;
        return this;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public EdiProperties setPartnerName(String partnerName) {
        this.partnerName = partnerName;
        return this;
    }

    public String getDirection() {
        return direction;
    }

    public EdiProperties setDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public String getValidateInput() {
        return validateInput;
    }

    public EdiProperties setValidateInput(String validateInput) {
        this.validateInput = validateInput;
        return this;
    }

    public String getValidateOutput() {
        return validateOutput;
    }

    public EdiProperties setValidateOutput(String validateOutput) {
        this.validateOutput = validateOutput;
        return this;
    }

    public String getUseIndicator() {
        return useIndicator;
    }

    public EdiProperties setUseIndicator(String useIndicator) {
        this.useIndicator = useIndicator;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EdiProperties{");
        sb.append("partnerPkId='").append(partnerPkId).append('\'');
        sb.append(", partnerName='").append(partnerName).append('\'');
        sb.append(", direction='").append(direction).append('\'');
        sb.append(", validateInput='").append(validateInput).append('\'');
        sb.append(", validateOutput='").append(validateOutput).append('\'');
        sb.append(", useIndicator='").append(useIndicator).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
