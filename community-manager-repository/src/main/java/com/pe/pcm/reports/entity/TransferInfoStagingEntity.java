/*
 *
 *  * Copyright (c) 2020 Pragma Edge Inc
 *  *
 *  * Licensed under the Pragma Edge Inc
 *  * you may not use this file except in compliance with the License.
 *  * You may obtain a copy of the License at
 *  *
 *  * https://pragmaedge.com/licenseagreement
 *  *
 *  * Unless required by applicable law or agreed to in writing, software
 *  * distributed under the License is distributed on an "AS IS" BASIS,
 *  * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  * See the License for the specific language governing permissions and
 *  * limitations under the License.
 *
 *
 */

package com.pe.pcm.reports.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.sql.Timestamp;

/**
 * @author Chenchu Kiran Reddy.
 */
@Entity
@Table(name = "PETPE_TRANSFERINFO_STAGING")
public class TransferInfoStagingEntity implements Serializable {
    @Id
    private Long seqid;

    private Timestamp filearrived;

    private String flowinout;

    private String typeoftransfer;

    private String senderid;

    private String reciverid;

    private String application;

    private String appintstatus;

    private String partnerackstatus;

    private String srcprotocol;

    private String srcfilename;

    private String srcarcfileloc;

    private String destfilename;

    private String destarcfileloc;

    private String destprotocol;

    private String doctype;

    private String pickbpid;

    private String corebpid;

    private String deliverybpid;

    private String status;

    private String errorstatus;

    private String adverrorstatus;

    private String partner;

    private String doctrans;

    private String encType;

    @Column(name = "CORRELATION_VALUE_1")
    private String correlationValue1;

    @Column(name = "CORRELATION_VALUE_2")
    private String correlationValue2;

    @Column(name = "CORRELATION_VALUE_3")
    private String correlationValue3;

    @Column(name = "CORRELATION_VALUE_4")
    private String correlationValue4;

    @Column(name = "CORRELATION_VALUE_5")
    private String correlationValue5;

    @Column(name = "CORRELATION_VALUE_6")
    private String correlationValue6;

    @Column(name = "CORRELATION_VALUE_7")
    private String correlationValue7;

    @Column(name = "CORRELATION_VALUE_8")
    private String correlationValue8;

    @Column(name = "CORRELATION_VALUE_9")
    private String correlationValue9;

    @Column(name = "CORRELATION_VALUE_10")
    private String correlationValue10;

    @Column(name = "CORRELATION_VALUE_11")
    private String correlationValue11;

    @Column(name = "CORRELATION_VALUE_12")
    private String correlationValue12;

    @Column(name = "CORRELATION_VALUE_13")
    private String correlationValue13;

    @Column(name = "CORRELATION_VALUE_14")
    private String correlationValue14;

    @Column(name = "CORRELATION_VALUE_15")
    private String correlationValue15;

    @Column(name = "CORRELATION_VALUE_16")
    private String correlationValue16;

    @Column(name = "CORRELATION_VALUE_17")
    private String correlationValue17;

    @Column(name = "CORRELATION_VALUE_18")
    private String correlationValue18;

    @Column(name = "CORRELATION_VALUE_19")
    private String correlationValue19;

    @Column(name = "CORRELATION_VALUE_20")
    private String correlationValue20;

    @Column(name = "CORRELATION_VALUE_21")
    private String correlationValue21;

    @Column(name = "CORRELATION_VALUE_22")
    private String correlationValue22;

    @Column(name = "CORRELATION_VALUE_23")
    private String correlationValue23;

    @Column(name = "CORRELATION_VALUE_24")
    private String correlationValue24;

    @Column(name = "CORRELATION_VALUE_25")
    private String correlationValue25;

    @Column(name = "CORRELATION_VALUE_26")
    private String correlationValue26;

    @Column(name = "CORRELATION_VALUE_27")
    private String correlationValue27;

    @Column(name = "CORRELATION_VALUE_28")
    private String correlationValue28;

    @Column(name = "CORRELATION_VALUE_29")
    private String correlationValue29;

    @Column(name = "CORRELATION_VALUE_30")
    private String correlationValue30;

    @Column(name = "CORRELATION_VALUE_31")
    private String correlationValue31;

    @Column(name = "CORRELATION_VALUE_32")
    private String correlationValue32;

    @Column(name = "CORRELATION_VALUE_33")
    private String correlationValue33;

    @Column(name = "CORRELATION_VALUE_34")
    private String correlationValue34;

    @Column(name = "CORRELATION_VALUE_35")
    private String correlationValue35;

