/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.GetEntitySecurityentityType;
import com.xcase.open.transputs.GetEntitySecurityRequest;
import com.xcase.open.transputs.GetEntitySecurityResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetEntitySecurityMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetEntitySecurityMethod() {
        super();
//        LOGGER.debug("finishing GetEntitySecurityMethod()");
    }

    public GetEntitySecurityResponse getEntitySecurity(GetEntitySecurityRequest request) {
        LOGGER.debug("starting getEntitySecurity()");
        try {
            GetEntitySecurityResponse getEntitySecurityResponse = OpenResponseFactory.createGetEntitySecurityResponse();
            CommonApiWebProxy commonApiWebProxy = null;
            if (request.getAccessToken() != null) {
                commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl), request.getAccessToken());
            } else {
                commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            }
            
            String entityId = request.getEntityId();
            GetEntitySecurityentityType getEntitySecurityentityType = request.getEntitySecurityentityType();
            String userIds = request.getUserIds();
            String parentEntityId = request.getParentEntityId();
            /* Invoke the GetEntitySecurity() method */
//            EntitySecurityData entitySecurityData = commonApiWebProxy.GetEntitySecurity(entityId, getEntitySecurityentityType, userIds, parentEntityId);
//            LOGGER.debug("got entity security");
//            getEntitySecurityResponse.setEntitySecurityData(entitySecurityData);
            return getEntitySecurityResponse;
        } catch (Exception e) {
            LOGGER.warn("exception getting entity security: " + e.getMessage());
        }

        return null;
    }
}
