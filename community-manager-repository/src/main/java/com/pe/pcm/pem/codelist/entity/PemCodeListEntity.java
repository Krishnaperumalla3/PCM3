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

package com.pe.pcm.pem.codelist.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.StringJoiner;

@Entity
@Table(name = "PETPE_PEM_CODE_LIST")
public class PemCodeListEntity implements Serializable {

    @Id
    private String pkId;
    private String profileName;
    private String protocol;

    @Column(name = "correlation_value_1")
    private String correlationValue1;

    @Column(name = "correlation_value_2")
    private String correlationValue2;

    @Column(name = "correlation_value_3")
    private String correlationValue3;

    @Column(name = "correlation_value_4")
    private String correlationValue4;

    @Column(name = "correlation_value_5")
    private String correlationValue5;

    @Column(name = "correlation_value_6")
    private String correlationValue6;

    @Column(name = "correlation_value_7")
    private String correlationValue7;

    @Column(name = "correlation_value_8")
    private String correlationValue8;

    @Column(name = "correlation_value_9")
    private String correlationValue9;

    @Column(name = "correlation_value_10")
    private String correlationValue10;

    @Column(name = "correlation_value_11")
    private String correlationValue11;

    @Column(name = "correlation_value_12")
    private String correlationValue12;

    @Column(name = "correlation_value_13")
    private String correlationValue13;

    @Column(name = "correlation_value_14")
    private String correlationValue14;

    @Column(name = "correlation_value_15")
    private String correlationValue15;

    @Column(name = "correlation_value_16")
    private String correlationValue16;

    @Column(name = "correlation_value_17")
    private String correlationValue17;

    @Column(name = "correlation_value_18")
    private String correlationValue18;

    @Column(name = "correlation_value_19")
    private String correlationValue19;

    @Column(name = "correlation_value_20")
    private String correlationValue20;

    @Column(name = "correlation_value_21")
    private String correlationValue21;

    @Column(name = "correlation_value_22")
    private String correlationValue22;

    @Column(name = "correlation_value_23")
    private String correlationValue23;

    @Column(name = "correlation_value_24")
    private String correlationValue24;

    @Column(name = "correlation_value_25")
    private String correlationValue25;

    public String getPkId() {
        return pkId;
    }

    public PemCodeListEntity setPkId(String pkId) {
        this.pkId = pkId;
        return this;
    }

    public String getProfileName() {
        return profileName;
    }

    public PemCodeListEntity setProfileName(String profileName) {
        this.profileName = profileName;
        return this;
    }

    public String getProtocol() {
        return protocol;
    }

    public PemCodeListEntity setProtocol(String protocol) {
        this.protocol = protocol;
        return this;
    }

    public String getCorrelationValue1() {
        return correlationValue1;
    }

    public PemCodeListEntity setCorrelationValue1(String correlationValue1) {
        this.correlationValue1 = correlationValue1;
        return this;
    }

    public String getCorrelationValue2() {
        return correlationValue2;
    }

    public PemCodeListEntity setCorrelationValue2(String correlationValue2) {
        this.correlationValue2 = correlationValue2;
        return this;
    }

    public String getCorrelationValue3() {
        return correlationValue3;
    }

    public PemCodeListEntity setCorrelationValue3(String correlationValue3) {
        this.correlationValue3 = correlationValue3;
        return this;
    }

    public String getCorrelationValue4() {
        return correlationValue4;
    }

    public PemCodeListEntity setCorrelationValue4(String correlationValue4) {
        this.correlationValue4 = correlationValue4;
        return this;
    }

    public String getCorrelationValue5() {
        return correlationValue5;
    }

    public PemCodeListEntity setCorrelationValue5(String correlationValue5) {
        this.correlationValue5 = correlationValue5;
        return this;
    }

    public String getCorrelationValue6() {
        return correlationValue6;
    }

    public PemCodeListEntity setCorrelationValue6(String correlationValue6) {
        this.correlationValue6 = correlationValue6;
        return this;
    }

    public String getCorrelationValue7() {
        return correlationValue7;
    }

    public PemCodeListEntity setCorrelationValue7(String correlationValue7) {
        this.correlationValue7 = correlationValue7;
        return this;
    }

    public String getCorrelationValue8() {
        return correlationValue8;
    }

    public PemCodeListEntity setCorrelationValue8(String correlationValue8) {
        this.correlationValue8 = correlationValue8;
        return this;
    }

    public String getCorrelationValue9() {
        return correlationValue9;
    }

    public PemCodeListEntity setCorrelationValue9(String correlationValue9) {
        this.correlationValue9 = correlationValue9;
        return this;
    }

    public String getCorrelationValue10() {
        return correlationValue10;
    }

    public PemCodeListEntity setCorrelationValue10(String correlationValue10) {
        this.correlationValue10 = correlationValue10;
        return this;
    }

    public String getCorrelationValue11() {
        return correlationValue11;
    }

