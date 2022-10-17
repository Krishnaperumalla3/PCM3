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

package com.pe.pcm.reports;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.util.Map;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class ReportsModel {

    private Map<String, Long> docTransVolume;
    private Map<String, Long> statusVolume;
    private Map<String, Long> flowInOutVolume;
    private Map<String, Long> docTypeVolume;
    private Map<String, Long> partnerVolume;


    public Map<String, Long> getDocTransVolume() {
        return docTransVolume;
    }

    public ReportsModel setDocTransVolume(Map<String, Long> docTransVolume) {
        this.docTransVolume = docTransVolume;
        return this;
    }

    public Map<String, Long> getStatusVolume() {
        return statusVolume;
    }

    public ReportsModel setStatusVolume(Map<String, Long> statusVolume) {
        this.statusVolume = statusVolume;
        return this;
    }

    public Map<String, Long> getFlowInOutVolume() {
        return flowInOutVolume;
    }

    public ReportsModel setFlowInOutVolume(Map<String, Long> flowInOutVolume) {
        this.flowInOutVolume = flowInOutVolume;
        return this;
    }

    public Map<String, Long> getDocTypeVolume() {
        return docTypeVolume;
    }

    public ReportsModel setDocTypeVolume(Map<String, Long> docTypeVolume) {
        this.docTypeVolume = docTypeVolume;
        return this;
    }

    public Map<String, Long> getPartnerVolume() {
        return partnerVolume;
    }

    public ReportsModel setPartnerVolume(Map<String, Long> partnerVolume) {
        this.partnerVolume = partnerVolume;
        return this;
    }
}
