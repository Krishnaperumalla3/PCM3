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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author Kiran Reddy.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class CommunityManagerNameModel implements Serializable {

    private String name;

    public CommunityManagerNameModel(String name) {
        this.name = name;
    }

    public CommunityManagerNameModel() { //Needed
    }

    public String getName() {
        return name;
    }

    public CommunityManagerNameModel setName(String name) {
        this.name = name;
        return this;
    }

    @Override
    public String toString() {
        return "CommunityManagerNameModel{" +
                "name='" + name + '\'' +
                '}';
    }
}
