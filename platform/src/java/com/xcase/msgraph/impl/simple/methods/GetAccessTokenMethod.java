/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.impl.simple.methods;

import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.msgraph.constant.MSGraphConstant;
import com.xcase.msgraph.factories.MSGraphResponseFactory;
import com.xcase.msgraph.transputs.GetAccessTokenRequest;
import com.xcase.msgraph.transputs.GetAccessTokenResponse;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetAccessTokenMethod extends BaseMSGraphMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetAccessTokenMethod() {
        super();
//        LOGGER.debug("finishing GetAccessTokenMethod()");
    }

    public GetAccessTokenResponse getAccessToken(GetAccessTokenRequest getAccessTokenRequest) {
        LOGGER.debug("starting getAccessToken()");
        try {
            GetAccessTokenResponse getAccessTokenResponse = MSGraphResponseFactory.createGetAccessTokenResponse();
            String clientId = getAccessTokenRequest.getClientId();
            String clientSecret = getAccessTokenRequest.getClientSecret();
            String tenantId = getAccessTokenRequest.getTenantId();
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            NameValuePair clientIdNVP = new BasicNameValuePair("client_id", clientId);
            parameters.add(clientIdNVP);
            NameValuePair scopeNVP = new BasicNameValuePair("scope", config.getProperty(MSGraphConstant.CONFIG_API_OAUTH_DEFAULT_SCOPE));
            parameters.add(scopeNVP);
            NameValuePair clientSecretNVP = new BasicNameValuePair("client_secret", clientSecret);
            parameters.add(clientSecretNVP);
            NameValuePair grantTypeNVP = new BasicNameValuePair("grant_type", "client_credentials");
            parameters.add(grantTypeNVP);
            JsonElement accessTokenElement = httpManager.doJsonPost(config.getProperty(MSGraphConstant.CONFIG_API_OAUTH_TOKEN_PREFIX) + CommonConstant.SLASH_STRING + tenantId + CommonConstant.SLASH_STRING + config.getProperty(MSGraphConstant.CONFIG_API_OAUTH_TOKEN_PATH), null, parameters);
            String accessToken = ((JsonObject)accessTokenElement).getAsJsonPrimitive("access_token").getAsString();
            LOGGER.debug("accessToken is " + accessToken);
            getAccessTokenResponse.setAccessToken(accessToken);
            return getAccessTokenResponse;
        } catch (Exception e) {
            LOGGER.warn("exception getting access token: " + e.getMessage());
        }

        return null;
    }
}
