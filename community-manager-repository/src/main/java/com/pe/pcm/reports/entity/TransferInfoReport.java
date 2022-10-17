package com.pe.pcm.reports.entity;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.sql.Timestamp;

@Entity
@Table(name = "PETPE_TRANSFERINFO_REPORT")
public class TransferInfoReport {

    @Id
    private Long seqid;

    private Timestamp filearrived;

    private String application;

    private String srcfilename;

    private String destfilename;

    private String partner;

    private String uidNumber;

    private Long fileSize;

    private String app;

    private String bu;

    private Long chargeback;

    private String pnode;

    private String snode;

    public Long getSeqid() {
        return seqid;
    }

    public void setSeqid(Long seqid) {
        this.seqid = seqid;
    }

    public Timestamp getFilearrived() {
        return filearrived;
    }

    public void setFilearrived(Timestamp filearrived) {
        this.filearrived = filearrived;
    }

    public String getApplication() {
        return application;
    }

    public void setApplication(String application) {
        this.application = application;
    }

    public String getSrcfilename() {
        return srcfilename;
    }

    public void setSrcfilename(String srcfilename) {
        this.srcfilename = srcfilename;
    }

    public String getDestfilename() {
        return destfilename;
    }

    public void setDestfilename(String destfilename) {
        this.destfilename = destfilename;
    }

    public String getPartner() {
        return partner;
    }

    public void setPartner(String partner) {
        this.partner = partner;
    }

    public String getUidNumber() {
        return uidNumber;
    }

    public void setUidNumber(String uidNumber) {
        this.uidNumber = uidNumber;
    }

    public Long getFileSize() {
        return fileSize;
    }

    public void setFileSize(Long fileSize) {
        this.fileSize = fileSize;
    }

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    public String getBu() {
        return bu;
    }

    public void setBu(String bu) {
        this.bu = bu;
    }

    public Long getChargeback() {
        return chargeback;
    }

    public void setChargeback(Long chargeback) {
        this.chargeback = chargeback;
    }

    public String getPnode() {
        return pnode;
    }

    public void setPnode(String pnode) {
        this.pnode = pnode;
    }

    public String getSnode() {
        return snode;
    }

    public void setSnode(String snode) {
        this.snode = snode;
    }

    @Override
    public String toString() {
        return "TransferInfoReport{" +
                "seqid=" + seqid +
                ", filearrived=" + filearrived +
                ", application='" + application + '\'' +
                ", srcfilename='" + srcfilename + '\'' +
                ", destfilename='" + destfilename + '\'' +
                ", partner='" + partner + '\'' +
                ", uidNumber='" + uidNumber + '\'' +
                ", fileSize=" + fileSize +
                ", app='" + app + '\'' +
                ", bu='" + bu + '\'' +
                ", chargeback=" + chargeback +
                ", pnode='" + pnode + '\'' +
                ", snode='" + snode + '\'' +
                '}';
    }
}
