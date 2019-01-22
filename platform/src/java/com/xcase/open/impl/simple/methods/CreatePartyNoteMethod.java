/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.impl.simple.core.CreateNoteData;
import com.xcase.open.transputs.CreatePartyNoteRequest;
import com.xcase.open.transputs.CreatePartyNoteResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreatePartyNoteMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreatePartyNoteMethod() {
        super();
//        LOGGER.debug("finishing CreatePartyNoteMethod()");
    }

    public CreatePartyNoteResponse createPartyNote(CreatePartyNoteRequest createNoteRequest) {
        LOGGER.debug("starting createPartyNote()");
        try {
            String entityId = createNoteRequest.getEntityId();
            CreateNoteData createNoteData = createNoteRequest.getCreateNoteData();
            CreatePartyNoteResponse createNoteResponse = OpenResponseFactory.createCreatePartyNoteResponse();
            CommonApiWebProxy commonApiWebProxy = new CommonApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the CreatePartyNote() method */
            int noteID = commonApiWebProxy.CreatePartyNote(entityId, createNoteData);
            LOGGER.debug("noteID is " + noteID);
            createNoteResponse.setId(noteID);
            return createNoteResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating party note: " + e.getMessage());
        }

        return null;
    }
}
