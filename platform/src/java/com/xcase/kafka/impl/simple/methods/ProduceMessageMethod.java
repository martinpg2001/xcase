package com.xcase.kafka.impl.simple.methods;

import com.xcase.kafka.factories.KafkaResponseFactory;
import com.xcase.kafka.transputs.ProduceMessageRequest;
import com.xcase.kafka.transputs.ProduceMessageResponse;
import java.lang.invoke.MethodHandles;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProduceMessageMethod extends BaseKafkaMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public ProduceMessageResponse produceMessage(ProduceMessageRequest request) {
        LOGGER.debug("starting produceMessage()");
        ProduceMessageResponse response = KafkaResponseFactory.createProduceMessageResponse();
        LOGGER.debug("created response");
        try {
            Properties kafkaProducerProperties = request.getKafkaProducerProperties();
            LOGGER.debug("got kafkaProducerProperties");
            Producer<String, String> producer = new KafkaProducer<>(kafkaProducerProperties);
            LOGGER.debug("created producer");
            String topic = request.getTopic();
            LOGGER.debug("topic is " + topic);
            String key = request.getKey();
            LOGGER.debug("key is " + key);
            String value = request.getValue();
            LOGGER.debug("value is " + value);
            producer.send(new ProducerRecord<String, String>(topic, key, value));
            LOGGER.debug("created response");
            producer.close();
        } catch (Exception e) {
            LOGGER.warn("exception producing message: " + e.getMessage());
            e.printStackTrace();
        }
        
        return response;
    }

}
