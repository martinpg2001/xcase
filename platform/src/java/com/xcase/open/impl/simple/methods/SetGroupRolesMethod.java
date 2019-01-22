/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.SetGroupRolesRequest;
import com.xcase.open.transputs.SetGroupRolesResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class SetGroupRolesMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public SetGroupRolesMethod() {
        super();
//        LOGGER.debug("finishing SetGroupRolesMethod()");
    }

    public SetGroupRolesResponse setGroupRoles(SetGroupRolesRequest setGroupRolesRequest) {
        LOGGER.debug("starting setGroupRoles()");
        try {
            String groupId = setGroupRolesRequest.getGroupId();
            String[] users = setGroupRolesRequest.getRoleArray();
            SetGroupRolesResponse setGroupRolesResponse = OpenResponseFactory.createSetGroupRolesResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the SetGroupRoles() method */
            commonApiWebProxy.SetGroupRoles(groupId, users);
            LOGGER.debug("set group roles");
            return setGroupRolesResponse;
        } catch (Exception e) {
            LOGGER.warn("exception setting group roles: " + e.getMessage());
        }

        return null;
    }
}
