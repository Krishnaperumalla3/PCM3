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

package com.pe.pcm.workflow.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(name = "PETPE_PROCESSDOCS")
public class ProcessDocsEntity implements Serializable {

    @Id
    private String pkId;
    private String processRef;
    private String doctype;
    private String partnerid;
    private String reciverid;
    private String doctrans;
    private String filenamePattern;
    private String processRuleseq;
    private String version;

    public String getPkId() {
        return pkId;
    }

    public ProcessDocsEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getProcessRef() {
        return processRef;
    }

    public ProcessDocsEntity setProcessRef(String processRef) {
        this.processRef = processRef;
        return this;
    }

    public String getDoctype() {
        return doctype;
    }

    public ProcessDocsEntity setDoctype(String doctype) {
        this.doctype = doctype;
        return this;
    }

    public String getPartnerid() {
        return partnerid;
    }

    public ProcessDocsEntity setPartnerid(String partnerid) {
        this.partnerid = partnerid;
        return this;
    }

    public String getReciverid() {
        return reciverid;
    }

    public ProcessDocsEntity setReciverid(String reciverid) {
        this.reciverid = reciverid;
        return this;
    }

    public String getDoctrans() {
        return doctrans;
    }

    public ProcessDocsEntity setDoctrans(String doctrans) {
        this.doctrans = doctrans;
        return this;
    }

    public String getFilenamePattern() {
        return filenamePattern;
    }

    public ProcessDocsEntity setFilenamePattern(String filenamePattern) {
        this.filenamePattern = filenamePattern;
        return this;
    }

    public String getProcessRuleseq() {
        return processRuleseq;
    }

    public ProcessDocsEntity setProcessRuleseq(String processRuleseq) {
        this.processRuleseq = processRuleseq;
        return this;
    }

    public String getVersion() {
        return version;
    }

    public ProcessDocsEntity setVersion(String version) {
        this.version = version;
        return this;
    }

    @Override
    public String toString() {
        return "ProcessDocsEntity{" +
                "pkId='" + pkId + '\'' +
                ", processRef='" + processRef + '\'' +
                ", doctype='" + doctype + '\'' +
                ", partnerid='" + partnerid + '\'' +
                ", reciverid='" + reciverid + '\'' +
                ", doctrans='" + doctrans + '\'' +
                ", filenamePattern='" + filenamePattern + '\'' +
                ", processRuleseq='" + processRuleseq + '\'' +
                ", version='" + version + '\'' +
                '}';
    }
}
