/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.ChangePasswordRequest;
import com.xcase.open.transputs.ChangePasswordResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class ChangePasswordMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public ChangePasswordMethod() {
        super();
//        LOGGER.debug("finishing ChangePasswordMethod()");
    }

    public ChangePasswordResponse changePassword(ChangePasswordRequest changePasswordRequest) {
        LOGGER.debug("starting changePassword()");
        try {
            String userId = changePasswordRequest.getUserId();
            String changePassword = changePasswordRequest.getPassword();
            ChangePasswordResponse changePasswordResponse = OpenResponseFactory.createChangePasswordResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the ChangePassword() method */
            commonApiWebProxy.ChangePassword(userId, changePassword);
            LOGGER.debug("changed password");
            return changePasswordResponse;
        } catch (Exception e) {
            LOGGER.warn("exception changing password: " + e.getMessage());
        }

        return null;
    }
}
