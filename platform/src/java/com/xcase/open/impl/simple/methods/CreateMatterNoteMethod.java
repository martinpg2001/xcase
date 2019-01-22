/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateNoteData;
import com.xcase.open.transputs.CreateMatterNoteRequest;
import com.xcase.open.transputs.CreateMatterNoteResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateMatterNoteMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateMatterNoteMethod() {
        super();
//        LOGGER.debug("finishing CreateMatterNoteMethod()");
    }

    public CreateMatterNoteResponse createMatterNote(CreateMatterNoteRequest createNoteRequest) {
        LOGGER.debug("starting createMatterNote()");
        try {
            String entityId = createNoteRequest.getEntityId();
            CreateNoteData createNoteData = createNoteRequest.getCreateNoteData();
            String parentEntityId = createNoteRequest.getParentEntityId();
            CreateMatterNoteResponse createNoteResponse = OpenResponseFactory.createCreateMatterNoteResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateMatterNote() method */
            int noteID = commonApiWebProxy.CreateMatterNote(entityId, createNoteData, parentEntityId);
            LOGGER.debug("noteID is " + noteID);
            createNoteResponse.setId(noteID);
            return createNoteResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating note: " + e.getMessage());
        }

        return null;
    }
}
