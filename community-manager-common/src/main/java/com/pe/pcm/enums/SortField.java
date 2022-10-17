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

package com.pe.pcm.enums;

/**
 * @author Kiran Reddy.
 */
public enum SortField {

    VALUE("VALUE"),
    FILEARRIVED("FILEARRIVED"),
    DESTFILENAME("DESTFILENAME"),
    PARTNER("PARTNER"),
    APPLICATION("APPLICATION"),
    FLOWINOUT("FLOWINOUT"),
    SRCFILESIZE("SRCFILESIZE"),
    DOCTRANS("DOCTRANS"),
    SENDERID("SENDERID"),
    RECIVERID("RECIVERID"),
    STATUS("STATUS"),
    WFID("WF_ID"),
    OVERDUE("OVERDUETIME"),
    GROUPCOUNT("GROUPCOUNT"),
    PARTNERPROFILE("TP.TP_NAME"),
    APPLICATIONPROFILE("AP.APPLICATION_NAME"),
    SEQTYPE("PC.SEQ_TYPE"),
    FILENAME("PD.FILENAME_PATTERN"),
    FLOW("PC.FLOW"),
    FILETYPE("PD.FILENAME_PATTERN"),
    DOCTYPE("PD.DOCTYPE"),
    TRANSACTION("PD.DOCTRANS"),
    PARTNERID("PD.PARTNERID"),
    RECEIVERID("PD.RECIVERID"),
    RULENAME("PR.RULE_NAME"),
    RULEPROPERTY1("PR.PROPERTY_NAME_1"),
    RULEPROPERTY2("PR.PROPERTY_NAME_2"),
    RULEPROPERTY3("PR.PROPERTY_NAME_3"),
    RULEPROPERTY4("PR.PROPERTY_NAME_4"),
    RULEPROPERTY5("PR.PROPERTY_NAME_5"),
    RULEPROPERTY6("PR.PROPERTY_NAME_6"),
    RULEPROPERTY7("PR.PROPERTY_NAME_7"),
    RULEPROPERTY8("PR.PROPERTY_NAME_8"),
    RULEPROPERTY9("PR.PROPERTY_NAME_9"),
    RULEPROPERTY10("PR.PROPERTY_NAME_10"),
    RULEPROPERTY11("PR.PROPERTY_NAME_11"),
    RULEPROPERTY12("PR.PROPERTY_NAME_12"),
    RULEPROPERTY13("PR.PROPERTY_NAME_13"),
    RULEPROPERTY14("PR.PROPERTY_NAME_14"),
    RULEPROPERTY15("PR.PROPERTY_NAME_15"),
    BUSINESSPROCESSID("PR.RULE_NAME");

    private final String field;

    SortField(String field) {
        this.field = field;
    }

    public String getField() {
        return this.field;
    }

}
