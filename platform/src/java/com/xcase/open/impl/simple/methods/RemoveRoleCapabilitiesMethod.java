/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.RemoveRoleCapabilitiesRequest;
import com.xcase.open.transputs.RemoveRoleCapabilitiesResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class RemoveRoleCapabilitiesMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public RemoveRoleCapabilitiesMethod() {
        super();
//        LOGGER.debug("finishing RemoveRoleCapabilitiesMethod()");
    }

    public RemoveRoleCapabilitiesResponse removeRoleCapabilities(RemoveRoleCapabilitiesRequest removeRoleCapabilitiesRequest) {
        LOGGER.debug("starting removeRoleCapabilities()");
        try {
            String roleId = removeRoleCapabilitiesRequest.getRoleId();
            String[] capabilities = removeRoleCapabilitiesRequest.getCapabilitiesArray();
            RemoveRoleCapabilitiesResponse removeRoleCapabilitiesResponse = OpenResponseFactory.createRemoveRoleCapabilitiesResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the RemoveRoleCapabilities() method */
            commonApiWebProxy.RemoveRoleCapabilities(roleId, capabilities);
            LOGGER.debug("removed role capabilities");
            return removeRoleCapabilitiesResponse;
        } catch (Exception e) {
            LOGGER.warn("exception removing role capabilities: " + e.getMessage());
        }

        return null;
    }
}
