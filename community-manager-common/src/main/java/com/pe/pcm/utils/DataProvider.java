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

package com.pe.pcm.utils;

import com.pe.pcm.common.CommunityManagerKeyValueModel;
import com.pe.pcm.envelope.EnvelopeModel;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

public class DataProvider {

    private DataProvider() {
    }

    public static List<CommunityManagerKeyValueModel> getTerminatorsMap() {
        List<CommunityManagerKeyValueModel> terminatorsList = new ArrayList<>();
        terminatorsList.add(new CommunityManagerKeyValueModel("0x01", "^A"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x02", "^B"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x03", "^C"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x04", "^D"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x05", "^E"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x06", "^F"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x07", "^G"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x08", "^H"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x09", "^I"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x0A", "^J"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x0B", "^K"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x0C", "^L"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x0D", "^M"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x0E", "^N"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x0F", "^O"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x10", "^P"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x11", "^Q"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x12", "^R"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x13", "^S"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x14", "^T"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x15", "^U"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x16", "^V"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x17", "^W"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x18", "^X"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x19", "^Y"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x1A", "^Z"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x1B", "^["));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x1C", "^ "));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x1D", "^]"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x1E", "^^"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x1F", "^_"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x20", "SPACE"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x21", "!"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x22", ";"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x23", "#"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x24", "$"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x25", "%"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x26", ";"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x27", "\'"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x28", "("));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x29", ")"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x2A", "*"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x2B", "+"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x2C", ","));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x2D", "-"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x2E", "."));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x2F", "/"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x30", "0"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x31", "1"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x32", "2"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x33", "3"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x34", "4"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x35", "5"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x36", "6"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x37", "7"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x38", "8"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x39", "9"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x3A", ":"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x3B", ";"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x3C", "<"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x3D", "="));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x3E", ">"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x3F", "?"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x40", "@"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x41", "A"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x42", "B"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x43", "C"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x44", "D"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x45", "E"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x46", "F"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x47", "G"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x48", "H"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x49", "I"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x4A", "J"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x4B", "K"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x4C", "L"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x4D", "M"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x4E", "N"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x4F", "O"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x50", "P"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x51", "Q"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x52", "R"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x53", "S"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x54", "T"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x55", "U"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x56", "V"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x57", "W"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x58", "X"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x59", "Y"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x5A", "Z"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x5B", "["));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x5C", " "));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x5D", "]"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x5E", "^"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x5F", "_"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x60", "`"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x61", "a"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x62", "b"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x63", "c"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x64", "d"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x65", "e"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x66", "f"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x67", "g"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x68", "h"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x69", "i"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x6A", "j"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x6B", "k"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x6C", "l"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x6D", "m"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x6E", "n"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x6F", "o"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x70", "p"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x71", "q"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x72", "r"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x73", "s"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x74", "t"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x75", "u"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x76", "v"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x77", "w"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x78", "x"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x79", "y"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x7A", "z"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x7B", "{"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x7C", "|"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x7D", "}"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x7E", "~"));
        terminatorsList.add(new CommunityManagerKeyValueModel("0x7F", "DEL"));
        return terminatorsList;
    }

    public static String generateEnvelopeId(EnvelopeModel ediProperties, String segmentType) {
        Timestamp time = new java.sql.Timestamp(new java.util.Date().getTime());
        StringBuilder segStringBuilder = new StringBuilder();
        segStringBuilder.append(time);
        if ("ISA".equalsIgnoreCase(segmentType)) {
            segStringBuilder.append("_ISA_IEA_")
                    .append(ediProperties.getIsaSegment().getIsaSenderId()).append("_")
                    .append(ediProperties.getIsaSegment().getIsaReceiverId());
        } else if ("GS".equalsIgnoreCase(segmentType)) {
            segStringBuilder.append("_GS_GE_")
                    .append(ediProperties.getGsSegment().getGsSenderId()).append("_")
                    .append(ediProperties.getGsSegment().getGsReceiverId());
        } else if ("ST".equalsIgnoreCase(segmentType)) {
            segStringBuilder.append("_ST_SE_")
                    .append(ediProperties.getStSegment().getStSenderId()).append("_")
                    .append(ediProperties.getStSegment().getStReceiverId());
        }
        return segStringBuilder.toString();
    }

    public static String generateEnvelopeName(EnvelopeModel ediProperties, String segmentType) {
        StringBuilder segStringBuilder = new StringBuilder();
        if ("ISA".equalsIgnoreCase(segmentType)) {
            segStringBuilder.append("ISA_")
                    .append(ediProperties.getEdiProperties().getDirection()).append("_")
                    .append(ediProperties.getEdiProperties().getPartnerPkId()).append("_")
                    .append(ediProperties.getIsaSegment().getIsaSenderId()).append("_")
                    .append(ediProperties.getIsaSegment().getIsaReceiverId()).append("_")
                    .append(ediProperties.getStSegment().getTrnSetIdCode()).append("_")
                    .append(ediProperties.getStSegment().getAcceptLookAlias()).append("_")
                    .append(ediProperties.getGsSegment().getGroupVersion());
        } else if ("GS".equalsIgnoreCase(segmentType)) {
            segStringBuilder.append("GS_")
                    .append(ediProperties.getEdiProperties().getDirection()).append("_")
                    .append(ediProperties.getEdiProperties().getPartnerPkId()).append("_")
                    .append(ediProperties.getGsSegment().getGsSenderId()).append("_")
                    .append(ediProperties.getGsSegment().getGsReceiverId()).append("_")
                    .append(ediProperties.getStSegment().getTrnSetIdCode()).append("_")
                    .append(ediProperties.getStSegment().getAcceptLookAlias()).append("_")
                    .append(ediProperties.getGsSegment().getGroupVersion());
        } else if ("ST".equalsIgnoreCase(segmentType)) {
            segStringBuilder.append("ST_")
                    .append(ediProperties.getEdiProperties().getDirection()).append("_")
                    .append(ediProperties.getEdiProperties().getPartnerPkId()).append("_")
                    .append(ediProperties.getStSegment().getStSenderId()).append("_")
                    .append(ediProperties.getStSegment().getStReceiverId()).append("_")
                    .append(ediProperties.getStSegment().getTrnSetIdCode()).append("_")
                    .append(ediProperties.getStSegment().getAcceptLookAlias()).append("_")
                    .append(ediProperties.getGsSegment().getGroupVersion());
        }
        return segStringBuilder.toString();
    }

}
