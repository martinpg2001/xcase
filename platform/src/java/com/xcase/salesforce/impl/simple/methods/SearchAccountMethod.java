/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceResponseFactory;
import com.xcase.salesforce.objects.SalesforceException;
import com.xcase.salesforce.transputs.SearchAccountRequest;
import com.xcase.salesforce.transputs.SearchAccountResponse;
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
public class SearchAccountMethod extends BaseSalesforceMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     * @param searchAccountRequest
     * @return response
     * @throws IOException
     * @throws SalesforceException
     */
    public SearchAccountResponse searchAccount(SearchAccountRequest searchAccountRequest) throws IOException, SalesforceException {
        LOGGER.debug("starting searchAccount()");
        SearchAccountResponse searchAccountResponse = SalesforceResponseFactory.createSearchAccountResponse();
        LOGGER.debug("created search account response");
        String accessToken = searchAccountRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String searchString = searchAccountRequest.getSearchString();
        LOGGER.debug("searchString is " + searchString);
        StringBuffer urlBuff = super.getApiUrl("query/?q=" + searchString);
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
                JsonObject jsonObject = (JsonObject) jsonElement;
                JsonElement totalSizeElement = jsonObject.get("totalSize");
                int totalSize = totalSizeElement.getAsInt();
                LOGGER.debug("totalSize is " + totalSize);
                JsonArray recordsArray = jsonObject.getAsJsonArray("records");
                int recordsSize = recordsArray.size();
                LOGGER.debug("recordsSize is " + recordsSize);
                Iterator<JsonElement> recordsIterator = recordsArray.iterator();
                while (recordsIterator.hasNext()) {
                    JsonElement recordElement = recordsIterator.next();
                    LOGGER.debug("recordElement is " + recordElement.toString());
                }
            } else {
                String status = SalesforceConstant.STATUS_NOT_LOGGED_IN;
                searchAccountResponse.setStatus(status);
            }
        } catch (Exception e) {
            throw new SalesforceException("Failed to parse to a document.", e);
        }

        return searchAccountResponse;
    }
}
