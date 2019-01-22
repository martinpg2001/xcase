/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.*;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxObjectFactory;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.core.BoxConfigurationManager;
import com.xcase.box.objects.BoxException;
import com.xcase.box.objects.BoxUser;
import com.xcase.box.transputs.RefreshAccessTokenRequest;
import com.xcase.box.transputs.RefreshAccessTokenResponse;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author Martin
 *
 */
public class RefreshAccessTokenMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     */
    public RefreshAccessTokenMethod() {
        super();
    }

    /**
     *
     * @param refreshAccessTokenRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public RefreshAccessTokenResponse refreshAccessToken(RefreshAccessTokenRequest refreshAccessTokenRequest) throws IOException, BoxException {
        LOGGER.debug("starting refreshAccessToken()");
        RefreshAccessTokenResponse refreshAccessTokenResponse = BoxResponseFactory.createRefreshAccessTokenResponse();
        LOGGER.debug("created access token response");
        String refreshToken = refreshAccessTokenRequest.getRefreshToken();
        LOGGER.debug("refreshToken is " + refreshToken);
        String apiKey = refreshAccessTokenRequest.getApiKey();
        LOGGER.debug("apiKey is " + apiKey);
        String clientSecret = refreshAccessTokenRequest.getClientSecret();
        LOGGER.debug("clientSecret is " + clientSecret);
        boolean isEnterprise = refreshAccessTokenRequest.getIsEnterprise();
        LOGGER.debug("isEnterprise is " + isEnterprise);
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            String oAuthTokenUrl = super.xmlOAuthTokenUrl;
            LOGGER.debug("oAuthTokenUrl is " + oAuthTokenUrl);
            String refreshTokenString = "refresh_token";
            LOGGER.debug("BoxConstant.PARAM_NAME_GRANT_TYPE name is " + BoxConstant.PARAM_NAME_GRANT_TYPE);
            LOGGER.debug("refreshTokenString is " + refreshTokenString);
            LOGGER.debug("BoxConstant.PARAM_NAME_REFRESH_TOKEN name is " + BoxConstant.PARAM_NAME_REFRESH_TOKEN);
            LOGGER.debug("refreshToken is " + refreshToken);
            LOGGER.debug("BoxConstant.PARAM_NAME_CLIENT_ID is " + BoxConstant.PARAM_NAME_CLIENT_ID);
            LOGGER.debug("apiKey is " + apiKey);
            LOGGER.debug("BoxConstant.PARAM_NAME_CLIENT_SECRET is " + BoxConstant.PARAM_NAME_CLIENT_SECRET);
            LOGGER.debug("clientSecret is " + clientSecret);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_GRANT_TYPE, refreshTokenString));
            parameters.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_REFRESH_TOKEN, refreshToken));
            parameters.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_CLIENT_ID, apiKey));
            parameters.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_CLIENT_SECRET, clientSecret));
            try {
//                CommonHTTPManager commonHTTPManager = CommonHTTPManager.refreshCommonHTTPManager();
                JsonElement jsonElement = JsonNull.INSTANCE;
                try {
                    jsonElement = httpManager.doJsonPost(oAuthTokenUrl, null, parameters);
                } catch (Exception e) {
                    LOGGER.warn("exception posting to " + oAuthTokenUrl + ": " + e.getMessage());
                    /* Try again if first attempt fails */
                    jsonElement = httpManager.doJsonPost(oAuthTokenUrl, null, parameters);
                }

                if (!jsonElement.isJsonNull()) {
                    JsonObject jsonObject = (JsonObject) jsonElement;
                    JsonElement accessTokenElement = jsonObject.get("access_token");
                    if (accessTokenElement != null && !accessTokenElement.isJsonNull()) {
                        LOGGER.debug("access token element is not null");
                        JsonElement expiresInElement = jsonObject.get("expires_in");
                        JsonElement refreshTokenElement = jsonObject.get("refresh_token");
                        JsonElement tokenTypeElement = jsonObject.get("token_type");
                        String status = BoxConstant.STATUS_GET_ACCESS_TOKEN_OK;
                        refreshAccessTokenResponse.setStatus(status);
                        LOGGER.debug("status is OK");
                        String accessToken = accessTokenElement.getAsString();
                        LOGGER.debug("accessToken is " + accessToken);
                        refreshAccessTokenResponse.setAccessToken(accessToken);
                        int expiresIn = expiresInElement.getAsInt();
                        LOGGER.debug("expiresIn is " + expiresIn);
                        refreshAccessTokenResponse.setExpiresIn(expiresIn);
                        String newRefreshToken = refreshTokenElement.getAsString();
                        LOGGER.debug("newRefreshToken is " + newRefreshToken);
                        refreshAccessTokenResponse.setRefreshToken(newRefreshToken);
                        LOGGER.debug("set new refresh token property");
                        if (isEnterprise) {
                            BoxConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(BoxConstant.LOCAL_OAUTH2_ENTERPRISE_ACCESS_TOKEN, accessToken);
                            BoxConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(BoxConstant.LOCAL_OAUTH2_ENTERPRISE_REFRESH_TOKEN, newRefreshToken);
                            BoxConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
                        } else {
                            BoxConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(BoxConstant.LOCAL_OAUTH2_ACCESS_TOKEN, accessToken);
                            BoxConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(BoxConstant.LOCAL_OAUTH2_REFRESH_TOKEN, newRefreshToken);
                            BoxConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
                        }

                        String tokenType = tokenTypeElement.getAsString();
                        LOGGER.debug("tokenType is " + tokenType);
                        refreshAccessTokenResponse.setTokenType(tokenType);
                        refreshAccessTokenResponse.setStatus("SUCCESS");
                        refreshAccessTokenResponse.setMessage("Success refreshing access token");
                    } else {
                        JsonElement errorElement = jsonObject.get("error");
                        JsonElement errorDescriptionElement = jsonObject.get("error_description");
                        LOGGER.debug("error description is " + errorDescriptionElement.getAsString());
                        throw new Exception("Error received from Box: " + errorDescriptionElement.getAsString());
                    }
                } else {
                    String status = BoxConstant.STATUS_NOT_LOGGED_IN;
                    refreshAccessTokenResponse.setStatus(status);
                }
            } catch (Exception e) {
                LOGGER.debug("catching exception: " + e.getMessage());
                if (e instanceof java.lang.InstantiationException) {
                    LOGGER.debug(e.getStackTrace());
                }

                throw new BoxException("Failed to parse to a document.", e);
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_XML");
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);
            Element actionElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_API_KEY);
            Element ticketElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_TICKET);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(ticketElm);
            actionElm.setText(BoxConstant.ACTION_NAME_GET_ACCESS_TOKEN);
            apiKeyElm.setText(apiKey);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, document.asXML());
                LOGGER.debug("result is " + result);
                Document doc = DocumentHelper.parseText(result);
                Element responseElement = doc.getRootElement();
                LOGGER.debug("responseElm is " + responseElement.toString());
                Element statusElm = responseElement.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                refreshAccessTokenResponse.setStatus(status);
                if (BoxConstant.STATUS_GET_ACCESS_TOKEN_OK.equals(status)) {
                    Element accessTokenElm = responseElement.element(BoxConstant.PARAM_NAME_ACCESS_TOKEN);
                    Element userElm = responseElement.element(BoxConstant.PARAM_NAME_USER);
                    String accessToken = accessTokenElm.getText();
                    Element loginElm = userElm.element(BoxConstant.PARAM_NAME_LOGIN);
                    Element emailElm = userElm.element(BoxConstant.PARAM_NAME_EMAIL);
                    Element accessIdElm = userElm.element(BoxConstant.PARAM_NAME_ACCESS_ID);
                    Element userIdElm = userElm.element(BoxConstant.PARAM_NAME_USER_ID);
                    Element spaceAmountElm = userElm.element(BoxConstant.PARAM_NAME_SPACE_AMOUNT);
                    Element spaceUsedElm = userElm.element(BoxConstant.PARAM_NAME_SPACE_USED);
                    refreshAccessTokenResponse.setAccessToken(accessToken);
                    BoxUser user = BoxObjectFactory.createBoxUser();
                    user.setLogin(loginElm.getText());
                    user.setEmail(emailElm.getText());
                    user.setAccessId(accessIdElm.getText());
                    user.setUserId(userIdElm.getText());
                    long spaceAmount = 0;
                    try {
                        spaceAmount = Long.parseLong(spaceAmountElm.getText());
                    } catch (NumberFormatException nfe) {
                        LOGGER.warn("catching exception: " + nfe.getMessage());
                        spaceAmount = Long.MIN_VALUE;
                    }

                    user.setSpaceAmount(spaceAmount);
                    long spaceUsed = 0;
                    try {
                        spaceUsed = Long.parseLong(spaceUsedElm.getText());
                    } catch (NumberFormatException nfe) {
                        LOGGER.warn("catching exception: " + nfe.getMessage());
                        spaceUsed = Long.MIN_VALUE;
                    }

                    user.setSpaceUsed(spaceUsed);
                    refreshAccessTokenResponse.setUser(user);
                }
            } catch (Exception e) {
                throw new BoxException(getClass().getName() + ": failed to parse to a document.", e);
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return refreshAccessTokenResponse;
    }
}
