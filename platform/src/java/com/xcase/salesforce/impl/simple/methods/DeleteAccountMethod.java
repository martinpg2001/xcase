/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.JsonElement;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceResponseFactory;
import com.xcase.salesforce.objects.SalesforceException;
import com.xcase.salesforce.transputs.DeleteAccountRequest;
import com.xcase.salesforce.transputs.DeleteAccountResponse;
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
public class DeleteAccountMethod extends BaseSalesforceMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     * @param deleteAccountRequest
     * @return response
     * @throws IOException
     * @throws SalesforceException
     */
    public DeleteAccountResponse deleteAccount(DeleteAccountRequest deleteAccountRequest) throws IOException, SalesforceException {
        LOGGER.debug("starting deleteAccount()");
        DeleteAccountResponse deleteAccountResponse = SalesforceResponseFactory.createDeleteAccountResponse();
        LOGGER.debug("created search account response");
        String accessToken = deleteAccountRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String accountId = deleteAccountRequest.getAccountId();
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
            JsonElement jsonElement = httpManager.doJsonDelete(accountApiUrl, headers, parameters);
            if (!jsonElement.isJsonNull()) {
                LOGGER.debug("jsonElement is " + jsonElement.toString());
            } else {
                String status = SalesforceConstant.STATUS_NOT_LOGGED_IN;
                deleteAccountResponse.setStatus(status);
            }
        } catch (Exception e) {
            throw new SalesforceException("Failed to parse to a document.", e);
        }

        return deleteAccountResponse;
    }
}
