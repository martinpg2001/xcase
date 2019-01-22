/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.TermsApiWebProxy;
import com.xcase.open.transputs.GetTermCategoriesRequest;
import com.xcase.open.transputs.GetTermCategoriesResponse;
import java.lang.invoke.*;
import java.net.URL;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetTermCategoriesMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetTermCategoriesMethod() {
        super();
//        LOGGER.debug("finishing GetTermCategoriesMethod()");
    }

    public GetTermCategoriesResponse getTermCategories(GetTermCategoriesRequest getTermCategoriesRequest) {
        LOGGER.debug("starting getTermCategories()");
        try {
            GetTermCategoriesResponse getTermCategoriesResponse = OpenResponseFactory.createGetTermCategoriesResponse();
            TermsApiWebProxy termsApiWebProxy = new TermsApiWebProxy(new URL(swaggerApiUrl));
            /* Invoke the GetTermCategories() method */
//            TermCategoryData[] termCategoryDataArray = termsApiWebProxy.GetTermCategories();
//            getTermCategoriesResponse.setTermCategoryDataArray(termCategoryDataArray);
//            return getTermCategoriesResponse;
        } catch (Exception e) {
            LOGGER.warn("exception getting term categories: " + e.getMessage());
        }

        return null;
    }
}
