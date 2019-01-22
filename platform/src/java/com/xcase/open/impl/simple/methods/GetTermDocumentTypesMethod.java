/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.TermsApiWebProxy;
import com.xcase.open.transputs.GetTermDocumentTypesRequest;
import com.xcase.open.transputs.GetTermDocumentTypesResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetTermDocumentTypesMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetTermDocumentTypesMethod() {
        super();
//        LOGGER.debug("finishing GetTermDocumentTypesMethod()");
    }

    public GetTermDocumentTypesResponse getTermDocumentTypes(GetTermDocumentTypesRequest getTermDocumentTypesRequest) {
        LOGGER.debug("starting getTermDocumentTypes()");
        try {
            GetTermDocumentTypesResponse getTermDocumentTypesResponse = OpenResponseFactory.createGetTermDocumentTypesResponse();
            TermsApiWebProxy termsApiWebProxy = new TermsApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the GetTermDocumentTypes() method */
//            TermDocumentTypeData[] termDocumentTypeDataArray = termsApiWebProxy.GetTermDocumentTypes();
//            getTermDocumentTypesResponse.setTermDocumentTypeDataArray(termDocumentTypeDataArray);
//            return getTermDocumentTypesResponse;
        } catch (Exception e) {
            LOGGER.warn("exception getting term document types: " + e.getMessage());
        }

        return null;
    }
}
