package com.xcase.kafka;

import com.xcase.kafka.transputs.*;

public interface KafkaExternalAPI {

    ProduceMessageResponse produceMessage(ProduceMessageRequest produceMessageRequest);

    ConsumeMessageResponse consumeMessage(ConsumeMessageRequest consumeMessageRequest);

}
