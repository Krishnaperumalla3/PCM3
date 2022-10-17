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

import com.pe.pcm.sterling.entity.identity.WfdIdentity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.StringJoiner;

@Entity
@Table(name = "WFD")
public class WfdEntity {

    @EmbeddedId
    private WfdIdentity wfdIdentity;
    private String name;
    private String description;
    private Integer status;

    public WfdIdentity getWfdIdentity() {
        return wfdIdentity;
    }

    public WfdEntity setWfdIdentity(WfdIdentity wfdIdentity) {
        this.wfdIdentity = wfdIdentity;
        return this;
    }

    public String getName() {
        return name;
    }

    public WfdEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public WfdEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public WfdEntity setStatus(Integer status) {
        this.status = status;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", WfdEntity.class.getSimpleName() + "[", "]")
                .add("wfdIdentity=" + wfdIdentity)
                .add("name='" + name + "'")
                .add("description='" + description + "'")
                .add("status=" + status)
                .toString();
    }

//    private String edited_by;
//    private Integer type;
//    private String mod_date;
//    private float life_span;
//    private Integer removal_method;
//    private Integer persistence_level;
//    private String encoding;
//    private Integer recovery_level;
//    private String onfault;
//    private Integer priority;
//    private String doctracking;
//    private Integer wfdoptions;
//    private Integer deadline_intvl;
//    private Integer first_note;
//    private Integer second_note;
//    private Integer event_level;
//    private String execnode;
//    private String category;
//    private String organization_key;


}
