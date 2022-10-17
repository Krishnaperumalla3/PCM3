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

package com.pe.pcm.workflow.pem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.pe.pcm.jakson.deserializer.ListDataFlowMapperJsonDeserializer;
import com.pe.pcm.reports.DataFlowMapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PemContentWorkFlowModel implements Serializable {

    private String partnerProfile;
    private String applicationProfile;

    @JsonDeserialize(using = ListDataFlowMapperJsonDeserializer.class)
    private List<DataFlowMapper> content;

    public List<DataFlowMapper> getContent() {
        return content;
    }

    public PemContentWorkFlowModel setContent(List<DataFlowMapper> content) {
        this.content = content;
        return this;
    }

    public String getPartnerProfile() {
        return partnerProfile;
    }

    public PemContentWorkFlowModel setPartnerProfile(String partnerProfile) {
        this.partnerProfile = partnerProfile;
        return this;
    }

    public String getApplicationProfile() {
        return applicationProfile;
    }

    public PemContentWorkFlowModel setApplicationProfile(String applicationProfile) {
        this.applicationProfile = applicationProfile;
        return this;
    }


    public String toJsonString() {
        return "{" +
                "\"partnerProfile\":\"" + partnerProfile + "\",\n" +
                "\"applicationProfile\":\"" + applicationProfile + "\",\n" +
                "\"content\": [" + loadContentJson() + "]" +
                '}';
    }

    private String loadContentJson() {
        final ObjectMapper objectMapper = new ObjectMapper();
        StringBuilder sb = new StringBuilder();
        if (content == null || content.isEmpty()) {
            return "";
        } else {
            content.forEach(dataFlowMapper -> {
                try {
                    sb.append(sb.length() == 0 ? "" : ",")
                            .append(objectMapper.writeValueAsString(dataFlowMapper));
                } catch (JsonProcessingException e) {
                    //Nothing to do
                }
            });
        }
        return sb.toString();
    }
}
