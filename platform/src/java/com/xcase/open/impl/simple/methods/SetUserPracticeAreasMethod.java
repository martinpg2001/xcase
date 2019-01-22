/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.SetUserPracticeAreasRequest;
import com.xcase.open.transputs.SetUserPracticeAreasResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class SetUserPracticeAreasMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public SetUserPracticeAreasMethod() {
        super();
//        LOGGER.debug("finishing SetUserPracticeAreasMethod()");
    }

    public SetUserPracticeAreasResponse setUserPracticeAreas(SetUserPracticeAreasRequest setUserPracticeAreasRequest) {
        LOGGER.debug("starting setUserPracticeAreas()");
        try {
            String userId = setUserPracticeAreasRequest.getUserId();
            String[] roles = setUserPracticeAreasRequest.getPracticeAreaArray();
            SetUserPracticeAreasResponse setUserPracticeAreasResponse = OpenResponseFactory.createSetUserPracticeAreasResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the SetUserPracticeAreas() method */
            commonApiWebProxy.SetUserPracticeAreas(userId, roles);
            LOGGER.debug("set user roles");
            return setUserPracticeAreasResponse;
        } catch (Exception e) {
            LOGGER.warn("exception setting user roles: " + e.getMessage());
        }

        return null;
    }
}
