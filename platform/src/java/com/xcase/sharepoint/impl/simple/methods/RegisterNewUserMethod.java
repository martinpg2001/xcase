/**
 * Copyright 2016 Xcase All rights reserved.
 */
/**
 *
 */
package com.xcase.sharepoint.impl.simple.methods;

import com.xcase.common.constant.CommonConstant;
import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.factories.SharepointObjectFactory;
import com.xcase.sharepoint.factories.SharepointResponseFactory;
import com.xcase.sharepoint.objects.SharepointException;
import com.xcase.sharepoint.objects.SharepointUser;
import com.xcase.sharepoint.transputs.RegisterNewUserRequest;
import com.xcase.sharepoint.transputs.RegisterNewUserResponse;
import java.io.IOException;
import org.apache.commons.codec.net.URLCodec;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class RegisterNewUserMethod extends BaseSharepointMethod {

    /**
     * This method is used to register a user. Upon a successful registration,
     * the status param will be 'successful_register'. If registration wasn't
     * successful, status field can be: 'e_register', 'email_invalid',
     * 'email_already_registered', 'application_restricted'.
     *
     * @param registerNewUserRequest request
     * @return response
     * @throws IOException io exception
     * @throws SharepointException box exception
     */
    public RegisterNewUserResponse registerNewUser(
            RegisterNewUserRequest registerNewUserRequest) throws IOException,
            SharepointException {
        RegisterNewUserResponse registerNewUserResponse = SharepointResponseFactory
                .createRegisterNewUserResponse();

        String apiKey = registerNewUserRequest.getApiKey();
        String loginName = registerNewUserRequest.getLoginName();
        String password = registerNewUserRequest.getPassword();

        if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            URLCodec codec = new URLCodec();
            loginName = codec.encode(loginName, "ISO-8859-1");
            password = codec.encode(password, "ISO-8859-1");
            StringBuffer urlBuff = super.getRestUrl(SharepointConstant.ACTION_NAME_REGISTER_NEW_USER);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(SharepointConstant.PARAM_NAME_API_KEY);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(apiKey);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(SharepointConstant.PARAM_NAME_LOGIN);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(loginName);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(SharepointConstant.PARAM_NAME_PASSWORD);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(password);
            try {
                String entityString = httpManager.doStringGet(urlBuff.toString(), null, null, null);
                Document doc = DocumentHelper.parseText(entityString);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(SharepointConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                registerNewUserResponse.setStatus(status);
                if (SharepointConstant.STATUS_SUCCESSFUL_REGISTER.equals(status)) {
                    Element authTokenElm = responseElm.element(SharepointConstant.PARAM_NAME_AUTH_TOKEN);
                    Element userElm = responseElm.element(SharepointConstant.PARAM_NAME_USER);
                    Element loginElm = userElm.element(SharepointConstant.PARAM_NAME_LOGIN);
                    Element emailElm = userElm.element(SharepointConstant.PARAM_NAME_EMAIL);
                    Element accessIdElm = userElm.element(SharepointConstant.PARAM_NAME_ACCESS_ID);
                    Element userIdElm = userElm.element(SharepointConstant.PARAM_NAME_USER_ID);
                    Element spaceAmountElm = userElm.element(SharepointConstant.PARAM_NAME_SPACE_AMOUNT);
                    Element spaceUsedElm = userElm.element(SharepointConstant.PARAM_NAME_SPACE_USED);
                    String authToken = authTokenElm.getText();
                    registerNewUserResponse.setAuthToken(authToken);
                    SharepointUser user = SharepointObjectFactory.createSharepointUser();
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
                    registerNewUserResponse.setUser(user);
                }
            } catch (Exception e) {
                SharepointException be = new SharepointException(
                        "failed to parse to a document.", e);
                be.setStatus(registerNewUserResponse.getStatus());
                throw be;
            }
        } else if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);
            Element actionElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_API_KEY);
            Element inLoginElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_LOGIN);
            Element passwordElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_PASSWORD);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(inLoginElm);
            requestElm.add(passwordElm);
            actionElm.setText(SharepointConstant.ACTION_NAME_REGISTER_NEW_USER);
            apiKeyElm.setText(apiKey);
            inLoginElm.setText(loginName);
            passwordElm.setText(password);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, null, null, document.asXML(), null);
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(SharepointConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                registerNewUserResponse.setStatus(status);
                if (SharepointConstant.STATUS_SUCCESSFUL_REGISTER.equals(status)) {
                    Element authTokenElm = responseElm.element(SharepointConstant.PARAM_NAME_AUTH_TOKEN);
                    Element userElm = responseElm.element(SharepointConstant.PARAM_NAME_USER);
                    Element loginElm = userElm.element(SharepointConstant.PARAM_NAME_LOGIN);
                    Element emailElm = userElm.element(SharepointConstant.PARAM_NAME_EMAIL);
                    Element accessIdElm = userElm.element(SharepointConstant.PARAM_NAME_ACCESS_ID);
                    Element userIdElm = userElm.element(SharepointConstant.PARAM_NAME_USER_ID);
                    Element spaceAmountElm = userElm.element(SharepointConstant.PARAM_NAME_SPACE_AMOUNT);
                    Element spaceUsedElm = userElm.element(SharepointConstant.PARAM_NAME_SPACE_USED);
                    String authToken = authTokenElm.getText();
                    registerNewUserResponse.setAuthToken(authToken);
                    SharepointUser user = SharepointObjectFactory.createSharepointUser();
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
                    registerNewUserResponse.setUser(user);
                }
            } catch (Exception e) {
                SharepointException be = new SharepointException("failed to parse to a document.", e);
                be.setStatus(registerNewUserResponse.getStatus());
                throw be;
            }
        } else if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + SharepointConstant.CONFIG_API_REQUEST_FORMAT_SOAP);
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized " + apiRequestFormat);
        }

        return registerNewUserResponse;

    }
}
