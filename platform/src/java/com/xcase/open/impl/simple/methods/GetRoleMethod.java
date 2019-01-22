/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.RoleData;
import com.xcase.open.transputs.GetRoleRequest;
import com.xcase.open.transputs.GetRoleResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetRoleMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetRoleMethod() {
        super();
//        LOGGER.debug("finishing GetRoleMethod()");
    }

    public GetRoleResponse getRole(GetRoleRequest getRoleRequest) {
        LOGGER.debug("starting getRole()");
        try {
            GetRoleResponse getRoleResponse = OpenResponseFactory.createGetRoleResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            String roleId = getRoleRequest.getRoleId();
            /* Invoke the GetRole() method */
            RoleData roleData = commonApiWebProxy.GetRole(roleId);
            LOGGER.debug("got role");
            getRoleResponse.setRoleData(roleData);
            return getRoleResponse;
        } catch (Exception e) {
            LOGGER.warn("exception getting role: " + e.getMessage());
        }

        return null;
    }
}
