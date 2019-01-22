/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.UpdateGroupData;
import com.xcase.open.transputs.UpdateGroupRequest;
import com.xcase.open.transputs.UpdateGroupResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdateGroupMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateGroupMethod() {
        super();
//        LOGGER.debug("finishing UpdateGroupMethod()");
    }

    public UpdateGroupResponse updateGroup(UpdateGroupRequest updateGroupRequest) {
        LOGGER.debug("starting updateGroup()");
        try {
            String groupId = updateGroupRequest.getId();
            UpdateGroupData updateGroupData = updateGroupRequest.getUpdateGroupData();
            LOGGER.debug("got updateGroupData");
            UpdateGroupResponse updateGroupResponse = OpenResponseFactory.createUpdateGroupResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the UpdateGroup() method */
            commonApiWebProxy.UpdateGroup(groupId, updateGroupData);
            LOGGER.debug("updated group");
            return updateGroupResponse;
        } catch (Exception e) {
            LOGGER.warn("exception updating group: " + e.getMessage());
        }

        return null;
    }
}
