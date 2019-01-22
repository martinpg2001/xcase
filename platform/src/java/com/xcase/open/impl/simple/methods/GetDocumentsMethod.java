/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.TermsApiWebProxy;
import com.xcase.open.transputs.GetDocumentsRequest;
import com.xcase.open.transputs.GetDocumentsResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetDocumentsMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetDocumentsMethod() {
        super();
//        LOGGER.debug("finishing GetDocumentsMethod()");
    }

    public GetDocumentsResponse getDocuments(GetDocumentsRequest getDocumentsRequest) {
        LOGGER.debug("starting getDocuments()");
        try {
            GetDocumentsResponse getDocumentsResponse = OpenResponseFactory.createGetDocumentsResponse();
            TermsApiWebProxy termsApiWebProxy = new TermsApiWebProxy(new URL(swaggerApiUrl));
            String clientId = getDocumentsRequest.getClientId();
            String matterId = getDocumentsRequest.getMatterId();
            /* Invoke the GetDocuments() method */
//            TermDocumentData[] termDocumentDataArray = termsApiWebProxy.GetDocuments(clientId, matterId);
//            return getDocumentsResponse;
        } catch (Exception e) {
            LOGGER.warn("exception getting documents: " + e.getMessage());
        }

        return null;
    }
}
