package com.xcase.kafka;

import com.xcase.kafka.impl.simple.methods.ProduceResponseMethod;
import com.xcase.kafka.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleKafkaImpl implements KafkaExternalAPI {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    ProduceResponseMethod produceResponseMethod = new ProduceResponseMethod();
    
    @Override
    public ProduceMessageResponse produceMessage(ProduceMessageRequest produceMessageRequest) {
        return this.produceResponseMethod.produceResponse(produceMessageRequest);
    }

}
