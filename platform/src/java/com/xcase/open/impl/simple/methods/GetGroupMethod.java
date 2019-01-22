/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.GroupData;
import com.xcase.open.transputs.GetGroupRequest;
import com.xcase.open.transputs.GetGroupResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetGroupMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetGroupMethod() {
        super();
//        LOGGER.debug("finishing GetGroupMethod()");
    }

    public GetGroupResponse getGroup(GetGroupRequest getGroupRequest) {
        LOGGER.debug("starting getGroup()");
        try {
            GetGroupResponse getGroupResponse = OpenResponseFactory.createGetGroupResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            String groupId = getGroupRequest.getGroupId();
            /* Invoke the GetGroup() method */
            GroupData groupData = commonApiWebProxy.GetGroup(groupId);
            LOGGER.debug("got group");
            getGroupResponse.setGroupData(groupData);
            return getGroupResponse;
        } catch (Exception e) {
            LOGGER.warn("exception getting group: " + e.getMessage());
        }

        return null;
    }
}
