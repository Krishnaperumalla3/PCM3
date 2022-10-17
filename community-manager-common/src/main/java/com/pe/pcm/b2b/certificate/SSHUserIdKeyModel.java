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

/**
 * @author Chenchu Kiran.
 */

public class SSHUserIdKeyModel implements Serializable {

    private String keyName;
    private boolean keyStatusEnabled = true;
    private String passPhrase;
    private String privateKeyData;

    public String getKeyName() {
        return keyName;
    }

    public SSHUserIdKeyModel setKeyName(String keyName) {
        this.keyName = keyName;
        return this;
    }

    public boolean isKeyStatusEnabled() {
        return keyStatusEnabled;
    }

    public SSHUserIdKeyModel setKeyStatusEnabled(boolean keyStatusEnabled) {
        this.keyStatusEnabled = keyStatusEnabled;
        return this;
    }

    public String getPassPhrase() {
        return passPhrase;
    }

    public SSHUserIdKeyModel setPassPhrase(String passPhrase) {
        this.passPhrase = passPhrase;
        return this;
    }

    public String getPrivateKeyData() {
        return privateKeyData;
    }

    public SSHUserIdKeyModel setPrivateKeyData(String privateKeyData) {
        this.privateKeyData = privateKeyData;
        return this;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("keyName", keyName)
                .append("keyStatusEnabled", keyStatusEnabled)
                .append("passPhrase", passPhrase)
                .append("privateKeyData", privateKeyData)
                .toString();
    }
}
