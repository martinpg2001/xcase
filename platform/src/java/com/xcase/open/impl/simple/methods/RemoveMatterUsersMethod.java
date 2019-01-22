/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.MatterUserData;
import com.xcase.open.transputs.RemoveMatterUsersRequest;
import com.xcase.open.transputs.RemoveMatterUsersResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class RemoveMatterUsersMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public RemoveMatterUsersMethod() {
        super();
//        LOGGER.debug("finishing RemoveMatterUsersMethod()");
    }

    public RemoveMatterUsersResponse removeMatterUsers(RemoveMatterUsersRequest removeMatterUsersRequest) {
        LOGGER.debug("starting removeMatterUsers()");
        try {
            String clientId = removeMatterUsersRequest.getClientId();
            String matterId = removeMatterUsersRequest.getMatterId();
            MatterUserData[] matterUserDataArray = removeMatterUsersRequest.getMatterUserDataArray();
            RemoveMatterUsersResponse removeMatterUsersResponse = OpenResponseFactory.createRemoveMatterUsersResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the RemoveMatterUsers() method */
            commonApiWebProxy.RemoveMatterUsers(matterId, matterUserDataArray, clientId);
            LOGGER.debug("removed matter users");
            return removeMatterUsersResponse;
        } catch (Exception e) {
            LOGGER.warn("exception removing matter users: " + e.getMessage());
        }

        return null;
    }
}
