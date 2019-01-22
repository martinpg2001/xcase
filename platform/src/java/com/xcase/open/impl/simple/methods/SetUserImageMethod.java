/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.SetUserImageRequest;
import com.xcase.open.transputs.SetUserImageResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class SetUserImageMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public SetUserImageMethod() {
        super();
//        LOGGER.debug("finishing SetUserImageMethod()");
    }

    public SetUserImageResponse setUserImage(SetUserImageRequest setUserImageRequest) {
        LOGGER.debug("starting setUserImage()");
        try {
            String userId = setUserImageRequest.getUserId();
            String image = setUserImageRequest.getImage();
            SetUserImageResponse setUserImageResponse = OpenResponseFactory.createSetUserImageResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the SetUserImage() method */
            commonApiWebProxy.SetUserImage(userId, image);
            LOGGER.debug("set user roles");
            return setUserImageResponse;
        } catch (Exception e) {
            LOGGER.warn("exception setting user roles: " + e.getMessage());
        }

        return null;
    }
}
