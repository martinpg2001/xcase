package com.xcase.kafka;

import com.xcase.kafka.impl.simple.methods.*;
import com.xcase.kafka.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleKafkaImpl implements KafkaExternalAPI {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    ConsumeResponseMethod consumeResponseMethod = new ConsumeResponseMethod();
    
    ProduceResponseMethod produceResponseMethod = new ProduceResponseMethod();
    
    @Override
    public ProduceMessageResponse produceMessage(ProduceMessageRequest produceMessageRequest) {
        return this.produceResponseMethod.produceResponse(produceMessageRequest);
    }

    @Override
    public ConsumeMessageResponse consumeMessage(ConsumeMessageRequest consumeMessageRequest) {
        return this.consumeResponseMethod.consumeMessage(consumeMessageRequest);
    }

}
