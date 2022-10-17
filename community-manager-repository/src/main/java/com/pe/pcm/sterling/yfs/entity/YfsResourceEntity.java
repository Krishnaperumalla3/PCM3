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

package com.pe.pcm.sterling.yfs.entity;

import com.pe.pcm.login.entity.PcmAudit;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "YFS_RESOURCE")
public class YfsResourceEntity extends PcmAudit implements Serializable {

    @Id
    private String resourceKey;
    private String resourceId;
    private String resourceDesc;
    private String origResourceId;
    private String parentResourceId;
    private String url;
    private String resourceType;
    private String resourceCreateType;
    private String resourceSeq;
    private String canAddToMenu;
    private String isPermissionControlled;
    private String showDetail;
    private String applicationName;
    private String serviceKey;
    private String outputXmlTemplateFileName;
    private String outputXslTemplateFileName;
    private String formClassName;
    private String overrideFormClassName;
    private String behaviorClassName;
    private String eventComponent;
    private String jsp;
    private String javascript;
    private String binding;
    private String displayBinding;
    private String altImageBinding;
    private String altImage;
    private String popup;
    private String documentType;
    private String outputNamespace;
    private String inputNamespace;
    private String resourceSubType;
    private String image;
    private String toolTip;
    private String viewId;
    private String overrideEntityId;
    private String selectionKeyName;
    private String overrideEntityKeyName;
    private String entityKeyName;
    private String closeWindowOnComplete;
    private String ignoreException;
    private String ignoreDefaultApi;
    private String height;
    private String width;
    private String input;
    private String template;
    private String apiName;
    private String flowName;
    private String skipAutoExecute;
    private String hideNavigationPanel;
    private String hideMaxRecords;
    private String systemKey;
    private String adapterKey;
    private String protocolKey;
    @Column(name = "PARAMETER_1")
    private String parameter1;
    @Column(name = "PARAMETER_2")
    private String parameter2;
    @Column(name = "PARAMETER_3")
    private String parameter3;
    @Column(name = "PARAMETER_4")
    private String parameter4;
    @Column(name = "PARAMETER_5")
    private String parameter5;
    private String suppressDecoration;
    @Column(name = "REDIRECTOR")
    private String reDirector;
    private String viewGroupId;
    private String version;
    private String applicationCode;
    private String supportsSearchToDetail;
    private String helpApplicationCode;
    private String suppressHelp;
    private String rollbackOnlyMode;
    private String isReport;
    private String status;


    public String getResourceKey() {
        return resourceKey;
    }

    public YfsResourceEntity setResourceKey(String resourceKey) {
        this.resourceKey = resourceKey;
        return this;
    }

    public String getResourceId() {
        return resourceId;
    }

    public YfsResourceEntity setResourceId(String resourceId) {
        this.resourceId = resourceId;
        return this;
    }

    public String getResourceDesc() {
        return resourceDesc;
    }

    public YfsResourceEntity setResourceDesc(String resourceDesc) {
        this.resourceDesc = resourceDesc;
        return this;
    }

    public String getOrigResourceId() {
        return origResourceId;
    }

    public YfsResourceEntity setOrigResourceId(String origResourceId) {
        this.origResourceId = origResourceId;
        return this;
    }

    public String getParentResourceId() {
        return parentResourceId;
    }

    public YfsResourceEntity setParentResourceId(String parentResourceId) {
        this.parentResourceId = parentResourceId;
        return this;
    }

    public String getUrl() {
        return url;
    }

    public YfsResourceEntity setUrl(String url) {
        this.url = url;
        return this;
    }

    public String getResourceType() {
        return resourceType;
    }

    public YfsResourceEntity setResourceType(String resourceType) {
        this.resourceType = resourceType;
        return this;
    }

    public String getResourceCreateType() {
        return resourceCreateType;
    }

    public YfsResourceEntity setResourceCreateType(String resourceCreateType) {
        this.resourceCreateType = resourceCreateType;
        return this;
    }

