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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "FG_DELIVCHAN_TMPL")
public class FgDelivChanTmplEntity extends PcmAudit {

    @Id
    @Column(name = "DELIVCHAN_T_KEY")
    private String delivChanTKey;
    private String routchanTmplKey;
    private String tmplName;
    private String pvMbxPattern;
    private String lateCreateMbx;
    private Integer protocol;

    public String getDelivChanTKey() {
        return delivChanTKey;
    }

    public FgDelivChanTmplEntity setDelivChanTKey(String delivChanTKey) {
        this.delivChanTKey = delivChanTKey;
        return this;
    }

    public String getRoutchanTmplKey() {
        return routchanTmplKey;
    }

    public FgDelivChanTmplEntity setRoutchanTmplKey(String routchanTmplKey) {
        this.routchanTmplKey = routchanTmplKey;
        return this;
    }

    public String getTmplName() {
        return tmplName;
    }

    public FgDelivChanTmplEntity setTmplName(String tmplName) {
        this.tmplName = tmplName;
        return this;
    }

    public String getPvMbxPattern() {
        return pvMbxPattern;
    }

    public FgDelivChanTmplEntity setPvMbxPattern(String pvMbxPattern) {
        this.pvMbxPattern = pvMbxPattern;
        return this;
    }

    public String getLateCreateMbx() {
        return lateCreateMbx;
    }

    public FgDelivChanTmplEntity setLateCreateMbx(String lateCreateMbx) {
        this.lateCreateMbx = lateCreateMbx;
        return this;
    }

    public Integer getProtocol() {
        return protocol;
    }

    public FgDelivChanTmplEntity setProtocol(Integer protocol) {
        this.protocol = protocol;
        return this;
    }
}
