/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.methods;

import com.xcase.common.constant.CommonConstant;
import com.xcase.common.utils.URLUtils;
import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.factories.SharepointResponseFactory;
import com.xcase.sharepoint.objects.SharepointException;
import com.xcase.sharepoint.transputs.RequestFriendsRequest;
import com.xcase.sharepoint.transputs.RequestFriendsResponse;
import java.io.IOException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class RequestFriendsMethod extends BaseSharepointMethod {

    /**
     * This method requests new friends to be added to your network. 'emails' -
     * array of emails. 'message' - text message that you want to send to
     * freinds. 'params' is an array of string where you can set additional
     * parameters, which are: box_auto_subscribe - subscribe to public boxes of
     * inveted users. no_email - don't send emails to invited users.
     *
     * On a successful result, you will receive status 's_request_friends'. If
     * the result wasn't successful, status field can be: e_request_friends.
     *
     * @param requestFriendsRequest request
     * @return response
     * @throws IOException io exception
     * @throws SharepointException box exception
     */
    public RequestFriendsResponse requestFriends(RequestFriendsRequest requestFriendsRequest) throws IOException, SharepointException {
        RequestFriendsResponse requestFriendsResponse = SharepointResponseFactory.createRequestFriendsResponse();
        String apiKey = requestFriendsRequest.getApiKey();
        String authToken = requestFriendsRequest.getAuthToken();
        String message = requestFriendsRequest.getMessage();
        String[] emails = requestFriendsRequest.getEmails();
        String[] params = requestFriendsRequest.getParams();
        if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            // encode to url namespace
            message = URLUtils.encodeUrl(message);
            for (int i = 0; i < emails.length; i++) {
                emails[i] = URLUtils.encodeUrl(emails[i]);
            }

            StringBuffer urlBuff = super.getRestUrl(SharepointConstant.ACTION_NAME_REQUEST_FRIENDS);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(SharepointConstant.PARAM_NAME_API_KEY);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(apiKey);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(SharepointConstant.PARAM_NAME_AUTH_TOKEN);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(authToken);
            if (emails == null) {
                urlBuff.append(CommonConstant.AND_SIGN_STRING);
                urlBuff.append(SharepointConstant.PARAM_NAME_PARAMS_EMAILS);
                urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            } else {
                for (int i = 0; i < emails.length; i++) {
                    String email = emails[i];
                    urlBuff.append(CommonConstant.AND_SIGN_STRING);
                    urlBuff.append(SharepointConstant.PARAM_NAME_PARAMS_EMAILS);
                    urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
                    urlBuff.append(email);
                }
            }

            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(SharepointConstant.PARAM_NAME_MESSAGE);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(message);
            if (params == null) {
                urlBuff.append(CommonConstant.AND_SIGN_STRING);
                urlBuff.append(SharepointConstant.PARAM_NAME_PARAMS_PARAMS);
                urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            } else {
                for (int i = 0; i < params.length; i++) {
                    String param = params[i];
                    urlBuff.append(CommonConstant.AND_SIGN_STRING);
                    urlBuff.append(SharepointConstant.PARAM_NAME_PARAMS_PARAMS);
                    urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
                    urlBuff.append(param);
                }
            }

            try {
                String entityString = httpManager.doStringGet(urlBuff.toString(), null, null, null);
                Document doc = DocumentHelper.parseText(entityString);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(SharepointConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                requestFriendsResponse.setStatus(status);
            } catch (Exception e) {
                SharepointException be = new SharepointException("failed to parse to a document.", e);
                be.setStatus(requestFriendsResponse.getStatus());
                throw be;
            }
        } else if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);
            Element actionElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_API_KEY);
            Element authTokenElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_AUTH_TOKEN);
            Element emailsElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_EMAILS);
            Element messageElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_MESSAGE);
            Element paramsElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_PARAMS);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(authTokenElm);
            requestElm.add(emailsElm);
            requestElm.add(messageElm);
            requestElm.add(paramsElm);
            actionElm.setText(SharepointConstant.ACTION_NAME_REQUEST_FRIENDS);
            apiKeyElm.setText(apiKey);
            authTokenElm.setText(authToken);
            messageElm.setText(message);
            if (emails != null) {
                for (int i = 0; i < emails.length; i++) {
                    String email = emails[i];
                    Element emailElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_ITEM);
                    emailElm.setText(email);
                    emailsElm.add(emailElm);
                }
            }

            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    String param = params[i];
                    Element paramElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_ITEM);
                    paramElm.setText(param);
                    paramsElm.add(paramElm);
                }
            }

            try {
                String result = httpManager.doStringPost(xmlApiUrl, null, null, document.asXML(), null);
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(SharepointConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                requestFriendsResponse.setStatus(status);
            } catch (Exception e) {
                SharepointException be = new SharepointException("failed to parse to a document.", e);
                be.setStatus(requestFriendsResponse.getStatus());
                throw be;
            }
        } else if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + SharepointConstant.CONFIG_API_REQUEST_FORMAT_SOAP);
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized " + apiRequestFormat);
        }

        return requestFriendsResponse;
    }
}
