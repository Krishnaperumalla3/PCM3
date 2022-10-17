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

package com.pe.pcm.enums;

/**
 * @author Chenchu Kiran.
 */
public enum EmailFields {

    USER("user"),
    BASE_URL("baseUrl"),
    PASSWORD("password"),
    PORT("port"),
    SUBJECT("subject"),
    DESC("desc"),
    HOST("host"),
    CONTACTMAIL("contactMail"),
    SIGNATURE("signature"),
    APP_NAME("appName"),
    NOTE("note"),
    ERROR_INFO("errorInfo");

    private String displayField;

    EmailFields(String displayField) {
        this.displayField = displayField;
    }

    public String getDisplayField() {
        return this.displayField;
    }
}
