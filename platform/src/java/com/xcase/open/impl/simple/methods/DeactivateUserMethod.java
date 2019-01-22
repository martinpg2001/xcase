/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.DeactivateUserRequest;
import com.xcase.open.transputs.DeactivateUserResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class DeactivateUserMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public DeactivateUserMethod() {
        super();
//        LOGGER.debug("finishing DeactivateUserMethod()");
    }

    public DeactivateUserResponse deactivateUser(DeactivateUserRequest deactivateUserRequest) {
        LOGGER.debug("starting deactivateUser()");
        try {
            DeactivateUserResponse deactivateUserResponse = OpenResponseFactory.createDeactivateUserResponse();
            String userId = deactivateUserRequest.getUserId();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the DeactivateUserMethod() method */
            commonApiWebProxy.DeactivateUser(userId);
            return deactivateUserResponse;
        } catch (Exception e) {
            LOGGER.warn("exception deactivating user: " + e.getMessage());
        }

        return null;
    }
}
