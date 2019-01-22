/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.UserData;
import com.xcase.open.transputs.GetUserRequest;
import com.xcase.open.transputs.GetUserResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetUserMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetUserMethod() {
        super();
//        LOGGER.debug("finishing GetUserMethod()");
    }

    public GetUserResponse getUser(GetUserRequest getUserRequest) {
        LOGGER.debug("starting getUser()");
        try {
            GetUserResponse getUserResponse = OpenResponseFactory.createGetUserResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            String userId = getUserRequest.getUserId();
            String[] properties = getUserRequest.getProperties();
            /* Invoke the GetUser() method */
            UserData userData = commonApiWebProxy.GetUser(userId);
            LOGGER.debug("got user");
            getUserResponse.setUserData(userData);
            return getUserResponse;
        } catch (Exception e) {
            LOGGER.warn("exception getting user: " + e.getMessage());
        }

        return null;
    }
}
