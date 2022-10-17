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

import java.io.Serializable;

/**
 * @author Kiran Reddy.
 */
public class CommunityManagerKeyValueModel implements Serializable {

    private String key;
    private String value;

    public CommunityManagerKeyValueModel() {
    }

    public CommunityManagerKeyValueModel(String key, String value) {
        this.key = key;
        this.value = value;
    }

    public String getKey() {
        return key;
    }

    public CommunityManagerKeyValueModel setKey(String key) {
        this.key = key;
        return this;
    }

    public String getValue() {
        return value;
    }

    public CommunityManagerKeyValueModel setValue(String value) {
        this.value = value;
        return this;
    }

    @Override
    public String toString() {
        return "CommunityManagerKeyValueModel{" +
                "key='" + key + '\'' +
                ", value='" + value + '\'' +
                '}';
    }
}
