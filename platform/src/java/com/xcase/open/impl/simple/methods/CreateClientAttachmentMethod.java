/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateAttachmentData;
import com.xcase.open.impl.simple.core.CreateAttachmententityType;
import com.xcase.open.transputs.CreateClientAttachmentRequest;
import com.xcase.open.transputs.CreateClientAttachmentResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateClientAttachmentMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateClientAttachmentMethod() {
        super();
//        LOGGER.debug("finishing CreateAttachmentMethod()");
    }

    public CreateClientAttachmentResponse createClientAttachment(CreateClientAttachmentRequest createAttachmentRequest) {
        LOGGER.debug("starting createClientAttachment()");
        try {
            CreateClientAttachmentResponse createAttachmentResponse = OpenResponseFactory.createCreateClientAttachmentResponse();
            CreateAttachmententityType createAttachmententityType = createAttachmentRequest.getCreateAttachmententityType();
            String entityId = createAttachmentRequest.getEntityId();
            CreateAttachmentData createAttachmentData = createAttachmentRequest.getCreateAttachmentData();
            String parentEntityId = createAttachmentRequest.getParentEntityId();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateClientAttachment() method */
            int attachmentID = commonApiWebProxy.CreateClientAttachment(entityId, createAttachmentData);
            LOGGER.debug("attachmentID is " + attachmentID);
            createAttachmentResponse.setId(attachmentID);
            return createAttachmentResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating client attachment: " + e.getMessage());
        }

        return null;
    }
}
