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

package com.pe.pcm.exception;

/**
 * @author Chenchu Kiran.
 */
public class CommunityManagerServiceException extends RuntimeException {

    private static final long serialVersionUID = 5364417055787541693L;

    private final int statusCode;

    private final String errorMessage;

    public CommunityManagerServiceException(String errorMessage) {
        this.errorMessage = errorMessage;
        this.statusCode = -1;
    }

    public CommunityManagerServiceException(int statusCode) {
        this.statusCode = statusCode;
        this.errorMessage = null;
    }

    public CommunityManagerServiceException(Exception e) {
        super(e);
        this.statusCode = -1;
        this.errorMessage = null;
    }

    public CommunityManagerServiceException(int statusCode, String errorMessage) {
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }

    public CommunityManagerServiceException(String errorMessage, Exception e) {
        super(e);
        this.statusCode = -1;
        this.errorMessage = errorMessage;
    }

    public CommunityManagerServiceException(int statusCode, String errorMessage, Exception e) {
        super(e);
        this.statusCode = statusCode;
        this.errorMessage = errorMessage;
    }


    public int getStatusCode() {
        return statusCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

}
