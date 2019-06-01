/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.JsonElement;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceResponseFactory;
import com.xcase.salesforce.objects.SalesforceException;
import com.xcase.salesforce.transputs.DeleteRecordRequest;
import com.xcase.salesforce.transputs.DeleteRecordResponse;
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
public class DeleteRecordMethod extends BaseSalesforceMethod {

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
    public DeleteRecordResponse deleteRecord(DeleteRecordRequest request) throws IOException, SalesforceException {
        LOGGER.debug("starting deleteRecord()");
        DeleteRecordResponse response = SalesforceResponseFactory.createDeleteRecordResponse();
        LOGGER.debug("created delete record response");
        try {
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            String recordType = request.getRecordType();
            LOGGER.debug("recordType is " + recordType);
            String recordId = request.getRecordId();
            LOGGER.debug("recordId is " + recordId);
            StringBuffer urlBuff = super.getApiUrl("sobjects/" + recordType);
            urlBuff.append("/" + recordId);
            String endPoint = urlBuff.toString();
            LOGGER.debug("endPoint is " + endPoint);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header[] headers = { header };
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseDelete(endPoint, headers,
                    parameters, null, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 204) {
                handleExpectedResponseCode(response, commonHttpResponse);
                JsonElement jsonElement = response.getJsonElement();
                if (!jsonElement.isJsonNull()) {
                    LOGGER.debug("jsonElement is " + jsonElement.toString());
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
