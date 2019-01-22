/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.SetUserDepartmentsRequest;
import com.xcase.open.transputs.SetUserDepartmentsResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class SetUserDepartmentsMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public SetUserDepartmentsMethod() {
        super();
//        LOGGER.debug("finishing SetUserDepartmentsMethod()");
    }

    public SetUserDepartmentsResponse setUserDepartments(SetUserDepartmentsRequest setUserDepartmentsRequest) {
        LOGGER.debug("starting setUserDepartments()");
        try {
            String userId = setUserDepartmentsRequest.getUserId();
            String[] departments = setUserDepartmentsRequest.getDepartmentArray();
            SetUserDepartmentsResponse setUserDepartmentsResponse = OpenResponseFactory.createSetUserDepartmentsResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the SetUserDepartments() method */
            commonApiWebProxy.SetUserDepartments(userId, departments);
            LOGGER.debug("set user departments");
            return setUserDepartmentsResponse;
        } catch (Exception e) {
            LOGGER.warn("exception setting user departments: " + e.getMessage());
        }

        return null;
    }
}
