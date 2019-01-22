/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.transputs.CreateTokensFromAuthorizationCodeRequest;
import com.xcase.open.transputs.CreateTokensFromAuthorizationCodeResponse;
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
public class CreateTokensFromAuthorizationCodeMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateTokensFromAuthorizationCodeMethod() {
        super();
//        LOGGER.debug("finishing CreateTokensFromAuthorizationCodeMethod()");
    }

    public CreateTokensFromAuthorizationCodeResponse createTokensFromAuthorizationCode(CreateTokensFromAuthorizationCodeRequest request) {
        LOGGER.debug("starting createTokensFromAuthorizationCode()");
        try {
            CreateTokensFromAuthorizationCodeResponse createTokensFromAuthorizationCodeResponse = OpenResponseFactory.createCreateTokensFromAuthorizationCodeResponse();
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
            String authorizationCode = request.getAuthorizationCode();
            LOGGER.debug("authorizationCode is " + authorizationCode);
            String clientId = request.getClientId();
            LOGGER.debug("clientId is " + clientId);
            String clientSecret = request.getClientSecret();
            LOGGER.debug("clientSecret is " + clientSecret);
            String tenantId = request.getTenantId();
            LOGGER.debug("tenantId is " + tenantId);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("grant_type", "authorization_code"));
            parameters.add(new BasicNameValuePair("code", authorizationCode));
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
            createTokensFromAuthorizationCodeResponse.setAccessToken(accessToken);
            String refreshToken = responseEntityJsonObject.get("refresh_token").getAsString();
            LOGGER.debug("refreshToken is " + refreshToken);
            createTokensFromAuthorizationCodeResponse.setRefreshToken(refreshToken);  
            return createTokensFromAuthorizationCodeResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating tokens from authorization code: " + e.getMessage());
        }

        return null;
    }        
}
