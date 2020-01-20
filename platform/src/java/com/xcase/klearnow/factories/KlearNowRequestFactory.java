package com.xcase.klearnow.factories;

import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.xcase.klearnow.transputs.*;

public class KlearNowRequestFactory extends BaseKlearNowFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static SendMessageRequest createSendMessageRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.SendMessageRequest");
        return (SendMessageRequest) obj;
    }

    public static SendMessageRequest createSendMessageRequest(String accessToken) {
        SendMessageRequest sendMessageRequest = createSendMessageRequest();
        sendMessageRequest.setAccessToken(accessToken);
        return sendMessageRequest;
    }

    public static GetAccessTokenRequest createGetAccessTokenRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.GetAccessTokenRequest");
        return (GetAccessTokenRequest) obj;
    }
}
