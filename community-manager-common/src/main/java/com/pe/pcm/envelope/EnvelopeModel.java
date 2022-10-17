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

import javax.validation.constraints.NotNull;

@JsonIgnoreProperties(ignoreUnknown = true)
public class EnvelopeModel {

    private String pkId;
    private EdiProperties ediProperties;
    private IsaSegment isaSegment;
    private GsSegment gsSegment;
    private InBound inBound;
    private StSegment stSegment;
    private OutBound outBound;

    public String getPkId() {
        return pkId;
    }

    public EnvelopeModel setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public EdiProperties getEdiProperties() {
        return ediProperties;
    }

    public EnvelopeModel setEdiProperties(EdiProperties ediProperties) {
        this.ediProperties = ediProperties;
        return this;
    }

    public IsaSegment getIsaSegment() {
        return isaSegment;
    }

    public EnvelopeModel setIsaSegment(IsaSegment isaSegment) {
        this.isaSegment = isaSegment;
        return this;
    }

    public GsSegment getGsSegment() {
        return gsSegment;
    }

    public EnvelopeModel setGsSegment(GsSegment gsSegment) {
        this.gsSegment = gsSegment;
        return this;
    }

    public InBound getInBound() {
        return inBound;
    }

    public EnvelopeModel setInBound(InBound inBound) {
        this.inBound = inBound;
        return this;
    }

    public StSegment getStSegment() {
        return stSegment;
    }

    public EnvelopeModel setStSegment(StSegment stSegment) {
        this.stSegment = stSegment;
        return this;
    }

    public OutBound getOutBound() {
        return outBound;
    }

    public EnvelopeModel setOutBound(OutBound outBound) {
        this.outBound = outBound;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("EnvelopeModel{");
        sb.append("pkId='").append(pkId).append('\'');
        sb.append(", ediProperties=").append(ediProperties);
        sb.append(", isaSegment=").append(isaSegment);
        sb.append(", gsSegment=").append(gsSegment);
        sb.append(", inBound=").append(inBound);
        sb.append(", stSegment=").append(stSegment);
        sb.append(", outBound=").append(outBound);
        sb.append('}');
        return sb.toString();
    }
}
