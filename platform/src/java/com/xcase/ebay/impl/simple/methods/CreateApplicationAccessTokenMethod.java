package com.xcase.ebay.impl.simple.methods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.ebay.transputs.CreateApplicationAccessTokenRequest;
import com.xcase.ebay.transputs.CreateApplicationAccessTokenResponse;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.ebay.constant.EBayConstant;
import com.xcase.ebay.factories.EBayResponseFactory;
import com.xcase.ebay.impl.simple.core.EBayConfigurationManager;
import com.xcase.ebay.objects.EBayException;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.commons.codec.binary.Base64;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.http.NameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateApplicationAccessTokenMethod extends BaseEBayMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public CreateApplicationAccessTokenResponse createApplicationAccessToken(CreateApplicationAccessTokenRequest createApplicationAcessTokenRequest) throws Exception {
        LOGGER.debug("starting createApplicationAccessToken()");
        CreateApplicationAccessTokenResponse createApplicationAccessTokenResponse = EBayResponseFactory.createCreateApplicationAccessTokenResponse();
        LOGGER.debug("created access token response");
        Header authorizationHeader = createAuthorizationHeader(createApplicationAcessTokenRequest);
        LOGGER.debug("created Authorization header");
        Header contentTypeHeader = createContentTypeHeader();
        Header[] headers = {authorizationHeader, contentTypeHeader};
        String entityString = "grant_type=client_credentials&scope=https://api.ebay.com/oauth/api_scope";
        LOGGER.debug("entityString is " + entityString);
        CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", "https://api.sandbox.ebay.com/identity/v1/oauth2/token", headers, null, entityString, null);
        int responseCode = commonHttpResponse.getResponseCode();
        if (responseCode == 200) {
            try {
                JsonElement jsonElement = ConverterUtils.parseStringToJson(commonHttpResponse.getResponseEntityString());;
                if (!jsonElement.isJsonNull()) {
                    JsonObject jsonObject = (JsonObject) jsonElement;
                    JsonElement accessTokenElement = jsonObject.get("access_token");
                    if (accessTokenElement != null && !accessTokenElement.isJsonNull()) {
                        LOGGER.debug("access token element is not null");
                        JsonElement expiresInElement = jsonObject.get("expires_in");
                        JsonElement tokenTypeElement = jsonObject.get("token_type");
                        String status = EBayConstant.STATUS_GET_ACCESS_TOKEN_OK;
                        createApplicationAccessTokenResponse.setStatus(status);
                        LOGGER.debug("status is OK");
                        String accessToken = accessTokenElement.getAsString();
                        LOGGER.debug("accessToken is " + accessToken);
                        createApplicationAccessTokenResponse.setAccessToken(accessToken);
                        int expiresIn = expiresInElement.getAsInt();
                        LOGGER.debug("expiresIn is " + expiresIn);
                        createApplicationAccessTokenResponse.setExpiresIn(expiresIn);
                        EBayConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
                        LOGGER.debug("stored local config properties");
                    } else {
                        JsonElement errorElement = jsonObject.get("error");
                        JsonElement errorDescriptionElement = jsonObject.get("error_description");
                        LOGGER.debug("error description is " + errorDescriptionElement.getAsString());
                    }
                } else {
                    String status = EBayConstant.STATUS_NOT_LOGGED_IN;
                    createApplicationAccessTokenResponse.setStatus(status);
                }
            } catch (Exception e) {
                throw new EBayException("Failed to parse to a document.", e);
            }
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return createApplicationAccessTokenResponse;
    }

    private Header createAuthorizationHeader(CreateApplicationAccessTokenRequest createApplicationAcessTokenRequest) {
        String basicCredentialsString = createCredentialsString(createApplicationAcessTokenRequest);
        LOGGER.debug("basicCredentialsString is " + basicCredentialsString);
        return new BasicHeader("Authorization", basicCredentialsString);
    }

    private String createCredentialsString(CreateApplicationAccessTokenRequest createApplicationAcessTokenRequest) {
        String credentialsString = createApplicationAcessTokenRequest.getClientId() + ":" + createApplicationAcessTokenRequest.getClientSecret();
        LOGGER.debug("credentialsString is " + credentialsString);
        return "Basic " + Base64.encodeBase64String(credentialsString.getBytes());
    }

}
