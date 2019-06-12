package com.xcase.kafka;

import com.xcase.kafka.factories.KafkaRequestFactory;
import com.xcase.kafka.transputs.ProduceMessageRequest;
import com.xcase.kafka.transputs.ProduceMessageResponse;
import java.lang.invoke.MethodHandles;
import java.util.Date;
import java.util.Properties;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KafkaApplication {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        try {
            KafkaExternalAPI kafkaExternalAPI = new SimpleKafkaImpl();
            Properties kafkaProducerProperties = new Properties();
            kafkaProducerProperties.put("bootstrap.servers", "localhost:9092");
            kafkaProducerProperties.put("acks", "all");
            kafkaProducerProperties.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            kafkaProducerProperties.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            ProduceMessageRequest produceMessageRequest = KafkaRequestFactory.createProduceMessageRequest();
            produceMessageRequest.setKafkaProducerProperties(kafkaProducerProperties);
            produceMessageRequest.setTopic("test");
            produceMessageRequest.setKey("messageKey");
            produceMessageRequest.setValue("This is another message with a value based on the current time: " + new Date().toString());
            ProduceMessageResponse produceMessageResponse = kafkaExternalAPI.produceMessage(produceMessageRequest);
        } catch (Exception e) {
            
        }
    }

}
