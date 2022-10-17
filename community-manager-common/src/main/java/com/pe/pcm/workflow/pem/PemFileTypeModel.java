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

package com.pe.pcm.workflow.pem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class PemFileTypeModel implements Serializable {

    private String pkId;
    private String partner;
    private String application;
    private String flowType;
    private String senderId;
    private String receiverId;
    private String fileName;
    private String docType;
    private String transaction;
    private String status;
    private String errorInfo;
    private String operation;
    private boolean isRegexFind;

    public PemFileTypeModel() {
        //Need
    }

    public String getPkId() {
        return pkId;
    }

    public PemFileTypeModel setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getPartner() {
        return partner;
    }

    public PemFileTypeModel setPartner(String partner) {
        this.partner = partner;
        return this;
    }

    public String getApplication() {
        return application;
    }

    public PemFileTypeModel setApplication(String application) {
        this.application = application;
        return this;
    }

    public String getFlowType() {
        return flowType;
    }

    public PemFileTypeModel setFlowType(String flowType) {
        this.flowType = flowType;
        return this;
    }

    public String getSenderId() {
        return senderId;
    }

    public PemFileTypeModel setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public PemFileTypeModel setReceiverId(String receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public String getFileName() {
        return fileName;
    }

    public PemFileTypeModel setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    public String getDocType() {
        return docType;
    }

    public PemFileTypeModel setDocType(String docType) {
        this.docType = docType;
        return this;
    }

    public String getTransaction() {
        return transaction;
    }

    public PemFileTypeModel setTransaction(String transaction) {
        this.transaction = transaction;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public PemFileTypeModel setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getErrorInfo() {
        return errorInfo;
    }

    public PemFileTypeModel setErrorInfo(String errorInfo) {
        this.errorInfo = errorInfo;
        return this;
    }

    public String getOperation() {
        return operation;
    }

    public PemFileTypeModel setOperation(String operation) {
        this.operation = operation;
        return this;
    }

    public boolean isRegexFind() {
        return isRegexFind;
    }

    public PemFileTypeModel setRegexFind(boolean regexFind) {
        isRegexFind = regexFind;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PemFileTypeModel.class.getSimpleName() + "[", "]")
                .add("pkId='" + pkId + "'")
                .add("partner='" + partner + "'")
                .add("application='" + application + "'")
                .add("flowType='" + flowType + "'")
                .add("senderId='" + senderId + "'")
                .add("receiverId='" + receiverId + "'")
                .add("fileName='" + fileName + "'")
                .add("docType='" + docType + "'")
                .add("transaction='" + transaction + "'")
                .add("status='" + status + "'")
                .add("errorInfo='" + errorInfo + "'")
                .add("operation='" + operation + "'")
                .add("isRegexFind=" + isRegexFind)
                .toString();
    }
}
