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
import com.xcase.box.transputs.GetAccessTokenRequest;
import com.xcase.box.transputs.GetAccessTokenResponse;
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
 * @author martin
 *
 */
public class GetAccessTokenMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     * @param getAccessTokenRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetAccessTokenResponse getAccessToken(GetAccessTokenRequest getAccessTokenRequest) throws IOException, BoxException {
        LOGGER.debug("starting getAccessToken()");
        GetAccessTokenResponse getAccessTokenResponse = BoxResponseFactory.createGetAccessTokenResponse();
        LOGGER.debug("created access token response");
        String clientId = getAccessTokenRequest.getApiKey();
        LOGGER.debug("clientId is " + clientId);
        String authorizationCode = getAccessTokenRequest.getAuthorizationCode();
        LOGGER.debug("authorizationCode is " + authorizationCode);
        String clientSecret = getAccessTokenRequest.getClientSecret();
        LOGGER.debug("clientSecret is " + clientSecret);
        boolean isEnterprise = getAccessTokenRequest.getIsEnterprise();
        LOGGER.debug("isEnterprise is " + isEnterprise);
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            String oAuthTokenUrl = super.xmlOAuthTokenUrl;
            LOGGER.debug("oAuthTokenUrl is " + oAuthTokenUrl);
//            HttpPost postMethod = new HttpPost(xmlOAuthTokenUrl);
//            LOGGER.debug("created postMethod with URL " + xmlOAuthTokenUrl);
//            postMethod.setDoAuthentication(true);
            String authorizationCodeString = "authorization_code";
            LOGGER.debug("BoxConstant.PARAM_NAME_GRANT_TYPE name is " + BoxConstant.PARAM_NAME_GRANT_TYPE);
            LOGGER.debug("BoxConstant.PARAM_NAME_GRANT_TYPE value is " + authorizationCodeString);
            LOGGER.debug("BoxConstant.PARAM_NAME_CODE name is " + BoxConstant.PARAM_NAME_CODE);
            LOGGER.debug("BoxConstant.PARAM_NAME_CODE value is " + authorizationCode);
            LOGGER.debug("BoxConstant.PARAM_NAME_CLIENT_ID name is " + BoxConstant.PARAM_NAME_CLIENT_ID);
            LOGGER.debug("BoxConstant.PARAM_NAME_CLIENT_ID value is " + clientId);
            LOGGER.debug("BoxConstant.PARAM_NAME_CLIENT_SECRET name is " + BoxConstant.PARAM_NAME_CLIENT_SECRET);
            LOGGER.debug("BoxConstant.PARAM_NAME_CLIENT_SECRET value is " + clientSecret);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_GRANT_TYPE, authorizationCodeString));
            parameters.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_CODE, authorizationCode));
            parameters.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_CLIENT_ID, clientId));
            parameters.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_CLIENT_SECRET, clientSecret));
            try {
                JsonElement jsonElement = httpManager.doJsonPost(oAuthTokenUrl, null, parameters);
                if (!jsonElement.isJsonNull()) {
                    JsonObject jsonObject = (JsonObject) jsonElement;
                    JsonElement accessTokenElement = jsonObject.get("access_token");
                    if (accessTokenElement != null && !accessTokenElement.isJsonNull()) {
                        LOGGER.debug("access token element is not null");
                        JsonElement expiresInElement = jsonObject.get("expires_in");
                        JsonElement refreshTokenElement = jsonObject.get("refresh_token");
                        JsonElement tokenTypeElement = jsonObject.get("token_type");
                        String status = BoxConstant.STATUS_GET_ACCESS_TOKEN_OK;
                        getAccessTokenResponse.setStatus(status);
                        LOGGER.info("status is OK");
                        String accessToken = accessTokenElement.getAsString();
                        LOGGER.info("accessToken is " + accessToken);
                        getAccessTokenResponse.setAccessToken(accessToken);
                        int expiresIn = expiresInElement.getAsInt();
                        LOGGER.info("expiresIn is " + expiresIn);
                        getAccessTokenResponse.setExpiresIn(expiresIn);
                        String newRefreshToken = refreshTokenElement.getAsString();
                        LOGGER.info("newRefreshToken is " + newRefreshToken);
                        getAccessTokenResponse.setRefreshToken(newRefreshToken);
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
                        getAccessTokenResponse.setTokenType(tokenType);
                        BoxConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
                        LOGGER.debug("stored config properties");
                    } else {
                        JsonElement errorElement = jsonObject.get("error");
                        JsonElement errorDescriptionElement = jsonObject.get("error_description");
                        LOGGER.debug("error description is " + errorDescriptionElement.getAsString());
                        throw new Exception("Error received from Box: " + errorDescriptionElement.getAsString());
                    }
                } else {
                    String status = BoxConstant.STATUS_NOT_LOGGED_IN;
                    getAccessTokenResponse.setStatus(status);
                }
            } catch (Exception e) {
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
            apiKeyElm.setText(clientId);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, document.asXML());
                LOGGER.debug("result is " + result);
                Document doc = DocumentHelper.parseText(result);
                Element responseElement = doc.getRootElement();
                LOGGER.debug("responseElm is " + responseElement.toString());
                Element statusElm = responseElement.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                getAccessTokenResponse.setStatus(status);
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
                    getAccessTokenResponse.setAccessToken(accessToken);
                    BoxUser user = BoxObjectFactory.createBoxUser();
                    user.setLogin(loginElm.getText());
                    user.setEmail(emailElm.getText());
                    user.setAccessId(accessIdElm.getText());
                    user.setUserId(userIdElm.getText());
                    long spaceAmount = 0;
                    try {
                        spaceAmount = Long.parseLong(spaceAmountElm.getText());
                    } catch (NumberFormatException e) {
                        spaceAmount = Long.MIN_VALUE;
                    }

                    user.setSpaceAmount(spaceAmount);
                    long spaceUsed = 0;
                    try {
                        spaceUsed = Long.parseLong(spaceUsedElm.getText());
                    } catch (NumberFormatException e) {
                        spaceUsed = Long.MIN_VALUE;
                    }

                    user.setSpaceUsed(spaceUsed);
                    getAccessTokenResponse.setUser(user);
                }
            } catch (Exception e) {
                throw new BoxException(getClass().getName() + ": failed to parse to a document.", e);
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return getAccessTokenResponse;
    }
}
