/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.DisableUserLoginRequest;
import com.xcase.open.transputs.DisableUserLoginResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class DisableUserLoginMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public DisableUserLoginMethod() {
        super();
//        LOGGER.debug("finishing DisableUserLoginMethod()");
    }

    public DisableUserLoginResponse disableUserLogin(DisableUserLoginRequest disableUserLoginRequest) {
        LOGGER.debug("starting disableUserLogin()");
        try {
            DisableUserLoginResponse disableUserLoginResponse = OpenResponseFactory.createDisableUserLoginResponse();
            String userId = disableUserLoginRequest.getUserId();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the DisableUserLoginMethod() method */
            commonApiWebProxy.DisableUserLogin(userId);
            LOGGER.debug("disabled user login");
            return disableUserLoginResponse;
        } catch (Exception e) {
            LOGGER.warn("exception disabling user login: " + e.getMessage());
        }

        return null;
    }
}
