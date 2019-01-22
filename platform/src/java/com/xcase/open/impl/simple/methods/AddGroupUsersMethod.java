/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.AddGroupUsersRequest;
import com.xcase.open.transputs.AddGroupUsersResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class AddGroupUsersMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public AddGroupUsersMethod() {
        super();
//        LOGGER.debug("finishing AddGroupUsersMethod()");
    }

    public AddGroupUsersResponse addGroupUsers(AddGroupUsersRequest addGroupUsersRequest) {
        LOGGER.debug("starting addGroupUsers()");
        try {
            String groupId = addGroupUsersRequest.getGroupId();
            String[] users = addGroupUsersRequest.getUserArray();
            AddGroupUsersResponse addGroupUsersResponse = OpenResponseFactory.createAddGroupUsersResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the AddGroupUsers() method */
            commonApiWebProxy.AddGroupUsers(groupId, users);
            LOGGER.debug("added group users");
            return addGroupUsersResponse;
        } catch (Exception e) {
            LOGGER.warn("exception adding group users: " + e.getMessage());
        }

        return null;
    }
}
