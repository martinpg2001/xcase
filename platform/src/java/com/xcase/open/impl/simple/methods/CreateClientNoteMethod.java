/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateNoteData;
import com.xcase.open.transputs.CreateClientNoteRequest;
import com.xcase.open.transputs.CreateClientNoteResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateClientNoteMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateClientNoteMethod() {
        super();
//        LOGGER.debug("finishing CreateClientNoteMethod()");
    }

    public CreateClientNoteResponse createClientNote(CreateClientNoteRequest createNoteRequest) {
        LOGGER.debug("starting createClientNote()");
        try {
            String entityId = createNoteRequest.getEntityId();
            CreateNoteData createNoteData = createNoteRequest.getCreateNoteData();
            CreateClientNoteResponse createNoteResponse = OpenResponseFactory.createCreateClientNoteResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreateClientNote() method */
            int noteID = commonApiWebProxy.CreateClientNote(entityId, createNoteData);
            LOGGER.debug("noteID is " + noteID);
            createNoteResponse.setId(noteID);
            return createNoteResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating client note: " + e.getMessage());
        }

        return null;
    }
}
