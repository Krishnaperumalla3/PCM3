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
import com.pe.pcm.common.CommunityManagerNameModel;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PemEmailModel implements Serializable {

    private String host;
    private String port;
    private String desc;
    private String subject;
    private String userName;
    private String password;
    private String note;
    private Boolean isWithAttachment;
    private String  attachmentContentBase64;
    private String  attachmentName;
    private String  attachmentExtension;

    private List<CommunityManagerNameModel> toMails = new ArrayList<>();
    private List<CommunityManagerNameModel> bccMails = new ArrayList<>();
    private List<CommunityManagerNameModel> ccMails = new ArrayList<>();

    private boolean isNormalMail;
    private String normalToMails;
    private String normalBccMails;
    private String normalCcMails;

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getHost() {
        return host;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }


    public List<CommunityManagerNameModel> getToMails() {
        return toMails;
    }

    public void setToMails(List<CommunityManagerNameModel> toMails) {
        this.toMails = toMails;
    }

    public List<CommunityManagerNameModel> getBccMails() {
        return bccMails;
    }

    public void setBccMails(List<CommunityManagerNameModel> bccMails) {
        this.bccMails = bccMails;
    }

    public List<CommunityManagerNameModel> getCcMails() {
        return ccMails;
    }

    public void setCcMails(List<CommunityManagerNameModel> ccMails) {
        this.ccMails = ccMails;
    }

    public boolean isNormalMail() {
        return isNormalMail;
    }

    public PemEmailModel setNormalMail(boolean normalMail) {
        isNormalMail = normalMail;
        return this;
    }

    public String getNormalToMails() {
        return normalToMails;
    }

    public PemEmailModel setNormalToMails(String normalToMails) {
        this.normalToMails = normalToMails;
        return this;
    }

    public String getNormalBccMails() {
        return normalBccMails;
    }

    public PemEmailModel setNormalBccMails(String normalBccMails) {
        this.normalBccMails = normalBccMails;
        return this;
    }

    public String getNormalCcMails() {
        return normalCcMails;
    }

    public PemEmailModel setNormalCcMails(String normalCcMails) {
        this.normalCcMails = normalCcMails;
        return this;
    }

    public Boolean getWithAttachment() {
        return isWithAttachment;
    }

    public PemEmailModel setWithAttachment(Boolean withAttachment) {
        isWithAttachment = withAttachment;
        return this;
    }

    public String getAttachmentContentBase64() {
        return attachmentContentBase64;
    }

    public PemEmailModel setAttachmentContentBase64(String attachmentContentBase64) {
        this.attachmentContentBase64 = attachmentContentBase64;
        return this;
    }

    public String getAttachmentName() {
        return attachmentName;
    }

    public PemEmailModel setAttachmentName(String attachmentName) {
        this.attachmentName = attachmentName;
        return this;
    }

    public String getAttachmentExtension() {
        return attachmentExtension;
    }

    public PemEmailModel setAttachmentExtension(String attachmentExtension) {
        this.attachmentExtension = attachmentExtension;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PemEmailModel.class.getSimpleName() + "[", "]")
                .add("host='" + host + "'")
                .add("port='" + port + "'")
                .add("desc='" + desc + "'")
                .add("subject='" + subject + "'")
                .add("userName='" + userName + "'")
                .add("password='" + password + "'")
                .add("toMails=" + toMails)
                .add("bccMails=" + bccMails)
                .add("ccMails=" + ccMails)
                .add("isNormalMail=" + isNormalMail)
                .add("normalToMails=" + normalToMails)
                .add("normalBccMails=" + normalBccMails)
                .add("normalCcMails=" + normalCcMails)
                .toString();
    }
}
