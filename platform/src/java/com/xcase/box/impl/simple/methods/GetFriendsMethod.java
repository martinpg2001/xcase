/**
 * Copyright 2016 Xcase All rights reserved.
 */
/**
 *
 */
package com.xcase.box.impl.simple.methods;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.utils.ConverterUtils;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.GetFriendsRequest;
import com.xcase.box.transputs.GetFriendsResponse;
import com.xcase.common.constant.CommonConstant;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class GetFriendsMethod extends BaseBoxMethod {

    /**
     * this parameter decides whether zip the content with base64 encode or not.
     */
    public static final String PARAMS_KEY_NOZIP = "nozip";

    /**
     * This method is used to retrieve a list of freinds.
     *
     * 'params' is an array of the string where you can set additional
     * parameters, which are: nozip - do not zip tree xml.
     *
     * On a successful result you will receive 's_get_friends' as the status and
     * base64 encoded (and zipped) friends xml. Friends xml looks like this:
     * ......
     *
     * @param getFriendsRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exceptoin
     */
    public GetFriendsResponse getFriends(GetFriendsRequest getFriendsRequest)
            throws IOException, BoxException {

        GetFriendsResponse getFriendsResponse = BoxResponseFactory.createGetFriendsResponse();
        String apiKey = getFriendsRequest.getApiKey();
        String authToken = getFriendsRequest.getAuthToken();
        String[] params = getFriendsRequest.getParams();
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            StringBuffer urlBuff = super.getRestUrl(BoxConstant.ACTION_NAME_GET_FRIENDS);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_API_KEY);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(apiKey);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_AUTH_TOKEN);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(authToken);
            if (params == null) {
                urlBuff.append(CommonConstant.AND_SIGN_STRING);
                urlBuff.append(BoxConstant.PARAM_NAME_PARAMS_PARAMS);
                urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);

            } else {
                for (int i = 0; i < params.length; i++) {
                    String param = params[i];
                    urlBuff.append(CommonConstant.AND_SIGN_STRING);
                    urlBuff.append(BoxConstant.PARAM_NAME_PARAMS_PARAMS);
                    urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
                    urlBuff.append(param);
                }
            }

            try {
                String entityString = httpManager.doStringGet(urlBuff.toString(), null, null, null);
                Document doc = DocumentHelper.parseText(entityString);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                getFriendsResponse.setStatus(status);
                if (BoxConstant.STATUS_S_GET_FRIENDS.equals(status)) {
                    Element friendsElm = responseElm.element(BoxConstant.PARAM_NAME_FRIENDS);
                    if (params != null && Arrays.asList(params).contains(PARAMS_KEY_NOZIP)) {
                        List friendsList = ConverterUtils.toFriendsList(friendsElm);
                        getFriendsResponse.setFriendList(friendsList);
                    } else {
                        getFriendsResponse.setEncodedFriends(friendsElm.getText());
                    }
                }
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(getFriendsResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);

            Element actionElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_API_KEY);
            Element authTokenElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_AUTH_TOKEN);
            Element paramsElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_PARAMS);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(authTokenElm);
            requestElm.add(paramsElm);
            //
            actionElm.setText(BoxConstant.ACTION_NAME_GET_FRIENDS);
            apiKeyElm.setText(apiKey);
            authTokenElm.setText(authToken);
            if (params != null) {
                for (int i = 0; i < params.length; i++) {
                    String param = params[i];
                    Element paramElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_ITEM);
                    paramElm.setText(param);
                    paramsElm.add(paramElm);
                }
            }

            try {
                String result = httpManager.doStringPost(xmlApiUrl, null, null, document.asXML(), null);
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                getFriendsResponse.setStatus(status);
                if (BoxConstant.STATUS_S_GET_FRIENDS.equals(status)) {
                    Element friendsElm = responseElm.element(BoxConstant.PARAM_NAME_FRIENDS);
                    if (params != null && Arrays.asList(params).contains(PARAMS_KEY_NOZIP)) {
                        List friendsList = ConverterUtils.toFriendsList(friendsElm);
                        getFriendsResponse.setFriendList(friendsList);
                    } else {
                        getFriendsResponse.setEncodedFriends(friendsElm.getText());
                    }
                }
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(getFriendsResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return getFriendsResponse;
    }
}
