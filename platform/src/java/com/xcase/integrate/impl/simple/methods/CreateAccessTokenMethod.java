/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.google.gson.*;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.impl.simple.core.IntegrateConfigurationManager;
import com.xcase.integrate.objects.IntegrateException;
import com.xcase.integrate.transputs.CreateAccessTokenRequest;
import com.xcase.integrate.transputs.CreateAccessTokenResponse;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.logging.log4j.*;

/**
 * @author martin
 *
 */
public class CreateAccessTokenMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     * @param createAccessTokenRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public CreateAccessTokenResponse createAccessToken(CreateAccessTokenRequest createAccessTokenRequest) throws IOException, IntegrateException {
        LOGGER.debug("starting createAccessToken()");
        CreateAccessTokenResponse createAccessTokenResponse = IntegrateResponseFactory.createCreateAccessTokenResponse();
        LOGGER.debug("created access token response");
//        String clientId = createAccessTokenRequest.getApiKey();
//        LOGGER.debug("clientId is " + clientId);
//        String authorizationCode = createAccessTokenRequest.getAuthorizationCode();
//        LOGGER.debug("authorizationCode is " + authorizationCode);
//        String clientSecret = createAccessTokenRequest.getClientSecret();
//        LOGGER.debug("clientSecret is " + clientSecret);
//        boolean isEnterprise = createAccessTokenRequest.getIsEnterprise();
//        LOGGER.debug("isEnterprise is " + isEnterprise);
        if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + IntegrateConstant.CONFIG_API_REQUEST_FORMAT_REST);
            String apiVersionUrl = super.apiVersionUrl;
            LOGGER.debug("apiVersionUrl is " + apiVersionUrl);
//            HttpPost postMethod = new HttpPost(xmlOAuthTokenUrl);
//            LOGGER.debug("created postMethod with URL " + xmlOAuthTokenUrl);
//            postMethod.setDoAuthentication(true);
            String authorizationCodeString = "authorization_code";
//            LOGGER.debug("BoxConstant.PARAM_NAME_GRANT_TYPE name is " + IntegrateConstant.PARAM_NAME_GRANT_TYPE);
//            LOGGER.debug("BoxConstant.PARAM_NAME_GRANT_TYPE value is " + authorizationCodeString);
//            LOGGER.debug("BoxConstant.PARAM_NAME_CODE name is " + IntegrateConstant.PARAM_NAME_CODE);
//            LOGGER.debug("BoxConstant.PARAM_NAME_CODE value is " + authorizationCode);
//            LOGGER.debug("BoxConstant.PARAM_NAME_CLIENT_ID name is " + IntegrateConstant.PARAM_NAME_CLIENT_ID);
//            LOGGER.debug("BoxConstant.PARAM_NAME_CLIENT_ID value is " + clientId);
//            LOGGER.debug("BoxConstant.PARAM_NAME_CLIENT_SECRET name is " + IntegrateConstant.PARAM_NAME_CLIENT_SECRET);
//            LOGGER.debug("BoxConstant.PARAM_NAME_CLIENT_SECRET value is " + clientSecret);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
//            parameters.add(new BasicNameValuePair(IntegrateConstant.PARAM_NAME_GRANT_TYPE, authorizationCodeString));
//            parameters.add(new BasicNameValuePair(IntegrateConstant.PARAM_NAME_CODE, authorizationCode));
//            parameters.add(new BasicNameValuePair(IntegrateConstant.PARAM_NAME_CLIENT_ID, clientId));
//            parameters.add(new BasicNameValuePair(IntegrateConstant.PARAM_NAME_CLIENT_SECRET, clientSecret));
            try {
                JsonElement jsonElement = httpManager.doJsonPost(apiVersionUrl, null, parameters);
                if (!jsonElement.isJsonNull()) {
                    JsonObject jsonObject = (JsonObject) jsonElement;
                    JsonElement accessTokenElement = jsonObject.get("access_token");
                    if (accessTokenElement != null && !accessTokenElement.isJsonNull()) {
                        LOGGER.debug("access token element is not null");
                        JsonElement expiresInElement = jsonObject.get("expires_in");
                        JsonElement refreshTokenElement = jsonObject.get("refresh_token");
                        JsonElement tokenTypeElement = jsonObject.get("token_type");
                        String status = IntegrateConstant.STATUS_GET_ACCESS_TOKEN_OK;
                        createAccessTokenResponse.setStatus(status);
                        LOGGER.info("status is OK");
                        String accessToken = accessTokenElement.getAsString();
                        LOGGER.info("accessToken is " + accessToken);
                        createAccessTokenResponse.setAccessToken(accessToken);
                        int expiresIn = expiresInElement.getAsInt();
                        LOGGER.info("expiresIn is " + expiresIn);
                        createAccessTokenResponse.setExpiresIn(expiresIn);
                        String newRefreshToken = refreshTokenElement.getAsString();
                        LOGGER.info("newRefreshToken is " + newRefreshToken);
                        createAccessTokenResponse.setRefreshToken(newRefreshToken);
                        IntegrateConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
                        LOGGER.debug("stored local config properties");
                    } else {
                        JsonElement errorElement = jsonObject.get("error");
                        JsonElement errorDescriptionElement = jsonObject.get("error_description");
                        LOGGER.debug("error description is " + errorDescriptionElement.getAsString());
                    }
                } else {
                    String status = IntegrateConstant.STATUS_NOT_LOGGED_IN;
                    createAccessTokenResponse.setStatus(status);
                }
            } catch (Exception e) {
                throw new IntegrateException("Failed to parse to a document.", e);
            }
        } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML");
            try {
                /* TODO: if necessary, add support here for XML format. */
            } catch (Exception e) {
                throw new IntegrateException(getClass().getName() + ": failed to parse to a document.", e);
            }
        } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is IntegrateConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return createAccessTokenResponse;
    }
}
