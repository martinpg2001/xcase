/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.JsonElement;
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
     * @param deleteRecordRequest
     * @return response
     * @throws IOException
     * @throws SalesforceException
     */
    public DeleteRecordResponse deleteRecord(DeleteRecordRequest deleteRecordRequest) throws IOException, SalesforceException {
        LOGGER.debug("starting deleteRecord()");
        DeleteRecordResponse deleteRecordResponse = SalesforceResponseFactory.createDeleteRecordResponse();
        LOGGER.debug("created delete record response");
        String accessToken = deleteRecordRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String recordType = deleteRecordRequest.getRecordType();
        LOGGER.debug("recordType is " + recordType);
        String recordId = deleteRecordRequest.getRecordId();
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
            JsonElement jsonElement = httpManager.doJsonDelete(accountApiUrl, headers, parameters);
            if (!jsonElement.isJsonNull()) {
                LOGGER.debug("jsonElement is " + jsonElement.toString());
            } else {
                String status = SalesforceConstant.STATUS_NOT_LOGGED_IN;
                deleteRecordResponse.setStatus(status);
            }
        } catch (Exception e) {
            LOGGER.warn("catching exception: " + e.getMessage());
            throw new SalesforceException("Failed to parse to a document.", e);
        }

        return deleteRecordResponse;
    }
}
