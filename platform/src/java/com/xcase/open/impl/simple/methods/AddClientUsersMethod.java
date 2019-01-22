/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.ClientUserData;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.AddClientUsersRequest;
import com.xcase.open.transputs.AddClientUsersResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class AddClientUsersMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public AddClientUsersMethod() {
        super();
//        LOGGER.debug("finishing AddClientUsersMethod()");
    }

    public AddClientUsersResponse addClientUsers(AddClientUsersRequest addClientUsersRequest) {
        LOGGER.debug("starting addClientUsers()");
        try {
            String clientId = addClientUsersRequest.getClientId();
            LOGGER.debug("clientId is " + clientId);
            ClientUserData[] clientUserDataArray = addClientUsersRequest.getClientUserDataArray();
            AddClientUsersResponse addClientUsersResponse = OpenResponseFactory.createAddClientUsersResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the AddClientUsers() method */
            commonApiWebProxy.AddClientUsers(clientId, clientUserDataArray);
            LOGGER.debug("added client users");
            return addClientUsersResponse;
        } catch (Exception e) {
            LOGGER.warn("exception adding client users: " + e.getMessage());
        }

        return null;
    }
}
