package com.xcase.slack;

import com.xcase.slack.impl.simple.methods.GetAccessTokenMethod;
import com.xcase.slack.transputs.*;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleSlackImpl implements SlackExternalAPI {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * GetAccessTokenMethod action implementation.
     */
    private GetAccessTokenMethod getAccessTokenMethod = new GetAccessTokenMethod();
    
    @Override
    public GetAccessTokenResponse getAccessToken(GetAccessTokenRequest request) {
        return this.getAccessTokenMethod.getAccessToken(request);
    }

}
