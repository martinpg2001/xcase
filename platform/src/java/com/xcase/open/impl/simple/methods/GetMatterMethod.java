/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.MatterData;
import com.xcase.open.transputs.GetMatterRequest;
import com.xcase.open.transputs.GetMatterResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetMatterMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetMatterMethod() {
        super();
//        LOGGER.debug("finishing GetMatterMethod()");
    }

    public GetMatterResponse getMatter(GetMatterRequest request) {
        LOGGER.debug("starting getMatter()");
        try {
            GetMatterResponse getMatterResponse = OpenResponseFactory.createGetMatterResponse();
            CommonApiWebProxy commonApiWebProxy = null;
            if (request.getAccessToken() != null) {
                commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl), request.getAccessToken());
            } else {
                commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            }
            
            String clientId = request.getClientId();
            String matterId = request.getMatterId();
            String[] properties = request.getProperties();
            /* Invoke the GetMatter() method */
            MatterData matterData = commonApiWebProxy.GetMatter(matterId, properties, clientId);
            LOGGER.debug("got matter");
            getMatterResponse.setMatterData(matterData);
            return getMatterResponse;
        } catch (Exception e) {
            LOGGER.warn("exception getting matter: " + e.getMessage());
        }

        return null;
    }
}
