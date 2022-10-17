/*
 * Copyright (c) 2020 Pragma Edge Inc
 *
 * Licensed under the Pragma Edge Inc, Version 6.1 (the "License");
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

package com.pe.pcm.b2b.fileprocess;

import java.util.StringJoiner;

public class RemoteProcessFlowModel {

    private Long workFlowId;

    public RemoteProcessFlowModel(Long workFlowId) {
        this.workFlowId = workFlowId;
    }

    public Long getWorkFlowId() {
        return workFlowId;
    }

    public RemoteProcessFlowModel setWorkFlowId(Long workFlowId) {
        this.workFlowId = workFlowId;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RemoteProcessFlowModel.class.getSimpleName() + "[", "]")
                .add("workFlowId=" + workFlowId)
                .toString();
    }
}
