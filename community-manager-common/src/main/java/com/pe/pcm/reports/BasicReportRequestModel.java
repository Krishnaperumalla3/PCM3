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

@JsonIgnoreProperties(ignoreUnknown = true)
public class BasicReportRequestModel {

    private String dataRangeStart;
    private String dateRangeEnd;
    private String partner;
    private String status;
    private String direction;
    private String transferType;
    private String transaction;
    private String docType;

    public String getDataRangeStart() {
        return dataRangeStart;
    }

    public BasicReportRequestModel setDataRangeStart(String dataRangeStart) {
        this.dataRangeStart = dataRangeStart;
        return this;
    }

    public String getDateRangeEnd() {
        return dateRangeEnd;
    }

    public BasicReportRequestModel setDateRangeEnd(String dateRangeEnd) {
        this.dateRangeEnd = dateRangeEnd;
        return this;
    }

    public String getPartner() {
        return partner;
    }

    public BasicReportRequestModel setPartner(String partner) {
        this.partner = partner;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public BasicReportRequestModel setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getDirection() {
        return direction;
    }

    public BasicReportRequestModel setDirection(String direction) {
        this.direction = direction;
        return this;
    }

    public String getTransferType() {
        return transferType;
    }

    public BasicReportRequestModel setTransferType(String transferType) {
        this.transferType = transferType;
        return this;
    }

    public String getTransaction() {
        return transaction;
    }

    public BasicReportRequestModel setTransaction(String transaction) {
        this.transaction = transaction;
        return this;
    }

    public String getDocType() {
        return docType;
    }

    public BasicReportRequestModel setDocType(String docType) {
        this.docType = docType;
        return this;
    }
}