    public String getResourceSeq() {
        return resourceSeq;
    }

    public YfsResourceEntity setResourceSeq(String resourceSeq) {
        this.resourceSeq = resourceSeq;
        return this;
    }

    public String getCanAddToMenu() {
        return canAddToMenu;
    }

    public YfsResourceEntity setCanAddToMenu(String canAddToMenu) {
        this.canAddToMenu = canAddToMenu;
        return this;
    }

    public String getIsPermissionControlled() {
        return isPermissionControlled;
    }

    public YfsResourceEntity setIsPermissionControlled(String isPermissionControlled) {
        this.isPermissionControlled = isPermissionControlled;
        return this;
    }

    public String getShowDetail() {
        return showDetail;
    }

    public YfsResourceEntity setShowDetail(String showDetail) {
        this.showDetail = showDetail;
        return this;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public YfsResourceEntity setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public String getServiceKey() {
        return serviceKey;
    }

    public YfsResourceEntity setServiceKey(String serviceKey) {
        this.serviceKey = serviceKey;
        return this;
    }

    public String getOutputXmlTemplateFileName() {
        return outputXmlTemplateFileName;
    }

    public YfsResourceEntity setOutputXmlTemplateFileName(String outputXmlTemplateFileName) {
        this.outputXmlTemplateFileName = outputXmlTemplateFileName;
        return this;
    }

    public String getOutputXslTemplateFileName() {
        return outputXslTemplateFileName;
    }

    public YfsResourceEntity setOutputXslTemplateFileName(String outputXslTemplateFileName) {
        this.outputXslTemplateFileName = outputXslTemplateFileName;
        return this;
    }

    public String getFormClassName() {
        return formClassName;
    }

    public YfsResourceEntity setFormClassName(String formClassName) {
        this.formClassName = formClassName;
        return this;
    }

    public String getOverrideFormClassName() {
        return overrideFormClassName;
    }

    public YfsResourceEntity setOverrideFormClassName(String overrideFormClassName) {
        this.overrideFormClassName = overrideFormClassName;
        return this;
    }

    public String getBehaviorClassName() {
        return behaviorClassName;
    }

    public YfsResourceEntity setBehaviorClassName(String behaviorClassName) {
        this.behaviorClassName = behaviorClassName;
        return this;
    }

    public String getEventComponent() {
        return eventComponent;
    }

    public YfsResourceEntity setEventComponent(String eventComponent) {
        this.eventComponent = eventComponent;
        return this;
    }

    public String getJsp() {
        return jsp;
    }

    public YfsResourceEntity setJsp(String jsp) {
        this.jsp = jsp;
        return this;
    }

    public String getJavascript() {
        return javascript;
    }

    public YfsResourceEntity setJavascript(String javascript) {
        this.javascript = javascript;
        return this;
    }

    public String getBinding() {
        return binding;
    }

    public YfsResourceEntity setBinding(String binding) {
        this.binding = binding;
        return this;
    }

    public String getDisplayBinding() {
        return displayBinding;
    }

    public YfsResourceEntity setDisplayBinding(String displayBinding) {
        this.displayBinding = displayBinding;
        return this;
    }

    public String getAltImageBinding() {
        return altImageBinding;
    }

    public YfsResourceEntity setAltImageBinding(String altImageBinding) {
        this.altImageBinding = altImageBinding;
        return this;
    }

    public String getAltImage() {
        return altImage;
    }

    public YfsResourceEntity setAltImage(String altImage) {
        this.altImage = altImage;
        return this;
    }

    public String getPopup() {
        return popup;
    }

    public YfsResourceEntity setPopup(String popup) {
        this.popup = popup;
        return this;
    }

    public String getDocumentType() {
        return documentType;
    }

    public YfsResourceEntity setDocumentType(String documentType) {
        this.documentType = documentType;
        return this;
    }

    public String getOutputNamespace() {
        return outputNamespace;
    }

    public YfsResourceEntity setOutputNamespace(String outputNamespace) {
        this.outputNamespace = outputNamespace;
        return this;
    }

    public String getInputNamespace() {
        return inputNamespace;
    }

    public YfsResourceEntity setInputNamespace(String inputNamespace) {
        this.inputNamespace = inputNamespace;
        return this;
    }

    public String getResourceSubType() {
        return resourceSubType;
    }

    public YfsResourceEntity setResourceSubType(String resourceSubType) {
        this.resourceSubType = resourceSubType;
        return this;
    }

    public String getImage() {
        return image;
    }

    public YfsResourceEntity setImage(String image) {
        this.image = image;
        return this;
    }

    public String getToolTip() {
        return toolTip;
    }

    public YfsResourceEntity setToolTip(String toolTip) {
        this.toolTip = toolTip;
        return this;
    }

    public String getViewId() {
        return viewId;
    }

    public YfsResourceEntity setViewId(String viewId) {
        this.viewId = viewId;
        return this;
    }

    public String getOverrideEntityId() {
        return overrideEntityId;
    }

    public YfsResourceEntity setOverrideEntityId(String overrideEntityId) {
        this.overrideEntityId = overrideEntityId;
        return this;
    }

    public String getSelectionKeyName() {
        return selectionKeyName;
    }

    public YfsResourceEntity setSelectionKeyName(String selectionKeyName) {
        this.selectionKeyName = selectionKeyName;
        return this;
    }

    public String getOverrideEntityKeyName() {
        return overrideEntityKeyName;
    }

    public YfsResourceEntity setOverrideEntityKeyName(String overrideEntityKeyName) {
        this.overrideEntityKeyName = overrideEntityKeyName;
        return this;
    }

    public String getEntityKeyName() {
        return entityKeyName;
    }

    public YfsResourceEntity setEntityKeyName(String entityKeyName) {
        this.entityKeyName = entityKeyName;
        return this;
    }

    public String getCloseWindowOnComplete() {
        return closeWindowOnComplete;
    }

    public YfsResourceEntity setCloseWindowOnComplete(String closeWindowOnComplete) {
        this.closeWindowOnComplete = closeWindowOnComplete;
        return this;
    }

    public String getIgnoreException() {
        return ignoreException;
    }

    public YfsResourceEntity setIgnoreException(String ignoreException) {
        this.ignoreException = ignoreException;
        return this;
    }

    public String getIgnoreDefaultApi() {
        return ignoreDefaultApi;
    }

    public YfsResourceEntity setIgnoreDefaultApi(String ignoreDefaultApi) {
        this.ignoreDefaultApi = ignoreDefaultApi;
        return this;
    }

    public String getHeight() {
        return height;
    }

    public YfsResourceEntity setHeight(String height) {
        this.height = height;
        return this;
    }

    public String getWidth() {
        return width;
    }

    public YfsResourceEntity setWidth(String width) {
        this.width = width;
        return this;
    }

    public String getInput() {
        return input;
    }

    public YfsResourceEntity setInput(String input) {
        this.input = input;
        return this;
    }

    public String getTemplate() {
        return template;
    }

    public YfsResourceEntity setTemplate(String template) {
        this.template = template;
        return this;
    }

    public String getApiName() {
        return apiName;
    }

    public YfsResourceEntity setApiName(String apiName) {
        this.apiName = apiName;
        return this;
    }

    public String getFlowName() {
        return flowName;
    }

    public YfsResourceEntity setFlowName(String flowName) {
        this.flowName = flowName;
        return this;
    }

    public String getSkipAutoExecute() {
        return skipAutoExecute;
    }

    public YfsResourceEntity setSkipAutoExecute(String skipAutoExecute) {
        this.skipAutoExecute = skipAutoExecute;
        return this;
    }

    public String getHideNavigationPanel() {
        return hideNavigationPanel;
    }

    public YfsResourceEntity setHideNavigationPanel(String hideNavigationPanel) {
        this.hideNavigationPanel = hideNavigationPanel;
        return this;
    }

    public String getHideMaxRecords() {
        return hideMaxRecords;
    }

    public YfsResourceEntity setHideMaxRecords(String hideMaxRecords) {
        this.hideMaxRecords = hideMaxRecords;
        return this;
    }

    public String getSystemKey() {
        return systemKey;
    }

    public YfsResourceEntity setSystemKey(String systemKey) {
        this.systemKey = systemKey;
        return this;
    }

    public String getAdapterKey() {
        return adapterKey;
    }

    public YfsResourceEntity setAdapterKey(String adapterKey) {
        this.adapterKey = adapterKey;
        return this;
    }

    public String getProtocolKey() {
        return protocolKey;
    }

    public YfsResourceEntity setProtocolKey(String protocolKey) {
        this.protocolKey = protocolKey;
        return this;
    }

    public String getParameter1() {
        return parameter1;
    }

    public YfsResourceEntity setParameter1(String parameter1) {
        this.parameter1 = parameter1;
        return this;
    }

    public String getParameter2() {
        return parameter2;
    }

    public YfsResourceEntity setParameter2(String parameter2) {
        this.parameter2 = parameter2;
        return this;
    }

    public String getParameter3() {
        return parameter3;
    }

    public YfsResourceEntity setParameter3(String parameter3) {
        this.parameter3 = parameter3;
        return this;
    }

    public String getParameter4() {
        return parameter4;
    }

    public YfsResourceEntity setParameter4(String parameter4) {
        this.parameter4 = parameter4;
        return this;
    }

    public String getParameter5() {
        return parameter5;
    }

    public YfsResourceEntity setParameter5(String parameter5) {
        this.parameter5 = parameter5;
        return this;
    }

    public String getSuppressDecoration() {
        return suppressDecoration;
    }

    public YfsResourceEntity setSuppressDecoration(String suppressDecoration) {
        this.suppressDecoration = suppressDecoration;
        return this;
    }

    public String getReDirector() {
        return reDirector;
    }

    public YfsResourceEntity setReDirector(String reDirector) {
        this.reDirector = reDirector;
        return this;
    }

    public String getViewGroupId() {
        return viewGroupId;
    }

    public YfsResourceEntity setViewGroupId(String viewGroupId) {
        this.viewGroupId = viewGroupId;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public YfsResourceEntity setVersion(String version) {
        this.version = version;
        return this;
    }

    public String getApplicationCode() {
        return applicationCode;
    }

    public YfsResourceEntity setApplicationCode(String applicationCode) {
        this.applicationCode = applicationCode;
        return this;
    }

    public String getSupportsSearchToDetail() {
        return supportsSearchToDetail;
    }

    public YfsResourceEntity setSupportsSearchToDetail(String supportsSearchToDetail) {
        this.supportsSearchToDetail = supportsSearchToDetail;
        return this;
    }

    public String getHelpApplicationCode() {
        return helpApplicationCode;
    }

    public YfsResourceEntity setHelpApplicationCode(String helpApplicationCode) {
        this.helpApplicationCode = helpApplicationCode;
        return this;
    }

    public String getSuppressHelp() {
        return suppressHelp;
    }

    public YfsResourceEntity setSuppressHelp(String suppressHelp) {
        this.suppressHelp = suppressHelp;
        return this;
    }

    public String getRollbackOnlyMode() {
        return rollbackOnlyMode;
    }

    public YfsResourceEntity setRollbackOnlyMode(String rollbackOnlyMode) {
        this.rollbackOnlyMode = rollbackOnlyMode;
        return this;
    }

    public String getIsReport() {
        return isReport;
    }

    public YfsResourceEntity setIsReport(String isReport) {
        this.isReport = isReport;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public YfsResourceEntity setStatus(String status) {
        this.status = status;
        return this;
    }
}
