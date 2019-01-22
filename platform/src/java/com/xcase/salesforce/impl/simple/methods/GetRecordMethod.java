/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceResponseFactory;
import com.xcase.salesforce.impl.simple.objects.SalesforceRecordImpl;
import com.xcase.salesforce.objects.SalesforceException;
import com.xcase.salesforce.transputs.GetRecordRequest;
import com.xcase.salesforce.transputs.GetRecordResponse;
import java.io.*;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class GetRecordMethod extends BaseSalesforceMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     * @param getRecordRequest
     * @return response
     * @throws IOException
     * @throws SalesforceException
     */
    public GetRecordResponse getRecord(GetRecordRequest getRecordRequest) throws IOException, SalesforceException {
        LOGGER.debug("starting getRecord()");
        GetRecordResponse getRecordResponse = SalesforceResponseFactory.createGetRecordResponse();
        LOGGER.debug("created get record response");
        String accessToken = getRecordRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String recordType = getRecordRequest.getRecordType();
        LOGGER.debug("recordType is " + recordType);
        String recordId = getRecordRequest.getRecordId();
        LOGGER.debug("recordId is " + recordId);
        StringBuffer urlBuff = super.getApiUrl("sobjects/" + recordType);
        urlBuff.append("/" + recordId);
        String accountApiUrl = urlBuff.toString();
        LOGGER.debug("accountApiUrl is " + accountApiUrl);
        String bearerString = "Bearer " + accessToken;
        LOGGER.debug("bearerString is " + bearerString);
        Header header = new BasicHeader("Authorization", bearerString);
        LOGGER.debug("created Authorization header");
        Header[] headers = {header};
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        try {
            JsonElement jsonElement = httpManager.doJsonGet(accountApiUrl, headers, parameters);
            if (!jsonElement.isJsonNull()) {
                LOGGER.debug("jsonElement is " + jsonElement.toString());
                JsonObject jsonObject = (JsonObject) jsonElement;
                SalesforceRecordImpl salesforceRecordImpl = SalesforceRecordImpl.CreateSalesforceRecordImpl(jsonObject);
                LOGGER.debug("created salesforceRecordImpl from jsonObject");
                String recordName = salesforceRecordImpl.getName();
                LOGGER.info("recordName is " + recordName);
            } else {
                String status = SalesforceConstant.STATUS_NOT_LOGGED_IN;
                getRecordResponse.setStatus(status);
            }
        } catch (Exception e) {
            LOGGER.warn("catching exception: " + e.getMessage());
            throw new SalesforceException("Failed to parse to a document.", e);
        }

        return getRecordResponse;
    }
}
