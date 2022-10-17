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

package com.pe.pcm.ssp;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;

import javax.xml.bind.annotation.*;
import java.io.Serializable;
import java.util.List;

@XmlRootElement(name = "inboundNodes")
@XmlAccessorType(XmlAccessType.FIELD)
public class InboundNodesModel implements Serializable {

    private static final long serialVersionUID = 1L;

    @XmlElement(name = "inboundNodeDef")
    @JacksonXmlElementWrapper(useWrapping = false)
    private List<SSPNodeModel> inboundNodeDef;

    public List<SSPNodeModel> getInboundNodeDef() {
        return inboundNodeDef;
    }

    public InboundNodesModel setInboundNodeDef(List<SSPNodeModel> inboundNodeDef) {
        this.inboundNodeDef = inboundNodeDef;
        return this;
    }

}
