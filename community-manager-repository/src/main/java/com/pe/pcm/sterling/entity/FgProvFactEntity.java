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

package com.pe.pcm.sterling.entity;

import com.pe.pcm.protocol.as2.si.entity.SciAudit;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "FG_PROV_FACT")
public class FgProvFactEntity extends SciAudit implements Serializable {

    @Id
    private String provFactKey;
    private String routchanKey;
    private String factValue;


    public String getProvFactKey() {
        return provFactKey;
    }

    public FgProvFactEntity setProvFactKey(String provFactKey) {
        this.provFactKey = provFactKey;
        return this;
    }

    public String getRoutchanKey() {
        return routchanKey;
    }

    public FgProvFactEntity setRoutchanKey(String routchanKey) {
        this.routchanKey = routchanKey;
        return this;
    }

    public String getFactValue() {
        return factValue;
    }

    public FgProvFactEntity setFactValue(String factValue) {
        this.factValue = factValue;
        return this;
    }
}
