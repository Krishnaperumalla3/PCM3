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

package com.pe.pcm.b2b.deserialize;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class B2bCdNodesDeserializeModel implements Serializable {

    private String serverNodeName;
    private String serverHost;

    public String getServerNodeName() {
        return serverNodeName;
    }

    public B2bCdNodesDeserializeModel setServerNodeName(String serverNodeName) {
        this.serverNodeName = serverNodeName;
        return this;
    }

    public String getServerHost() {
        return serverHost;
    }

    public B2bCdNodesDeserializeModel setServerHost(String serverHost) {
        this.serverHost = serverHost;
        return this;
    }
}
