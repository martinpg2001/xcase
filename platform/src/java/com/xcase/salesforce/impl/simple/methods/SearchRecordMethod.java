/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceResponseFactory;
import com.xcase.salesforce.objects.SalesforceException;
import com.xcase.salesforce.transputs.SearchRecordRequest;
import com.xcase.salesforce.transputs.SearchRecordResponse;
import java.io.*;
import java.lang.invoke.*;
import java.util.*;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class SearchRecordMethod extends BaseSalesforceMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     * @param request
     * @return response
     * @throws IOException
     * @throws SalesforceException
     */
    public SearchRecordResponse searchRecord(SearchRecordRequest request) throws IOException, SalesforceException {
        LOGGER.debug("starting searchRecord()");
        SearchRecordResponse response = SalesforceResponseFactory.createSearchRecordResponse();
        LOGGER.debug("created search record response");
        try {
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            String searchString = request.getSearchString();
            LOGGER.debug("searchString is " + searchString);
            StringBuffer urlBuff = super.getApiUrl("search/?q=" + searchString);
            String endPoint = urlBuff.toString();
            LOGGER.debug("endPoint is " + endPoint);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header[] headers = { header };
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, parameters,
                    null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                handleExpectedResponseCode(response, commonHttpResponse);
            } else {
                handleUnexpectedResponseCode(response, commonHttpResponse);
            }
        } catch (Exception e) {
            handleUnexpectedException(response, e);
        }

        return response;
    }
}
