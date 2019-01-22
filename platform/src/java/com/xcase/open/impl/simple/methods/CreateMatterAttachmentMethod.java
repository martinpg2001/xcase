/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateAttachmentData;
import com.xcase.open.transputs.CreateMatterAttachmentRequest;
import com.xcase.open.transputs.CreateMatterAttachmentResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateMatterAttachmentMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateMatterAttachmentMethod() {
        super();
//        LOGGER.debug("finishing CreateMatterAttachmentMethod()");
    }

    public CreateMatterAttachmentResponse createMatterAttachment(CreateMatterAttachmentRequest createAttachmentRequest) {
        LOGGER.debug("starting createMatterAttachment()");
        try {
            CreateMatterAttachmentResponse createAttachmentResponse = OpenResponseFactory.createCreateMatterAttachmentResponse();
            String entityId = createAttachmentRequest.getEntityId();
            CreateAttachmentData createAttachmentData = createAttachmentRequest.getCreateAttachmentData();
            String parentEntityId = createAttachmentRequest.getParentEntityId();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateMatterAttachment() method */
            int attachmentID = commonApiWebProxy.CreateMatterAttachment(entityId, createAttachmentData, parentEntityId);
            LOGGER.debug("attachmentID is " + attachmentID);
            createAttachmentResponse.setId(attachmentID);
            return createAttachmentResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating matter attachment: " + e.getMessage());
        }

        return null;
    }
}
