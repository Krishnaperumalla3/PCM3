package com.pe.pcm.mode;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class CommonModel implements Serializable {

    private String actualLightWellUrl;
    private Oauth2AuthModelForMode oauth2AuthModelForMode;
    private ModeDocumentSearchModel modeDocumentSearchModel;

    private IdentitySearchModel identitySearchModel;
    private IdentityModel identityModel;

    private SendRuleSearchModel sendRuleSearchModel;

    private AddRuleModel addRuleModel;

    public String getActualLightWellUrl() {
        return actualLightWellUrl;
    }

    public CommonModel setActualLightWellUrl(String actualLightWellUrl) {
        this.actualLightWellUrl = actualLightWellUrl;
        return this;
    }
    public Oauth2AuthModelForMode getOauth2AuthModelForMode() {
        return oauth2AuthModelForMode;
    }

    public CommonModel setOauth2AuthModelForMode(Oauth2AuthModelForMode oauth2AuthModelForMode) {
        this.oauth2AuthModelForMode = oauth2AuthModelForMode;
        return this;
    }

    public ModeDocumentSearchModel getModeDocumentSearchModel() {
        return modeDocumentSearchModel;
    }

    public CommonModel setModeDocumentSearchModel(ModeDocumentSearchModel modeDocumentSearchModel) {
        this.modeDocumentSearchModel = modeDocumentSearchModel;
        return this;
    }

    public IdentityModel getIdentityModel() {
        return identityModel;
    }

    public CommonModel setIdentityModel(IdentityModel identityModel) {
        this.identityModel = identityModel;
        return this;
    }

    public IdentitySearchModel getIdentitySearchModel() {
        return identitySearchModel;
    }

    public CommonModel setIdentitySearchModel(IdentitySearchModel identitySearchModel) {
        this.identitySearchModel = identitySearchModel;
        return this;
    }

    public SendRuleSearchModel getSendRuleSearchModel() {
        return sendRuleSearchModel;
    }

    public CommonModel setSendRuleSearchModel(SendRuleSearchModel sendRuleSearchModel) {
        this.sendRuleSearchModel = sendRuleSearchModel;
        return this;
    }

    public AddRuleModel getAddRuleModel() {
        return addRuleModel;
    }

    public CommonModel setAddRuleModel(AddRuleModel addRuleModel) {
        this.addRuleModel = addRuleModel;
        return this;
    }
}
