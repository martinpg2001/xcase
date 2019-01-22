/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateOrReplaceUserEntitySecurityentityType;
import com.xcase.open.impl.simple.core.SecurityData;
import com.xcase.open.transputs.CreateOrReplaceUserClientSecurityRequest;
import com.xcase.open.transputs.CreateOrReplaceUserClientSecurityResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateOrReplaceUserClientSecurityMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateOrReplaceUserClientSecurityMethod() {
        super();
//        LOGGER.debug("finishing CreateOrReplaceUserEntitySecurityMethod()");
    }

    public CreateOrReplaceUserClientSecurityResponse createOrReplaceUserEntitySecurity(CreateOrReplaceUserClientSecurityRequest createOrReplaceUserEntitySecurityRequest) {
        LOGGER.debug("starting createOrReplaceUserEntitySecurity()");
        try {
            CreateOrReplaceUserEntitySecurityentityType createOrReplaceUserEntitySecurityentityType = createOrReplaceUserEntitySecurityRequest.getCreateOrReplaceUserEntitySecurityentityType();
            String entityId = createOrReplaceUserEntitySecurityRequest.getEntityId();
            SecurityData[] securityDataArray = createOrReplaceUserEntitySecurityRequest.getSecurityDataArray();
            String parentEntityId = createOrReplaceUserEntitySecurityRequest.getParentEntityId();
            CreateOrReplaceUserClientSecurityResponse createOrReplaceUserEntitySecurityResponse = OpenResponseFactory.createCreateOrReplaceUserEntitySecurityResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateOrReplaceUserEntitySecurity() method */
//            commonApiWebProxy.CreateOrReplaceUserEntitySecurity(entityId, createOrReplaceUserEntitySecurityentityType, securityDataArray, parentEntityId);
            return createOrReplaceUserEntitySecurityResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating or replacing group entity security: " + e.getMessage());
        }

        return null;
    }
}
