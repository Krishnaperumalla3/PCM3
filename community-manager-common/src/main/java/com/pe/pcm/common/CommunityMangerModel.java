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

package com.pe.pcm.common;

import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlElementWrapper;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty;
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * @author Chenchu Kiran Reddy.
 */
@XmlRootElement(name = "entities")
@JacksonXmlRootElement(localName = "entities")
public class CommunityMangerModel<T extends Serializable> implements Serializable {

    private List<T> content;

    public CommunityMangerModel() {
        //Don't remove, this will be used at runtime.
    }

    public CommunityMangerModel(List<T> content) {
        this.content = content;
    }

    public CommunityMangerModel<T> setContent(List<T> content) {
        this.content = content;
        return this;
    }

    @XmlElement(name = "entity")
    @JacksonXmlProperty(localName = "entity")
    @JacksonXmlElementWrapper(useWrapping = false)
    public List<T> getContent() {
        return content;
    }

}
