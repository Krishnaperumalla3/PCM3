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

import com.pe.pcm.protocol.as2.si.entity.identity.SftpProfileXrefKhostKeyIdentity;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@Entity
@Table (name = "SFTP_PROF_XREF_KHOST_KEY")
public class SftpProfileXrefKhostKeyEntity implements Serializable {

    @EmbeddedId
    private SftpProfileXrefKhostKeyIdentity sftpProfileXrefKhostKeyIdentity;

    public SftpProfileXrefKhostKeyIdentity getSftpProfileXrefKhostKeyIdentity() {
        return sftpProfileXrefKhostKeyIdentity;
    }

    public SftpProfileXrefKhostKeyEntity setSftpProfileXrefKhostKeyIdentity(SftpProfileXrefKhostKeyIdentity sftpProfileXrefKhostKeyIdentity) {
        this.sftpProfileXrefKhostKeyIdentity = sftpProfileXrefKhostKeyIdentity;
        return this;
    }
}
