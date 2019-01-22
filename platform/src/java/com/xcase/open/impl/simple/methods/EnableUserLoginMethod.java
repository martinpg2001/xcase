/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.EnableUserLoginRequest;
import com.xcase.open.transputs.EnableUserLoginResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class EnableUserLoginMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public EnableUserLoginMethod() {
        super();
//        LOGGER.debug("finishing EnableUserLoginMethod()");
    }

    public EnableUserLoginResponse enableUserLogin(EnableUserLoginRequest enableUserLoginRequest) {
        LOGGER.debug("starting enableUserLogin()");
        try {
            EnableUserLoginResponse enableUserLoginResponse = OpenResponseFactory.createEnableUserLoginResponse();
            String userId = enableUserLoginRequest.getUserId();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the EnableUserLoginMethod() method */
            commonApiWebProxy.EnableUserLogin(userId);
            LOGGER.debug("enabled user login");
            return enableUserLoginResponse;
        } catch (Exception e) {
            LOGGER.warn("exception enabling user login: " + e.getMessage());
        }

        return null;
    }
}
