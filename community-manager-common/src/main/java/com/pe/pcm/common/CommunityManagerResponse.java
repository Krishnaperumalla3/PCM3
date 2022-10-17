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

public class CommunityManagerResponse {

    public CommunityManagerResponse() { }

    private Integer statusCode;
    private String statusMessage;

    public CommunityManagerResponse(Integer statusCode, String statusMessage) {
        this.statusCode = statusCode;
        this.statusMessage = statusMessage;
    }


    public Integer getStatusCode() {
        return statusCode;
    }

    public CommunityManagerResponse setStatusCode(Integer statusCode) {
        this.statusCode = statusCode;
        return this;
    }

    public String getStatusMessage() {
        return statusMessage;
    }

    public CommunityManagerResponse setStatusMessage(String statusMessage) {
        this.statusMessage = statusMessage;
        return this;
    }

    @Override
    public String toString() {
        return "CommunityManagerResponse{" +
                "statusCode='" + statusCode + '\'' +
                ", statusMessage='" + statusMessage + '\'' +
                '}';
    }
}
