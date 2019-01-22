/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateAttachmentData;
import com.xcase.open.transputs.CreatePartyAttachmentRequest;
import com.xcase.open.transputs.CreatePartyAttachmentResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreatePartyAttachmentMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreatePartyAttachmentMethod() {
        super();
//        LOGGER.debug("finishing CreatePartyAttachmentMethod()");
    }

    public CreatePartyAttachmentResponse createPartyAttachment(CreatePartyAttachmentRequest createAttachmentRequest) {
        LOGGER.debug("starting createPartyAttachment()");
        try {
            CreatePartyAttachmentResponse createAttachmentResponse = OpenResponseFactory.createCreatePartyAttachmentResponse();
            String entityId = createAttachmentRequest.getEntityId();
            CreateAttachmentData createAttachmentData = createAttachmentRequest.getCreateAttachmentData();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreatePartyAttachment() method */
            int attachmentID = commonApiWebProxy.CreatePartyAttachment(entityId, createAttachmentData);
            LOGGER.debug("attachmentID is " + attachmentID);
            createAttachmentResponse.setId(attachmentID);
            return createAttachmentResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating party attachment: " + e.getMessage());
        }

        return null;
    }
}
