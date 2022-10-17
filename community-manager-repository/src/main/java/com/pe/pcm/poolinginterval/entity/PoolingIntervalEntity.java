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

package com.pe.pcm.poolinginterval.entity;

import com.pe.pcm.audit.Auditable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Entity
@Table(name = "PETPE_POOLING_INTERVAL")
public class PoolingIntervalEntity extends Auditable implements Serializable {

    @Id
    private String pkId;
    @NotNull
    private String interval;
    @NotNull
    private Integer seq;

    private String description;

    public String getPkId() {
        return pkId;
    }

    public PoolingIntervalEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getInterval() {
        return interval;
    }

    public PoolingIntervalEntity setInterval(String interval) {
        this.interval = interval;
        return this;
    }

    public Integer getSeq() {
        return seq;
    }

    public PoolingIntervalEntity setSeq(Integer seq) {
        this.seq = seq;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PoolingIntervalEntity setDescription(String description) {
        this.description = description;
        return this;
    }
}
