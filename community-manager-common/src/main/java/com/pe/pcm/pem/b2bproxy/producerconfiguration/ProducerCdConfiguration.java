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

package com.pe.pcm.pem.b2bproxy.producerconfiguration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pe.pcm.protocol.RemoteCdModel;

import java.io.Serializable;

/**
 * @author Shameer.v.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ProducerCdConfiguration implements Serializable {

    private String checkpointInterval;
    private String localNodeName;
    private String localUserId;
    private String operatingSystem;
    private String remoteNodeName;
    private String remotePassword;
    private String remoteSysopts;
    private String remoteUserId;

    public ProducerCdConfiguration() {
    }

    public ProducerCdConfiguration(RemoteCdModel remoteCdModel) {
        this.checkpointInterval = remoteCdModel.getCheckPoint();
        this.localNodeName = remoteCdModel.getLocalNodeName();
        this.localUserId = "";
        this.operatingSystem = remoteCdModel.getOperatingSystem();
        this.remoteNodeName = remoteCdModel.getNodeName();
        this.remotePassword = remoteCdModel.getsNodeIdPassword();
        this.remoteSysopts = remoteCdModel.getCopySisOpts();
        this.remoteUserId = remoteCdModel.getsNodeId();
    }

    public String getCheckpointInterval() {
        return checkpointInterval;
    }

    public ProducerCdConfiguration setCheckpointInterval(String checkpointInterval) {
        this.checkpointInterval = checkpointInterval;
        return this;
    }

    public String getLocalNodeName() {
        return localNodeName;
    }

    public ProducerCdConfiguration setLocalNodeName(String localNodeName) {
        this.localNodeName = localNodeName;
        return this;
    }

    public String getLocalUserId() {
        return localUserId;
    }

    public ProducerCdConfiguration setLocalUserId(String localUserId) {
        this.localUserId = localUserId;
        return this;
    }

    public String getOperatingSystem() {
        return operatingSystem;
    }

    public ProducerCdConfiguration setOperatingSystem(String operatingSystem) {
        this.operatingSystem = operatingSystem;
        return this;
    }

    public String getRemoteNodeName() {
        return remoteNodeName;
    }

    public ProducerCdConfiguration setRemoteNodeName(String remoteNodeName) {
        this.remoteNodeName = remoteNodeName;
        return this;
    }

    public String getRemotePassword() {
        return remotePassword;
    }

    public ProducerCdConfiguration setRemotePassword(String remotePassword) {
        this.remotePassword = remotePassword;
        return this;
    }

    public String getRemoteSysopts() {
        return remoteSysopts;
    }

    public ProducerCdConfiguration setRemoteSysopts(String remoteSysopts) {
        this.remoteSysopts = remoteSysopts;
        return this;
    }

    public String getRemoteUserId() {
        return remoteUserId;
    }

    public ProducerCdConfiguration setRemoteUserId(String remoteUserId) {
        this.remoteUserId = remoteUserId;
        return this;
    }
}