    public PemCodeListEntity setCorrelationValue11(String correlationValue11) {
        this.correlationValue11 = correlationValue11;
        return this;
    }

    public String getCorrelationValue12() {
        return correlationValue12;
    }

    public PemCodeListEntity setCorrelationValue12(String correlationValue12) {
        this.correlationValue12 = correlationValue12;
        return this;
    }

    public String getCorrelationValue13() {
        return correlationValue13;
    }

    public PemCodeListEntity setCorrelationValue13(String correlationValue13) {
        this.correlationValue13 = correlationValue13;
        return this;
    }

    public String getCorrelationValue14() {
        return correlationValue14;
    }

    public PemCodeListEntity setCorrelationValue14(String correlationValue14) {
        this.correlationValue14 = correlationValue14;
        return this;
    }

    public String getCorrelationValue15() {
        return correlationValue15;
    }

    public PemCodeListEntity setCorrelationValue15(String correlationValue15) {
        this.correlationValue15 = correlationValue15;
        return this;
    }

    public String getCorrelationValue16() {
        return correlationValue16;
    }

    public PemCodeListEntity setCorrelationValue16(String correlationValue16) {
        this.correlationValue16 = correlationValue16;
        return this;
    }

    public String getCorrelationValue17() {
        return correlationValue17;
    }

    public PemCodeListEntity setCorrelationValue17(String correlationValue17) {
        this.correlationValue17 = correlationValue17;
        return this;
    }

    public String getCorrelationValue18() {
        return correlationValue18;
    }

    public PemCodeListEntity setCorrelationValue18(String correlationValue18) {
        this.correlationValue18 = correlationValue18;
        return this;
    }

    public String getCorrelationValue19() {
        return correlationValue19;
    }

    public PemCodeListEntity setCorrelationValue19(String correlationValue19) {
        this.correlationValue19 = correlationValue19;
        return this;
    }

    public String getCorrelationValue20() {
        return correlationValue20;
    }

    public PemCodeListEntity setCorrelationValue20(String correlationValue20) {
        this.correlationValue20 = correlationValue20;
        return this;
    }

    public String getCorrelationValue21() {
        return correlationValue21;
    }

    public PemCodeListEntity setCorrelationValue21(String correlationValue21) {
        this.correlationValue21 = correlationValue21;
        return this;
    }

    public String getCorrelationValue22() {
        return correlationValue22;
    }

    public PemCodeListEntity setCorrelationValue22(String correlationValue22) {
        this.correlationValue22 = correlationValue22;
        return this;
    }

    public String getCorrelationValue23() {
        return correlationValue23;
    }

    public PemCodeListEntity setCorrelationValue23(String correlationValue23) {
        this.correlationValue23 = correlationValue23;
        return this;
    }

    public String getCorrelationValue24() {
        return correlationValue24;
    }

    public PemCodeListEntity setCorrelationValue24(String correlationValue24) {
        this.correlationValue24 = correlationValue24;
        return this;
    }

    public String getCorrelationValue25() {
        return correlationValue25;
    }

    public PemCodeListEntity setCorrelationValue25(String correlationValue25) {
        this.correlationValue25 = correlationValue25;
        return this;
    }

    @Override
    public String toString() {
        return new StringJoiner(", ", PemCodeListEntity.class.getSimpleName() + "[", "]")
                .add("pkId='" + pkId + "'")
                .add("profileName='" + profileName + "'")
                .add("protocol='" + protocol + "'")
                .add("correlationValue1='" + correlationValue1 + "'")
                .add("correlationValue2='" + correlationValue2 + "'")
                .add("correlationValue3='" + correlationValue3 + "'")
                .add("correlationValue4='" + correlationValue4 + "'")
                .add("correlationValue5='" + correlationValue5 + "'")
                .add("correlationValue6='" + correlationValue6 + "'")
                .add("correlationValue7='" + correlationValue7 + "'")
                .add("correlationValue8='" + correlationValue8 + "'")
                .add("correlationValue9='" + correlationValue9 + "'")
                .add("correlationValue10='" + correlationValue10 + "'")
                .add("correlationValue11='" + correlationValue11 + "'")
                .add("correlationValue12='" + correlationValue12 + "'")
                .add("correlationValue13='" + correlationValue13 + "'")
                .add("correlationValue14='" + correlationValue14 + "'")
                .add("correlationValue15='" + correlationValue15 + "'")
                .add("correlationValue16='" + correlationValue16 + "'")
                .add("correlationValue17='" + correlationValue17 + "'")
                .add("correlationValue18='" + correlationValue18 + "'")
                .add("correlationValue19='" + correlationValue19 + "'")
                .add("correlationValue20='" + correlationValue20 + "'")
                .add("correlationValue21='" + correlationValue21 + "'")
                .add("correlationValue22='" + correlationValue22 + "'")
                .add("correlationValue23='" + correlationValue23 + "'")
                .add("correlationValue24='" + correlationValue24 + "'")
                .add("correlationValue25='" + correlationValue25 + "'")
                .toString();
    }
}
