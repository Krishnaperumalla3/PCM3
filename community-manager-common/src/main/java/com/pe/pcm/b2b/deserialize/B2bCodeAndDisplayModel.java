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
public class B2bCodeAndDisplayModel implements Serializable {
    private String code;
    private String display;

    public String getCode() {
        return code;
    }

    public B2bCodeAndDisplayModel setCode(String code) {
        this.code = code;
        return this;
    }

    public String getDisplay() {
        return display;
    }

    public B2bCodeAndDisplayModel setDisplay(String display) {
        this.display = display;
        return this;
    }
}
