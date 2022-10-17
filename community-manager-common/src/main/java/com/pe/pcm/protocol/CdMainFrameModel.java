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

import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@JacksonXmlRootElement(localName = "MAIN_FRAME_MODEL")
@JsonPropertyOrder({"dcbParam", "dnsName", "unit", "storageClass", "space"})
public class CdMainFrameModel implements Serializable {

    private String dcbParam;
    private String dnsName;
    private String unit;
    private String storageClass;
    private String space;

    @JacksonXmlProperty(localName = "DCB_PARAM")
    public String getDcbParam() {
        return dcbParam;
    }

    public CdMainFrameModel setDcbParam(String dcbParam) {
        this.dcbParam = dcbParam;
        return this;
    }

    @JacksonXmlProperty(localName = "DNS_NAME")
    public String getDnsName() {
        return dnsName;
    }

    public CdMainFrameModel setDnsName(String dnsName) {
        this.dnsName = dnsName;
        return this;
    }

    @JacksonXmlProperty(localName = "UNIT")
    public String getUnit() {
        return unit;
    }

    public CdMainFrameModel setUnit(String unit) {
        this.unit = unit;
        return this;
    }

    @JacksonXmlProperty(localName = "STORAGE_CLASS")
    public String getStorageClass() {
        return storageClass;
    }

    public CdMainFrameModel setStorageClass(String storageClass) {
        this.storageClass = storageClass;
        return this;
    }

    @JacksonXmlProperty(localName = "SPACE")
    public String getSpace() {
        return space;
    }

    public CdMainFrameModel setSpace(String space) {
        this.space = space;
        return this;
    }
}
