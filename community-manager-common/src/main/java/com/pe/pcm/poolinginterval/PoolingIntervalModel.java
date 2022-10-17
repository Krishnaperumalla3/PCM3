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

package com.pe.pcm.poolinginterval;

import javax.validation.constraints.NotNull;
import java.util.StringJoiner;

public class PoolingIntervalModel {

    @NotNull
    private String pkId;
    @NotNull
    private String interval;
    @NotNull
    private Integer seq;

    private String description;

    public String getPkId() {
        return pkId;
    }

    public void setPkId(String pkId) {
        this.pkId = pkId;
    }

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public Integer getSeq() {
        return seq;
    }

    public void setSeq(Integer seq) {
        this.seq = seq;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PoolingIntervalModel.class.getSimpleName() + "[", "]")
                .add("pkId='" + pkId + "'")
                .add("interval='" + interval + "'")
                .add("seq=" + seq)
                .add("description='" + description + "'")
                .toString();
    }
}
