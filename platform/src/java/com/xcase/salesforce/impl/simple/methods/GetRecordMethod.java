/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
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
     * @param request
     * @return response
     * @throws IOException
     * @throws SalesforceException
     */
    public GetRecordResponse getRecord(GetRecordRequest request) throws IOException, SalesforceException {
        LOGGER.debug("starting getRecord()");
        GetRecordResponse response = SalesforceResponseFactory.createGetRecordResponse();
        LOGGER.debug("created get record response");
        try {
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            String recordType = request.getRecordType();
            LOGGER.debug("recordType is " + recordType);
            String recordId = request.getRecordId();
            LOGGER.debug("recordId is " + recordId);
            StringBuffer urlBuffer = null;
            if (recordId != null && !recordId.isEmpty()) {
                LOGGER.debug("recordId is neither null nor empty");
                urlBuffer = super.getApiUrl("sobjects/");
                urlBuffer.append(recordType + "/" + recordId);
            } else if (request.getRecordUrl() != null) {
                LOGGER.debug("recordUrl is not null");
                String recordUrl = request.getRecordUrl();
                LOGGER.debug("recordUrl is " + recordUrl);
                urlBuffer = new StringBuffer(this.apiUrlPrefix);
                urlBuffer.append(recordUrl);
            } else {
                LOGGER.warn("neither recordId nor recordUrl is provided");
            }

            String endPoint = urlBuffer.toString();
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
