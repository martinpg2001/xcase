package com.xcase.slack.factories;

import com.xcase.klearnow.factories.BaseKlearNowFactory;
import com.xcase.slack.transputs.GetAccessTokenRequest;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SlackRequestFactory extends BaseSlackFactory {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static GetAccessTokenRequest createGetAccessTokenRequest() {
        Object obj = newInstanceOf("slack.config.requestfactory.GetAccessTokenRequest");
        return (GetAccessTokenRequest) obj;
    }

}
