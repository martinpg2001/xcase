/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.RemoveGroupRolesRequest;
import com.xcase.open.transputs.RemoveGroupRolesResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class RemoveGroupRolesMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public RemoveGroupRolesMethod() {
        super();
//        LOGGER.debug("finishing RemoveGroupRolesMethod()");
    }

    public RemoveGroupRolesResponse removeGroupRoles(RemoveGroupRolesRequest removeGroupRolesRequest) {
        LOGGER.debug("starting removeGroupRoles()");
        try {
            String groupId = removeGroupRolesRequest.getGroupId();
            String[] users = removeGroupRolesRequest.getRoleArray();
            RemoveGroupRolesResponse removeGroupRolesResponse = OpenResponseFactory.createRemoveGroupRolesResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the RemoveGroupRoles() method */
            commonApiWebProxy.RemoveGroupRoles(groupId, users);
            LOGGER.debug("removed group roles");
            return removeGroupRolesResponse;
        } catch (Exception e) {
            LOGGER.warn("exception removing group roles: " + e.getMessage());
        }

        return null;
    }
}
