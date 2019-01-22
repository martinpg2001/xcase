/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.RemoveGroupUsersRequest;
import com.xcase.open.transputs.RemoveGroupUsersResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class RemoveGroupUsersMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public RemoveGroupUsersMethod() {
        super();
//        LOGGER.debug("finishing RemoveGroupUsersMethod()");
    }

    public RemoveGroupUsersResponse removeGroupUsers(RemoveGroupUsersRequest removeGroupUsersRequest) {
        LOGGER.debug("starting removeGroupUsers()");
        try {
            String groupId = removeGroupUsersRequest.getGroupId();
            String[] users = removeGroupUsersRequest.getUserArray();
            RemoveGroupUsersResponse removeGroupUsersResponse = OpenResponseFactory.createRemoveGroupUsersResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the RemoveGroupUsers() method */
            commonApiWebProxy.RemoveGroupUsers(groupId, users);
            LOGGER.debug("removed group users");
            return removeGroupUsersResponse;
        } catch (Exception e) {
            LOGGER.warn("exception removing group users: " + e.getMessage());
        }

        return null;
    }
}
