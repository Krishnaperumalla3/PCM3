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
import java.util.List;

/**
 * @author Kiran Reddy.
 */
public class CommunityManagerMapModel<K extends Serializable, T extends Serializable> implements Serializable {

    private K key;
    private List<T> list;

    public CommunityManagerMapModel(K key, List<T> list) {
        this.key = key;
        this.list = list;
    }

    public K getKey() {
        return key;
    }

    public CommunityManagerMapModel<K, T> setKey(K key) {
        this.key = key;
        return this;
    }

    public List<T> getList() {
        return list;
    }

    public CommunityManagerMapModel<K, T> setList(List<T> list) {
        this.list = list;
        return this;
    }
}
