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

package com.pe.pcm.pem.ws;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

import java.io.Serializable;
import java.util.List;

/**
 * @author Chenchu Kiran.
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PemWsRollOutModel implements Serializable {
    private int alertInterval;
    private String alertStartDate;
    private String contextData;
    private String description;
    private String dueDate;
    private String name;
    private Boolean rolloutInternally;
    private List<RollOutParticipants> participants;

    public int getAlertInterval() {
        return alertInterval;
    }

    public PemWsRollOutModel setAlertInterval(int alertInterval) {
        this.alertInterval = alertInterval;
        return this;
    }

    public String getAlertStartDate() {
        return alertStartDate;
    }

    public PemWsRollOutModel setAlertStartDate(String alertStartDate) {
        this.alertStartDate = alertStartDate;
        return this;
    }

    public String getContextData() {
        return contextData;
    }

    public PemWsRollOutModel setContextData(String contextData) {
        this.contextData = contextData;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public PemWsRollOutModel setDescription(String description) {
        this.description = description;
        return this;
    }

    public String getDueDate() {
        return dueDate;
    }

    public PemWsRollOutModel setDueDate(String dueDate) {
        this.dueDate = dueDate;
        return this;
    }

    public String getName() {
        return name;
    }

    public PemWsRollOutModel setName(String name) {
        this.name = name;
        return this;
    }

    public Boolean getRolloutInternally() {
        return rolloutInternally;
    }

    public PemWsRollOutModel setRolloutInternally(Boolean rolloutInternally) {
        this.rolloutInternally = rolloutInternally;
        return this;
    }

    public List<RollOutParticipants> getParticipants() {
        return participants;
    }

    public PemWsRollOutModel setParticipants(List<RollOutParticipants> participants) {
        this.participants = participants;
        return this;
    }

    @Override
    public String toString() {
        return "PemWsRollOutModel{" +
                "alertInterval=" + alertInterval +
                ", alertStartDate='" + alertStartDate + '\'' +
                ", contextData='" + contextData + '\'' +
                ", description='" + description + '\'' +
                ", dueDate='" + dueDate + '\'' +
                ", name='" + name + '\'' +
                ", rolloutInternally=" + rolloutInternally +
                ", participants=" + participants +
                '}';
    }
}
