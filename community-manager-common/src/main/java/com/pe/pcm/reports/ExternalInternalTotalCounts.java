package com.pe.pcm.reports;

import java.io.Serializable;

public class ExternalInternalTotalCounts implements Serializable {

    private String internalProducerCount;
    private String externalConsumerCount;
    private String externalProducerCount;
    private String internalConsumerCount;

    public String getInternalProducerCount() {
        return internalProducerCount;
    }

    public void setInternalProducerCount(String internalProducerCount) {
        this.internalProducerCount = internalProducerCount;
    }

    public String getExternalConsumerCount() {
        return externalConsumerCount;
    }

    public void setExternalConsumerCount(String externalConsumerCount) {
        this.externalConsumerCount = externalConsumerCount;
    }

    public String getExternalProducerCount() {
        return externalProducerCount;
    }

    public void setExternalProducerCount(String externalProducerCount) {
        this.externalProducerCount = externalProducerCount;
    }

    public String getInternalConsumerCount() {
        return internalConsumerCount;
    }

    public void setInternalConsumerCount(String internalConsumerCount) {
        this.internalConsumerCount = internalConsumerCount;
    }

    @Override
    public String toString() {
        return "ExternalInternalTotalCounts{" +
                "internalProducerCount='" + internalProducerCount + '\'' +
                ", externalConsumerCount='" + externalConsumerCount + '\'' +
                ", externalProducerCount='" + externalProducerCount + '\'' +
                ", internalConsumerCount='" + internalConsumerCount + '\'' +
                '}';
    }

    public ExternalInternalTotalCounts(String internalProducerCount, String externalConsumerCount, String externalProducerCount, String internalConsumerCount) {
        this.internalProducerCount = internalProducerCount;
        this.externalConsumerCount = externalConsumerCount;
        this.externalProducerCount = externalProducerCount;
        this.internalConsumerCount = internalConsumerCount;
    }

    public ExternalInternalTotalCounts() {
    }
}
