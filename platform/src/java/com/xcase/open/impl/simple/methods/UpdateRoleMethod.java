/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.UpdateRoleData;
import com.xcase.open.transputs.UpdateRoleRequest;
import com.xcase.open.transputs.UpdateRoleResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdateRoleMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateRoleMethod() {
        super();
//        LOGGER.debug("finishing UpdateRoleMethod()");
    }

    public UpdateRoleResponse updateRole(UpdateRoleRequest updateRoleRequest) {
        LOGGER.debug("starting updateRole()");
        try {
            String roleId = updateRoleRequest.getId();
            UpdateRoleData updateRoleData = updateRoleRequest.getUpdateRoleData();
            LOGGER.debug("got updateRoleData");
            UpdateRoleResponse updateRoleResponse = OpenResponseFactory.createUpdateRoleResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdateRole() method */
            commonApiWebProxy.UpdateRole(roleId, updateRoleData);
            return updateRoleResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating role: " + e.getMessage());
        }

        return null;
    }
}
