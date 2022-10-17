/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.sterling.mailbox;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProducerFileStructureModel implements Serializable {
    private List<ProducerFileStructureLayerModel> producerFileStructureLayerList = new ArrayList<>();

    public List<ProducerFileStructureLayerModel> getProducerFileStructureLayerList() {
        return producerFileStructureLayerList;
    }

    public ProducerFileStructureModel setProducerFileStructureLayerList(List<ProducerFileStructureLayerModel> producerFileStructureLayerList) {
        this.producerFileStructureLayerList = producerFileStructureLayerList;
        return this;
    }
}
