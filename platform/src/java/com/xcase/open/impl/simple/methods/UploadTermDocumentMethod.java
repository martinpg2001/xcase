/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.TermsApiWebProxy;
import com.xcase.open.transputs.UploadTermDocumentRequest;
import com.xcase.open.transputs.UploadTermDocumentResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UploadTermDocumentMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UploadTermDocumentMethod() {
        super();
//        LOGGER.debug("finishing UploadTermDocumentMethod()");
    }

    public UploadTermDocumentResponse uploadTermDocument(UploadTermDocumentRequest uploadTermDocumentRequest) {
        LOGGER.debug("starting uploadTermDocument()");
        try {
            UploadTermDocumentResponse uploadTermDocumentResponse = OpenResponseFactory.createUploadTermDocumentResponse();
            TermsApiWebProxy termsApiWebProxy = new TermsApiWebProxy(new URL(swaggerApiUrl));
//            TermDocumentUpload termDocumentUpload = uploadTermDocumentRequest.getTermDocumentUpload();
//            /* Invoke the UploadTermDocument() method */
//            int termDocumentId = termsApiWebProxy.UploadTermDocument(termDocumentUpload);
//            uploadTermDocumentResponse.setTermDocumentId(termDocumentId);
//            return uploadTermDocumentResponse;
        } catch (Exception e) {
            LOGGER.warn("exception uploading term document: " + e.getMessage());
        }

        return null;
    }
}
