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

import java.io.Serializable;

/**
 * @author Shameer.v.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class ConsumerWsConfiguration implements Serializable {

    private String destinationAgentName;
    private String destinationAgentQueueManager;
    private String destinationChecksumMethod;
    private String destinationDirSetOrSpace;
    private String destinationFileAlreadyExists;
    private String destinationType;
    private String jobName;
    private String metadata;
    private String postAntProperties;
    private String postAntTargets;
    private String postCommand;
    private String postCommandType;
    private String postExecArgs;
    private int postRetryCount;
    private int postRetryWait;
    private String postReturnCode;
    private String preAntProperties;
    private String preAntTargets;
    private String preCommand;
    private String preCommandType;
    private String preExecArgs;
    private int preRetryCount;
    private int preRetryWait;
    private String preReturnCode;
    private int priority;
    private String queueManager;
    private String replyQueue;
    private String sourceAgentAdapter;
    private String transferMode;

    public String getDestinationAgentName() {
        return destinationAgentName;
    }

    public ConsumerWsConfiguration setDestinationAgentName(String destinationAgentName) {
        this.destinationAgentName = destinationAgentName;
        return this;
    }

    public String getDestinationAgentQueueManager() {
        return destinationAgentQueueManager;
    }

    public ConsumerWsConfiguration setDestinationAgentQueueManager(String destinationAgentQueueManager) {
        this.destinationAgentQueueManager = destinationAgentQueueManager;
        return this;
    }

    public String getDestinationChecksumMethod() {
        return destinationChecksumMethod;
    }

    public ConsumerWsConfiguration setDestinationChecksumMethod(String destinationChecksumMethod) {
        this.destinationChecksumMethod = destinationChecksumMethod;
        return this;
    }

    public String getDestinationDirSetOrSpace() {
        return destinationDirSetOrSpace;
    }

    public ConsumerWsConfiguration setDestinationDirSetOrSpace(String destinationDirSetOrSpace) {
        this.destinationDirSetOrSpace = destinationDirSetOrSpace;
        return this;
    }

    public String getDestinationFileAlreadyExists() {
        return destinationFileAlreadyExists;
    }

    public ConsumerWsConfiguration setDestinationFileAlreadyExists(String destinationFileAlreadyExists) {
        this.destinationFileAlreadyExists = destinationFileAlreadyExists;
        return this;
    }

    public String getDestinationType() {
        return destinationType;
    }

    public ConsumerWsConfiguration setDestinationType(String destinationType) {
        this.destinationType = destinationType;
        return this;
    }

    public String getJobName() {
        return jobName;
    }

    public ConsumerWsConfiguration setJobName(String jobName) {
        this.jobName = jobName;
        return this;
    }

    public String getMetadata() {
        return metadata;
    }

    public ConsumerWsConfiguration setMetadata(String metadata) {
        this.metadata = metadata;
        return this;
    }

    public String getPostAntProperties() {
        return postAntProperties;
    }

    public ConsumerWsConfiguration setPostAntProperties(String postAntProperties) {
        this.postAntProperties = postAntProperties;
        return this;
    }

    public String getPostAntTargets() {
        return postAntTargets;
    }

    public ConsumerWsConfiguration setPostAntTargets(String postAntTargets) {
        this.postAntTargets = postAntTargets;
        return this;
    }

    public String getPostCommand() {
        return postCommand;
    }

    public ConsumerWsConfiguration setPostCommand(String postCommand) {
        this.postCommand = postCommand;
        return this;
    }

    public String getPostCommandType() {
        return postCommandType;
    }

    public ConsumerWsConfiguration setPostCommandType(String postCommandType) {
        this.postCommandType = postCommandType;
        return this;
    }

    public String getPostExecArgs() {
        return postExecArgs;
    }

    public ConsumerWsConfiguration setPostExecArgs(String postExecArgs) {
        this.postExecArgs = postExecArgs;
        return this;
    }

    public int getPostRetryCount() {
        return postRetryCount;
    }

    public ConsumerWsConfiguration setPostRetryCount(int postRetryCount) {
        this.postRetryCount = postRetryCount;
        return this;
    }

    public int getPostRetryWait() {
        return postRetryWait;
    }

    public ConsumerWsConfiguration setPostRetryWait(int postRetryWait) {
        this.postRetryWait = postRetryWait;
        return this;
    }

    public String getPostReturnCode() {
        return postReturnCode;
    }

    public ConsumerWsConfiguration setPostReturnCode(String postReturnCode) {
        this.postReturnCode = postReturnCode;
        return this;
    }

    public String getPreAntProperties() {
        return preAntProperties;
    }

    public ConsumerWsConfiguration setPreAntProperties(String preAntProperties) {
        this.preAntProperties = preAntProperties;
        return this;
    }

    public String getPreAntTargets() {
        return preAntTargets;
    }

    public ConsumerWsConfiguration setPreAntTargets(String preAntTargets) {
        this.preAntTargets = preAntTargets;
        return this;
    }

    public String getPreCommand() {
        return preCommand;
    }

    public ConsumerWsConfiguration setPreCommand(String preCommand) {
        this.preCommand = preCommand;
        return this;
    }

    public String getPreCommandType() {
        return preCommandType;
    }

    public ConsumerWsConfiguration setPreCommandType(String preCommandType) {
        this.preCommandType = preCommandType;
        return this;
    }

    public String getPreExecArgs() {
        return preExecArgs;
    }

    public ConsumerWsConfiguration setPreExecArgs(String preExecArgs) {
        this.preExecArgs = preExecArgs;
        return this;
    }

    public int getPreRetryCount() {
        return preRetryCount;
    }

    public ConsumerWsConfiguration setPreRetryCount(int preRetryCount) {
        this.preRetryCount = preRetryCount;
        return this;
    }

    public int getPreRetryWait() {
        return preRetryWait;
    }

    public ConsumerWsConfiguration setPreRetryWait(int preRetryWait) {
        this.preRetryWait = preRetryWait;
        return this;
    }

    public String getPreReturnCode() {
        return preReturnCode;
    }

    public ConsumerWsConfiguration setPreReturnCode(String preReturnCode) {
        this.preReturnCode = preReturnCode;
        return this;
    }

    public int getPriority() {
        return priority;
    }

    public ConsumerWsConfiguration setPriority(int priority) {
        this.priority = priority;
        return this;
    }

    public String getQueueManager() {
        return queueManager;
    }

    public ConsumerWsConfiguration setQueueManager(String queueManager) {
        this.queueManager = queueManager;
        return this;
    }

    public String getReplyQueue() {
        return replyQueue;
    }

    public ConsumerWsConfiguration setReplyQueue(String replyQueue) {
        this.replyQueue = replyQueue;
        return this;
    }

    public String getSourceAgentAdapter() {
        return sourceAgentAdapter;
    }

    public ConsumerWsConfiguration setSourceAgentAdapter(String sourceAgentAdapter) {
        this.sourceAgentAdapter = sourceAgentAdapter;
        return this;
    }

    public String getTransferMode() {
        return transferMode;
    }

    public ConsumerWsConfiguration setTransferMode(String transferMode) {
        this.transferMode = transferMode;
        return this;
    }
}
