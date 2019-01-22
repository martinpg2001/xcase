/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.AddGroupRolesRequest;
import com.xcase.open.transputs.AddGroupRolesResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class AddGroupRolesMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public AddGroupRolesMethod() {
        super();
//        LOGGER.debug("finishing AddGroupRolesMethod()");
    }

    public AddGroupRolesResponse addGroupRoles(AddGroupRolesRequest addGroupRolesRequest) {
        LOGGER.debug("starting addGroupRoles()");
        try {
            String groupId = addGroupRolesRequest.getGroupId();
            String[] users = addGroupRolesRequest.getRoleArray();
            AddGroupRolesResponse addGroupRolesResponse = OpenResponseFactory.createAddGroupRolesResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the AddGroupRoles() method */
            commonApiWebProxy.AddGroupRoles(groupId, users);
            LOGGER.debug("added group roles");
            return addGroupRolesResponse;
        } catch (Exception e) {
            LOGGER.warn("exception adding group roles: " + e.getMessage());
        }

        return null;
    }
}
