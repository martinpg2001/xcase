/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceResponseFactory;
import com.xcase.salesforce.objects.SalesforceException;
import com.xcase.salesforce.transputs.CreateRecordRequest;
import com.xcase.salesforce.transputs.CreateRecordResponse;
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
public class CreateRecordMethod extends BaseSalesforceMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     * @param createRecordRequest
     * @return response
     * @throws IOException
     * @throws SalesforceException
     */
    public CreateRecordResponse createRecord(CreateRecordRequest createRecordRequest) throws IOException, SalesforceException {
        LOGGER.debug("starting createRecord()");
        CreateRecordResponse createRecordResponse = SalesforceResponseFactory.createCreateRecordResponse();
        LOGGER.debug("created create account response");
        String accessToken = createRecordRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String recordType = createRecordRequest.getRecordType();
        LOGGER.debug("recordType is " + recordType);
        String recordBody = createRecordRequest.getRecordBody();
        LOGGER.debug("recordBody is " + recordBody);
        StringBuffer urlBuff = super.getApiUrl("sobjects/" + recordType);
        String accountApiUrl = urlBuff.toString();
        LOGGER.debug("accountApiUrl is " + accountApiUrl);
        String bearerString = "Bearer " + accessToken;
        LOGGER.debug("bearerString is " + bearerString);
        Header authorizationHeader = new BasicHeader("Authorization", bearerString);
        LOGGER.debug("created Authorization header");
        Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
        LOGGER.debug("created Content-Type header");
        Header[] headers = {authorizationHeader, contentTypeHeader};
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        try {
            JsonElement jsonElement = httpManager.doJsonPost(accountApiUrl, headers, parameters, recordBody);
            if (!jsonElement.isJsonNull()) {
                LOGGER.debug("jsonElement is " + jsonElement.toString());
                JsonObject jsonObject = (JsonObject) jsonElement;
                JsonElement idElement = jsonObject.get("id");
                String id = idElement.getAsString();
                LOGGER.info("id is " + id);
                createRecordResponse.setRecordId(id);
            } else {
                String status = SalesforceConstant.STATUS_NOT_LOGGED_IN;
                createRecordResponse.setStatus(status);
            }
        } catch (Exception e) {
            throw new SalesforceException("Failed to parse to a document.", e);
        }

        return createRecordResponse;
    }
}
