/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceResponseFactory;
import com.xcase.salesforce.objects.SalesforceException;
import com.xcase.salesforce.transputs.UpdateRecordRequest;
import com.xcase.salesforce.transputs.UpdateRecordResponse;
import java.io.IOException;
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
public class UpdateRecordMethod extends BaseSalesforceMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     * @param updateRecordRequest
     * @return response
     * @throws IOException
     * @throws SalesforceException
     */
    public UpdateRecordResponse updateRecord(UpdateRecordRequest updateRecordRequest) throws IOException, SalesforceException {
        LOGGER.debug("starting updateRecord()");
        UpdateRecordResponse updateRecordResponse = SalesforceResponseFactory.createUpdateRecordResponse();
        LOGGER.debug("created update record response");
        String accessToken = updateRecordRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String recordType = updateRecordRequest.getRecordType();
        LOGGER.debug("recordType is " + recordType);
        String recordId = updateRecordRequest.getRecordId();
        LOGGER.debug("recordId is " + recordId);
        String recordBody = updateRecordRequest.getRecordBody();
        LOGGER.debug("recordBody is " + recordBody);
        /* TODO: work out how to construct requestBody to update record properties */
        StringBuffer urlBuff = super.getApiUrl("sobjects/" + recordType + "/" + recordId + "?_HttpMethod=PATCH");
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
            } else {
                String status = SalesforceConstant.STATUS_NOT_LOGGED_IN;
                updateRecordResponse.setStatus(status);
            }
        } catch (Exception e) {
            throw new SalesforceException("Failed to parse to a document.", e);
        }

        return updateRecordResponse;
    }
}
