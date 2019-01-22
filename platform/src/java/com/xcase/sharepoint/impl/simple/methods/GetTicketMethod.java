/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.methods;

import com.xcase.common.constant.CommonConstant;
import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.factories.SharepointResponseFactory;
import com.xcase.sharepoint.objects.SharepointException;
import com.xcase.sharepoint.transputs.GetTicketRequest;
import com.xcase.sharepoint.transputs.GetTicketResponse;
import java.io.IOException;
import java.lang.invoke.*;
import org.apache.logging.log4j.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class GetTicketMethod extends BaseSharepointMethod {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     */
    public GetTicketMethod() {
        super();
    }

    /**
     *
     * @param getTicketRequest request
     * @return response
     * @throws IOException IO exception
     * @throws SharepointException box exception
     */
    public GetTicketResponse getTicket(GetTicketRequest getTicketRequest) throws IOException, SharepointException {
        GetTicketResponse getTicketResponse = SharepointResponseFactory.createGetTicketResponse();
        String apiKey = getTicketRequest.getApiKey();
        if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + SharepointConstant.CONFIG_API_REQUEST_FORMAT_REST);
            StringBuffer urlBuff = super.getRestUrl(SharepointConstant.ACTION_NAME_GET_TICKET);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(SharepointConstant.PARAM_NAME_API_KEY);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(apiKey);
            try {
                String entityString = httpManager.doStringGet(urlBuff.toString());
                Document doc = DocumentHelper.parseText(entityString);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(SharepointConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                getTicketResponse.setStatus(status);
                if (SharepointConstant.STATUS_GET_TICKET_OK.equals(status)) {
                    Element ticketElm = responseElm.element(SharepointConstant.PARAM_NAME_TICKET);
                    String ticket = ticketElm.getText();
                    getTicketResponse.setTicket(ticket);
                }
            } catch (Exception e) {
                throw new SharepointException("failed to parse to a document.", e);
            }

        } else if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + SharepointConstant.CONFIG_API_REQUEST_FORMAT_XML);
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);
            Element actionElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_API_KEY);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            actionElm.setText(SharepointConstant.ACTION_NAME_GET_TICKET);
            apiKeyElm.setText(apiKey);
            LOGGER.debug("xmlApiUrl is " + xmlApiUrl);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, document.asXML());
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(SharepointConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                getTicketResponse.setStatus(status);
                if (SharepointConstant.STATUS_GET_TICKET_OK.equals(status)) {
                    Element ticketElm = responseElm.element(SharepointConstant.PARAM_NAME_TICKET);
                    String ticket = ticketElm.getText();
                    getTicketResponse.setTicket(ticket);
                }
            } catch (Exception e) {
                throw new SharepointException("failed to parse to a document.", e);
            }
        } else if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            Document document = getBaseSoapDocument();
            Element actionElm = getElementByActionName(SharepointConstant.ACTION_NAME_GET_TICKET);
            Element bodyElm = document.getRootElement().element("soap:Body");
            bodyElm.add(actionElm);
            Element apiKeyElm = getSoapElement(SharepointConstant.PARAM_NAME_API_KEY, SharepointConstant.SOAP_TYPE_STRING);
            actionElm.add(apiKeyElm);
            apiKeyElm.setText(apiKey);
            try {
                String result = httpManager.doStringPost(soapApiUrl, document.asXML());
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.elementByID("ns4:" + SharepointConstant.ACTION_NAME_GET_TICKET + "Response");
                Element statusElm = responseElm.element(SharepointConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                getTicketResponse.setStatus(status);
                if (SharepointConstant.STATUS_GET_TICKET_OK.equals(status)) {
                    Element ticketElm = responseElm.element(SharepointConstant.PARAM_NAME_TICKET);
                    String ticket = ticketElm.getText();
                    getTicketResponse.setTicket(ticket);
                }
            } catch (Exception e) {
                throw new SharepointException("failed to parse to a document.", e);
            }
        } else {
            // unreachable code.
            System.gc();
        }

        return getTicketResponse;
    }
}
