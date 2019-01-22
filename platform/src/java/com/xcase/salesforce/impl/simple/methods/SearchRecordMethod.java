/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
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
     * @param searchRecordRequest
     * @return response
     * @throws IOException
     * @throws SalesforceException
     */
    public SearchRecordResponse searchRecord(SearchRecordRequest searchRecordRequest) throws IOException, SalesforceException {
        LOGGER.debug("info searchRecord()");
        SearchRecordResponse searchRecordResponse = SalesforceResponseFactory.createSearchRecordResponse();
        LOGGER.debug("created search record response");
        String accessToken = searchRecordRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String searchString = searchRecordRequest.getSearchString();
        LOGGER.debug("searchString is " + searchString);
        StringBuffer urlBuff = super.getApiUrl("search/?q=" + searchString);
        String accountApiUrl = urlBuff.toString();
        LOGGER.debug("accountApiUrl is " + accountApiUrl);
        String bearerString = "Bearer " + accessToken;
        LOGGER.debug("bearerString is " + bearerString);
        Header header = new BasicHeader("Authorization", bearerString);
        LOGGER.debug("created Authorization header");
        Header[] headers = {header};
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        //parameters.add(new BasicNameValuePair("q", searchString));
        try {
            JsonElement jsonElement = httpManager.doJsonGet(accountApiUrl, headers, parameters);
            if (!jsonElement.isJsonNull()) {
                LOGGER.debug("jsonElement is " + jsonElement.toString());
                JsonArray jsonArray = (JsonArray) jsonElement;
                int jsonArraySize = jsonArray.size();
                LOGGER.info("jsonArraySize is " + jsonArraySize);
                Iterator<JsonElement> recordsIterator = jsonArray.iterator();
                while (recordsIterator.hasNext()) {
                    JsonElement recordElement = recordsIterator.next();
                    LOGGER.debug("recordElement is " + recordElement.toString());
                }
            } else {
                String status = SalesforceConstant.STATUS_NOT_LOGGED_IN;
                searchRecordResponse.setStatus(status);
            }
        } catch (Exception e) {
            throw new SalesforceException("Failed to parse to a document.", e);
        }

        return searchRecordResponse;
    }
}
