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

package com.pe.pcm.b2b.routing.channels;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RoutingChannelModel implements Serializable {

    private String routingChannelKey;
    private String templateName;
    private String producer;
    private String consumer;
    private String producerMailboxPath;

    public String getRoutingChannelKey() {
        return routingChannelKey;
    }

    public RoutingChannelModel setRoutingChannelKey(String routingChannelKey) {
        this.routingChannelKey = routingChannelKey;
        return this;
    }

    public String getTemplateName() {
        return templateName;
    }

    public RoutingChannelModel setTemplateName(String templateName) {
        this.templateName = templateName;
        return this;
    }

    public String getProducer() {
        return producer;
    }

    public RoutingChannelModel setProducer(String producer) {
        this.producer = producer;
        return this;
    }

    public String getConsumer() {
        return consumer;
    }

    public RoutingChannelModel setConsumer(String consumer) {
        this.consumer = consumer;
        return this;
    }

    public String getProducerMailboxPath() {
        return producerMailboxPath;
    }

    public RoutingChannelModel setProducerMailboxPath(String producerMailboxPath) {
        this.producerMailboxPath = producerMailboxPath;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RoutingChannelModel.class.getSimpleName() + "[", "]")
                .add("routingChannelKey='" + routingChannelKey + "'")
                .add("templateName='" + templateName + "'")
                .add("producer='" + producer + "'")
                .add("consumer='" + consumer + "'")
                .add("producerMailboxPath='" + producerMailboxPath + "'")
                .toString();
    }
}
