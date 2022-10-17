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

package com.pe.pcm.workflow.pem.template;

import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Chenchu Kiran.
 */
public class TemplateProcessDocModel {
    private String pkId;
    private List<TemplateProcessRulesModel> templateProcessRulesModelList = new ArrayList<>();

    public String getPkId() {
        return pkId;
    }

    public TemplateProcessDocModel setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public List<TemplateProcessRulesModel> getTemplateProcessRulesModelList() {
        return templateProcessRulesModelList;
    }

    public TemplateProcessDocModel setTemplateProcessRulesModelList(List<TemplateProcessRulesModel> templateProcessRulesModelList) {
        this.templateProcessRulesModelList = templateProcessRulesModelList;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", TemplateProcessDocModel.class.getSimpleName() + "[", "]")
                .add("pkId='" + pkId + "'")
                .add("templateProcessRulesModelList=" + templateProcessRulesModelList)
                .toString();
    }
}
