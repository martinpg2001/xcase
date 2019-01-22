/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.MatterUserData;
import com.xcase.open.transputs.AddMatterUsersRequest;
import com.xcase.open.transputs.AddMatterUsersResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class AddMatterUsersMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public AddMatterUsersMethod() {
        super();
//        LOGGER.debug("finishing AddMatterUsersMethod()");
    }

    public AddMatterUsersResponse addMatterUsers(AddMatterUsersRequest addMatterUsersRequest) {
        LOGGER.debug("starting addMatterUsers()");
        try {
            String clientId = addMatterUsersRequest.getClientId();
            String matterId = addMatterUsersRequest.getMatterId();
            MatterUserData[] matterUserDataArray = addMatterUsersRequest.getMatterUserDataArray();
            AddMatterUsersResponse addMatterUsersResponse = OpenResponseFactory.createAddMatterUsersResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the AddMatterUsers() method */
            commonApiWebProxy.AddMatterUsers(matterId, matterUserDataArray, clientId);
            LOGGER.debug("added matter users");
            return addMatterUsersResponse;
        } catch (Exception e) {
            LOGGER.warn("exception adding matter users: " + e.getMessage());
        }

        return null;
    }
}
