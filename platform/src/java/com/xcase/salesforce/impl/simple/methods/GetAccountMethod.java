/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceResponseFactory;
import com.xcase.salesforce.impl.simple.objects.SalesforceAccountImpl;
import com.xcase.salesforce.objects.SalesforceException;
import com.xcase.salesforce.transputs.GetAccountRequest;
import com.xcase.salesforce.transputs.GetAccountResponse;
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
public class GetAccountMethod extends BaseSalesforceMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     * @param getAccountRequest
     * @return response
     * @throws IOException
     * @throws SalesforceException
     */
    public GetAccountResponse getAccount(GetAccountRequest getAccountRequest) throws IOException, SalesforceException {
        LOGGER.debug("starting getAccount()");
        GetAccountResponse getAccountResponse = SalesforceResponseFactory.createGetAccountResponse();
        LOGGER.debug("created account response");
        String accessToken = getAccountRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String accountId = getAccountRequest.getAccountId();
        LOGGER.debug("accountId is " + accountId);
        StringBuffer urlBuff = super.getApiUrl("sobjects/Account");
        urlBuff.append("/" + accountId);
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
                SalesforceAccountImpl salesforceAccountImpl = SalesforceAccountImpl.CreateSalesforceAccountImpl(jsonObject);
                LOGGER.debug("created salesforceAccountImpl from jsonObject");
                String accountName = salesforceAccountImpl.getName();
                LOGGER.debug("accountName is " + accountName);
            } else {
                String status = SalesforceConstant.STATUS_NOT_LOGGED_IN;
                getAccountResponse.setStatus(status);
            }
        } catch (Exception e) {
            throw new SalesforceException("Failed to parse to a document.", e);
        }

        return getAccountResponse;
    }
}
