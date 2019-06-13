package com.xcase.kafka;

import com.xcase.kafka.constant.KafkaConstant;
import com.xcase.kafka.factories.KafkaRequestFactory;
import com.xcase.kafka.impl.simple.core.KafkaConfigurationManager;
import com.xcase.kafka.transputs.*;
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
            String bootstrapServers = KafkaConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KafkaConstant.LOCAL_BOOTSTRAP_SERVERS);
            LOGGER.debug("bootstrapServers is " + bootstrapServers);
            String acks = KafkaConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KafkaConstant.LOCAL_ACKS);
            LOGGER.debug("acks is " + acks);
            String groupId = KafkaConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KafkaConstant.LOCAL_GROUP_ID);
            LOGGER.debug("groupId is " + groupId);
            String enableAutoCommit = KafkaConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KafkaConstant.LOCAL_ENABLE_AUTO_COMMIT);
            LOGGER.debug("enableAutoCommit is " + enableAutoCommit);
            String autoCommitInterval = KafkaConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KafkaConstant.LOCAL_AUTO_COMMIT_INTERVAL);
            LOGGER.debug("autoCommitInterval is " + autoCommitInterval);
            String keySerializer = KafkaConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KafkaConstant.LOCAL_KEY_SERIALIZER);
            LOGGER.debug("keySerializer is " + keySerializer);
            String valueSerializer = KafkaConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KafkaConstant.LOCAL_VALUE_SERIALIZER);
            LOGGER.debug("valueSerializer is " + valueSerializer);
            String keyDeserializer = KafkaConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KafkaConstant.LOCAL_KEY_DESERIALIZER);
            LOGGER.debug("keyDeserializer is " + keyDeserializer);
            String valueDeserializer = KafkaConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KafkaConstant.LOCAL_VALUE_DESERIALIZER);
            LOGGER.debug("valueDeserializer is " + valueDeserializer);
            String topic = KafkaConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KafkaConstant.LOCAL_TOPIC);
            LOGGER.debug("topic is " + topic);
            Properties kafkaProducerProperties = new Properties();
            kafkaProducerProperties.put("bootstrap.servers", bootstrapServers);
            kafkaProducerProperties.put("acks", acks);
            kafkaProducerProperties.put("key.serializer", keySerializer);
            kafkaProducerProperties.put("value.serializer", valueSerializer);
            Properties kafkaConsumerProperties = new Properties();
            kafkaConsumerProperties.put("bootstrap.servers", bootstrapServers);
            kafkaConsumerProperties.setProperty("group.id", groupId);
            kafkaConsumerProperties.setProperty("enable.auto.commit", enableAutoCommit);
            kafkaConsumerProperties.setProperty("auto.commit.interval.ms", autoCommitInterval);
            kafkaConsumerProperties.put("key.deserializer", keyDeserializer);
            kafkaConsumerProperties.put("value.deserializer", valueDeserializer);
            ProduceMessageRequest produceMessageRequest = KafkaRequestFactory.createProduceMessageRequest();
            produceMessageRequest.setKafkaProducerProperties(kafkaProducerProperties);
            produceMessageRequest.setTopic(topic);
            produceMessageRequest.setKey("messageKey");
            produceMessageRequest.setValue("This is another message with a value based on the current time: " + new Date().toString());
            ProduceMessageResponse produceMessageResponse = kafkaExternalAPI.produceMessage(produceMessageRequest);
            ConsumeMessageRequest consumeMessageRequest = KafkaRequestFactory.createConsumeMessageRequest();
            consumeMessageRequest.setKafkaConsumerProperties(kafkaConsumerProperties);
            consumeMessageRequest.setTopic(topic);
            ConsumeMessageResponse consumeMessageResponse = kafkaExternalAPI.consumeMessage(consumeMessageRequest);
        } catch (Exception e) {
            
        }
    }

}
