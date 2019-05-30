/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce;

import com.google.gson.JsonObject;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.impl.simple.core.SalesforceConfigurationManager;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceRequestFactory;
import com.xcase.salesforce.transputs.GetAccessTokenRequest;
import com.xcase.salesforce.transputs.GetAccessTokenResponse;
import com.xcase.salesforce.transputs.GetAccountRequest;
import com.xcase.salesforce.transputs.GetAccountResponse;
import com.xcase.salesforce.transputs.GetUserRequest;
import com.xcase.salesforce.transputs.GetUserResponse;

import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
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

    /**
     * @param args
     *            the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        try {
            String authorizePrefixURL = SalesforceConfigurationManager.getConfigurationManager().getConfig().getProperty(SalesforceConstant.CONFIG_API_OAUTH_AUTHORIZE_PREFIX);
            LOGGER.debug("authorizePrefixURL is " + authorizePrefixURL);
            String tokenPrefixURL = SalesforceConfigurationManager.getConfigurationManager().getConfig().getProperty(SalesforceConstant.CONFIG_API_OAUTH_TOKEN_PREFIX);
            LOGGER.debug("tokenURL is " + tokenPrefixURL);
            String authorizationCode = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SalesforceConstant.LOCAL_OAUTH2_AUTHORIZATION_CODE);
            LOGGER.debug("authorizationCode is " + authorizationCode);
            String consumerKey = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SalesforceConstant.LOCAL_OAUTH2_CLIENT_ID);
            LOGGER.debug("consumerKey is " + consumerKey);
            String consumerSecret = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SalesforceConstant.LOCAL_OAUTH2_CLIENT_SECRET);
            LOGGER.debug("consumerSecret is " + consumerSecret);
            String redirectURI = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SalesforceConstant.LOCAL_OAUTH2_REDIRECT_URL);
            LOGGER.debug("redirectURI is " + redirectURI);
            String username = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SalesforceConstant.LOCAL_OAUTH2_USERNAME);
            LOGGER.debug("username is " + username);
            String password = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SalesforceConstant.LOCAL_OAUTH2_PASSWORD);
            LOGGER.debug("password is " + password);
            String authorizationCodeURL = authorizePrefixURL + CommonConstant.QUESTION_MARK_STRING + "response_type" + CommonConstant.EQUALS_SIGN_STRING + "code"
                    + CommonConstant.AND_SIGN_STRING + "client_id" + CommonConstant.EQUALS_SIGN_STRING + consumerKey + CommonConstant.AND_SIGN_STRING + "redirect_uri"
                    + CommonConstant.EQUALS_SIGN_STRING + redirectURI;
            LOGGER.debug("authorizationCodeURL is " + authorizationCodeURL);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("grant_type", "password"));
            parameters.add(new BasicNameValuePair("client_id", consumerKey));
            parameters.add(new BasicNameValuePair("client_secret", consumerSecret));
            parameters.add(new BasicNameValuePair("username", username));
            parameters.add(new BasicNameValuePair("password", password));
            CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", tokenPrefixURL, null, parameters, null, null);
            LOGGER.debug("got response status code " + commonHttpResponse.getResponseCode());
            String responseEntityString = commonHttpResponse.getResponseEntityString();
            LOGGER.debug("responseEntityString is " + responseEntityString);
            JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
            String accessToken = responseEntityJsonObject.get("access_token").getAsString();
            LOGGER.debug("accessToken is " + accessToken);
            String refreshToken = "";
            LOGGER.debug("refreshToken is " + refreshToken);
            SalesforceExternalAPI iSalesforceExternalAPI = new SimpleSalesforceImpl();
            LOGGER.debug("created iSalesforceExternalAPI");
            // generateTokenPair();
            /*
            LOGGER.debug("about to get access token");
            GetAccessTokenRequest getAccessTokenRequest = SalesforceRequestFactory.createGetAccessTokenRequest(consumerKey, consumerSecret, null);
            LOGGER.debug("got access token request");
            getAccessTokenRequest.setAuthorizationCode(authorizationCode);
            GetAccessTokenResponse getAccessTokenResponse = iSalesforceExternalAPI.getAccessToken(getAccessTokenRequest);
            LOGGER.debug("got access token response");
            if (SalesforceConstant.STATUS_NOT_LOGGED_IN.equals(getAccessTokenResponse.getStatus())) {
                LOGGER.debug("status is not logged in");
                return;
            }

            LOGGER.debug("status is logged in");
            String accessToken = getAccessTokenResponse.getAccessToken();
            LOGGER.debug("your access token is " + accessToken);
            */
            /* Get a user */
            String userId = "0054P000009Be9r";
            GetUserRequest getUserRequest = SalesforceRequestFactory.createGetUserRequest(accessToken, userId);
            LOGGER.debug("created getUserRequest");
            GetUserResponse getUserResponse = iSalesforceExternalAPI.getUser(getUserRequest);            
            /* Get an account */
            String accountId = "001R000000pcP7z";
            GetAccountRequest getAccountRequest = SalesforceRequestFactory.createGetAccountRequest(accessToken, accountId);
            LOGGER.debug("created getAccountRequest");
            GetAccountResponse getAccountResponse = iSalesforceExternalAPI.getAccount(getAccountRequest);
        } catch (Exception e) {

        }
    }

    private static void generateTokenPair() throws Exception, IOException {
        String authorizationCode = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SalesforceConstant.LOCAL_OAUTH2_AUTHORIZATION_CODE);
        LOGGER.debug("authorizationCode is " + authorizationCode);
        String consumerKey = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SalesforceConstant.LOCAL_OAUTH2_CLIENT_ID);
        LOGGER.debug("consumerKey is " + consumerKey);
        String consumerSecret = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SalesforceConstant.LOCAL_OAUTH2_CLIENT_SECRET);
        LOGGER.debug("consumerSecret is " + consumerSecret);
        String tokenURL = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SalesforceConstant.LOCAL_OAUTH2_TOKEN_URL);
        LOGGER.debug("tokenURL is " + tokenURL);
        String redirectURI = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SalesforceConstant.LOCAL_OAUTH2_REDIRECT_URL);
        LOGGER.debug("redirectURI is " + redirectURI);
        CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("code", authorizationCode));
        parameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        parameters.add(new BasicNameValuePair("client_id", consumerKey));
        parameters.add(new BasicNameValuePair("client_secret", consumerSecret));
        parameters.add(new BasicNameValuePair("redirect_uri", redirectURI));
        CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", tokenURL, null, parameters, null, null);
        LOGGER.debug("got response status code " + commonHttpResponse.getResponseCode());
        String responseEntityString = commonHttpResponse.getResponseEntityString();
        LOGGER.debug("responseEntityString is " + responseEntityString);
        JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
        String accessToken = responseEntityJsonObject.get("access_token").getAsString();
        LOGGER.debug("accessToken is " + accessToken);
        SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(SalesforceConstant.LOCAL_OAUTH2_ACCESS_TOKEN, accessToken);
        SalesforceConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
    }

    public static Header createAcceptHeader() {
        String acceptHeader = "application/json";
        LOGGER.debug("acceptHeader is " + acceptHeader);
        return new BasicHeader("Accept", acceptHeader);
    }

    public static Header createContentTypeHeader() {
        String contentTypeHeader = "application/json";
        LOGGER.debug("contentTypeHeader is " + contentTypeHeader);
        return new BasicHeader("Content-Type", contentTypeHeader);
    }

    public static Header createSalesforceAuthenticationTokenHeader(String accessToken) {
        return new BasicHeader(SalesforceConfigurationManager.getConfigurationManager().getConfig().getProperty(SalesforceConstant.CONFIG_API_AUTHENTICATION_HEADER), accessToken);
    }
}
