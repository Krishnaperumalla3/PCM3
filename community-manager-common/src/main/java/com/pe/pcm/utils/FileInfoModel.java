/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc
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

package com.pe.pcm.utils;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class FileInfoModel implements Serializable {

    private String fileName;
    private String filePath;
    private String operation;
    private String content;

    private String host;
    private int port;
    private String userName;
    private String password;
    private Boolean isPemKey;
    private boolean isLocalDirectory;

    public Boolean getIsPemKey() {
        return isPemKey;
    }

    public FileInfoModel setIsPemKey(Boolean isPemKey) {
        this.isPemKey = isPemKey;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public FileInfoModel setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getFilePath() {
        return filePath;
    }

    public FileInfoModel setFilePath(String filePath) {
        this.filePath = filePath;
        return this;
    }

    public String getOperation() {
        return operation;
    }

    public FileInfoModel setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public String getContent() {
        return content;
    }

    public FileInfoModel setContent(String content) {
        this.content = content;
        return this;
    }

    public String getHost() {
        return host;
    }

    public FileInfoModel setHost(String host) {
        this.host = host;
        return this;
    }

    public int getPort() {
        return port;
    }

    public FileInfoModel setPort(int port) {
        this.port = port;
        return this;
    }

    public String getUserName() {
        return userName;
    }

    public FileInfoModel setUserName(String userName) {
        this.userName = userName;
        return this;
    }

    public String getPassword() {
        return password;
    }

    public FileInfoModel setPassword(String password) {
        this.password = password;
        return this;
    }

    public boolean isLocalDirectory() {
        return isLocalDirectory;
    }

    public FileInfoModel setLocalDirectory(boolean localDirectory) {
        isLocalDirectory = localDirectory;
        return this;
    }
}