    @Column(name = "CORRELATION_VALUE_36")
    private String correlationValue36;

    @Column(name = "CORRELATION_VALUE_37")
    private String correlationValue37;

    @Column(name = "CORRELATION_VALUE_38")
    private String correlationValue38;

    @Column(name = "CORRELATION_VALUE_39")
    private String correlationValue39;

    @Column(name = "CORRELATION_VALUE_40")
    private String correlationValue40;

    @Column(name = "CORRELATION_VALUE_41")
    private String correlationVaule41;

    @Column(name = "CORRELATION_VALUE_42")
    private String correlationVaule42;

    @Column(name = "CORRELATION_VALUE_43")
    private String correlationVaule43;

    @Column(name = "CORRELATION_VALUE_44")
    private String correlationVaule44;

    @Column(name = "CORRELATION_VALUE_45")
    private String correlationVaule45;

    @Column(name = "CORRELATION_VALUE_46")
    private String correlationVaule46;

    @Column(name = "CORRELATION_VALUE_47")
    private String correlationValue47;

    @Column(name = "CORRELATION_VALUE_48")
    private String correlationValue48;

    @Column(name = "CORRELATION_VALUE_49")
    private String correlationValue49;

    @Column(name = "CORRELATION_VALUE_50")
    private String correlationValue50;

    private String transfile;

    private String statusComments;

    private String isEncrypted;

    public Long getSeqid() {
        return seqid;
    }

    public TransferInfoStagingEntity setSeqid(Long seqid) {
        this.seqid = seqid;
        return this;
    }

    public Timestamp getFilearrived() {
        return filearrived;
    }

    public TransferInfoStagingEntity setFilearrived(Timestamp filearrived) {
        this.filearrived = filearrived;
        return this;
    }

    public String getFlowinout() {
        return flowinout;
    }

    public TransferInfoStagingEntity setFlowinout(String flowinout) {
        this.flowinout = flowinout;
        return this;
    }

    public String getTypeoftransfer() {
        return typeoftransfer;
    }

    public TransferInfoStagingEntity setTypeoftransfer(String typeoftransfer) {
        this.typeoftransfer = typeoftransfer;
        return this;
    }

    public String getSenderid() {
        return senderid;
    }

    public TransferInfoStagingEntity setSenderid(String senderid) {
        this.senderid = senderid;
        return this;
    }

    public String getReciverid() {
        return reciverid;
    }

    public TransferInfoStagingEntity setReciverid(String reciverid) {
        this.reciverid = reciverid;
        return this;
    }

    public String getApplication() {
        return application;
    }

    public TransferInfoStagingEntity setApplication(String application) {
        this.application = application;
        return this;
    }

    public String getAppintstatus() {
        return appintstatus;
    }

    public TransferInfoStagingEntity setAppintstatus(String appintstatus) {
        this.appintstatus = appintstatus;
        return this;
    }

    public String getPartnerackstatus() {
        return partnerackstatus;
    }

    public TransferInfoStagingEntity setPartnerackstatus(String partnerackstatus) {
        this.partnerackstatus = partnerackstatus;
        return this;
    }

    public String getSrcprotocol() {
        return srcprotocol;
    }

    public TransferInfoStagingEntity setSrcprotocol(String srcprotocol) {
        this.srcprotocol = srcprotocol;
        return this;
    }

    public String getSrcfilename() {
        return srcfilename;
    }

    public TransferInfoStagingEntity setSrcfilename(String srcfilename) {
        this.srcfilename = srcfilename;
        return this;
    }

    public String getSrcarcfileloc() {
        return srcarcfileloc;
    }

    public TransferInfoStagingEntity setSrcarcfileloc(String srcarcfileloc) {
        this.srcarcfileloc = srcarcfileloc;
        return this;
    }

    public String getDestfilename() {
        return destfilename;
    }

    public TransferInfoStagingEntity setDestfilename(String destfilename) {
        this.destfilename = destfilename;
        return this;
    }

    public String getDestarcfileloc() {
        return destarcfileloc;
    }

    public TransferInfoStagingEntity setDestarcfileloc(String destarcfileloc) {
        this.destarcfileloc = destarcfileloc;
        return this;
    }

    public String getDestprotocol() {
        return destprotocol;
    }

    public TransferInfoStagingEntity setDestprotocol(String destprotocol) {
        this.destprotocol = destprotocol;
        return this;
    }

    public String getDoctype() {
        return doctype;
    }

    public TransferInfoStagingEntity setDoctype(String doctype) {
        this.doctype = doctype;
        return this;
    }

