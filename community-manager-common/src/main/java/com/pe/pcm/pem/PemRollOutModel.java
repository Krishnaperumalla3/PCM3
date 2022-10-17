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

package com.pe.pcm.pem;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
public class PemRollOutModel implements Serializable {

    private boolean rollOutInternally;
    private String activityDefinition;
    private int alertInterval;
    private String alertStartDate;
    private String contextData;
    private String description;
    private String dueDate;
    private String name;
    private List<PemAccountExpiryModel> expiryList = new ArrayList<>();

    public boolean isRollOutInternally() {
        return rollOutInternally;
    }

    public PemRollOutModel setRollOutInternally(boolean rollOutInternally) {
        this.rollOutInternally = rollOutInternally;
        return this;
    }

    public String getActivityDefinition() {
        return activityDefinition;
    }

    public PemRollOutModel setActivityDefinition(String activityDefinition) {
        this.activityDefinition = activityDefinition;
        return this;
    }

    public int getAlertInterval() {
        return alertInterval;
    }

    public PemRollOutModel setAlertInterval(int alertInterval) {
        this.alertInterval = alertInterval;
        return this;
    }

    public String getAlertStartDate() {
        return alertStartDate;
    }

    public PemRollOutModel setAlertStartDate(String alertStartDate) {
        this.alertStartDate = alertStartDate;
        return this;
    }

    public String getContextData() {
        return contextData;
    }

    public PemRollOutModel setContextData(String contextData) {
        this.contextData = contextData;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PemRollOutModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDueDate() {
        return dueDate;
    }

    public PemRollOutModel setDueDate(String dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public String getName() {
        return name;
    }

    public PemRollOutModel setName(String name) {
        this.name = name;
        return this;
    }

    public List<PemAccountExpiryModel> getExpiryList() {
        return expiryList;
    }

    public PemRollOutModel setExpiryList(List<PemAccountExpiryModel> expiryList) {
        this.expiryList = expiryList;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PemRollOutModel.class.getSimpleName() + "[", "]")
                .add("rollOutInternally=" + rollOutInternally)
                .add("activityDefinition='" + activityDefinition + "'")
                .add("alertInterval=" + alertInterval)
                .add("alertStartDate='" + alertStartDate + "'")
                .add("contextData='" + contextData + "'")
                .add("description='" + description + "'")
                .add("dueDate='" + dueDate + "'")
                .add("name='" + name + "'")
                .add("expiryList=" + expiryList)
                .toString();
    }
}
