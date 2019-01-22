/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.TermsApiWebProxy;
import com.xcase.open.transputs.GetTermRequest;
import com.xcase.open.transputs.GetTermResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetTermMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetTermMethod() {
        super();
//        LOGGER.debug("finishing GetTermMethod()");
    }

    public GetTermResponse getTerm(GetTermRequest getTermRequest) {
        LOGGER.debug("starting getTerm()");
        try {
            GetTermResponse getTermResponse = OpenResponseFactory.createGetTermResponse();
            TermsApiWebProxy termsApiWebProxy = new TermsApiWebProxy(new URL(swaggerApiUrl));
            String clientId = getTermRequest.getClientId();
            String matterId = getTermRequest.getMatterId();
            String definitionName = getTermRequest.getDefinitionName();
            /* Invoke the GetTerm() method */
//            TermData termData = termsApiWebProxy.GetTerm(clientId, matterId, definitionName);
//            LOGGER.debug("got term");
//            getTermResponse.setTermData(termData);
//            return getTermResponse;
        } catch (Exception e) {
            LOGGER.warn("exception getting term: " + e.getMessage());
        }

        return null;
    }
}
