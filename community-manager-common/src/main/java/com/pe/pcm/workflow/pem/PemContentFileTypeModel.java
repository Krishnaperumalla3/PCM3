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
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import java.io.Serializable;
import java.util.List;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PemContentFileTypeModel implements Serializable {

    private String partnerProfile;
    private String applicationProfile;
    private String partnerProtocol;
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<PemFileTypeModel> content;

    public String getPartnerProfile() {
        return partnerProfile;
    }

    public PemContentFileTypeModel setPartnerProfile(String partnerProfile) {
        this.partnerProfile = partnerProfile;
        return this;
    }

    public String getApplicationProfile() {
        return applicationProfile;
    }

    public PemContentFileTypeModel setApplicationProfile(String applicationProfile) {
        this.applicationProfile = applicationProfile;
        return this;
    }

    public String getPartnerProtocol() {
        return partnerProtocol;
    }

    public PemContentFileTypeModel setPartnerProtocol(String partnerProtocol) {
        this.partnerProtocol = partnerProtocol;
        return this;
    }

    public List<PemFileTypeModel> getContent() {
        return content;
    }

    public PemContentFileTypeModel setContent(List<PemFileTypeModel> content) {
        this.content = content;
        return this;
    }
}
