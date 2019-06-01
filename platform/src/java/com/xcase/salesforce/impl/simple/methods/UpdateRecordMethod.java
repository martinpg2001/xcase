/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
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
     * @param request
     * @return response
     * @throws IOException
     * @throws SalesforceException
     */
    public UpdateRecordResponse updateRecord(UpdateRecordRequest request) throws IOException, SalesforceException {
        LOGGER.debug("starting updateRecord()");
        UpdateRecordResponse response = SalesforceResponseFactory.createUpdateRecordResponse();
        LOGGER.debug("created update record response");
        try {
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            String recordType = request.getRecordType();
            LOGGER.debug("recordType is " + recordType);
            String recordId = request.getRecordId();
            LOGGER.debug("recordId is " + recordId);
            String recordBody = request.getRecordBody();
            LOGGER.debug("recordBody is " + recordBody);
            /* TODO: work out how to construct requestBody to update record properties */
            StringBuffer urlBuff = super.getApiUrl("sobjects/" + recordType + "/" + recordId + "?_HttpMethod=PATCH");
            String endPoint = urlBuff.toString();
            LOGGER.debug("endPoint is " + endPoint);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header authorizationHeader = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
            LOGGER.debug("created Content-Type header");
            Header[] headers = { authorizationHeader, contentTypeHeader };
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponsePost(endPoint, headers, parameters, recordBody, 
                    null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 201) {
                handleExpectedResponseCode(response, commonHttpResponse);
                JsonElement jsonElement = response.getJsonElement();
                if (!jsonElement.isJsonNull()) {
                    LOGGER.debug("jsonElement is " + jsonElement.toString());
                    JsonObject jsonObject = (JsonObject) jsonElement;
                }
            } else {
                handleUnexpectedResponseCode(response, commonHttpResponse);
            }
        } catch (Exception e) {
            handleUnexpectedException(response, e);
        }

        return response;
    }
}
