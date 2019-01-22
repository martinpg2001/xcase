/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce;

import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceRequestFactory;
import com.xcase.salesforce.transputs.GetAccessTokenRequest;
import com.xcase.salesforce.transputs.GetAccessTokenResponse;
import com.xcase.salesforce.transputs.GetAccountRequest;
import com.xcase.salesforce.transputs.GetAccountResponse;
import java.lang.invoke.*;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class SalesforceApplication {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static final String CLIENT_ID = "3MVG9Iu66FKeHhIPECItckL5nX.eyqm9SXdjRLjQW9bx9VrbJVRI6W9tvmFM5GaVGAkwfxdRUoAN5Cc1LZ06F";
    public static final String CLIENT_SECRET = "8869198281593835213";
    public static final String REFRESH_TOKEN = "5Aep8619hWPJoouFYbRZLY3mazegL5Nbrc.HvUEM535Qm5QBzoGXQe0yL_71p4JeQ4cigUUziqh.0wtm8CehSY0";
    public static final String AUTHORIZATION_CODE = "";

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        String authorizationCode = "";
        LOGGER.debug("authorizationCode is " + authorizationCode);
        String refreshToken = "";
        LOGGER.debug("refreshToken is " + refreshToken);
        SalesforceExternalAPI iSalesforceExternalAPI = new SimpleSalesforceImpl();
        LOGGER.debug("created iSalesforceExternalAPI");

        try {
            LOGGER.debug("about to get access token");
            GetAccessTokenRequest getAccessTokenRequest = SalesforceRequestFactory.createGetAccessTokenRequest(CLIENT_ID, CLIENT_SECRET, REFRESH_TOKEN);
            LOGGER.debug("got access token request");
            GetAccessTokenResponse getAccessTokenResponse = iSalesforceExternalAPI.getAccessToken(getAccessTokenRequest);
            LOGGER.debug("got access token response");
            if (SalesforceConstant.STATUS_NOT_LOGGED_IN.equals(getAccessTokenResponse.getStatus())) {
                LOGGER.debug("status is not logged in");
                return;
            }

            LOGGER.debug("status is logged in");
            String accessToken = getAccessTokenResponse.getAccessToken();
            LOGGER.debug("your access token is " + accessToken);

            // Get an account
            String accountId = "001R000000pcP7z";
            GetAccountRequest getAccountRequest = SalesforceRequestFactory.createGetAccountRequest(accessToken, accountId);
            LOGGER.debug("created getAccountRequest");
            GetAccountResponse getAccountResponse = iSalesforceExternalAPI.getAccount(getAccountRequest);
        } catch (Exception e) {

        }
    }
}
