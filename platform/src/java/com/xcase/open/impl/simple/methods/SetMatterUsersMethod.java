/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.MatterUserData;
import com.xcase.open.transputs.SetMatterUsersRequest;
import com.xcase.open.transputs.SetMatterUsersResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class SetMatterUsersMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public SetMatterUsersMethod() {
        super();
//        LOGGER.debug("finishing SetMatterUsersMethod()");
    }

    public SetMatterUsersResponse setMatterUsers(SetMatterUsersRequest setMatterUsersRequest) {
        LOGGER.debug("starting setMatterUsers()");
        try {
            String clientId = setMatterUsersRequest.getClientId();
            String matterId = setMatterUsersRequest.getMatterId();
            MatterUserData[] matterUserDataArray = setMatterUsersRequest.getMatterUserDataArray();
            SetMatterUsersResponse setMatterUsersResponse = OpenResponseFactory.createSetMatterUsersResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the SetMatterUsers() method */
            commonApiWebProxy.SetMatterUsers(matterId, matterUserDataArray, clientId);
            LOGGER.debug("set matter users");
            return setMatterUsersResponse;
        } catch (Exception e) {
            LOGGER.warn("exception setting matter users: " + e.getMessage());
        }

        return null;
    }
}
