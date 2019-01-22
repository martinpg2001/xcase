/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateUserData;
import com.xcase.open.transputs.CreateUserRequest;
import com.xcase.open.transputs.CreateUserResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateUserMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateUserMethod() {
        super();
//        LOGGER.debug("finishing CreateUserMethod()");
    }

    public CreateUserResponse createUser(CreateUserRequest createUserRequest) {
        LOGGER.debug("starting createUser()");
        try {
            CreateUserData createUserData = createUserRequest.getCreateUserData();
            LOGGER.debug("got createUserData");
            CreateUserResponse createUserResponse = OpenResponseFactory.createCreateUserResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateUser() method */
            int userID = commonApiWebProxy.CreateUser(createUserData);
            LOGGER.debug("userID is " + userID);
            createUserResponse.setId(userID);
            return createUserResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating user: " + e.getMessage());
        }

        return null;
    }
}
