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

import com.pe.pcm.sterling.entity.identity.AuthUserXrefSshIdentity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.StringJoiner;

/**
 * @author Chenchu Kiran.
 * This is ref table for User Authorised User keys.
 */
@Entity
@Table(name = "AUTHO_XREF_SSH")
public class AuthUserXrefSshEntity implements Serializable {

    @EmbeddedId
    private AuthUserXrefSshIdentity authUserXrefSshIdentity;

    public AuthUserXrefSshIdentity getAuthUserXrefSshIdentity() {
        return authUserXrefSshIdentity;
    }

    public AuthUserXrefSshEntity setAuthUserXrefSshIdentity(AuthUserXrefSshIdentity authUserXrefSshIdentity) {
        this.authUserXrefSshIdentity = authUserXrefSshIdentity;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", AuthUserXrefSshEntity.class.getSimpleName() + "[", "]")
                .add("authUserXrefSshIdentity=" + authUserXrefSshIdentity)
                .toString();
    }
}
