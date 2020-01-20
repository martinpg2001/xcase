package com.xcase.klearnow;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.xcase.klearnow.transputs.*;
import com.xcase.klearnow.impl.simple.methods.*;

public class SimpleKlearNowImpl implements KlearNowExternalAPI {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * Mail action implementation.
     */
    private GetAccessTokenMethod getAccessTokenMethod = new GetAccessTokenMethod();

    /**
     * Mail action implementation.
     */
    private SendMessageMethod sendMessageMethod = new SendMessageMethod();

    @Override
    public SendMessageResponse sendMessage(SendMessageRequest sendMessageRequest) {
        return this.sendMessageMethod.sendMessage(sendMessageRequest);
    }

    @Override
    public GetAccessTokenResponse getAccessToken(GetAccessTokenRequest getAccessTokenRequest) {
        return this.getAccessTokenMethod.getAccessToken(getAccessTokenRequest);
    }

}
