/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceResponseFactory;
import com.xcase.salesforce.objects.SalesforceException;
import com.xcase.salesforce.transputs.QueryRecordRequest;
import com.xcase.salesforce.transputs.QueryRecordResponse;
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
public class QueryRecordMethod extends BaseSalesforceMethod {

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
    public QueryRecordResponse queryRecord(QueryRecordRequest request) throws IOException, SalesforceException {
        LOGGER.debug("starting queryRecord()");
        QueryRecordResponse response = SalesforceResponseFactory.createQueryRecordResponse();
        LOGGER.debug("created query account response");
        try {
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            String queryString = request.getQueryString();
            LOGGER.debug("queryString is " + queryString);
            StringBuffer urlBuff = super.getApiUrl("query/?q=" + queryString);
            String endPoint = urlBuff.toString();
            LOGGER.debug("endPoint is " + endPoint);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header[] headers = { header };
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, parameters, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                handleExpectedResponseCode(response, commonHttpResponse);
            } else {
                handleUnexpectedResponseCode(response, commonHttpResponse);
            }

            JsonElement jsonElement = response.getJsonElement();
            if (!jsonElement.isJsonNull()) {
                LOGGER.debug("jsonElement is " + jsonElement.toString());
                JsonObject jsonObject = (JsonObject) jsonElement;
                LOGGER.debug("cast jsonElement to jsonObject");
                JsonElement totalSizeElement = jsonObject.get("totalSize");
                if (totalSizeElement != null) {
                    int totalSize = totalSizeElement.getAsInt();
                    LOGGER.debug("totalSize is " + totalSize);
                } else {
                    LOGGER.debug("totalSizeElement is null");
                }

                JsonArray recordsArray = jsonObject.getAsJsonArray("records");
                if (recordsArray != null) {
                    int recordsSize = recordsArray.size();
                    LOGGER.debug("recordsSize is " + recordsSize);
                    Iterator<JsonElement> recordsIterator = recordsArray.iterator();
                    while (recordsIterator.hasNext()) {
                        JsonElement recordElement = recordsIterator.next();
                        LOGGER.debug("recordElement is " + recordElement.toString());
                    }
                } else {
                    LOGGER.debug("recordsArray is null");
                }
            }
        } catch (Exception e) {
            handleUnexpectedException(response, e);
        }

        return response;
    }
}
