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

@JsonIgnoreProperties(ignoreUnknown = true)
public class GsSegment {

    private String gsSenderId;
    private String gsReceiverId;
    private String functionalIdCode;
    private String respAgencyCode;
    private String groupVersion;

    public String getGsSenderId() {
        return gsSenderId;
    }

    public GsSegment setGsSenderId(String gsSenderId) {
        this.gsSenderId = gsSenderId;
        return this;
    }

    public String getGsReceiverId() {
        return gsReceiverId;
    }

    public GsSegment setGsReceiverId(String gsReceiverId) {
        this.gsReceiverId = gsReceiverId;
        return this;
    }

    public String getFunctionalIdCode() {
        return functionalIdCode;
    }

    public GsSegment setFunctionalIdCode(String functionalIdCode) {
        this.functionalIdCode = functionalIdCode;
        return this;
    }

    public String getRespAgencyCode() {
        return respAgencyCode;
    }

    public GsSegment setRespAgencyCode(String respAgencyCode) {
        this.respAgencyCode = respAgencyCode;
        return this;
    }

    public String getGroupVersion() {
        return groupVersion;
    }

    public GsSegment setGroupVersion(String groupVersion) {
        this.groupVersion = groupVersion;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("GsSegment{");
        sb.append("gsSenderId='").append(gsSenderId).append('\'');
        sb.append(", gsReceiverId='").append(gsReceiverId).append('\'');
        sb.append(", functionalIdCode='").append(functionalIdCode).append('\'');
        sb.append(", respAgencyCode='").append(respAgencyCode).append('\'');
        sb.append(", groupVersion='").append(groupVersion).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
