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

package com.pe.pcm.pem.codelist;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

/**
 * @author Shameer.v.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PemProxyCodeSet implements Serializable {

    private String description;
    private String id;
    private String receiverCode;
    private String senderCode;
    private String text1;
    private String text2;
    private String text3;
    private String text4;
    private String text5;
    private String text6;
    private String text7;
    private String text8;
    private String text9;

    public String getDescription() {
        return description;
    }

    public PemProxyCodeSet setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getId() {
        return id;
    }

    public PemProxyCodeSet setId(String id) {
        this.id = id;
        return this;
    }

    public String getReceiverCode() {
        return receiverCode;
    }

    public PemProxyCodeSet setReceiverCode(String receiverCode) {
        this.receiverCode = receiverCode;
        return this;
    }

    public String getSenderCode() {
        return senderCode;
    }

    public PemProxyCodeSet setSenderCode(String senderCode) {
        this.senderCode = senderCode;
        return this;
    }

    public String getText1() {
        return text1;
    }

    public PemProxyCodeSet setText1(String text1) {
        this.text1 = text1;
        return this;
    }

    public String getText2() {
        return text2;
    }

    public PemProxyCodeSet setText2(String text2) {
        this.text2 = text2;
        return this;
    }

    public String getText3() {
        return text3;
    }

    public PemProxyCodeSet setText3(String text3) {
        this.text3 = text3;
        return this;
    }

    public String getText4() {
        return text4;
    }

    public PemProxyCodeSet setText4(String text4) {
        this.text4 = text4;
        return this;
    }

    public String getText5() {
        return text5;
    }

    public PemProxyCodeSet setText5(String text5) {
        this.text5 = text5;
        return this;
    }

    public String getText6() {
        return text6;
    }

    public PemProxyCodeSet setText6(String text6) {
        this.text6 = text6;
        return this;
    }

    public String getText7() {
        return text7;
    }

    public PemProxyCodeSet setText7(String text7) {
        this.text7 = text7;
        return this;
    }

    public String getText8() {
        return text8;
    }

    public PemProxyCodeSet setText8(String text8) {
        this.text8 = text8;
        return this;
    }

    public String getText9() {
        return text9;
    }

    public PemProxyCodeSet setText9(String text9) {
        this.text9 = text9;
        return this;
    }
}
