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

package com.pe.pcm.envelope;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class StSegment {

    private String stSenderId;
    private String stReceiverId;
    private String trnSetIdCode;
    private String acceptLookAlias;


    public String getStSenderId() {
        return stSenderId;
    }

    public StSegment setStSenderId(String stSenderId) {
        this.stSenderId = stSenderId;
        return this;
    }

    public String getStReceiverId() {
        return stReceiverId;
    }

    public StSegment setStReceiverId(String stReceiverId) {
        this.stReceiverId = stReceiverId;
        return this;
    }

    public String getTrnSetIdCode() {
        return trnSetIdCode;
    }

    public StSegment setTrnSetIdCode(String trnSetIdCode) {
        this.trnSetIdCode = trnSetIdCode;
        return this;
    }

    public String getAcceptLookAlias() {
        return acceptLookAlias;
    }

    public StSegment setAcceptLookAlias(String acceptLookAlias) {
        this.acceptLookAlias = acceptLookAlias;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("StSegment{");
        sb.append("stSenderId='").append(stSenderId).append('\'');
        sb.append(", stReceiverId='").append(stReceiverId).append('\'');
        sb.append(", trnSetIdCode='").append(trnSetIdCode).append('\'');
        sb.append(", acceptLookAlias='").append(acceptLookAlias).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
