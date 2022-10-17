package com.pe.pcm.reports.entity;


import javax.persistence.*;
import java.io.Serializable;
import java.sql.Timestamp;

@Entity
@Table(name = "PETPE_CHARGEBACK_SLABS")
public class PetpeChargebackSlabs implements Serializable  {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "slab_id_generator")
    @SequenceGenerator(name = "slab_id_generator", allocationSize = 1, sequenceName = "CHARGEBACK_SLABS_SLAB_ID")
    @Column(name = "SLAB_ID")
    private Long slabId;

    @Column(name = "MIN_CHARGE")
    private Double minCharge;

    @Column(name = "FLAT_1")
    private Double flat1;

    @Column(name = "RATE_1_10")
    private Double rate110;

    @Column(name = "FLAT_10")
    private Double flat10;

    @Column(name = "RATE_10_25")
    private Double rate1025;

    @Column(name = "FLAT_25")
    private Double flat25;

    @Column(name = "RATE_ABOVE_25")
    private Double rateAbove25;

    @Column(name = "FLAT_OTHER")
    private Double flatOther;

    @Column(name = "ACTIVE")
    private Double active;

    @Column(name = "CREATE_DATE")
    private Timestamp createDate;

    @Column(name = "LASTUPDATE_DATE")
    private Timestamp lastupdateDate;

    public PetpeChargebackSlabs() {

    }

    public Long getSlabId() {
        return slabId;
    }

    public void setSlabId(Long slabId) {
        this.slabId = slabId;
    }

    public Double getMinCharge() {
        return minCharge;
    }

    public void setMinCharge(Double minCharge) {
        this.minCharge = minCharge;
    }

    public Double getFlat1() {
        return flat1;
    }

    public void setFlat1(Double flat1) {
        this.flat1 = flat1;
    }

    public Double getRate110() {
        return rate110;
    }

    public void setRate110(Double rate110) {
        this.rate110 = rate110;
    }

    public Double getFlat10() {
        return flat10;
    }

    public void setFlat10(Double flat10) {
        this.flat10 = flat10;
    }

    public Double getRate1025() {
        return rate1025;
    }

    public void setRate1025(Double rate1025) {
        this.rate1025 = rate1025;
    }

    public Double getFlat25() {
        return flat25;
    }

    public void setFlat25(Double flat25) {
        this.flat25 = flat25;
    }

    public Double getRateAbove25() {
        return rateAbove25;
    }

    public void setRateAbove25(Double rateAbove25) {
        this.rateAbove25 = rateAbove25;
    }

    public Double getFlatOther() {
        return flatOther;
    }

    public void setFlatOther(Double flatOther) {
        this.flatOther = flatOther;
    }

    public Double getActive() {
        return active;
    }

    public void setActive(Double active) {
        this.active = active;
    }

    public Timestamp getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Timestamp createDate) {
        this.createDate = createDate;
    }

    public Timestamp getLastupdateDate() {
        return lastupdateDate;
    }

    public void setLastupdateDate(Timestamp lastupdateDate) {
        this.lastupdateDate = lastupdateDate;
    }


    @Override
    public String toString() {
        return "PetpeChargebackSlabs{" +
                "slabId=" + slabId +
                ", minCharge=" + minCharge +
                ", flat1=" + flat1 +
                ", rate110=" + rate110 +
                ", flat10=" + flat10 +
                ", flat25=" + flat25 +
                ", rateAbove25=" + rateAbove25 +
                ", flatOther=" + flatOther +
                ", active=" + active +
                ", createDate=" + createDate +
                ", lastupdateDate=" + lastupdateDate +
                '}';
    }
}
