/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.*;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceResponseFactory;
import com.xcase.salesforce.impl.simple.core.SalesforceConfigurationManager;
import com.xcase.salesforce.objects.SalesforceException;
import com.xcase.salesforce.transputs.RefreshAccessTokenRequest;
import com.xcase.salesforce.transputs.RefreshAccessTokenResponse;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class RefreshAccessTokenMethod extends BaseSalesforceMethod {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     * @param refreshAccessTokenRequest
     * @return response
     * @throws IOException
     * @throws SalesforceException
     */
    public RefreshAccessTokenResponse refreshAccessToken(RefreshAccessTokenRequest refreshAccessTokenRequest) throws IOException, SalesforceException {
        LOGGER.debug("starting refreshAccessToken()");
        RefreshAccessTokenResponse refreshAccessTokenResponse = SalesforceResponseFactory.createRefreshAccessTokenResponse();
        LOGGER.debug("created refreshAccessTokenResponse");
        String clientId = refreshAccessTokenRequest.getClientId();
        LOGGER.debug("clientId is " + clientId);
        String clientSecret = refreshAccessTokenRequest.getClientSecret();
        LOGGER.debug("clientSecret is " + clientSecret);
        String refreshToken = refreshAccessTokenRequest.getRefreshToken();
        LOGGER.debug("refreshToken is " + refreshToken);
        String oAuthTokenUrl = super.xmlOAuthTokenUrl;
        LOGGER.debug("oAuthTokenUrl is " + oAuthTokenUrl);
        String refreshTokenString = "refresh_token";
        LOGGER.debug("SalesforceConstant.PARAM_NAME_GRANT_TYPE name is " + SalesforceConstant.PARAM_NAME_GRANT_TYPE);
        LOGGER.debug("SalesforceConstant.PARAM_NAME_GRANT_TYPE value is " + refreshTokenString);
        LOGGER.debug("SalesforceConstant.PARAM_NAME_CLIENT_ID name is " + SalesforceConstant.PARAM_NAME_CLIENT_ID);
        LOGGER.debug("SalesforceConstant.PARAM_NAME_CLIENT_ID value is " + clientId);
        LOGGER.debug("SalesforceConstant.PARAM_NAME_CLIENT_SECRET name is " + SalesforceConstant.PARAM_NAME_CLIENT_SECRET);
        LOGGER.debug("SalesforceConstant.PARAM_NAME_CLIENT_SECRET value is " + clientSecret);
        LOGGER.debug("SalesforceConstant.PARAM_NAME_REFRESH_TOKEN name is " + SalesforceConstant.PARAM_NAME_REFRESH_TOKEN);
        LOGGER.debug("SalesforceConstant.PARAM_NAME_REFRESH_TOKEN value is " + refreshToken);
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair(SalesforceConstant.PARAM_NAME_GRANT_TYPE, refreshTokenString));
        parameters.add(new BasicNameValuePair(SalesforceConstant.PARAM_NAME_CLIENT_ID, clientId));
        parameters.add(new BasicNameValuePair(SalesforceConstant.PARAM_NAME_CLIENT_SECRET, clientSecret));
        parameters.add(new BasicNameValuePair(SalesforceConstant.PARAM_NAME_REFRESH_TOKEN, refreshToken));
        try {
            JsonElement jsonElement = httpManager.doJsonPost(oAuthTokenUrl, null, parameters);
            if (!jsonElement.isJsonNull()) {
                LOGGER.debug("jsonElement is " + jsonElement.toString());
                JsonObject jsonObject = (JsonObject) jsonElement;
                JsonElement accessTokenElement = jsonObject.get("access_token");
                if (accessTokenElement != null && !accessTokenElement.isJsonNull()) {
                    LOGGER.debug("access token element is not null");
                    JsonElement instanceUrlElement = jsonObject.get("instance_url");
                    String status = SalesforceConstant.STATUS_GET_ACCESS_TOKEN_OK;
                    refreshAccessTokenResponse.setStatus(status);
                    LOGGER.debug("status is OK");
                    String accessToken = accessTokenElement.getAsString();
                    LOGGER.debug("accessToken is " + accessToken);
                    refreshAccessTokenResponse.setAccessToken(accessToken);
                    LOGGER.debug("set accessToken");
                    SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(SalesforceConstant.LOCAL_OAUTH2_ACCESS_TOKEN, accessToken);
                    LOGGER.debug("set accessToken property");
                    String instanceUrl = instanceUrlElement.getAsString();
                    LOGGER.debug("instanceUrl is " + instanceUrl);
                    //getAccessTokenResponse.setInstanceUrl(instanceUrl);
                    SalesforceConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(SalesforceConstant.LOCAL_OAUTH2_INSTANCE_URL, instanceUrl);
                    LOGGER.debug("about to store local config properties");
                    SalesforceConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
                    LOGGER.debug("stored local config properties");
                } else {
                    JsonElement errorElement = jsonObject.get("error");
                    JsonElement errorDescriptionElement = jsonObject.get("error_description");
                    LOGGER.debug("error description is " + errorDescriptionElement.getAsString());
                }
            } else {
                String status = SalesforceConstant.STATUS_NOT_LOGGED_IN;
                refreshAccessTokenResponse.setStatus(status);
            }
        } catch (Exception e) {
            LOGGER.warn("catching exception: " + e.getMessage());
            throw new SalesforceException("Failed to parse to a document.", e);
        }

        return refreshAccessTokenResponse;
    }
}
