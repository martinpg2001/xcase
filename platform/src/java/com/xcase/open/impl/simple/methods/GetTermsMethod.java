/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.TermData;
import com.xcase.open.impl.simple.core.TermsApiWebProxy;
import com.xcase.open.transputs.GetTermsRequest;
import com.xcase.open.transputs.GetTermsResponse;
import java.lang.invoke.*;
import java.net.*;
import java.util.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetTermsMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetTermsMethod() {
        super();
//        LOGGER.debug("finishing GetTermsMethod()");
    }

    public GetTermsResponse getTerms(GetTermsRequest getTermsRequest) {
        LOGGER.debug("starting getTerms()");
        try {
            GetTermsResponse getTermsResponse = OpenResponseFactory.createGetTermsResponse();
            TermsApiWebProxy termsApiWebProxy = new TermsApiWebProxy(new URL(swaggerApiUrl));
            String filter__category = getTermsRequest.getFilterCategory();
            String filter__client = getTermsRequest.getFilterClient();
            String filter__matter = getTermsRequest.getFilterMatter();
            String filter__name = getTermsRequest.getFilterName();
            Date filter__effectiveStartDate = getTermsRequest.getFilterEffectiveStartDate();
            Date filter__effectiveEndDate = getTermsRequest.getFilterEffectiveEndDate();
            /* Invoke the GetTerms() method */
            TermData[] termDataArray = termsApiWebProxy.GetTerms(filter__category, filter__client, filter__matter, filter__name, filter__effectiveStartDate, filter__effectiveEndDate);
            LOGGER.debug("got terms");
            getTermsResponse.setTermDataArray(termDataArray);
            return getTermsResponse;
        } catch (Exception e) {
            LOGGER.warn("exception getting terms: " + e.getMessage());
        }

        return null;
    }
}
