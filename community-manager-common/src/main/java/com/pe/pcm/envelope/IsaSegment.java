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
public class IsaSegment {

    private String isaSenderIdQal;
    private String isaReceiverIdQal;
    private String isaSenderId;
    private String isaReceiverId;
    private String interVersion;
    private String globalContNo;
    private String invokeBPForIsa;
    private String businessProcess;
    private String perContNumCheck;
    private String perDupNumCheck;
    private String isaAcceptLookAlias;


    public String getIsaSenderIdQal() {
        return isaSenderIdQal;
    }

    public IsaSegment setIsaSenderIdQal(String isaSenderIdQal) {
        this.isaSenderIdQal = isaSenderIdQal;
        return this;
    }

    public String getIsaReceiverIdQal() {
        return isaReceiverIdQal;
    }

    public IsaSegment setIsaReceiverIdQal(String isaReceiverIdQal) {
        this.isaReceiverIdQal = isaReceiverIdQal;
        return this;
    }

    public String getIsaSenderId() {
        return isaSenderId;
    }

    public IsaSegment setIsaSenderId(String isaSenderId) {
        this.isaSenderId = isaSenderId;
        return this;
    }

    public String getIsaReceiverId() {
        return isaReceiverId;
    }

    public IsaSegment setIsaReceiverId(String isaReceiverId) {
        this.isaReceiverId = isaReceiverId;
        return this;
    }

    public String getInterVersion() {
        return interVersion;
    }

    public IsaSegment setInterVersion(String interVersion) {
        this.interVersion = interVersion;
        return this;
    }

    public String getGlobalContNo() {
        return globalContNo;
    }

    public IsaSegment setGlobalContNo(String globalContNo) {
        this.globalContNo = globalContNo;
        return this;
    }

    public String getInvokeBPForIsa() {
        return invokeBPForIsa;
    }

    public IsaSegment setInvokeBPForIsa(String invokeBPForIsa) {
        this.invokeBPForIsa = invokeBPForIsa;
        return this;
    }

    public String getBusinessProcess() {
        return businessProcess;
    }

    public IsaSegment setBusinessProcess(String businessProcess) {
        this.businessProcess = businessProcess;
        return this;
    }

    public String getPerContNumCheck() {
        return perContNumCheck;
    }

    public IsaSegment setPerContNumCheck(String perContNumCheck) {
        this.perContNumCheck = perContNumCheck;
        return this;
    }

    public String getPerDupNumCheck() {
        return perDupNumCheck;
    }

    public IsaSegment setPerDupNumCheck(String perDupNumCheck) {
        this.perDupNumCheck = perDupNumCheck;
        return this;
    }

    public String getIsaAcceptLookAlias() {
        return isaAcceptLookAlias;
    }

    public IsaSegment setIsaAcceptLookAlias(String isaAcceptLookAlias) {
        this.isaAcceptLookAlias = isaAcceptLookAlias;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("IsaSegment{");
        sb.append("isaSenderIdQal='").append(isaSenderIdQal).append('\'');
        sb.append(", isaReceiverIdQal='").append(isaReceiverIdQal).append('\'');
        sb.append(", isaSenderId='").append(isaSenderId).append('\'');
        sb.append(", isaReceiverId='").append(isaReceiverId).append('\'');
        sb.append(", interVersion='").append(interVersion).append('\'');
        sb.append(", globalContNo='").append(globalContNo).append('\'');
        sb.append(", invokeBPForIsa='").append(invokeBPForIsa).append('\'');
        sb.append(", businessProcess='").append(businessProcess).append('\'');
        sb.append(", perContNumCheck='").append(perContNumCheck).append('\'');
        sb.append(", perDupNumCheck='").append(perDupNumCheck).append('\'');
        sb.append(", isaAcceptLookAlias='").append(isaAcceptLookAlias).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
