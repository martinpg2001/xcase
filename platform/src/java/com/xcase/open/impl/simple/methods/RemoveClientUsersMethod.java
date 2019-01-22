/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.ClientUserData;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.RemoveClientUsersRequest;
import com.xcase.open.transputs.RemoveClientUsersResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class RemoveClientUsersMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public RemoveClientUsersMethod() {
        super();
//        LOGGER.debug("finishing RemoveClientUsersMethod()");
    }

    public RemoveClientUsersResponse removeClientUsers(RemoveClientUsersRequest removeClientUsersRequest) {
        LOGGER.debug("starting removeClientUsers()");
        try {
            String clientId = removeClientUsersRequest.getClientId();
            LOGGER.debug("clientId is " + clientId);
            ClientUserData[] clientUserDataArray = removeClientUsersRequest.getClientUserDataArray();
            RemoveClientUsersResponse removeClientUsersResponse = OpenResponseFactory.createRemoveClientUsersResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the RemoveClientUsers() method */
            commonApiWebProxy.RemoveClientUsers(clientId, clientUserDataArray);
            LOGGER.debug("removed client users");
            return removeClientUsersResponse;
        } catch (Exception e) {
            LOGGER.warn("exception removing client users: " + e.getMessage());
        }

        return null;
    }
}
