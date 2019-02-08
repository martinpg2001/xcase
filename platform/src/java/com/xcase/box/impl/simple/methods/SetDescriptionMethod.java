/**
 * Copyright 2016 Xcase All rights reserved.
 */
/**
 *
 */
package com.xcase.box.impl.simple.methods;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.SetDescriptionRequest;
import com.xcase.box.transputs.SetDescriptionResponse;
import com.xcase.common.constant.CommonConstant;
import java.io.IOException;
import org.apache.commons.codec.net.URLCodec;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class SetDescriptionMethod extends BaseBoxMethod {

    /**
     * This method sets a description to a file or folder. 'target' can be a
     * 'file' or 'folder', 'target_id' - id of file or folder, description -
     * string that should be set as description.
     *
     * On successful a result, you will receive status 's_set_description'. If
     * the result wasn't successful, status field can be: e_set_description.
     *
     * @param setDescriptionRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public SetDescriptionResponse setDescription(
            SetDescriptionRequest setDescriptionRequest) throws IOException,
            BoxException {
        SetDescriptionResponse baseBoxResponse = BoxResponseFactory
                .createSetDescriptionResponse();

        String apiKey = setDescriptionRequest.getApiKey();
        String authToken = setDescriptionRequest.getAuthToken();
        String target = setDescriptionRequest.getTarget();
        String targetId = setDescriptionRequest.getTargetId();
        String description = setDescriptionRequest.getDescription();

        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {

            URLCodec codec = new URLCodec();
            description = codec.encode(description, "ISO-8859-1");
            StringBuffer urlBuff = super.getRestUrl(BoxConstant.ACTION_NAME_SET_DESCRIPTION);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_API_KEY);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(apiKey);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_AUTH_TOKEN);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(authToken);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_TARGET);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(target);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_TARGET_ID);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(targetId);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_DESCRIPTION);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(description);
            try {
                String entityString = httpManager.doStringGet(urlBuff.toString(), null, null, null);
                Document doc = DocumentHelper.parseText(entityString);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                baseBoxResponse.setStatus(status);
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(baseBoxResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);
            Element actionElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_API_KEY);
            Element authTokenElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_AUTH_TOKEN);
            Element targetElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_TARGET);
            Element targetIdElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_TARGET_ID);
            Element descriptionElm = DocumentHelper
                    .createElement(BoxConstant.PARAM_NAME_DESCRIPTION);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(authTokenElm);
            requestElm.add(targetElm);
            requestElm.add(targetIdElm);
            requestElm.add(descriptionElm);
            actionElm.setText(BoxConstant.ACTION_NAME_SET_DESCRIPTION);
            apiKeyElm.setText(apiKey);
            authTokenElm.setText(authToken);
            targetElm.setText(target);
            targetIdElm.setText(targetId);
            descriptionElm.setText(description);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, null, null, document.asXML(), null);
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                baseBoxResponse.setStatus(status);
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(baseBoxResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return baseBoxResponse;
    }
}
