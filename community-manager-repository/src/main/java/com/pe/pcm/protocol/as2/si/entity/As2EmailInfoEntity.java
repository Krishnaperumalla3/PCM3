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

package com.pe.pcm.protocol.as2.si.entity;

import com.pe.pcm.protocol.as2.si.entity.identity.As2EmailInfoIdentity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "AS2_EMAIL_INFO")
public class As2EmailInfoEntity implements Serializable {

    @EmbeddedId
    As2EmailInfoIdentity as2EmailInfoIdentity;

    private String emailAddress;

    private String emailServer;

    private Integer emailPort;

    public As2EmailInfoIdentity getAs2EmailInfoIdentity() {
        return as2EmailInfoIdentity;
    }

    public As2EmailInfoEntity setAs2EmailInfoIdentity(As2EmailInfoIdentity as2EmailInfoIdentity) {
        this.as2EmailInfoIdentity = as2EmailInfoIdentity;
        return this;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public As2EmailInfoEntity setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
        return this;
    }

    public String getEmailServer() {
        return emailServer;
    }

    public As2EmailInfoEntity setEmailServer(String emailServer) {
        this.emailServer = emailServer;
        return this;
    }

    public Integer getEmailPort() {
        return emailPort;
    }

    public As2EmailInfoEntity setEmailPort(Integer emailPort) {
        this.emailPort = emailPort;
        return this;
    }
}
