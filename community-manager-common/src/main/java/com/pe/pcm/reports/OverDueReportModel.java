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

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OverDueReportModel {

    private String dateRangeStart;
    private String dateRangeEnd;
    private String partner;
    private String docTrans;
    private String destFileName;
    private String status;
    private String senderId;
    private String receiverId;
    private String groupCount;

    public String getDateRangeStart() {
        return dateRangeStart;
    }

    public OverDueReportModel setDateRangeStart(String dateRangeStart) {
        this.dateRangeStart = dateRangeStart;
        return this;
    }

    public String getDateRangeEnd() {
        return dateRangeEnd;
    }

    public OverDueReportModel setDateRangeEnd(String dateRangeEnd) {
        this.dateRangeEnd = dateRangeEnd;
        return this;
    }

    public String getPartner() {
        return partner;
    }

    public OverDueReportModel setPartner(String partner) {
        this.partner = partner;
        return this;
    }

    public String getDocTrans() {
        return docTrans;
    }

    public OverDueReportModel setDocTrans(String docTrans) {
        this.docTrans = docTrans;
        return this;
    }

    public String getDestFileName() {
        return destFileName;
    }

    public OverDueReportModel setDestFileName(String destFileName) {
        this.destFileName = destFileName;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public OverDueReportModel setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getSenderId() {
        return senderId;
    }

    public OverDueReportModel setSenderId(String senderId) {
        this.senderId = senderId;
        return this;
    }

    public String getReceiverId() {
        return receiverId;
    }

    public OverDueReportModel setReceiverId(String receiverId) {
        this.receiverId = receiverId;
        return this;
    }

    public String getGroupCount() {
        return groupCount;
    }

    public OverDueReportModel setGroupCount(String groupCount) {
        this.groupCount = groupCount;
        return this;
    }


}
