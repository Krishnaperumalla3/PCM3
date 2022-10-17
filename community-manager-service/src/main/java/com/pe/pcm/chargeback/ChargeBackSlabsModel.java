package com.pe.pcm.chargeback;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import java.io.Serializable;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ChargeBackSlabsModel implements Serializable{

    private Long slabId;
    private Double minCharge;
    private Double flat1;
    private Double rate110;
    private Double flat10;
    private Double rate1025;
    private Double flat25;
    private Double rateAbove25;
    private Double flatOther;
    private Double active;

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

    public ChargeBackSlabsModel() {
    }

    @Override
    public String toString() {
        return "ChargeBackSlabsModel{" +
                "slabId=" + slabId +
                ", minCharge=" + minCharge +
                ", flat1=" + flat1 +
                ", rate110=" + rate110 +
                ", flat10=" + flat10 +
                ", rate1025=" + rate1025 +
                ", flat25=" + flat25 +
                ", rateAbove25=" + rateAbove25 +
                ", flatOther=" + flatOther +
                ", active=" + active +
                '}';
    }

    public ChargeBackSlabsModel(Long slabId, Double minCharge, Double flat1, Double rate110, Double flat10, Double rate1025, Double flat25, Double rateAbove25, Double flatOther, Double active) {
        this.slabId = slabId;
        this.minCharge = minCharge;
        this.flat1 = flat1;
        this.rate110 = rate110;
        this.flat10 = flat10;
        this.rate1025 = rate1025;
        this.flat25 = flat25;
        this.rateAbove25 = rateAbove25;
        this.flatOther = flatOther;
        this.active = active;
    }
}
