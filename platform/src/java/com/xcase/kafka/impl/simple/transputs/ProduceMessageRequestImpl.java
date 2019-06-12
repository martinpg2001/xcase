package com.xcase.kafka.impl.simple.transputs;

import com.xcase.kafka.transputs.ProduceMessageRequest;

import java.util.Properties;

public class ProduceMessageRequestImpl extends KafkaRequestImpl implements ProduceMessageRequest {
    private Properties kafkaProducerProperties;
    private String key;
    private String topic;
    private String value;
    
    @Override
    public Properties getKafkaProducerProperties() {
        return kafkaProducerProperties;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public void setKafkaProducerProperties(Properties kafkaProducerProperties) {
        this.kafkaProducerProperties = kafkaProducerProperties;
    }

    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public void setTopic(String topic) {
        this.topic = topic;
    }

    @Override
    public void setValue(String value) {
        this.value = value;
    }

}
