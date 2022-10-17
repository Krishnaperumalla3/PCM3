package com.pe.pcm.reports;

public class DataProducerConsumerFilecountModel {


    private String consumer;
    private String producer;
    private String count;

    public String getConsumer() {
        return consumer;
    }

    public void setConsumer(String consumer) {
        this.consumer = consumer;
    }

    public String getProducer() {
        return producer;
    }

    public void setProducer(String producer) {
        this.producer = producer;
    }

    public String getCount() {
        return count;
    }

    public void setCount(String count) {
        this.count = count;
    }

    @Override
    public String toString() {
        return "DataProducerConsumerFilecountModel{" +
                "consumer='" + consumer + '\'' +
                ", producer='" + producer + '\'' +
                ", count='" + count + '\'' +
                '}';
    }

    public DataProducerConsumerFilecountModel(String consumer, String producer, String count) {
        this.consumer = consumer;
        this.producer = producer;
        this.count = count;

    }


}
