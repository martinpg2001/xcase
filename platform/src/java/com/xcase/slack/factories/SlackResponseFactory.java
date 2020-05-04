package com.xcase.slack.factories;

import com.xcase.slack.transputs.GetAccessTokenResponse;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SlackResponseFactory extends BaseSlackFactory {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public static GetAccessTokenResponse createGetAccessTokenResponse() {
        Object obj = newInstanceOf("slack.config.responsefactory.GetAccessTokenResponse");
        return (GetAccessTokenResponse) obj;
    }

}
