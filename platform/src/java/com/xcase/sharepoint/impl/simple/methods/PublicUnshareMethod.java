/**
 * Copyright 2016 Xcase All rights reserved.
 */
/**
 *
 */
package com.xcase.sharepoint.impl.simple.methods;

import com.xcase.common.constant.CommonConstant;
import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.factories.SharepointResponseFactory;
import com.xcase.sharepoint.objects.SharepointException;
import com.xcase.sharepoint.transputs.PublicUnshareRequest;
import com.xcase.sharepoint.transputs.PublicUnshareResponse;
import java.io.IOException;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class PublicUnshareMethod extends BaseSharepointMethod {

    /**
     * This method unshares a shared file or folder. 'target' param shoud be
     * either 'file' or 'folder', 'target_id' is id of a file or folder to be
     * unshared.
     *
     * On a successful result, the status will be 'unshare_ok'. If the result
     * wasn't successful, the status field can be: 'unshare_error',
     * 'wrong_node', 'not_logged_in', 'application_restricted'.
     *
     * @param publicUnshareRequest request
     * @return response
     * @throws IOException io exception
     * @throws SharepointException box exception
     */
    public PublicUnshareResponse publicUnshare(PublicUnshareRequest publicUnshareRequest) throws IOException, SharepointException {
        PublicUnshareResponse baseBoxResponse = SharepointResponseFactory.createPublicUnshareResponse();
        String apiKey = publicUnshareRequest.getApiKey();
        String authToken = publicUnshareRequest.getAuthToken();
        String target = publicUnshareRequest.getTarget();
        String targetId = publicUnshareRequest.getTargetId();
        if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            StringBuffer urlBuff = super.getRestUrl(SharepointConstant.ACTION_NAME_PUBLIC_UNSHARE);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(SharepointConstant.PARAM_NAME_API_KEY);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(apiKey);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(SharepointConstant.PARAM_NAME_AUTH_TOKEN);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(authToken);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(SharepointConstant.PARAM_NAME_TARGET);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(target);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(SharepointConstant.PARAM_NAME_TARGET_ID);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(targetId);
            try {
                String entityString = httpManager.doStringGet(urlBuff.toString(), null, null, null);
                Document doc = DocumentHelper.parseText(entityString);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(SharepointConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                baseBoxResponse.setStatus(status);
            } catch (Exception e) {
                SharepointException be = new SharepointException("failed to parse to a document.", e);
                be.setStatus(baseBoxResponse.getStatus());
                throw be;
            }
        } else if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper
                    .createElement(SharepointConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);

            Element actionElm = DocumentHelper
                    .createElement(SharepointConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper
                    .createElement(SharepointConstant.PARAM_NAME_API_KEY);
            Element authTokenElm = DocumentHelper
                    .createElement(SharepointConstant.PARAM_NAME_AUTH_TOKEN);
            Element targetElm = DocumentHelper
                    .createElement(SharepointConstant.PARAM_NAME_TARGET);
            Element targetIdElm = DocumentHelper
                    .createElement(SharepointConstant.PARAM_NAME_TARGET_ID);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(authTokenElm);
            requestElm.add(targetElm);
            requestElm.add(targetIdElm);
            actionElm.setText(SharepointConstant.ACTION_NAME_PUBLIC_UNSHARE);
            apiKeyElm.setText(apiKey);
            authTokenElm.setText(authToken);
            targetElm.setText(target);
            targetIdElm.setText(targetId);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, document.asXML());
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(SharepointConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                baseBoxResponse.setStatus(status);
            } catch (Exception e) {
                SharepointException be = new SharepointException("failed to parse to a document.", e);
                be.setStatus(baseBoxResponse.getStatus());
                throw be;
            }
        } else if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + SharepointConstant.CONFIG_API_REQUEST_FORMAT_SOAP);
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized " + apiRequestFormat);
        }

        return baseBoxResponse;

    }
}
