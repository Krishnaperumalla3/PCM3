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

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
public class ServerDetails implements Serializable {

    private static final long serialVersionUID = 1L;
    @XmlElement
    private String host;
    @XmlElement
    private Integer port;
    @XmlElement
    private String nodeName;

    public ServerDetails() {
    }

    public ServerDetails(String host, Integer port, String nodeName) {
        this.host = host;
        this.port = port;
        this.nodeName = nodeName;
    }

    public String getHost() {
        return host;
    }

    public ServerDetails setHost(String host) {
        this.host = host;
        return this;
    }

    public Integer getPort() {
        return port;
    }

    public ServerDetails setPort(Integer port) {
        this.port = port;
        return this;
    }

    public String getNodeName() {
        return nodeName;
    }

    public ServerDetails setNodeName(String nodeName) {
        this.nodeName = nodeName;
        return this;
    }
}
