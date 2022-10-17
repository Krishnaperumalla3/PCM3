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

package com.pe.pcm.envelope;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class OutBound {

    private String segTerm;
    private String eleTerm;
    private String subEleTerm;
    private String releaseCharacter;
    private String useCorrelation;
    private String dataExtraction;
    private String extractionMailBox;
    private String extractionMailBoxBp;
    private String expectAck;
    private String intAckReq;
    private String ackOverDueHr;
    private String ackOverDueMin;

    public String getSegTerm() {
        return segTerm;
    }

    public OutBound setSegTerm(String segTerm) {
        this.segTerm = segTerm;
        return this;
    }

    public String getEleTerm() {
        return eleTerm;
    }

    public OutBound setEleTerm(String eleTerm) {
        this.eleTerm = eleTerm;
        return this;
    }

    public String getSubEleTerm() {
        return subEleTerm;
    }

    public OutBound setSubEleTerm(String subEleTerm) {
        this.subEleTerm = subEleTerm;
        return this;
    }

    public String getReleaseCharacter() {
        return releaseCharacter;
    }

    public OutBound setReleaseCharacter(String releaseCharacter) {
        this.releaseCharacter = releaseCharacter;
        return this;
    }

    public String getUseCorrelation() {
        return useCorrelation;
    }

    public OutBound setUseCorrelation(String useCorrelation) {
        this.useCorrelation = useCorrelation;
        return this;
    }

    public String getDataExtraction() {
        return dataExtraction;
    }

    public OutBound setDataExtraction(String dataExtraction) {
        this.dataExtraction = dataExtraction;
        return this;
    }

    public String getExtractionMailBox() {
        return extractionMailBox;
    }

    public OutBound setExtractionMailBox(String extractionMailBox) {
        this.extractionMailBox = extractionMailBox;
        return this;
    }

    public String getExtractionMailBoxBp() {
        return extractionMailBoxBp;
    }

    public OutBound setExtractionMailBoxBp(String extractionMailBoxBp) {
        this.extractionMailBoxBp = extractionMailBoxBp;
        return this;
    }

    public String getExpectAck() {
        return expectAck;
    }

    public OutBound setExpectAck(String expectAck) {
        this.expectAck = expectAck;
        return this;
    }

    public String getIntAckReq() {
        return intAckReq;
    }

    public OutBound setIntAckReq(String intAckReq) {
        this.intAckReq = intAckReq;
        return this;
    }

    public String getAckOverDueHr() {
        return ackOverDueHr;
    }

    public OutBound setAckOverDueHr(String ackOverDueHr) {
        this.ackOverDueHr = ackOverDueHr;
        return this;
    }

    public String getAckOverDueMin() {
        return ackOverDueMin;
    }

    public OutBound setAckOverDueMin(String ackOverDueMin) {
        this.ackOverDueMin = ackOverDueMin;
        return this;
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("OutBound{");
        sb.append("segTerm='").append(segTerm).append('\'');
        sb.append(", eleTerm='").append(eleTerm).append('\'');
        sb.append(", subEleTerm='").append(subEleTerm).append('\'');
        sb.append(", releaseCharacter='").append(releaseCharacter).append('\'');
        sb.append(", useCorrelation='").append(useCorrelation).append('\'');
        sb.append(", dataExtraction='").append(dataExtraction).append('\'');
        sb.append(", extractionMailBox='").append(extractionMailBox).append('\'');
        sb.append(", extractionMailBoxBp='").append(extractionMailBoxBp).append('\'');
        sb.append(", expectAck='").append(expectAck).append('\'');
        sb.append(", intAckReq='").append(intAckReq).append('\'');
        sb.append(", ackOverDueHr='").append(ackOverDueHr).append('\'');
        sb.append(", ackOverDueMin='").append(ackOverDueMin).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
