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

import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author Chenchu Kiran.
 */
public class SshKnownHostKeyModel implements Serializable {
    private String keyData;
    private String keyName;
    private Boolean keyStatusEnabled = true;

    public String getKeyData() {
        return keyData;
    }

    public SshKnownHostKeyModel setKeyData(String keyData) {
        this.keyData = keyData;
        return this;
    }

    public String getKeyName() {
        return keyName;
    }

    public SshKnownHostKeyModel setKeyName(String keyName) {
        this.keyName = keyName;
        return this;
    }

    public Boolean getKeyStatusEnabled() {
        return keyStatusEnabled;
    }

    public SshKnownHostKeyModel setKeyStatusEnabled(Boolean keyStatusEnabled) {
        this.keyStatusEnabled = keyStatusEnabled;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", SshKnownHostKeyModel.class.getSimpleName() + "[", "]")
                .add("keyData='" + keyData + "'")
                .add("keyName='" + keyName + "'")
                .add("keyStatusEnabled=" + keyStatusEnabled)
                .toString();
    }
}
