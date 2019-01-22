/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.SetGroupUsersRequest;
import com.xcase.open.transputs.SetGroupUsersResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class SetGroupUsersMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public SetGroupUsersMethod() {
        super();
//        LOGGER.debug("finishing SetGroupUsersMethod()");
    }

    public SetGroupUsersResponse setGroupUsers(SetGroupUsersRequest setGroupUsersRequest) {
        LOGGER.debug("starting setGroupUsers()");
        try {
            String groupId = setGroupUsersRequest.getGroupId();
            String[] users = setGroupUsersRequest.getUserArray();
            SetGroupUsersResponse setGroupUsersResponse = OpenResponseFactory.createSetGroupUsersResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the SetGroupUsers() method */
            commonApiWebProxy.SetGroupUsers(groupId, users);
            LOGGER.debug("set group users");
            return setGroupUsersResponse;
        } catch (Exception e) {
            LOGGER.warn("exception setting group users: " + e.getMessage());
        }

        return null;
    }
}
