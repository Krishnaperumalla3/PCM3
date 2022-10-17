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

package com.pe.pcm.pem.b2bproxy.consumerconfiguration;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.pe.pcm.protocol.RemoteCdModel;

import java.io.Serializable;

import static com.pe.pcm.utils.CommonFunctions.isNotNull;

/**
 * @author Shameer.v.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerCdConfiguration implements Serializable {

    private String checkpointInterval;
    private String localNodeName;
    private String localUserId;
    private String remoteDisposition;
    private String remoteFileName;
    private String remoteNodeName;
    private String remotePassword;
    private String remoteSysopts;
    private String remoteUserId;

    public ConsumerCdConfiguration() {
    }

    public ConsumerCdConfiguration(RemoteCdModel remoteCdModel) {
        this.checkpointInterval = remoteCdModel.getCheckPoint();
        this.localNodeName = remoteCdModel.getLocalNodeName();
        this.localUserId = "";
        this.remoteDisposition = remoteCdModel.getDisposition();
        if (isNotNull(remoteCdModel.getOutDirectory())) {
            if (isNotNull(remoteCdModel.getRemoteFileName())) {
                this.remoteFileName = remoteCdModel.getOutDirectory() + "/" + remoteCdModel.getRemoteFileName();
            } else {
                this.remoteFileName = remoteCdModel.getOutDirectory();
            }
        } else {
            this.remoteFileName = remoteCdModel.getRemoteFileName();
        }
        this.remoteNodeName = remoteCdModel.getNodeName();
        this.remotePassword = remoteCdModel.getsNodeIdPassword();
        this.remoteSysopts = remoteCdModel.getCopySisOpts();
        this.remoteUserId = remoteCdModel.getsNodeId();
    }

    public String getCheckpointInterval() {
        return checkpointInterval;
    }

    public ConsumerCdConfiguration setCheckpointInterval(String checkpointInterval) {
        this.checkpointInterval = checkpointInterval;
        return this;
    }

    public String getLocalNodeName() {
        return localNodeName;
    }

    public ConsumerCdConfiguration setLocalNodeName(String localNodeName) {
        this.localNodeName = localNodeName;
        return this;
    }

    public String getLocalUserId() {
        return localUserId;
    }

    public ConsumerCdConfiguration setLocalUserId(String localUserId) {
        this.localUserId = localUserId;
        return this;
    }

    public String getRemoteDisposition() {
        return remoteDisposition;
    }

    public ConsumerCdConfiguration setRemoteDisposition(String remoteDisposition) {
        this.remoteDisposition = remoteDisposition;
        return this;
    }

    public String getRemoteFileName() {
        return remoteFileName;
    }

    public ConsumerCdConfiguration setRemoteFileName(String remoteFileName) {
        this.remoteFileName = remoteFileName;
        return this;
    }

    public String getRemoteNodeName() {
        return remoteNodeName;
    }

    public ConsumerCdConfiguration setRemoteNodeName(String remoteNodeName) {
        this.remoteNodeName = remoteNodeName;
        return this;
    }

    public String getRemotePassword() {
        return remotePassword;
    }

    public ConsumerCdConfiguration setRemotePassword(String remotePassword) {
        this.remotePassword = remotePassword;
        return this;
    }

    public String getRemoteSysopts() {
        return remoteSysopts;
    }

    public ConsumerCdConfiguration setRemoteSysopts(String remoteSysopts) {
        this.remoteSysopts = remoteSysopts;
        return this;
    }

    public String getRemoteUserId() {
        return remoteUserId;
    }

    public ConsumerCdConfiguration setRemoteUserId(String remoteUserId) {
        this.remoteUserId = remoteUserId;
        return this;
    }
}