    public String getPickbpid() {
        return pickbpid;
    }

    public TransferInfoStagingEntity setPickbpid(String pickbpid) {
        this.pickbpid = pickbpid;
        return this;
    }

    public String getCorebpid() {
        return corebpid;
    }

    public TransferInfoStagingEntity setCorebpid(String corebpid) {
        this.corebpid = corebpid;
        return this;
    }

    public String getDeliverybpid() {
        return deliverybpid;
    }

    public TransferInfoStagingEntity setDeliverybpid(String deliverybpid) {
        this.deliverybpid = deliverybpid;
        return this;
    }

    public String getStatus() {
        return status;
    }

    public TransferInfoStagingEntity setStatus(String status) {
        this.status = status;
        return this;
    }

    public String getErrorstatus() {
        return errorstatus;
    }

    public TransferInfoStagingEntity setErrorstatus(String errorstatus) {
        this.errorstatus = errorstatus;
        return this;
    }

    public String getAdverrorstatus() {
        return adverrorstatus;
    }

    public TransferInfoStagingEntity setAdverrorstatus(String adverrorstatus) {
        this.adverrorstatus = adverrorstatus;
        return this;
    }

    public String getPartner() {
        return partner;
    }

    public TransferInfoStagingEntity setPartner(String partner) {
        this.partner = partner;
        return this;
    }

    public String getDoctrans() {
        return doctrans;
    }

    public TransferInfoStagingEntity setDoctrans(String doctrans) {
        this.doctrans = doctrans;
        return this;
    }

    public String getEncType() {
        return encType;
    }

    public TransferInfoStagingEntity setEncType(String encType) {
        this.encType = encType;
        return this;
    }

    public String getCorrelationValue1() {
        return correlationValue1;
    }

    public TransferInfoStagingEntity setCorrelationValue1(String correlationValue1) {
        this.correlationValue1 = correlationValue1;
        return this;
    }

    public String getCorrelationValue2() {
        return correlationValue2;
    }

    public TransferInfoStagingEntity setCorrelationValue2(String correlationValue2) {
        this.correlationValue2 = correlationValue2;
        return this;
    }

    public String getCorrelationValue3() {
        return correlationValue3;
    }

    public TransferInfoStagingEntity setCorrelationValue3(String correlationValue3) {
        this.correlationValue3 = correlationValue3;
        return this;
    }

    public String getCorrelationValue4() {
        return correlationValue4;
    }

    public TransferInfoStagingEntity setCorrelationValue4(String correlationValue4) {
        this.correlationValue4 = correlationValue4;
        return this;
    }

    public String getCorrelationValue5() {
        return correlationValue5;
    }

    public TransferInfoStagingEntity setCorrelationValue5(String correlationValue5) {
        this.correlationValue5 = correlationValue5;
        return this;
    }

    public String getCorrelationValue6() {
        return correlationValue6;
    }

    public TransferInfoStagingEntity setCorrelationValue6(String correlationValue6) {
        this.correlationValue6 = correlationValue6;
        return this;
    }

    public String getCorrelationValue7() {
        return correlationValue7;
    }

    public TransferInfoStagingEntity setCorrelationValue7(String correlationValue7) {
        this.correlationValue7 = correlationValue7;
        return this;
    }

    public String getCorrelationValue8() {
        return correlationValue8;
    }

    public TransferInfoStagingEntity setCorrelationValue8(String correlationValue8) {
        this.correlationValue8 = correlationValue8;
        return this;
    }

    public String getCorrelationValue9() {
        return correlationValue9;
    }

    public TransferInfoStagingEntity setCorrelationValue9(String correlationValue9) {
        this.correlationValue9 = correlationValue9;
        return this;
    }

    public String getCorrelationValue10() {
        return correlationValue10;
    }

    public TransferInfoStagingEntity setCorrelationValue10(String correlationValue10) {
        this.correlationValue10 = correlationValue10;
        return this;
    }

    public String getCorrelationValue11() {
        return correlationValue11;
    }

    public TransferInfoStagingEntity setCorrelationValue11(String correlationValue11) {
        this.correlationValue11 = correlationValue11;
        return this;
    }

    public String getCorrelationValue12() {
        return correlationValue12;
    }

    public TransferInfoStagingEntity setCorrelationValue12(String correlationValue12) {
        this.correlationValue12 = correlationValue12;
        return this;
    }

    public String getCorrelationValue13() {
        return correlationValue13;
    }

