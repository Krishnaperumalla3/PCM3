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

package com.pe.pcm.reports;

public class OverDueMapper {

    private int rowNum;
    private String value;
    private String fileArrived;
    private String destFileName;
    private String partner;
    private String docTrans;
    private String senderId;
    private String receiverId;
    private String wfId;
    private String groupCount;
    private String overdue;

    public String getValue() {
        return value;
    }

    public OverDueMapper setValue(String value) {
        this.value = value;
        return this;
    }

    public String getFileArrived() {
        return fileArrived;
    }

    public OverDueMapper setFileArrived(String fileArrived) {
        this.fileArrived = fileArrived;
        return this;
    }

    public String getDestFileName() {
        return destFileName;
    }

    public OverDueMapper setDestFileName(String destFileName) {
        this.destFileName = destFileName;
        return this;
    }

    public String getPartner() {
        return partner;
    }

    public OverDueMapper setPartner(String partner) {
        this.partner = partner;
        return this;
    }

    public String getDocTrans() {
        return docTrans;
    }

    public OverDueMapper setDocTrans(String docTrans) {
        this.docTrans = docTrans;
        return this;
    }

    public String getSenderId() {
        return senderId;
    }

    public OverDueMapper setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public OverDueMapper setReceiverId(String receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public String getWfId() {
        return wfId;
    }

    public OverDueMapper setWfId(String wfId) {
        this.wfId = wfId;
        return this;
    }

    public String getGroupCount() {
        return groupCount;
    }

    public OverDueMapper setGroupCount(String groupCount) {
        this.groupCount = groupCount;
        return this;
    }

    public int getRowNum() {
        return rowNum;
    }

    public OverDueMapper setRowNum(int rowNum) {
        this.rowNum = rowNum;
        return this;
    }

    public String getOverdue() {
        return overdue;
    }

    public OverDueMapper setOverdue(String overdue) {
        this.overdue = overdue;
        return this;
    }
}
