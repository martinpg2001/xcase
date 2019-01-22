/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.transputs.CreateTokensFromRefreshTokenRequest;
import com.xcase.open.transputs.CreateTokensFromRefreshTokenResponse;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateTokensFromRefreshTokenMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateTokensFromRefreshTokenMethod() {
        super();
//        LOGGER.debug("finishing CreateTokensFromRefreshCodeMethod()");
    }

    public CreateTokensFromRefreshTokenResponse createTokensFromRefreshToken(CreateTokensFromRefreshTokenRequest request) {
        LOGGER.debug("starting createTokensFromRefreshToken()");
        try {
            CreateTokensFromRefreshTokenResponse createTokensFromRefreshTokenResponse = OpenResponseFactory.createCreateTokensFromRefreshTokenResponse();
            String baseUrl = null;
            String tokenUrl = request.getTokenUrl();
            if (tokenUrl == null|| tokenUrl.isEmpty()) {
                baseUrl = request.getBaseUrl();
                LOGGER.debug("baseUrl is " + baseUrl);
                tokenUrl = baseUrl + "auth/oauth/token";
            }
            
            LOGGER.debug("tokenURL is " + tokenUrl);
            String redirectUrl = request.getRedirectUrl();
            if (redirectUrl == null|| redirectUrl.isEmpty()) {
                baseUrl = request.getBaseUrl();
                LOGGER.debug("baseUrl is " + baseUrl);
                redirectUrl = baseUrl + "api/oauth2/callback";
            }
            
            LOGGER.debug("redirectUrl is " + redirectUrl);
            Header acceptHeader = createAcceptHeader();
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {acceptHeader, contentTypeHeader};
            String refreshToken = request.getRefreshToken();
            LOGGER.debug("refreshToken is " + refreshToken);
            String clientId = request.getClientId();
            LOGGER.debug("clientId is " + clientId);
            String clientSecret = request.getClientSecret();
            LOGGER.debug("clientSecret is " + clientSecret);
            String tenantId = request.getTenantId();
            LOGGER.debug("tenantId is " + tenantId);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("grant_type", "refresh_token"));
            parameters.add(new BasicNameValuePair("refresh_token", refreshToken));
            parameters.add(new BasicNameValuePair("client_id", clientId));
            parameters.add(new BasicNameValuePair("client_secret", clientSecret));
            parameters.add(new BasicNameValuePair("redirect_uri", redirectUrl));          
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", tokenUrl, headers, parameters, null, null);
            LOGGER.debug("got response status code " + commonHttpResponse.getResponseCode());
            String responseEntityString = commonHttpResponse.getResponseEntityString();
            LOGGER.debug("responseEntityString is " + responseEntityString);
            JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
            String accessToken = responseEntityJsonObject.get("access_token").getAsString();
            LOGGER.debug("accessToken is " + accessToken);
            createTokensFromRefreshTokenResponse.setAccessToken(accessToken);
            String updatedRefreshToken = responseEntityJsonObject.get("refresh_token").getAsString();
            LOGGER.debug("updatedRefreshToken is " + updatedRefreshToken);
            createTokensFromRefreshTokenResponse.setRefreshToken(updatedRefreshToken);  
            return createTokensFromRefreshTokenResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating tokens from refresh token: " + e.getMessage());
        }

        return null;
    }      
}
