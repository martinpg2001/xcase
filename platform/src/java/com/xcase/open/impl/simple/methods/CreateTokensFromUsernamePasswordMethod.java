/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.impl.simple.core.ClientUserData;
import com.xcase.open.impl.simple.core.CommonApiWebProxy;
import com.xcase.open.transputs.CreateTokensFromUsernamePasswordRequest;
import com.xcase.open.transputs.CreateTokensFromUsernamePasswordResponse;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martinpg
 */
public class CreateTokensFromUsernamePasswordMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateTokensFromUsernamePasswordMethod() {
        super();
//        LOGGER.debug("finishing CreateTokensFromUsernamePasswordMethod()");
    }

    public CreateTokensFromUsernamePasswordResponse createTokensFromUsernamePassword(CreateTokensFromUsernamePasswordRequest createTokensFromUsernamePasswordRequest) {
        LOGGER.debug("starting createTokensFromUsernamePassword()");
        try {
            CreateTokensFromUsernamePasswordResponse createTokensFromUsernamePasswordResponse = OpenResponseFactory.createCreateTokensFromUsernamePasswordResponse();
            String baseUrl = null;
            String tokenUrl = createTokensFromUsernamePasswordRequest.getTokenUrl();
            if (tokenUrl == null|| tokenUrl.isEmpty()) {
                baseUrl = createTokensFromUsernamePasswordRequest.getBaseUrl();
                LOGGER.debug("baseUrl is " + baseUrl);
                tokenUrl = baseUrl + "auth/oauth/token";
            }
            
            LOGGER.debug("tokenURL is " + tokenUrl);
            Header acceptHeader = createAcceptHeader();
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {acceptHeader, contentTypeHeader};
            String username = createTokensFromUsernamePasswordRequest.getUsername();
            LOGGER.debug("username is " + username);
            String password = createTokensFromUsernamePasswordRequest.getPassword();
            LOGGER.debug("password is " + password);
            String tenantId = createTokensFromUsernamePasswordRequest.getTenantId();
            LOGGER.debug("tenantId is " + tenantId);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("grant_type", "password"));
            parameters.add(new BasicNameValuePair("username", username));
            parameters.add(new BasicNameValuePair("password", password));
            parameters.add(new BasicNameValuePair("tenantId", tenantId));          
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", tokenUrl, headers, parameters, null, null);
            LOGGER.debug("got response status code " + commonHttpResponse.getResponseCode());
            String responseEntityString = commonHttpResponse.getResponseEntityString();
            LOGGER.debug("responseEntityString is " + responseEntityString);
            JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
            String accessToken = responseEntityJsonObject.get("access_token").getAsString();
            LOGGER.debug("accessToken is " + accessToken);
            createTokensFromUsernamePasswordResponse.setAccessToken(accessToken);           
            return createTokensFromUsernamePasswordResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating tokens: " + e.getMessage());
        }

        return null;
    }    
}
