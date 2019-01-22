/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.integrate.objects.OperationStatusMessage;
import com.xcase.integrate.objects.IntegrateException;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.transputs.UpdateRuleRequest;
import com.xcase.integrate.transputs.UpdateRuleResponse;
import java.io.IOException;
import java.io.StringReader;
import java.lang.invoke.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martinpg
 */
public class UpdateRuleMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public UpdateRuleResponse updateRule(UpdateRuleRequest request) throws IOException, IntegrateException {
        LOGGER.debug("starting updateRule()");
        UpdateRuleResponse response = IntegrateResponseFactory.createUpdateRuleResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "rules";
            Integer ruleId = request.getRuleId();
            LOGGER.debug("ruleId is " + ruleId);
            String ruleChangeRequest = request.getRuleChangeRequest();
            LOGGER.debug("ruleChangeRequest is " + ruleChangeRequest);
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object + CommonConstant.SLASH_STRING + ruleId;
            Header acceptHeader = createAcceptHeader();
            LOGGER.debug("created Accept header");
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createIntegrateAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponsePatch(endPoint, headers, null, ruleChangeRequest, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
                if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_JSON.equals(this.apiRequestFormat)) {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
                    /* TODO process response if needed */
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(OperationStatusMessage.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    StringReader stringReader = new StringReader(responseEntityString);
                    LOGGER.debug("created stringReader");
                    OperationStatusMessage operationStatusMessage = (OperationStatusMessage) jaxbUnmarshaller.unmarshal(stringReader);
                    LOGGER.debug("created operationStatusMessage");
                    String message = operationStatusMessage.message;
                    LOGGER.debug("message is " + message);
                    response.setOperationMessage(message);
                    String status = operationStatusMessage.status;
                    LOGGER.debug("executionStatus is " + status);
                    response.setOperationStatus(status);
                } else {
                    LOGGER.warn("unexpected API request format: " + this.apiRequestFormat);
                    response.setMessage("Unexpected API request format: " + this.apiRequestFormat);
                    response.setStatus("FAIL");
                }
            } else if (responseCode == 206) {
                LOGGER.debug("response code: " + responseCode);
                response.setResponseCode(responseCode);
                response.setMessage("Response code: " + responseCode);
            } else {
                handleUnexpectedResponseCode(response, commonHttpResponse);
            }
        } catch (Exception e) {
            handleUnexpectedException(response, e);
        }

        return response;
    }
}
