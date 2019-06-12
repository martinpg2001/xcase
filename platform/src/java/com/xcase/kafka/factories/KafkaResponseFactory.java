package com.xcase.kafka.factories;

import com.xcase.integrate.transputs.CreateAccessTokenRequest;
import com.xcase.kafka.transputs.ProduceMessageResponse;

public class KafkaResponseFactory extends BaseKafkaFactory {

    public static ProduceMessageResponse createProduceMessageResponse() {
        Object obj = newInstanceOf("kafka.config.responsefactory.ProduceMessageResponse");
        return (ProduceMessageResponse) obj;
    }

}
