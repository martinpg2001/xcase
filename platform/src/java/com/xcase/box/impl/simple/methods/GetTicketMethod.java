/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.GetTicketRequest;
import com.xcase.box.transputs.GetTicketResponse;
import com.xcase.common.constant.CommonConstant;
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
public class GetTicketMethod extends BaseBoxMethod {

    /**
     * log4j object.
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
     * @throws BoxException box exception
     */
    public GetTicketResponse getTicket(GetTicketRequest getTicketRequest) throws IOException, BoxException {
        GetTicketResponse getTicketResponse = BoxResponseFactory.createGetTicketResponse();
        String apiKey = getTicketRequest.getApiKey();
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            StringBuffer urlBuff = super.getRestUrl(BoxConstant.ACTION_NAME_GET_TICKET);
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_API_KEY);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(apiKey);
            try {
                String entityString = httpManager.doStringGet(urlBuff.toString(), null, null, null);
                Document doc = DocumentHelper.parseText(entityString);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                getTicketResponse.setStatus(status);
                if (BoxConstant.STATUS_GET_TICKET_OK.equals(status)) {
                    Element ticketElm = responseElm.element(BoxConstant.PARAM_NAME_TICKET);
                    String ticket = ticketElm.getText();
                    getTicketResponse.setTicket(ticket);
                }
            } catch (Exception e) {
                throw new BoxException("failed to parse to a document.", e);
            }

        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_XML);
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);
            Element actionElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_API_KEY);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            //
            actionElm.setText(BoxConstant.ACTION_NAME_GET_TICKET);
            apiKeyElm.setText(apiKey);
            LOGGER.debug("xmlApiUrl is " + xmlApiUrl);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, null, null, document.asXML(), null);
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                getTicketResponse.setStatus(status);
                if (BoxConstant.STATUS_GET_TICKET_OK.equals(status)) {
                    Element ticketElm = responseElm.element(BoxConstant.PARAM_NAME_TICKET);
                    String ticket = ticketElm.getText();
                    getTicketResponse.setTicket(ticket);
                }
            } catch (Exception e) {
                throw new BoxException("failed to parse to a document.", e);
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            Document document = getBaseSoapDocument();
            Element actionElm = getElementByActionName(BoxConstant.ACTION_NAME_GET_TICKET);
            Element bodyElm = document.getRootElement().element("soap:Body");
            bodyElm.add(actionElm);
            Element apiKeyElm = getSoapElement(BoxConstant.PARAM_NAME_API_KEY, BoxConstant.SOAP_TYPE_STRING);
            actionElm.add(apiKeyElm);
            apiKeyElm.setText(apiKey);
            try {
                String result = httpManager.doStringPost(soapApiUrl, null, null, document.asXML(), null);
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.elementByID("ns4:" + BoxConstant.ACTION_NAME_GET_TICKET + "Response");
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                getTicketResponse.setStatus(status);
                if (BoxConstant.STATUS_GET_TICKET_OK.equals(status)) {
                    Element ticketElm = responseElm.element(BoxConstant.PARAM_NAME_TICKET);
                    String ticket = ticketElm.getText();
                    getTicketResponse.setTicket(ticket);
                }
            } catch (Exception e) {
                throw new BoxException("failed to parse to a document.", e);
            }
        } else {
            // unreachable code.
            System.gc();
        }

        return getTicketResponse;
    }
}
