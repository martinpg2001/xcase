package com.xcase.kafka.transputs;

import com.xcase.kafka.transputs.KafkaRequest;
import java.util.Properties;

public interface ConsumeMessageRequest extends KafkaRequest {

    Properties getKafkaConsumerProperties();
    
    String getTopic();
    
    void setKafkaConsumerProperties(Properties kafkaConsumerProperties);

    void setTopic(String topic);

}
