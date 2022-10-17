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

package com.pe.pcm.b2b.routing.rules;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;

public class RemoteRoutingRules implements Serializable {

    private String action;
    private String actionType;
    private String evaluationMode;
    private String messageNamePattern;
    private String name;
    private List<String> mailboxes = new ArrayList<>();
    private String runRuleAs;

    public String getAction() {
        return action;
    }

    public RemoteRoutingRules setAction(String action) {
        this.action = action;
        return this;
    }

    public String getActionType() {
        return actionType;
    }

    public RemoteRoutingRules setActionType(String actionType) {
        this.actionType = actionType;
        return this;
    }

    public String getEvaluationMode() {
        return evaluationMode;
    }

    public RemoteRoutingRules setEvaluationMode(String evaluationMode) {
        this.evaluationMode = evaluationMode;
        return this;
    }

    public String getMessageNamePattern() {
        return messageNamePattern;
    }

    public RemoteRoutingRules setMessageNamePattern(String messageNamePattern) {
        this.messageNamePattern = messageNamePattern;
        return this;
    }

    public String getName() {
        return name;
    }

    public RemoteRoutingRules setName(String name) {
        this.name = name;
        return this;
    }

    public List<String> getMailboxes() {
        return mailboxes;
    }

    public RemoteRoutingRules setMailboxes(List<String> mailboxes) {
        this.mailboxes = mailboxes;
        return this;
    }

    public String getRunRuleAs() {
        return runRuleAs;
    }

    public RemoteRoutingRules setRunRuleAs(String runRuleAs) {
        this.runRuleAs = runRuleAs;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", RemoteRoutingRules.class.getSimpleName() + "[", "]")
                .add("action='" + action + "'")
                .add("actionType='" + actionType + "'")
                .add("evaluationMode='" + evaluationMode + "'")
                .add("messageNamePattern='" + messageNamePattern + "'")
                .add("name='" + name + "'")
                .add("mailboxes=" + mailboxes)
                .add("runRuleAs='" + runRuleAs + "'")
                .toString();
    }
}
