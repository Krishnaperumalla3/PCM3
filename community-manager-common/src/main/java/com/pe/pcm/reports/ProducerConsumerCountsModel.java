package com.pe.pcm.reports;

public class ProducerConsumerCountsModel {

    String totalProducerCount;
    String totalConsumerCount;

    public String getTotalProducerCount() {
        return totalProducerCount;
    }

    public void setTotalProducerCount(String totalProducerCount) {
        this.totalProducerCount = totalProducerCount;
    }

    public String getTotalConsumerCount() {
        return totalConsumerCount;
    }

    public void setTotalConsumerCount(String totalConsumerCount) {
        this.totalConsumerCount = totalConsumerCount;
    }

    public ProducerConsumerCountsModel(String totalProducerCount, String totalConsumerCount) {
        this.totalProducerCount = totalProducerCount;
        this.totalConsumerCount = totalConsumerCount;
    }

    @Override
    public String toString() {
        return "ProducerConsumerCountsModel{" +
                "totalProducerCount='" + totalProducerCount + '\'' +
                ", totalConsumerCount='" + totalConsumerCount + '\'' +
                '}';
    }
}
