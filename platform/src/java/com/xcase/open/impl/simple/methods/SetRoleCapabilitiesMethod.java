/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.SetRoleCapabilitiesRequest;
import com.xcase.open.transputs.SetRoleCapabilitiesResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class SetRoleCapabilitiesMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public SetRoleCapabilitiesMethod() {
        super();
//        LOGGER.debug("finishing SetRoleCapabilitiesMethod()");
    }

    public SetRoleCapabilitiesResponse setRoleCapabilities(SetRoleCapabilitiesRequest setRoleCapabilitiesRequest) {
        LOGGER.debug("starting setRoleCapabilities()");
        try {
            String roleId = setRoleCapabilitiesRequest.getRoleId();
            String[] capabilities = setRoleCapabilitiesRequest.getCapabilitiesArray();
            SetRoleCapabilitiesResponse setRoleCapabilitiesResponse = OpenResponseFactory.createSetRoleCapabilitiesResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the SetRoleCapabilities() method */
            commonApiWebProxy.SetRoleCapabilities(roleId, capabilities);
            LOGGER.debug("set role capabilities");
            return setRoleCapabilitiesResponse;
        } catch (Exception e) {
            LOGGER.warn("exception setting role capabilities: " + e.getMessage());
        }

        return null;
    }
}
