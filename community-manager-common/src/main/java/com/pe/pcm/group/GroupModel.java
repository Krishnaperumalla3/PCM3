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

package com.pe.pcm.group;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

@JsonIgnoreProperties(ignoreUnknown = true)
public class GroupModel implements Serializable {

    private static final long serialVersionUID = 2222132165253676714L;
    private String pkId;
    private String groupName;
    private List<String> partnerList = new ArrayList<>();

    public String getPkId() {
        return pkId;
    }

    public GroupModel setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getGroupName() {
        return groupName;
    }

    public GroupModel setGroupName(String groupName) {
        this.groupName = groupName;
        return this;
    }

    public List<String> getPartnerList() {
        return partnerList;
    }

    public GroupModel setPartnerList(List<String> partnerList) {
        this.partnerList = partnerList;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", GroupModel.class.getSimpleName() + "[", "]")
                .add("pkId='" + pkId + "'")
                .add("groupName='" + groupName + "'")
                .add("partnerList=" + partnerList)
                .toString();
    }
}
