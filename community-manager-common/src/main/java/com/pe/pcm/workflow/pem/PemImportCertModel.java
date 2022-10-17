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

import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * @author Chenchu Kiran.
 */
public class PemImportCertModel implements Serializable {
    @NotNull
    private String certOrKeyName;

    @NotNull
    private String certOrKeyData;

    public PemImportCertModel(@NotNull String certOrKeyName, @NotNull String certOrKeyData) {
        this.certOrKeyName = certOrKeyName;
        this.certOrKeyData = certOrKeyData;
    }

    public PemImportCertModel() {
        //Dont delete this , we need this while loading data in Resource
    }

    public String getCertOrKeyName() {
        return certOrKeyName;
    }

    public PemImportCertModel setCertOrKeyName(String certOrKeyName) {
        this.certOrKeyName = certOrKeyName;
        return this;
    }

    public String getCertOrKeyData() {
        return certOrKeyData;
    }

    public PemImportCertModel setCertOrKeyData(String certOrKeyData) {
        this.certOrKeyData = certOrKeyData;
        return this;
    }
}
