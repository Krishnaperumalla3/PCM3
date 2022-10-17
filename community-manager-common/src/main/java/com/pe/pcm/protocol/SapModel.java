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
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.pe.pcm.profile.ProfileModel;

import javax.validation.constraints.NotNull;

//@JacksonXmlRootElement(localName = "PARTNER_SAP")
//@JsonPropertyOrder({"pkId", "profileName", "profileId", "emailId", "phone",
//        "protocol", "addressLine1", "addressLine2", "status", "sapRoute", "adapterName", "hubInfo"})
@JsonIgnoreProperties(ignoreUnknown = true)
public class SapModel extends ProfileModel {

    @NotNull
    private String sapRoute;
    @NotNull
    private String adapterName;

    @JacksonXmlProperty(localName = "SAP_ROUTE")
    public String getSapRoute() {
        return sapRoute;
    }

    public SapModel setSapRoute(String sapRoute) {
        this.sapRoute = sapRoute;
        return this;
    }

    @JacksonXmlProperty(localName = "ADAPTER_NAME")
    public String getAdapterName() {
        return adapterName;
    }

    public SapModel setAdapterName(String adapterName) {
        this.adapterName = adapterName;
        return this;
    }

}
