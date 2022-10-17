/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc.
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

package com.pe.pcm.user;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class UserStatusModel implements Serializable {
    @NotNull
    private String pkId;
    @NotNull
    private Boolean status;

    public String getPkId() {
        return pkId;
    }

    public UserStatusModel setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public Boolean getStatus() {
        return status;
    }

    public UserStatusModel setStatus(Boolean status) {
        this.status = status;
        return this;
    }
}
