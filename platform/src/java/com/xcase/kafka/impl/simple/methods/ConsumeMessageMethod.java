package com.xcase.kafka.impl.simple.methods;

import com.xcase.kafka.factories.KafkaResponseFactory;
import com.xcase.kafka.impl.simple.methods.BaseKafkaMethod;
import com.xcase.kafka.transputs.ConsumeMessageRequest;
import com.xcase.kafka.transputs.ConsumeMessageResponse;
import java.lang.invoke.MethodHandles;
import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ConsumeMessageMethod extends BaseKafkaMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public ConsumeMessageResponse consumeMessage(ConsumeMessageRequest request) {
        LOGGER.debug("starting consumeMessage()");
        ConsumeMessageResponse response = KafkaResponseFactory.createConsumeMessageResponse();
        LOGGER.debug("created response");
        try {
            Properties kafkaConsumerProperties = request.getKafkaConsumerProperties();
            LOGGER.debug("got kafkaConsumerProperties");
            KafkaConsumer<String, String> consumer = new KafkaConsumer<>(kafkaConsumerProperties);
            LOGGER.debug("created consumer");
            String topic = request.getTopic();
            LOGGER.debug("topic is " + topic);
            consumer.subscribe(Arrays.asList(topic));
            ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(100));
            for (ConsumerRecord<String, String> record : records) {
                LOGGER.debug("offset = %d, key = %s, value = %s%n", record.offset(), record.key(), record.value());
            }
            
            consumer.close();
        } catch (Exception e) {
            LOGGER.warn("exception consuming message: " + e.getMessage());
            e.printStackTrace();
        }
        
        return response;
    }
}
