package com.pe.pcm.apiconnect;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import java.io.Serializable;
import java.util.List;

/**
 * @author Shameer.v.
 */
@JsonInclude(JsonInclude.Include.NON_NULL)
@XmlAccessorType(XmlAccessType.FIELD)
@JsonIgnoreProperties(ignoreUnknown = true)
public class OutLookApiModel implements Serializable {

    private String actualApi;
    private String actualApiWebMethod;
    private List<APIHPDataModel> apiHeaderDataList;
    private List<APIHPDataModel> apiParamDataList;
    private Oauth2AuthModel Oauth2Auth;

    public String getActualApi() {
        return actualApi;
    }

    public OutLookApiModel setActualApi(String actualApi) {
        this.actualApi = actualApi;
        return this;
    }

    public Oauth2AuthModel getOauth2Auth() {
        return Oauth2Auth;
    }

    public OutLookApiModel setOauth2Auth(Oauth2AuthModel oauth2Auth) {
        Oauth2Auth = oauth2Auth;
        return this;
    }

    public String getActualApiWebMethod() {
        return actualApiWebMethod;
    }

    public OutLookApiModel setActualApiWebMethod(String actualApiWebMethod) {
        this.actualApiWebMethod = actualApiWebMethod;
        return this;
    }

    public List<APIHPDataModel> getApiHeaderDataList() {
        return apiHeaderDataList;
    }

    public OutLookApiModel setApiHeaderDataList(List<APIHPDataModel> apiHeaderDataList) {
        this.apiHeaderDataList = apiHeaderDataList;
        return this;
    }

    public List<APIHPDataModel> getApiParamDataList() {
        return apiParamDataList;
    }

    public OutLookApiModel setApiParamDataList(List<APIHPDataModel> apiParamDataList) {
        this.apiParamDataList = apiParamDataList;
        return this;
    }
}
