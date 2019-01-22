/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.ClientUserData;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.SetClientUsersRequest;
import com.xcase.open.transputs.SetClientUsersResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class SetClientUsersMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public SetClientUsersMethod() {
        super();
//        LOGGER.debug("finishing SetClientUsersMethod()");
    }

    public SetClientUsersResponse setClientUsers(SetClientUsersRequest setClientUsersRequest) {
        LOGGER.debug("starting setClientUsers()");
        try {
            String clientId = setClientUsersRequest.getClientId();
            LOGGER.debug("clientId is " + clientId);
            ClientUserData[] clientUserDataArray = setClientUsersRequest.getClientUserDataArray();
            SetClientUsersResponse setClientUsersResponse = OpenResponseFactory.createSetClientUsersResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the SetClientUsers() method */
            commonApiWebProxy.SetClientUsers(clientId, clientUserDataArray);
            LOGGER.debug("set client users");
            return setClientUsersResponse;
        } catch (Exception e) {
            LOGGER.warn("exception setting client users: " + e.getMessage());
        }

        return null;
    }
}
