/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateRoleData;
import com.xcase.open.transputs.CreateRoleRequest;
import com.xcase.open.transputs.CreateRoleResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateRoleMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateRoleMethod() {
        super();
//        LOGGER.debug("finishing CreateRoleMethod()");
    }

    public CreateRoleResponse createRole(CreateRoleRequest createRoleRequest) {
        LOGGER.debug("starting createRole()");
        try {
            CreateRoleData createRoleData = createRoleRequest.getCreateRoleData();
            LOGGER.debug("got createRoleData");
            CreateRoleResponse createRoleResponse = OpenResponseFactory.createCreateRoleResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateRole() method */
            int roleID = commonApiWebProxy.CreateRole(createRoleData);
            LOGGER.debug("roleID is " + roleID);
            createRoleResponse.setId(roleID);
            return createRoleResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating role: " + e.getMessage());
        }

        return null;
    }
}
