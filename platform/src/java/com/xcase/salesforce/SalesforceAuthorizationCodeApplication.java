package com.xcase.salesforce;

import com.google.gson.*;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceRequestFactory;
import com.xcase.salesforce.impl.simple.core.SalesforceConfigurationManager;
import com.xcase.salesforce.transputs.*;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 * This is a sample application to showcase how to get and refresh access tokens using Xcase classes.
 * An authorization code must be obtained using the Salesforce Web UI, and added to the salesforce-local-config.properties
 * before running this application.
 * 
 * Standard Salesforce URL are stored in the salesforce-config.properties file. Properties specific to your 
 * account such as authorization code, username, password, client id, and client secret, are stored locally in the salesforce-local-config.properties 
 * files, and the instance-specific URLs are updated in this file.
 * 
 * @author martin
 */
public class SalesforceAuthorizationCodeApplication {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        try {
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
            /* Get access and refresh tokens from authorization code */
            SalesforceExternalAPI iSalesforceExternalAPI = new SimpleSalesforceImpl();
            GetAccessTokenRequest getAccessTokenRequest = SalesforceRequestFactory.createGetAccessTokenRequest();
            getAccessTokenRequest.setAuthorizationCode(authorizationCode);
            getAccessTokenRequest.setClientId(consumerKey);
            getAccessTokenRequest.setClientSecret(consumerSecret);
            GetAccessTokenResponse getAccessTokenResponse = iSalesforceExternalAPI.getAccessToken(getAccessTokenRequest);
            String accessToken = getAccessTokenResponse.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            String refreshToken = getAccessTokenResponse.getRefreshToken();
            LOGGER.debug("refreshToken is " + refreshToken);
            SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
            .setProperty(SalesforceConstant.LOCAL_OAUTH2_ACCESS_TOKEN, accessToken);
            SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
            .setProperty(SalesforceConstant.LOCAL_OAUTH2_REFRESH_TOKEN, refreshToken);
            SalesforceConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
            /*
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
            */
            /* Refresh tokens */
            RefreshAccessTokenRequest refreshAccessTokenRequest = SalesforceRequestFactory.createRefreshAccessTokenRequest();
            LOGGER.debug("created refreshAccessTokenRequest");
            refreshAccessTokenRequest.setClientId(SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SalesforceConstant.LOCAL_OAUTH2_CLIENT_ID));
            refreshAccessTokenRequest.setClientSecret(SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SalesforceConstant.LOCAL_OAUTH2_CLIENT_SECRET));
            refreshAccessTokenRequest.setRefreshToken(SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(SalesforceConstant.LOCAL_OAUTH2_REFRESH_TOKEN));
            RefreshAccessTokenResponse refreshAccessTokenResponse = iSalesforceExternalAPI.refreshAccessToken(refreshAccessTokenRequest);            
            accessToken = refreshAccessTokenResponse.getAccessToken();
            LOGGER.debug("refreshed accessToken is " + accessToken);
        } catch (Exception e) {
            
        }
    }

}
