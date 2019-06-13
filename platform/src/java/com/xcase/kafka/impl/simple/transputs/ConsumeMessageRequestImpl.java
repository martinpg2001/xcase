package com.xcase.kafka.impl.simple.transputs;

import com.xcase.kafka.transputs.ConsumeMessageRequest;

import java.util.Properties;

public class ConsumeMessageRequestImpl extends KafkaRequestImpl implements ConsumeMessageRequest {
    private Properties kafkaConsumerProperties;
    private String topic;
    
    @Override
    public Properties getKafkaConsumerProperties() {
        return kafkaConsumerProperties;
    }

    @Override
    public String getTopic() {
        return topic;
    }

    @Override
    public void setKafkaConsumerProperties(Properties kafkaConsumerProperties) {
        this.kafkaConsumerProperties = kafkaConsumerProperties;
    }

    @Override
    public void setTopic(String topic) {
        this.topic = topic;
    }

}
