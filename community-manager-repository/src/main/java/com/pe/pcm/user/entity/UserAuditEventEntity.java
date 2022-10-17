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

package com.pe.pcm.user.entity;

import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_SO_USERS_AUDIT")
public class UserAuditEventEntity implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_AUDIT_GENERATOR")
    @SequenceGenerator(name = "USERS_AUDIT_GENERATOR", allocationSize = 1, sequenceName = "SEQ_PETPE_SO_USERS_AUDIT")
    private Long id;
    private String principle;
    private String eventType;
    private String eventData;
    @CreationTimestamp
    private Timestamp eventDate;

    public Long getId() {
        return id;
    }

    public UserAuditEventEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getPrinciple() {
        return principle;
    }

    public UserAuditEventEntity setPrinciple(String principle) {
        this.principle = principle;
        return this;
    }

    public Timestamp getEventDate() {
        return eventDate;
    }

    public UserAuditEventEntity setEventDate(Timestamp eventDate) {
        this.eventDate = eventDate;
        return this;
    }

    public String getEventType() {
        return eventType;
    }

    public UserAuditEventEntity setEventType(String eventType) {
        this.eventType = eventType;
        return this;
    }

    public String getEventData() {
        return eventData;
    }

    public UserAuditEventEntity setEventData(String eventData) {
        this.eventData = eventData;
        return this;
    }
}
