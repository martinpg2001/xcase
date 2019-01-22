/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.AddRoleCapabilitiesRequest;
import com.xcase.open.transputs.AddRoleCapabilitiesResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class AddRoleCapabilitiesMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public AddRoleCapabilitiesMethod() {
        super();
//        LOGGER.debug("finishing AddRoleCapabilitiesMethod()");
    }

    public AddRoleCapabilitiesResponse addRoleCapabilities(AddRoleCapabilitiesRequest addRoleCapabilitiesRequest) {
        LOGGER.debug("starting addRoleCapabilities()");
        try {
            String roleId = addRoleCapabilitiesRequest.getRoleId();
            String[] capabilities = addRoleCapabilitiesRequest.getCapabilitiesArray();
            AddRoleCapabilitiesResponse addRoleCapabilitiesResponse = OpenResponseFactory.createAddRoleCapabilitiesResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the AddRoleCapabilities() method */
            commonApiWebProxy.AddRoleCapabilities(roleId, capabilities);
            LOGGER.debug("added role capabilities");
            return addRoleCapabilitiesResponse;
        } catch (Exception e) {
            LOGGER.warn("exception adding role capabilities: " + e.getMessage());
        }

        return null;
    }
}
