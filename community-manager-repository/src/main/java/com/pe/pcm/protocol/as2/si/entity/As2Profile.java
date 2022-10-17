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

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

import static com.pe.pcm.utils.PCMConstants.*;

@Entity
@Table(name = "AS2_PROFILE")
public class As2Profile extends SciAudit implements Serializable {

    @Id
    private String profileId;

    private String isOrg;

    private String isOriginalOrg = N;

    private String httpClientAdapter;

    public As2Profile() {
        this.setLockid(1);
        this.setCreateuserid(INSTALL_PROCESS);
        this.setModifyuserid(INSTALL_PROCESS);
        this.setCreateprogid(UI);
        this.setModifyprogid(UI);
    }

    public String getProfileId() {
        return profileId;
    }

    public As2Profile setProfileId(String profileId) {
        this.profileId = profileId;
        return this;
    }

    public String getIsOrg() {
        return isOrg;
    }

    public As2Profile setIsOrg(String isOrg) {
        this.isOrg = isOrg;
        return this;
    }

    public String getIsOriginalOrg() {
        return isOriginalOrg;
    }

    public As2Profile setIsOriginalOrg(String isOriginalOrg) {
        this.isOriginalOrg = isOriginalOrg;
        return this;
    }

    public String getHttpClientAdapter() {
        return httpClientAdapter;
    }

    public As2Profile setHttpClientAdapter(String httpClientAdapter) {
        this.httpClientAdapter = httpClientAdapter;
        return this;
    }
}
