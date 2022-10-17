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

package com.pe.pcm.workflow.pem;

import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
public class PemImportFlowModel implements Serializable {

    private String partnerName;
    private String applicationName;
    private String data;

    public String getPartnerName() {
        return partnerName;
    }

    public PemImportFlowModel setPartnerName(String partnerName) {
        this.partnerName = partnerName;
        return this;
    }

    public String getApplicationName() {
        return applicationName;
    }

    public PemImportFlowModel setApplicationName(String applicationName) {
        this.applicationName = applicationName;
        return this;
    }

    public String getData() {
        return data;
    }

    public PemImportFlowModel setData(String data) {
        this.data = data;
        return this;
    }

}
