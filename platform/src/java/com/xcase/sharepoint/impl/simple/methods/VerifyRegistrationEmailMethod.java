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
import com.xcase.sharepoint.transputs.VerifyRegistrationEmailRequest;
import com.xcase.sharepoint.transputs.VerifyRegistrationEmailResponse;
import java.io.IOException;
import org.apache.commons.codec.net.URLCodec;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class VerifyRegistrationEmailMethod extends BaseSharepointMethod {

    /**
     * This method is used to verify user registration email . Upon a not used
     * and right registration email, the status param will be 'email_ok'. Else
     * status field can be: 'email_invalid', 'email_already_registered',
     * 'application_restricted'.
     *
     * @param verifyRegistrationEmailRequest request
     * @return response
     * @throws IOException io exception
     * @throws SharepointException box exception
     */
    public VerifyRegistrationEmailResponse verifyRegistrationEmail(
            VerifyRegistrationEmailRequest verifyRegistrationEmailRequest)
            throws IOException, SharepointException {
        VerifyRegistrationEmailResponse baseBoxResponse = SharepointResponseFactory
                .createVerifyRegistrationEmailResponse();

        String apiKey = verifyRegistrationEmailRequest.getApiKey();
        String loginName = verifyRegistrationEmailRequest.getLoginName();

        if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            URLCodec codec = new URLCodec();
            loginName = codec.encode(loginName, "ISO-8859-1");
            StringBuffer urlBuff = super.getRestUrl(SharepointConstant.ACTION_NAME_VERIFY_REGISTRATION_EMAIL);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(SharepointConstant.PARAM_NAME_API_KEY);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(apiKey);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(SharepointConstant.PARAM_NAME_LOGIN);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(loginName);
            try {
                String entityString = httpManager.doStringGet(urlBuff.toString());
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
            Element requestElm = DocumentHelper.createElement("request");
            document.add(requestElm);
            Element actionElm = DocumentHelper.createElement("action");
            Element apiKeyElm = DocumentHelper.createElement("api_key");
            Element loginElm = DocumentHelper.createElement("login");
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(loginElm);
            actionElm.setText(SharepointConstant.ACTION_NAME_VERIFY_REGISTRATION_EMAIL);
            apiKeyElm.setText(apiKey);
            loginElm.setText(loginName);
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
