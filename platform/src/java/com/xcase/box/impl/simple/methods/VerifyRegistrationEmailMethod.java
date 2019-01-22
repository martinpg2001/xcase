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
import com.xcase.box.transputs.VerifyRegistrationEmailRequest;
import com.xcase.box.transputs.VerifyRegistrationEmailResponse;
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
public class VerifyRegistrationEmailMethod extends BaseBoxMethod {

    /**
     * This method is used to verify user registration email . Upon a not used
     * and right registration email, the status param will be 'email_ok'. Else
     * status field can be: 'email_invalid', 'email_already_registered',
     * 'application_restricted'.
     *
     * @param verifyRegistrationEmailRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public VerifyRegistrationEmailResponse verifyRegistrationEmail(
            VerifyRegistrationEmailRequest verifyRegistrationEmailRequest)
            throws IOException, BoxException {
        VerifyRegistrationEmailResponse baseBoxResponse = BoxResponseFactory
                .createVerifyRegistrationEmailResponse();

        String apiKey = verifyRegistrationEmailRequest.getApiKey();
        String loginName = verifyRegistrationEmailRequest.getLoginName();
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            URLCodec codec = new URLCodec();
            loginName = codec.encode(loginName, "ISO-8859-1");
            StringBuffer urlBuff = super.getRestUrl(BoxConstant.ACTION_NAME_VERIFY_REGISTRATION_EMAIL);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_API_KEY);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(apiKey);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_LOGIN);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(loginName);
            try {
                String entityString = httpManager.doStringGet(urlBuff.toString());
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
            Element requestElm = DocumentHelper.createElement("request");
            document.add(requestElm);
            Element actionElm = DocumentHelper.createElement("action");
            Element apiKeyElm = DocumentHelper.createElement("api_key");
            Element loginElm = DocumentHelper.createElement("login");
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(loginElm);
            actionElm.setText(BoxConstant.ACTION_NAME_VERIFY_REGISTRATION_EMAIL);
            apiKeyElm.setText(apiKey);
            loginElm.setText(loginName);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, document.asXML());
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
