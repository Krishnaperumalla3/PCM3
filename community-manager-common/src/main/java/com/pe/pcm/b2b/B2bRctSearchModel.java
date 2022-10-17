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

package com.pe.pcm.b2b;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class B2bRctSearchModel implements Serializable {

    private String templateName;
    private String producerName;
    private String consumerName;

    public String getTemplateName() {
        return templateName;
    }

    public B2bRctSearchModel setTemplateName(String templateName) {
        this.templateName = templateName;
        return this;
    }

    public String getProducerName() {
        return producerName;
    }

    public B2bRctSearchModel setProducerName(String producerName) {
        this.producerName = producerName;
        return this;
    }

    public String getConsumerName() {
        return consumerName;
    }

    public B2bRctSearchModel setConsumerName(String consumerName) {
        this.consumerName = consumerName;
        return this;
    }
}
