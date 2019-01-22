/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.UpdateUserData;
import com.xcase.open.transputs.UpdateUserRequest;
import com.xcase.open.transputs.UpdateUserResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdateUserMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateUserMethod() {
        super();
//        LOGGER.debug("finishing UpdateUserMethod()");
    }

    public UpdateUserResponse updateUser(UpdateUserRequest updateUserRequest) {
        LOGGER.debug("starting updateUser()");
        try {
            String userId = updateUserRequest.getUserId();
            UpdateUserData updateUserData = updateUserRequest.getUpdateUserData();
            LOGGER.debug("got updateUserData");
            UpdateUserResponse updateUserResponse = OpenResponseFactory.createUpdateUserResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdateUser() method */
            commonApiWebProxy.UpdateUser(userId, updateUserData);
            LOGGER.debug("updated user");
            return updateUserResponse;
        } catch (Exception e) {
            LOGGER.warn("exception updating user: " + e.getMessage());
        }

        return null;
    }
}
