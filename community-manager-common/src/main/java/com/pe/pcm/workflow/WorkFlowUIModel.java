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

package com.pe.pcm.workflow;

import java.io.Serializable;

public class WorkFlowUIModel implements Serializable {

    private static final long serialVersionUID = 4311020219609364229L;

    private ProcessFlowModel inboundFlow = new ProcessFlowModel();

    private ProcessFlowModel outboundFlow = new ProcessFlowModel();

    public ProcessFlowModel getInboundFlow() {
        return inboundFlow;
    }

    public WorkFlowUIModel setInboundFlow(ProcessFlowModel inboundFlow) {
        this.inboundFlow = inboundFlow;
        return this;
    }

    public ProcessFlowModel getOutboundFlow() {
        return outboundFlow;
    }

    public WorkFlowUIModel setOutboundFlow(ProcessFlowModel outboundFlow) {
        this.outboundFlow = outboundFlow;
        return this;
    }

}
