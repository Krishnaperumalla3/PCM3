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
public class InBound {

    private String complianceCheck;
    private String complianceCheckMap;
    private String retainEnv;
    private String genInboundAck;
    private String ackDetailLevel;

    public String getComplianceCheck() {
        return complianceCheck;
    }

    public InBound setComplianceCheck(String complianceCheck) {
        this.complianceCheck = complianceCheck;
        return this;
    }

    public String getComplianceCheckMap() {
        return complianceCheckMap;
    }

    public InBound setComplianceCheckMap(String complianceCheckMap) {
        this.complianceCheckMap = complianceCheckMap;
        return this;
    }

    public String getRetainEnv() {
        return retainEnv;
    }

    public InBound setRetainEnv(String retainEnv) {
        this.retainEnv = retainEnv;
        return this;
    }

    public String getGenInboundAck() {
        return genInboundAck;
    }

    public InBound setGenInboundAck(String genInboundAck) {
        this.genInboundAck = genInboundAck;
        return this;
    }

    public String getAckDetailLevel() {
        return ackDetailLevel;
    }

    public InBound setAckDetailLevel(String ackDetailLevel) {
        this.ackDetailLevel = ackDetailLevel;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("InBound{");
        sb.append("complianceCheck='").append(complianceCheck).append('\'');
        sb.append(", complianceCheckMap='").append(complianceCheckMap).append('\'');
        sb.append(", retainEnv='").append(retainEnv).append('\'');
        sb.append(", genInboundAck='").append(genInboundAck).append('\'');
        sb.append(", ackDetailLevel='").append(ackDetailLevel).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
