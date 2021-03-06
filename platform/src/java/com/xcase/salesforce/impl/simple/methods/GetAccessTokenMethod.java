/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceResponseFactory;
import com.xcase.salesforce.impl.simple.core.SalesforceConfigurationManager;
import com.xcase.salesforce.objects.SalesforceException;
import com.xcase.salesforce.transputs.GetAccessTokenRequest;
import com.xcase.salesforce.transputs.GetAccessTokenResponse;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class GetAccessTokenMethod extends BaseSalesforceMethod {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     * @param request
     * @return response
     * @throws IOException
     * @throws SalesforceException
     */
    public GetAccessTokenResponse getAccessToken(GetAccessTokenRequest request)
            throws IOException, SalesforceException {
        LOGGER.debug("starting getAccessToken()");
        GetAccessTokenResponse response = SalesforceResponseFactory.createGetAccessTokenResponse();
        LOGGER.debug("created response");
        try {
            String clientId = request.getClientId();
            LOGGER.debug("clientId is " + clientId);
            String authorizationCode = request.getAuthorizationCode();
            LOGGER.debug("authorizationCode is " + authorizationCode);
            String clientSecret = request.getClientSecret();
            LOGGER.debug("clientSecret is " + clientSecret);
            String refreshToken = request.getRefreshToken();
            LOGGER.debug("refreshToken is " + refreshToken);
            String endPoint = super.jsonOAuthTokenUrl;
            LOGGER.debug("endPoint is " + endPoint);
            Header[] headers = { };
            String grant_type = null;
            if (authorizationCode != null && !authorizationCode.isEmpty()) {
                grant_type = "authorization_code";
            } else if (refreshToken != null && !refreshToken.isEmpty()) {
                grant_type = "refresh_token";
            } else {
                LOGGER.warn("unrecognized grant type");
            }

            String redirectURI = SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                    .getProperty(SalesforceConstant.LOCAL_OAUTH2_REDIRECT_URL);
            LOGGER.debug("redirectURI is " + redirectURI);
            LOGGER.debug(
                    "SalesforceConstant.PARAM_NAME_GRANT_TYPE name is " + SalesforceConstant.PARAM_NAME_GRANT_TYPE);
            LOGGER.debug("SalesforceConstant.PARAM_NAME_GRANT_TYPE value is " + grant_type);
            LOGGER.debug("SalesforceConstant.PARAM_NAME_CLIENT_ID name is " + SalesforceConstant.PARAM_NAME_CLIENT_ID);
            LOGGER.debug("SalesforceConstant.PARAM_NAME_CLIENT_ID value is " + clientId);
            LOGGER.debug("SalesforceConstant.PARAM_NAME_CLIENT_SECRET name is "
                    + SalesforceConstant.PARAM_NAME_CLIENT_SECRET);
            LOGGER.debug("SalesforceConstant.PARAM_NAME_CLIENT_SECRET value is " + clientSecret);
            LOGGER.debug("SalesforceConstant.PARAM_NAME_REFRESH_TOKEN name is "
                    + SalesforceConstant.PARAM_NAME_REFRESH_TOKEN);
            LOGGER.debug("SalesforceConstant.PARAM_NAME_REFRESH_TOKEN value is " + refreshToken);
            LOGGER.debug(
                    "SalesforceConstant.PARAM_NAME_REDIRECT_URI name is " + SalesforceConstant.PARAM_NAME_REDIRECT_URI);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair(SalesforceConstant.PARAM_NAME_GRANT_TYPE, grant_type));
            parameters.add(new BasicNameValuePair(SalesforceConstant.PARAM_NAME_CLIENT_ID, clientId));
            parameters.add(new BasicNameValuePair(SalesforceConstant.PARAM_NAME_CLIENT_SECRET, clientSecret));
            if (grant_type != null && grant_type.equals("refresh_token")) {
                parameters.add(new BasicNameValuePair(SalesforceConstant.PARAM_NAME_REFRESH_TOKEN, refreshToken));
            } else {
                parameters.add(new BasicNameValuePair(SalesforceConstant.PARAM_NAME_CODE, authorizationCode));
            }

            parameters.add(new BasicNameValuePair(SalesforceConstant.PARAM_NAME_REDIRECT_URI, redirectURI));
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponsePost(endPoint, headers, parameters, null, 
                    null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                handleExpectedResponseCode(response, commonHttpResponse);
                JsonElement jsonElement = response.getJsonElement();
                LOGGER.debug("jsonElement is " + jsonElement.toString());
                JsonObject jsonObject = (JsonObject) jsonElement;
                JsonElement accessTokenElement = jsonObject.get("access_token");
                if (accessTokenElement != null && !accessTokenElement.isJsonNull()) {
                    LOGGER.debug("access token element is not null");
                    JsonElement instanceUrlElement = jsonObject.get("instance_url");
                    String status = SalesforceConstant.STATUS_GET_ACCESS_TOKEN_OK;
                    response.setStatus(status);
                    LOGGER.debug("status is OK");
                    String accessToken = accessTokenElement.getAsString();
                    LOGGER.debug("accessToken is " + accessToken);
                    response.setAccessToken(accessToken);
                    LOGGER.debug("set accessToken");
                    SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                            .setProperty(SalesforceConstant.LOCAL_OAUTH2_ACCESS_TOKEN, accessToken);
                    LOGGER.debug("set accessToken property");
                    String instanceUrl = instanceUrlElement.getAsString();
                    LOGGER.debug("instanceUrl is " + instanceUrl);
                    SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                            .setProperty(SalesforceConstant.LOCAL_OAUTH2_INSTANCE_URL, instanceUrl);
                    if (jsonObject.get("refresh_token") != null) {
                        String updatedRefreshToken = jsonObject.get("refresh_token").getAsString();
                        LOGGER.debug("updatedRefreshToken is " + updatedRefreshToken);
                        response.setRefreshToken(updatedRefreshToken);
                        SalesforceConfigurationManager.getConfigurationManager().getLocalConfig()
                                .setProperty(SalesforceConstant.LOCAL_OAUTH2_REFRESH_TOKEN, instanceUrl);
                    }

                    LOGGER.debug("about to store local config properties");
                    SalesforceConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
                    LOGGER.debug("stored local config properties");
                } else {
                    JsonElement errorElement = jsonObject.get("error");
                    JsonElement errorDescriptionElement = jsonObject.get("error_description");
                    LOGGER.debug("error description is " + errorDescriptionElement.getAsString());
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
