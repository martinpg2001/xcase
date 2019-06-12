package com.xcase.kafka.factories;

import com.xcase.kafka.transputs.ProduceMessageRequest;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KafkaRequestFactory extends BaseKafkaFactory {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static ProduceMessageRequest createProduceMessageRequest() {
        Object obj = newInstanceOf("kafka.config.requestfactory.ProduceMessageRequest");
        return (ProduceMessageRequest) obj;
    }

}
