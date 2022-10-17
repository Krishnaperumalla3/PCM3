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

package com.pe.pcm.certificate.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.sql.Date;
import java.util.StringJoiner;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table(name = "CERTS_AND_PRI_KEY")
public class CertsAndPriKeyEntity implements Serializable {

    @Id
    private String objectId;
    private String name;
    private String username;
    private Date notBefore;
    private Date notAfter;
    @NotNull
    private Integer status;

    public String getObjectId() {
        return objectId;
    }

    public CertsAndPriKeyEntity setObjectId(String objectId) {
        this.objectId = objectId;
        return this;
    }

    public String getName() {
        return name;
    }

    public CertsAndPriKeyEntity setName(String name) {
        this.name = name;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public CertsAndPriKeyEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public Date getNotBefore() {
        return notBefore;
    }

    public void setNotBefore(Date notBefore) {
        this.notBefore = notBefore;
    }

    public Date getNotAfter() {
        return notAfter;
    }

    public void setNotAfter(Date notAfter) {
        this.notAfter = notAfter;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", CertsAndPriKeyEntity.class.getSimpleName() + "[", "]")
                .add("objectId='" + objectId + "'")
                .add("name='" + name + "'")
                .add("username='" + username + "'")
                .add("notBefore=" + notBefore)
                .add("notAfter=" + notAfter)
                .add("status=" + status)
                .toString();
    }

}
