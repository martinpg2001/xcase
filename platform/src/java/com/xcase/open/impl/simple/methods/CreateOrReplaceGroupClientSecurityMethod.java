/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateOrReplaceGroupEntitySecurityentityType;
import com.xcase.open.impl.simple.core.GroupSecurityData;
import com.xcase.open.transputs.CreateOrReplaceGroupClientSecurityRequest;
import com.xcase.open.transputs.CreateOrReplaceGroupClientSecurityResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateOrReplaceGroupClientSecurityMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateOrReplaceGroupClientSecurityMethod() {
        super();
//        LOGGER.debug("finishing CreateOrReplaceGroupEntitySecurityMethod()");
    }

    public CreateOrReplaceGroupClientSecurityResponse createOrReplaceGroupEntitySecurity(CreateOrReplaceGroupClientSecurityRequest createOrReplaceGroupEntitySecurityRequest) {
        LOGGER.debug("starting createOrReplaceGroupEntitySecurity()");
        try {
            CreateOrReplaceGroupEntitySecurityentityType createOrReplaceGroupEntitySecurityentityType = createOrReplaceGroupEntitySecurityRequest.getCreateOrReplaceGroupEntitySecurityentityType();
            String entityId = createOrReplaceGroupEntitySecurityRequest.getEntityId();
            GroupSecurityData[] groupSecurityData = createOrReplaceGroupEntitySecurityRequest.getGroupSecurityDataArray();
            String parentEntityId = createOrReplaceGroupEntitySecurityRequest.getParentEntityId();
            CreateOrReplaceGroupClientSecurityResponse createOrReplaceGroupEntitySecurityResponse = OpenResponseFactory.createCreateOrReplaceGroupEntitySecurityResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateOrReplaceGroupEntitySecurity() method */
//            commonApiWebProxy.CreateOrReplaceGroupEntitySecurity(entityId, createOrReplaceGroupEntitySecurityentityType, groupSecurityData, parentEntityId);
            return createOrReplaceGroupEntitySecurityResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating or replacing group entity security: " + e.getMessage());
        }

        return null;
    }
}
