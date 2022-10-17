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

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Chenchu Kiran.
 */
public class GroupPermissionsModel implements Serializable {

    private List<GroupNameModel> producerGroupNameList = new ArrayList<>();
    private List<GroupNameModel> consumerGroupNameList = new ArrayList<>();

    public List<GroupNameModel> getProducerGroupNameList() {
        return producerGroupNameList;
    }

    public GroupPermissionsModel setProducerGroupNameList(List<GroupNameModel> producerGroupNameList) {
        this.producerGroupNameList = producerGroupNameList;
        return this;
    }

    public List<GroupNameModel> getConsumerGroupNameList() {
        return consumerGroupNameList;
    }

    public GroupPermissionsModel setConsumerGroupNameList(List<GroupNameModel> consumerGroupNameList) {
        this.consumerGroupNameList = consumerGroupNameList;
        return this;
    }
}