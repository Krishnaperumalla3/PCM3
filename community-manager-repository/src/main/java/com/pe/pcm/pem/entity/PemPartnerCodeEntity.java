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

package com.pe.pcm.pem.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "PETPE_PEM_PARTNER_CODES")
public class PemPartnerCodeEntity {
    @Id
    @NotNull
    private String partnerName;

    @NotNull
    private String partnerCode;

    public PemPartnerCodeEntity() {
    }

    public PemPartnerCodeEntity(@NotNull String partnerName, @NotNull String partnerCode) {
        this.partnerName = partnerName;
        this.partnerCode = partnerCode;
    }

    public String getPartnerName() {
        return partnerName;
    }

    public void setPartnerName(String partnerName) {
        this.partnerName = partnerName;
    }

    public String getPartnerCode() {
        return partnerCode;
    }

    public void setPartnerCode(String partnerCode) {
        this.partnerCode = partnerCode;
    }

    @Override
    public String toString() {
        return "PemPartnerCodeEntity{" +
                "partnerName='" + partnerName + '\'' +
                ", partnerCode='" + partnerCode + '\'' +
                '}';
    }
}
