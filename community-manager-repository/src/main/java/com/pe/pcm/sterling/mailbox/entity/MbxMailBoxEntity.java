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

package com.pe.pcm.sterling.mailbox.entity;

import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.StringJoiner;

@Entity
@Table(name = "MBX_MAILBOX")
public class MbxMailBoxEntity implements Serializable {

    @Id
    /*@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "mbxMailbox_generator")
    @SequenceGenerator(name = "mbxMailbox_generator", allocationSize = 1, sequenceName = "MBX_MAILBOX_GUID_SEQUENCE")*/
    private Long mailboxId;
    private Long parentId;
    private String path;
    private String description;
    private String pathUp;
    private Character mailboxType;
    private String linkedTo;
    @UpdateTimestamp
    private Timestamp lastModification;

    public Long getMailboxId() {
        return mailboxId;
    }

    public MbxMailBoxEntity setMailboxId(Long mailboxId) {
        this.mailboxId = mailboxId;
        return this;
    }

    public Long getParentId() {
        return parentId;
    }

    public MbxMailBoxEntity setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getPath() {
        return path;
    }

    public MbxMailBoxEntity setPath(String path) {
        this.path = path;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public MbxMailBoxEntity setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getPathUp() {
        return pathUp;
    }

    public MbxMailBoxEntity setPathUp(String pathUp) {
        this.pathUp = pathUp;
        return this;
    }


    public String getLinkedTo() {
        return linkedTo;
    }

    public MbxMailBoxEntity setLinkedTo(String linkedTo) {
        this.linkedTo = linkedTo;
        return this;
    }

    public Timestamp getLastModification() {
        return lastModification;
    }

    public MbxMailBoxEntity setLastModification(Timestamp lastModification) {
        this.lastModification = lastModification;
        return this;
    }

    public Character getMailboxType() {
        return mailboxType;
    }

    public MbxMailBoxEntity setMailboxType(Character mailboxType) {
        this.mailboxType = mailboxType;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", MbxMailBoxEntity.class.getSimpleName() + "[", "]")
                .add("mailboxId=" + mailboxId)
                .add("parentId=" + parentId)
                .add("path='" + path + "'")
                .add("description='" + description + "'")
                .add("pathUp='" + pathUp + "'")
                .add("mailboxType='" + mailboxType + "'")
                .add("linkedTo='" + linkedTo + "'")
                .add("lastModification=" + lastModification)
                .toString();
    }
}
