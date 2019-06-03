/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce;

import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.impl.simple.core.SalesforceConfigurationManager;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceRequestFactory;
import com.xcase.salesforce.transputs.*;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.*;

/**
 * This is a sample application to showcase how to invoke the Salesforce REST APis using Xcase classes.
 * After logging in to get an access token and the instance URL, the application updates its instance API URL to 
 * the later version. It then executes a number of API calls to show how request and response classes are used 
 * by method classes.
 * 
 * Standard Salesforce URL are stored in the salesforce-config.properties file. Properties specific to your 
 * account, username, password, client id, and client secret, are stored locally in the salesforce-local-config.properties 
 * files, and the instance-specific URLs are updated in this file.
 * 
 * @author martin
 */
public class SalesforceApplication {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        try {
            /* Initialze the CommonHTTPManager */
            CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
            /* Retrieve properties from the Salesforce properties files */
            String authorizePrefixURL = SalesforceConfigurationManager.getConfigurationManager().getConfig()
                    .getProperty(SalesforceConstant.CONFIG_API_OAUTH_AUTHORIZE_PREFIX);
            LOGGER.debug("authorizePrefixURL is " + authorizePrefixURL);
            String tokenPrefixURL = SalesforceConfigurationManager.getConfigurationManager().getConfig()
                    .getProperty(SalesforceConstant.CONFIG_API_OAUTH_TOKEN_PREFIX);
            LOGGER.debug("tokenURL is " + tokenPrefixURL);
            String authorizationCode = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                    .getProperty(SalesforceConstant.LOCAL_OAUTH2_AUTHORIZATION_CODE);
            LOGGER.debug("authorizationCode is " + authorizationCode);
            String consumerKey = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                    .getProperty(SalesforceConstant.LOCAL_OAUTH2_CLIENT_ID);
            LOGGER.debug("consumerKey is " + consumerKey);
            String consumerSecret = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                    .getProperty(SalesforceConstant.LOCAL_OAUTH2_CLIENT_SECRET);
            LOGGER.debug("consumerSecret is " + consumerSecret);
            String redirectURI = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                    .getProperty(SalesforceConstant.LOCAL_OAUTH2_REDIRECT_URL);
            LOGGER.debug("redirectURI is " + redirectURI);
            String username = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                    .getProperty(SalesforceConstant.LOCAL_OAUTH2_USERNAME);
            LOGGER.debug("username is " + username);
            String password = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                    .getProperty(SalesforceConstant.LOCAL_OAUTH2_PASSWORD);
            LOGGER.debug("password is " + password);
            String authorizationCodeURL = authorizePrefixURL + CommonConstant.QUESTION_MARK_STRING + "response_type"
                    + CommonConstant.EQUALS_SIGN_STRING + "code" + CommonConstant.AND_SIGN_STRING + "client_id"
                    + CommonConstant.EQUALS_SIGN_STRING + consumerKey + CommonConstant.AND_SIGN_STRING + "redirect_uri"
                    + CommonConstant.EQUALS_SIGN_STRING + redirectURI;
            LOGGER.debug("authorizationCodeURL is " + authorizationCodeURL);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("client_id", consumerKey));
            parameters.add(new BasicNameValuePair("client_secret", consumerSecret));
            parameters.add(new BasicNameValuePair("grant_type", "password"));
            parameters.add(new BasicNameValuePair("password", password));
            parameters.add(new BasicNameValuePair("username", username));
            /* Log in to retrieve an access token */
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", tokenPrefixURL, null,
                    parameters, null, null);
            LOGGER.debug("got response status code " + commonHttpResponse.getResponseCode());
            String responseEntityString = commonHttpResponse.getResponseEntityString();
            LOGGER.debug("responseEntityString is " + responseEntityString);
            JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
            /* Retrieve the access token and instance URL from the response */
            String accessToken = responseEntityJsonObject.get("access_token").getAsString();
            LOGGER.debug("accessToken is " + accessToken);
            String instanceUrl = responseEntityJsonObject.get("instance_url").getAsString();
            LOGGER.debug("instanceUrl is " + instanceUrl);
            SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                    .setProperty(SalesforceConstant.LOCAL_OAUTH2_INSTANCE_URL, instanceUrl);
            LOGGER.debug("set instanceUrl to " + instanceUrl);
            instanceUrl = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                    .getProperty(SalesforceConstant.LOCAL_OAUTH2_INSTANCE_URL);
            LOGGER.debug("instanceURL is " + instanceUrl);
            String version = SalesforceConfigurationManager.getConfigurationManager().getConfig()
                    .getProperty(SalesforceConstant.CONFIG_API_VERSION);
            LOGGER.debug("version is " + version);
            /* Construct a version URL using local properties. */
            String servicesUrl = instanceUrl + "/services/data/";
            String versionUrl = servicesUrl + version;
            /* Use the services URL to get the latest Salesforce version. */
            commonHttpResponse = httpManager.doCommonHttpResponseMethod("GET", servicesUrl, null,
                    null, null, null);
            JsonElement versionJsonElement = ConverterUtils.parseStringToJson(commonHttpResponse.getResponseEntityString());
            if (versionJsonElement instanceof JsonObject) {
                /* Just the current version used to be returned by this call */
                version = "v" + ((JsonObject) versionJsonElement).get("version").getAsString();
                versionUrl = servicesUrl + version;
            } else {
                /* Now, an array of versions is returned by this call */
                JsonArray versionJsonArray = ((JsonArray) versionJsonElement);
                /* Iterate through the versions to get the latest version. */
                Iterator<JsonElement> versionIterator = versionJsonArray.iterator();
                while (versionIterator.hasNext()) {
                    version = "v" + ((JsonObject) versionIterator.next()).get("version").getAsString();
                    versionUrl = servicesUrl + version;
                }
            }
            
            LOGGER.debug("version is " + version);
            SalesforceConfigurationManager.getConfigurationManager().localConfig.setProperty(SalesforceConstant.LOCAL_OAUTH2_INSTANCE_VERSION, version);
            LOGGER.debug("versionUrl is " + versionUrl);
            SalesforceConfigurationManager.getConfigurationManager().localConfig.setProperty(SalesforceConstant.LOCAL_OAUTH2_INSTANCE_VERSION_URL, versionUrl);
            /* Save the updated instance and version information */
            SalesforceConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
            /* Create an instance of the Salesforce API to invoke Salesforce operations */
            SalesforceExternalAPI iSalesforceExternalAPI = new SimpleSalesforceImpl();
            LOGGER.debug("created iSalesforceExternalAPI");
            generateTokenPair();
            /*
             * LOGGER.debug("about to get access token"); GetAccessTokenRequest
             * getAccessTokenRequest =
             * SalesforceRequestFactory.createGetAccessTokenRequest(consumerKey,
             * consumerSecret, null); LOGGER.debug("got access token request");
             * getAccessTokenRequest.setAuthorizationCode(authorizationCode);
             * GetAccessTokenResponse getAccessTokenResponse =
             * iSalesforceExternalAPI.getAccessToken(getAccessTokenRequest);
             * LOGGER.debug("got access token response"); if
             * (SalesforceConstant.STATUS_NOT_LOGGED_IN.equals(getAccessTokenResponse.
             * getStatus())) { LOGGER.debug("status is not logged in"); return; }
             * 
             * LOGGER.debug("status is logged in"); String accessToken =
             * getAccessTokenResponse.getAccessToken(); LOGGER.debug("your access token is "
             * + accessToken);
             */
            /* Get a user */
            String userId = "0054P000009Be9r";
            GetUserRequest getUserRequest = SalesforceRequestFactory.createGetUserRequest(accessToken, userId);
            LOGGER.debug("created getUserRequest");
            GetUserResponse getUserResponse = iSalesforceExternalAPI.getUser(getUserRequest);
            /* Get an account */
            String accountId = "0014P000026SEdhQAG";
            GetAccountRequest getAccountRequest = SalesforceRequestFactory.createGetAccountRequest(accessToken,
                    accountId);
            LOGGER.debug("created getAccountRequest");
            GetAccountResponse getAccountResponse = iSalesforceExternalAPI.getAccount(getAccountRequest);
            /* Get a record */
            String recordId = "0014P000026SEdhQAG";
            GetRecordRequest getRecordRequest = SalesforceRequestFactory.createGetRecordRequest(accessToken, "Account",
                    recordId);
            LOGGER.debug("created getRecordRequest");
            GetRecordResponse getRecordResponse = iSalesforceExternalAPI.getRecord(getRecordRequest);
            /* Create an account */
            CreateAccountRequest createAccountRequest = SalesforceRequestFactory
                    .createCreateAccountRequest(accessToken);
            LOGGER.debug("created createAccountRequest");
            createAccountRequest.setAccountName("Bugbane");
            CreateAccountResponse createAccountResponse = iSalesforceExternalAPI.createAccount(createAccountRequest);
            accountId = createAccountResponse.getAccountId();
            LOGGER.debug("accountId is " + accountId);
            /* Search for accounts */
            SearchAccountRequest searchAccountRequest = SalesforceRequestFactory.createSearchAccountRequest(accessToken,
                    null);
            LOGGER.debug("created searchAccountRequest");
            String searchString = "Javamarket&sobject=Account";
            searchAccountRequest.setSearchString(searchString);
            SearchAccountResponse searchAccountResponse = iSalesforceExternalAPI.searchAccount(searchAccountRequest);
            JsonElement searchRecordsJsonElement = searchAccountResponse.getJsonElement();
            if (searchRecordsJsonElement != null) {
                JsonObject searchRecordsJsonObject = (JsonObject) searchRecordsJsonElement;
                JsonArray searchRecordsJsonArray = searchRecordsJsonObject.getAsJsonArray("searchRecords");
                if (searchRecordsJsonArray != null) {
                    LOGGER.debug("searchRecordsJsonArray is not null");
                    Iterator<JsonElement> searchRecordsJsonElementIterator = searchRecordsJsonArray.iterator();
                    while (searchRecordsJsonElementIterator.hasNext()) {
                        LOGGER.debug("searchRecordsJsonArray has next");
                        JsonElement searchRecordJsonElement = searchRecordsJsonElementIterator.next();
                        JsonObject attributesJsonObject = ((JsonObject) searchRecordJsonElement)
                                .getAsJsonObject("attributes");
                        String searchRecordId = ((JsonObject) searchRecordJsonElement).get("Id").getAsString();
                        LOGGER.debug("searchRecordId is " + searchRecordId);
                    }
                } else {
                    LOGGER.debug("searchRecordsJsonArray is null");
                }
            } else {
                LOGGER.debug("searchRecordsJsonElement is null");
            }
            
            /* Query for record */
            QueryRecordRequest queryRecordRequest = SalesforceRequestFactory.createQueryRecordRequest(accessToken,
                    null);
            LOGGER.debug("created queryRecordRequest");
            String queryString = "SELECT+name+FROM+Account";
            queryRecordRequest.setQueryString(queryString);
            QueryRecordResponse queryRecordResponse = iSalesforceExternalAPI.queryRecord(queryRecordRequest);
            JsonElement queryRecordsJsonElement = queryRecordResponse.getJsonElement();
            if (queryRecordsJsonElement != null) {
                JsonObject queryRecordsJsonObject = (JsonObject) queryRecordsJsonElement;
                JsonArray queryRecordJsonArray = queryRecordsJsonObject.getAsJsonArray("records");
                if (queryRecordJsonArray != null) {
                    LOGGER.debug("queryRecordJsonArray is not null");
                    Iterator<JsonElement> queryRecordJsonElementIterator = queryRecordJsonArray.iterator();
                    while (queryRecordJsonElementIterator.hasNext()) {
                        LOGGER.debug("queryRecordJsonElementIterator has next");
                        JsonElement queryRecordJsonElement = queryRecordJsonElementIterator.next();
                        LOGGER.debug("queryRecordJsonElement is " + queryRecordJsonElement);
                        JsonObject queryRecordJsonObject = (JsonObject) queryRecordJsonElement;
                        String url  = queryRecordJsonObject.getAsJsonObject("attributes").get("url").getAsString();
                        LOGGER.debug("url is " + url);
                        GetRecordRequest getQueryRecordRequest = SalesforceRequestFactory.createGetRecordRequest(accessToken);
                        LOGGER.debug("created getQueryRecordRequest");
                        getQueryRecordRequest.setRecordUrl(url);
                        GetRecordResponse getQueryRecordResponse = iSalesforceExternalAPI.getRecord(getQueryRecordRequest);
                    }
                } else {
                    LOGGER.debug("queryRecordJsonArray is null");
                }
            } else {
                LOGGER.debug("queryRecordsJsonElement is null");
            }
            
            /* Delete an account */
            DeleteAccountRequest deleteAccountRequest = SalesforceRequestFactory.createDeleteAccountRequest(accessToken,
                    accountId);
            LOGGER.debug("created deleteAccountRequest");
            DeleteAccountResponse deleteAccountResponse = iSalesforceExternalAPI.deleteAccount(deleteAccountRequest);
            LOGGER.debug("deleted account");
            /* Revoke access token */
            RevokeAccessTokenRequest revokeAcessTokenRequest = SalesforceRequestFactory.createRevokeAccessTokenRequest(accessToken);
            LOGGER.debug("created revokeAcessTokenRequest");
            RevokeAccessTokenResponse revokeAcessTokenResponse = iSalesforceExternalAPI.revokeAccessToken(revokeAcessTokenRequest);            
        } catch (Exception e) {
            LOGGER.warn("exception running application: " + e.getMessage());
        }
    }

    private static void generateTokenPair() throws Exception, IOException {
        LOGGER.debug("starting generateTokenPair()");
        String authorizationCode = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                .getProperty(SalesforceConstant.LOCAL_OAUTH2_AUTHORIZATION_CODE);
        LOGGER.debug("authorizationCode is " + authorizationCode);
        String consumerKey = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                .getProperty(SalesforceConstant.LOCAL_OAUTH2_CLIENT_ID);
        LOGGER.debug("consumerKey is " + consumerKey);
        String consumerSecret = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                .getProperty(SalesforceConstant.LOCAL_OAUTH2_CLIENT_SECRET);
        LOGGER.debug("consumerSecret is " + consumerSecret);
        String tokenURL = SalesforceConfigurationManager.getConfigurationManager().getConfig()
                .getProperty(SalesforceConstant.CONFIG_API_OAUTH_TOKEN_PREFIX);
        LOGGER.debug("tokenURL is " + tokenURL);
        String redirectURI = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                .getProperty(SalesforceConstant.LOCAL_OAUTH2_REDIRECT_URL);
        LOGGER.debug("redirectURI is " + redirectURI);
        CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("code", authorizationCode));
        parameters.add(new BasicNameValuePair("client_id", consumerKey));
        parameters.add(new BasicNameValuePair("client_secret", consumerSecret));
        parameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
        parameters.add(new BasicNameValuePair("redirect_uri", redirectURI));
        CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", tokenURL, null,
                parameters, null, null);
        LOGGER.debug("got response status code " + commonHttpResponse.getResponseCode());
        String responseEntityString = commonHttpResponse.getResponseEntityString();
        LOGGER.debug("responseEntityString is " + responseEntityString);
        JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
        String accessToken = responseEntityJsonObject.get("access_token").getAsString();
        LOGGER.debug("accessToken is " + accessToken);
        SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                .setProperty(SalesforceConstant.LOCAL_OAUTH2_ACCESS_TOKEN, accessToken);
        if (responseEntityJsonObject.get("refresh_token") != null) {
            String refreshToken = responseEntityJsonObject.get("refresh_token").getAsString();
            LOGGER.debug("refreshToken is " + refreshToken);
            SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                .setProperty(SalesforceConstant.LOCAL_OAUTH2_REFRESH_TOKEN, refreshToken);
        }
        
        SalesforceConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
        /* Refresh tokens */
        SalesforceExternalAPI iSalesforceExternalAPI = new SimpleSalesforceImpl();
        RefreshAccessTokenRequest refreshAccessTokenRequest = SalesforceRequestFactory.createRefreshAccessTokenRequest();
        LOGGER.debug("created refreshAccessTokenRequest");
        refreshAccessTokenRequest.setClientId(SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SalesforceConstant.LOCAL_OAUTH2_CLIENT_ID));
        refreshAccessTokenRequest.setClientSecret(SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SalesforceConstant.LOCAL_OAUTH2_CLIENT_SECRET));
        refreshAccessTokenRequest.setRefreshToken(SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SalesforceConstant.LOCAL_OAUTH2_REFRESH_TOKEN));
        RefreshAccessTokenResponse refreshAccessTokenResponse = iSalesforceExternalAPI.refreshAccessToken(refreshAccessTokenRequest);            
        accessToken = refreshAccessTokenResponse.getAccessToken();
        LOGGER.debug("refreshed accessToken is " + accessToken);
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
        return new BasicHeader(SalesforceConfigurationManager.getConfigurationManager().getConfig()
                .getProperty(SalesforceConstant.CONFIG_API_AUTHENTICATION_HEADER), accessToken);
    }
}
