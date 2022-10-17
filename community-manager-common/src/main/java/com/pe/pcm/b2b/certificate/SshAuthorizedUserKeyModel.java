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

package com.pe.pcm.b2b.certificate;

import org.apache.commons.lang3.builder.ToStringBuilder;

import java.io.Serializable;

public class SshAuthorizedUserKeyModel implements Serializable {

    private String keyData;
    private String keyName;
    private Boolean keyStatusEnabled = true;


    public String getKeyData() {
        return keyData;
    }

    public SshAuthorizedUserKeyModel setKeyData(String keyData) {
        this.keyData = keyData;
        return this;
    }

    public String getKeyName() {
        return keyName;
    }

    public SshAuthorizedUserKeyModel setKeyName(String keyName) {
        this.keyName = keyName;
        return this;
    }

    public Boolean getKeyStatusEnabled() {
        return keyStatusEnabled;
    }

    public SshAuthorizedUserKeyModel setKeyStatusEnabled(Boolean keyStatusEnabled) {
        this.keyStatusEnabled = keyStatusEnabled;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("keyData", keyData)
                .append("keyName", keyName)
                .append("keyStatusEnabled", keyStatusEnabled)
                .toString();
    }
}
