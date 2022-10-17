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

package com.pe.pcm.pem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class RemoteServerDetailsModel implements Serializable {

    @NotEmpty
    private String host;

    @NotEmpty
    private int port;

    @NotEmpty
    private String userName;

    @NotEmpty
    private String command;

    private String inputParam1;

    private String inputParam2;

    private String inputParam3;

    private String inputParam4;

    private String inputParam5;

    private Boolean isPemKey;

    private String password;

    private boolean isLocalFile;

    public String getPassword() {  return password; }

    public void setPassword(String password) { this.password = password; }

    public Boolean getIsPemKey() { return isPemKey; }

    public RemoteServerDetailsModel setIsPemKey(Boolean pemKey) { isPemKey = pemKey; return this; }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCommand() {   return command;  }

    public void setCommand(String command) { this.command = command;  }

    public String getInputParam1() {
        return inputParam1;
    }

    public void setInputParam1(String inputParam1) {
        this.inputParam1 = inputParam1;
    }

    public String getInputParam2() {
        return inputParam2;
    }

    public void setInputParam2(String inputParam2) {
        this.inputParam2 = inputParam2;
    }

    public String getInputParam3() {
        return inputParam3;
    }

    public void setInputParam3(String inputParam3) {
        this.inputParam3 = inputParam3;
    }

    public String getInputParam4() {
        return inputParam4;
    }

    public void setInputParam4(String inputParam4) {
        this.inputParam4 = inputParam4;
    }

    public String getInputParam5() {
        return inputParam5;
    }

    public void setInputParam5(String inputParam5) {
        this.inputParam5 = inputParam5;
    }

    public boolean isLocalFile() {
        return isLocalFile;
    }

    public RemoteServerDetailsModel setLocalFile(boolean localFile) {
        isLocalFile = localFile;
        return this;
    }
}
