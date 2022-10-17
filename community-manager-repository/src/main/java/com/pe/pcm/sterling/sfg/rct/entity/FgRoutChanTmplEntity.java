/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.sterling.sfg.rct.entity;

import com.pe.pcm.login.entity.PcmAudit;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "FG_ROUTCHAN_TMPL")
public class FgRoutChanTmplEntity extends PcmAudit {

    @Id
    private String routchanTmplKey;
    private String tmplName;
    private String pvMbxPattern;
    private String considType;
    private String bpName;
    private String bpConsNameXpath;
    private String substMode;
    private String substFrom;
    private String substTo;

    public String getRoutchanTmplKey() {
        return routchanTmplKey;
    }

    public FgRoutChanTmplEntity setRoutchanTmplKey(String routchanTmplKey) {
        this.routchanTmplKey = routchanTmplKey;
        return this;
    }

    public String getTmplName() {
        return tmplName;
    }

    public FgRoutChanTmplEntity setTmplName(String tmplName) {
        this.tmplName = tmplName;
        return this;
    }

    public String getPvMbxPattern() {
        return pvMbxPattern;
    }

    public FgRoutChanTmplEntity setPvMbxPattern(String pvMbxPattern) {
        this.pvMbxPattern = pvMbxPattern;
        return this;
    }

    public String getConsidType() {
        return considType;
    }

    public FgRoutChanTmplEntity setConsidType(String considType) {
        this.considType = considType;
        return this;
    }

    public String getBpName() {
        return bpName;
    }

    public FgRoutChanTmplEntity setBpName(String bpName) {
        this.bpName = bpName;
        return this;
    }

    public String getBpConsNameXpath() {
        return bpConsNameXpath;
    }

    public FgRoutChanTmplEntity setBpConsNameXpath(String bpConsNameXpath) {
        this.bpConsNameXpath = bpConsNameXpath;
        return this;
    }

    public String getSubstMode() {
        return substMode;
    }

    public FgRoutChanTmplEntity setSubstMode(String substMode) {
        this.substMode = substMode;
        return this;
    }

    public String getSubstFrom() {
        return substFrom;
    }

    public FgRoutChanTmplEntity setSubstFrom(String substFrom) {
        this.substFrom = substFrom;
        return this;
    }

    public String getSubstTo() {
        return substTo;
    }

    public FgRoutChanTmplEntity setSubstTo(String substTo) {
        this.substTo = substTo;
        return this;
    }

}
