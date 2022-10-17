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

package com.pe.pcm.b2b.protocol;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.pe.pcm.b2b.other.NodeGetModel;
import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;
import java.util.List;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class CdNetMapXrefConfiguration implements Serializable {

    private String netMapName;
    private List<NodeGetModel> nodes;

    public CdNetMapXrefConfiguration(String netMapName, List<NodeGetModel> nodes) {
        this.netMapName = netMapName;
        this.nodes = nodes;
    }

    public String getNetMapName() {
        return netMapName;
    }

    public CdNetMapXrefConfiguration setNetMapName(String netMapName) {
        this.netMapName = netMapName;
        return this;
    }

    public List<NodeGetModel> getNodes() {
        return nodes;
    }

    public CdNetMapXrefConfiguration setNodes(List<NodeGetModel> nodes) {
        this.nodes = nodes;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("netMapName", netMapName)
                .append("nodes", nodes)
                .toString();
    }
}
