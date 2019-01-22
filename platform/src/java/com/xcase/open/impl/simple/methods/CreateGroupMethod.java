/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateGroupData;
import com.xcase.open.transputs.CreateGroupRequest;
import com.xcase.open.transputs.CreateGroupResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateGroupMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateGroupMethod() {
        super();
//        LOGGER.debug("finishing CreateGroupMethod()");
    }

    public CreateGroupResponse createGroup(CreateGroupRequest createGroupRequest) {
        LOGGER.debug("starting createGroup()");
        try {
            CreateGroupData createGroupData = createGroupRequest.getCreateGroupData();
            LOGGER.debug("got createGroupData");
            CreateGroupResponse createGroupResponse = OpenResponseFactory.createCreateGroupResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateGroup() method */
            int groupID = commonApiWebProxy.CreateGroup(createGroupData);
            LOGGER.debug("groupID is " + groupID);
            createGroupResponse.setId(groupID);
            return createGroupResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating group: " + e.getMessage());
        }

        return null;
    }
}
