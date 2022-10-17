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

package com.pe.pcm.sterling.mailbox;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class DeliveryChannelTemplateModel implements Serializable {

    private String consumerMailboxPath;
    private boolean createMailboxAtRuntime;
    private boolean mailboxOrProtocol;
    private List<ConsumerFileStructureModel> consumerFileStructureModelList = new ArrayList<>();

    public String getConsumerMailboxPath() {
        return consumerMailboxPath;
    }

    public DeliveryChannelTemplateModel setConsumerMailboxPath(String consumerMailboxPath) {
        this.consumerMailboxPath = consumerMailboxPath;
        return this;
    }

    public boolean isCreateMailboxAtRuntime() {
        return createMailboxAtRuntime;
    }

    public DeliveryChannelTemplateModel setCreateMailboxAtRuntime(boolean createMailboxAtRuntime) {
        this.createMailboxAtRuntime = createMailboxAtRuntime;
        return this;
    }

    public boolean isMailboxOrProtocol() {
        return mailboxOrProtocol;
    }

    public DeliveryChannelTemplateModel setMailboxOrProtocol(boolean mailboxOrProtocol) {
        this.mailboxOrProtocol = mailboxOrProtocol;
        return this;
    }

    public List<ConsumerFileStructureModel> getConsumerFileStructureModelList() {
        return consumerFileStructureModelList;
    }

    public DeliveryChannelTemplateModel setConsumerFileStructureModelList(List<ConsumerFileStructureModel> consumerFileStructureModelList) {
        this.consumerFileStructureModelList = consumerFileStructureModelList;
        return this;
    }
}
