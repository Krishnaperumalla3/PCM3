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

package com.pe.pcm.sterling.entity;


import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "CD_NODE")
public class CdNodeEntity {

    @EmbeddedId
    private String nodeId;
    private String nodeName;
    private int maxPnodeSessions;
    private int maxSnodeSessions;

    public String getNodeId() {
        return nodeId;
    }

    public CdNodeEntity setNodeId(String nodeId) {
        this.nodeId = nodeId;
        return this;
    }

    public String getNodeName() {
        return nodeName;
    }

    public CdNodeEntity setNodeName(String nodeName) {
        this.nodeName = nodeName;
        return this;
    }

    public int getMaxPnodeSessions() {
        return maxPnodeSessions;
    }

    public CdNodeEntity setMaxPnodeSessions(int maxPnodeSessions) {
        this.maxPnodeSessions = maxPnodeSessions;
        return this;
    }

    public int getMaxSnodeSessions() {
        return maxSnodeSessions;
    }

    public CdNodeEntity setMaxSnodeSessions(int maxSnodeSessions) {
        this.maxSnodeSessions = maxSnodeSessions;
        return this;
    }
}
