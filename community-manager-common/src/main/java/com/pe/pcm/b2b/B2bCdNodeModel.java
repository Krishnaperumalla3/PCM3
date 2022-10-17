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

package com.pe.pcm.b2b;

import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
public class B2bCdNodeModel implements Serializable {

    private String nodeName; //searchByNodeName
    private String netMapName; //searchByNetMap

    public String getNodeName() {
        return nodeName;
    }

    public B2bCdNodeModel setNodeName(String nodeName) {
        this.nodeName = nodeName;
        return this;
    }

    public String getNetMapName() {
        return netMapName;
    }

    public B2bCdNodeModel setNetMapName(String netMapName) {
        this.netMapName = netMapName;
        return this;
    }
}