    public TransferInfoStagingEntity setCorrelationValue13(String correlationValue13) {
        this.correlationValue13 = correlationValue13;
        return this;
    }

    public String getCorrelationValue14() {
        return correlationValue14;
    }

    public TransferInfoStagingEntity setCorrelationValue14(String correlationValue14) {
        this.correlationValue14 = correlationValue14;
        return this;
    }

    public String getCorrelationValue15() {
        return correlationValue15;
    }

    public TransferInfoStagingEntity setCorrelationValue15(String correlationValue15) {
        this.correlationValue15 = correlationValue15;
        return this;
    }

    public String getCorrelationValue16() {
        return correlationValue16;
    }

    public TransferInfoStagingEntity setCorrelationValue16(String correlationValue16) {
        this.correlationValue16 = correlationValue16;
        return this;
    }

    public String getCorrelationValue17() {
        return correlationValue17;
    }

    public TransferInfoStagingEntity setCorrelationValue17(String correlationValue17) {
        this.correlationValue17 = correlationValue17;
        return this;
    }

    public String getCorrelationValue18() {
        return correlationValue18;
    }

    public TransferInfoStagingEntity setCorrelationValue18(String correlationValue18) {
        this.correlationValue18 = correlationValue18;
        return this;
    }

    public String getCorrelationValue19() {
        return correlationValue19;
    }

    public TransferInfoStagingEntity setCorrelationValue19(String correlationValue19) {
        this.correlationValue19 = correlationValue19;
        return this;
    }

    public String getCorrelationValue20() {
        return correlationValue20;
    }

    public TransferInfoStagingEntity setCorrelationValue20(String correlationValue20) {
        this.correlationValue20 = correlationValue20;
        return this;
    }

    public String getCorrelationValue21() {
        return correlationValue21;
    }

    public TransferInfoStagingEntity setCorrelationValue21(String correlationValue21) {
        this.correlationValue21 = correlationValue21;
        return this;
    }

    public String getCorrelationValue22() {
        return correlationValue22;
    }

    public TransferInfoStagingEntity setCorrelationValue22(String correlationValue22) {
        this.correlationValue22 = correlationValue22;
        return this;
    }

    public String getCorrelationValue23() {
        return correlationValue23;
    }

    public TransferInfoStagingEntity setCorrelationValue23(String correlationValue23) {
        this.correlationValue23 = correlationValue23;
        return this;
    }

    public String getCorrelationValue24() {
        return correlationValue24;
    }

    public TransferInfoStagingEntity setCorrelationValue24(String correlationValue24) {
        this.correlationValue24 = correlationValue24;
        return this;
    }

    public String getCorrelationValue25() {
        return correlationValue25;
    }

    public TransferInfoStagingEntity setCorrelationValue25(String correlationValue25) {
        this.correlationValue25 = correlationValue25;
        return this;
    }

    public String getCorrelationValue26() {
        return correlationValue26;
    }

    public TransferInfoStagingEntity setCorrelationValue26(String correlationValue26) {
        this.correlationValue26 = correlationValue26;
        return this;
    }

    public String getCorrelationValue27() {
        return correlationValue27;
    }

    public TransferInfoStagingEntity setCorrelationValue27(String correlationValue27) {
        this.correlationValue27 = correlationValue27;
        return this;
    }

    public String getCorrelationValue28() {
        return correlationValue28;
    }

    public TransferInfoStagingEntity setCorrelationValue28(String correlationValue28) {
        this.correlationValue28 = correlationValue28;
        return this;
    }

    public String getCorrelationValue29() {
        return correlationValue29;
    }

    public TransferInfoStagingEntity setCorrelationValue29(String correlationValue29) {
        this.correlationValue29 = correlationValue29;
        return this;
    }

    public String getCorrelationValue30() {
        return correlationValue30;
    }

    public TransferInfoStagingEntity setCorrelationValue30(String correlationValue30) {
        this.correlationValue30 = correlationValue30;
        return this;
    }

    public String getCorrelationValue31() {
        return correlationValue31;
    }

    public TransferInfoStagingEntity setCorrelationValue31(String correlationValue31) {
        this.correlationValue31 = correlationValue31;
        return this;
    }

    public String getCorrelationValue32() {
        return correlationValue32;
    }

    public TransferInfoStagingEntity setCorrelationValue32(String correlationValue32) {
        this.correlationValue32 = correlationValue32;
        return this;
    }

    public String getCorrelationValue33() {
        return correlationValue33;
    }

    public TransferInfoStagingEntity setCorrelationValue33(String correlationValue33) {
        this.correlationValue33 = correlationValue33;
        return this;
    }

