package com.xcase.kafka.transputs;

import java.util.Properties;

public interface ProduceMessageRequest extends KafkaRequest {

    Properties getKafkaProducerProperties();
    
    String getKey();
    
    String getTopic();
    
    String getValue();
    
    void setKafkaProducerProperties(Properties kafkaProducerProperties);
    
    void setKey(String key);

    void setTopic(String topic);

    void setValue(String value);

}
