/*
 *
 *  * Copyright (c) 2020 Pragma Edge Inc
 *  *
 *  * Licensed under the Pragma Edge Inc
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * https://pragmaedge.com/licenseagreement
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 */

package com.pe.pcm.user.entity;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * @author Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_SO_USERS_ATTEMPTS")
public class UserAttemptsEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "USERS_ATTEMPTS_GENERATOR")
    @SequenceGenerator(name = "USERS_ATTEMPTS_GENERATOR", allocationSize = 1, sequenceName = "SEQ_PETPE_SO_USERS_ATTEMPTS")
    private Long id;
    private String username;
    private Integer attempts;
    private Timestamp lastModified;

    public Long getId() {
        return id;
    }

    public UserAttemptsEntity setId(Long id) {
        this.id = id;
        return this;
    }

    public String getUsername() {
        return username;
    }

    public UserAttemptsEntity setUsername(String username) {
        this.username = username;
        return this;
    }

    public Integer getAttempts() {
        return attempts;
    }

    public UserAttemptsEntity setAttempts(Integer attempts) {
        this.attempts = attempts;
        return this;
    }

    public Timestamp getLastModified() {
        return lastModified;
    }

    public UserAttemptsEntity setLastModified(Timestamp lastModified) {
        this.lastModified = lastModified;
        return this;
    }

    @Override
    public String toString() {
        return "UserAttemptsEntity{" +
                "id=" + id +
                ", username='" + username + '\'' +
                ", attempts='" + attempts + '\'' +
                ", lastModified=" + lastModified +
                '}';
    }
}
