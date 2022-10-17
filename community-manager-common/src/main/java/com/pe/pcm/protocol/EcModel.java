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

package com.pe.pcm.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;
import com.pe.pcm.profile.ProfileModel;

@JacksonXmlRootElement(localName = "PARTNER_EXISTING_CONNECTION")
@JsonPropertyOrder({"pkId", "profileName", "profileId", "emailId", "phone",
        "protocol", "addressLine1", "addressLine2", "status", "ecProtocol", "ecProtocolReference", "hubInfo"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class EcModel extends ProfileModel {

    private String ecProtocol;
    private String ecProtocolReference;

    @JacksonXmlProperty(localName = "EC_PROTOCOL")
    public String getEcProtocol() {
        return ecProtocol;
    }

    public EcModel setEcProtocol(String ecProtocol) {
        this.ecProtocol = ecProtocol;
        return this;
    }

    @JacksonXmlProperty(localName = "EC_PROTOCOL_REFERENCE")
    public String getEcProtocolReference() {
        return ecProtocolReference;
    }

    public EcModel setEcProtocolReference(String ecProtocolReference) {
        this.ecProtocolReference = ecProtocolReference;
        return this;
    }

}
