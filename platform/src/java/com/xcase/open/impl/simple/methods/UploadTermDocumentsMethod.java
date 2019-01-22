/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.TermsApiWebProxy;
import com.xcase.open.transputs.UploadTermDocumentsRequest;
import com.xcase.open.transputs.UploadTermDocumentsResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UploadTermDocumentsMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UploadTermDocumentsMethod() {
        super();
//        LOGGER.debug("finishing UploadTermDocumentsMethod()");
    }

    public UploadTermDocumentsResponse uploadTermDocuments(UploadTermDocumentsRequest uploadTermDocumentsRequest) {
        LOGGER.debug("starting uploadTermDocuments()");
        try {
            UploadTermDocumentsResponse uploadTermDocumentsResponse = OpenResponseFactory.createUploadTermDocumentsResponse();
            TermsApiWebProxy termsApiWebProxy = new TermsApiWebProxy(new URL(swaggerApiUrl));
//            TermDocumentUploadData termDocumentUploadData = uploadTermDocumentsRequest.getTermDocumentUploadData();
//            /* Invoke the UploadTermDocuments() method */
//            termsApiWebProxy.UploadTermDocuments(termDocumentUploadData);
//            LOGGER.debug("uploaded term documents");
//            return uploadTermDocumentsResponse;
        } catch (Exception e) {
            LOGGER.warn("exception uploading term documents: " + e.getMessage());
        }

        return null;
    }
}
