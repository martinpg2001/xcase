/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.TermsApiWebProxy;
import com.xcase.open.transputs.GetTermsForEntityRequest;
import com.xcase.open.transputs.GetTermsForEntityResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetTermsForEntityMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetTermsForEntityMethod() {
        super();
//        LOGGER.debug("finishing GetTermsForEntityMethod()");
    }

    public GetTermsForEntityResponse getTermsForEntity(GetTermsForEntityRequest getTermsForEntityRequest) {
        LOGGER.debug("starting getTermsForEntity()");
        try {
            GetTermsForEntityResponse getTermsForEntityResponse = OpenResponseFactory.createGetTermsForEntityResponse();
            TermsApiWebProxy termsApiWebProxy = new TermsApiWebProxy(new URL(swaggerApiUrl));
            String client = getTermsForEntityRequest.getClientId();
            LOGGER.debug("client is " + client);
            String matter = getTermsForEntityRequest.getMatterId();
            LOGGER.debug("matter is " + matter);
            String categoryName = getTermsForEntityRequest.getCategoryName();
            LOGGER.debug("categoryName is " + categoryName);
            String definitionName = getTermsForEntityRequest.getDefinitionName();
            LOGGER.debug("definitionName is " + definitionName);
            /* Invoke the GetTermsForEntity() method */
//            TermData[] termDataArray = termsApiWebProxy.GetTermsForEntity(client, matter, categoryName, definitionName);
//            getTermsForEntityResponse.setTermDataArray(termDataArray);
//            return getTermsForEntityResponse;
        } catch (Exception e) {
            LOGGER.warn("exception getting terms for entity: " + e.getMessage());
        }

        return null;
    }
}
