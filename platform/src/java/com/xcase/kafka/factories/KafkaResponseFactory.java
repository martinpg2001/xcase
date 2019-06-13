package com.xcase.kafka.factories;

import com.xcase.kafka.transputs.*;

public class KafkaResponseFactory extends BaseKafkaFactory {

    public static ProduceMessageResponse createProduceMessageResponse() {
        Object obj = newInstanceOf("kafka.config.responsefactory.ProduceMessageResponse");
        return (ProduceMessageResponse) obj;
    }

    public static ConsumeMessageResponse createConsumeMessageResponse() {
        Object obj = newInstanceOf("kafka.config.responsefactory.ConsumeMessageResponse");
        return (ConsumeMessageResponse) obj;
    }

}