    public String getCorrelationValue34() {
        return correlationValue34;
    }

    public TransferInfoStagingEntity setCorrelationValue34(String correlationValue34) {
        this.correlationValue34 = correlationValue34;
        return this;
    }

    public String getCorrelationValue35() {
        return correlationValue35;
    }

    public TransferInfoStagingEntity setCorrelationValue35(String correlationValue35) {
        this.correlationValue35 = correlationValue35;
        return this;
    }

    public String getCorrelationValue36() {
        return correlationValue36;
    }

    public TransferInfoStagingEntity setCorrelationValue36(String correlationValue36) {
        this.correlationValue36 = correlationValue36;
        return this;
    }

    public String getCorrelationValue37() {
        return correlationValue37;
    }

    public TransferInfoStagingEntity setCorrelationValue37(String correlationValue37) {
        this.correlationValue37 = correlationValue37;
        return this;
    }

    public String getCorrelationValue38() {
        return correlationValue38;
    }

    public TransferInfoStagingEntity setCorrelationValue38(String correlationValue38) {
        this.correlationValue38 = correlationValue38;
        return this;
    }

    public String getCorrelationValue39() {
        return correlationValue39;
    }

    public TransferInfoStagingEntity setCorrelationValue39(String correlationValue39) {
        this.correlationValue39 = correlationValue39;
        return this;
    }

    public String getCorrelationValue40() {
        return correlationValue40;
    }

    public TransferInfoStagingEntity setCorrelationValue40(String correlationValue40) {
        this.correlationValue40 = correlationValue40;
        return this;
    }

    public String getCorrelationVaule41() {
        return correlationVaule41;
    }

    public TransferInfoStagingEntity setCorrelationVaule41(String correlationVaule41) {
        this.correlationVaule41 = correlationVaule41;
        return this;
    }

    public String getCorrelationVaule42() {
        return correlationVaule42;
    }

    public TransferInfoStagingEntity setCorrelationVaule42(String correlationVaule42) {
        this.correlationVaule42 = correlationVaule42;
        return this;
    }

    public String getCorrelationVaule43() {
        return correlationVaule43;
    }

    public TransferInfoStagingEntity setCorrelationVaule43(String correlationVaule43) {
        this.correlationVaule43 = correlationVaule43;
        return this;
    }

    public String getCorrelationVaule44() {
        return correlationVaule44;
    }

    public TransferInfoStagingEntity setCorrelationVaule44(String correlationVaule44) {
        this.correlationVaule44 = correlationVaule44;
        return this;
    }

    public String getCorrelationVaule45() {
        return correlationVaule45;
    }

    public TransferInfoStagingEntity setCorrelationVaule45(String correlationVaule45) {
        this.correlationVaule45 = correlationVaule45;
        return this;
    }

    public String getCorrelationVaule46() {
        return correlationVaule46;
    }

    public TransferInfoStagingEntity setCorrelationVaule46(String correlationVaule46) {
        this.correlationVaule46 = correlationVaule46;
        return this;
    }

    public String getCorrelationValue47() {
        return correlationValue47;
    }

    public TransferInfoStagingEntity setCorrelationValue47(String correlationValue47) {
        this.correlationValue47 = correlationValue47;
        return this;
    }

    public String getCorrelationValue48() {
        return correlationValue48;
    }

    public TransferInfoStagingEntity setCorrelationValue48(String correlationValue48) {
        this.correlationValue48 = correlationValue48;
        return this;
    }

    public String getCorrelationValue49() {
        return correlationValue49;
    }

    public TransferInfoStagingEntity setCorrelationValue49(String correlationValue49) {
        this.correlationValue49 = correlationValue49;
        return this;
    }

    public String getCorrelationValue50() {
        return correlationValue50;
    }

    public TransferInfoStagingEntity setCorrelationValue50(String correlationValue50) {
        this.correlationValue50 = correlationValue50;
        return this;
    }

    public String getTransfile() {
        return transfile;
    }

    public TransferInfoStagingEntity setTransfile(String transfile) {
        this.transfile = transfile;
        return this;
    }

    public String getStatusComments() {
        return statusComments;
    }

    public TransferInfoStagingEntity setStatusComments(String statusComments) {
        this.statusComments = statusComments;
        return this;
    }

    public String getIsEncrypted() {
        return isEncrypted;
    }

    public TransferInfoStagingEntity setIsEncrypted(String isEncrypted) {
        this.isEncrypted = isEncrypted;
        return this;
    }
}
