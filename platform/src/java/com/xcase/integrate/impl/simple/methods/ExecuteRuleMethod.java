/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.integrate.objects.RuleExecutionResponse;
import com.xcase.integrate.objects.IntegrateOutput;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.transputs.ExecuteRuleRequest;
import com.xcase.integrate.transputs.ExecuteRuleResponse;
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
public class ExecuteRuleMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public ExecuteRuleResponse executeRule(ExecuteRuleRequest request) {
        LOGGER.debug("starting executeRule()");
        ExecuteRuleResponse response = IntegrateResponseFactory.createExecuteRuleResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "rules";
            Integer ruleId = request.getRuleId();
            LOGGER.debug("ruleId is " + ruleId);
            String ruleExecutionRequest = request.getRuleExecutionRequest();
            LOGGER.debug("ruleExecutionRequest is " + ruleExecutionRequest);
            Integer waitForCompletion = request.getWaitForCompletion();
            LOGGER.debug("waitForCompletion is " + waitForCompletion);
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object + CommonConstant.SLASH_STRING + ruleId + CommonConstant.SLASH_STRING + "execution";
            if (waitForCompletion != null) {
                endPoint = endPoint + CommonConstant.QUESTION_MARK_STRING + IntegrateConstant.WAIT_FOR_COMPLETION + CommonConstant.EQUALS_SIGN_STRING + waitForCompletion;
            }

            Header acceptHeader = createAcceptHeader();
            LOGGER.debug("created Accept header");
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createIntegrateAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponsePost(endPoint, headers, null, ruleExecutionRequest, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
                if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_JSON.equals(this.apiRequestFormat)) {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
                    JsonObject responseJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
                    RuleExecutionResponse ruleExecutionResponse = gson.fromJson(responseJsonObject, RuleExecutionResponse.class);
                    LOGGER.debug("created ruleExecutionResponse");
                    String correlationId = ruleExecutionResponse.correlation_id;
                    LOGGER.debug("correlationId is " + correlationId);
                    response.setCorrelationId(correlationId);
                    String executionStatus = ruleExecutionResponse.execution_status;
                    LOGGER.debug("executionStatus is " + executionStatus);
                    response.setRuleExecutionStatus(executionStatus);
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(RuleExecutionResponse.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    StringReader stringReader = new StringReader(responseEntityString);
                    LOGGER.debug("created stringReader");
                    RuleExecutionResponse ruleExecutionResponse = (RuleExecutionResponse) jaxbUnmarshaller.unmarshal(stringReader);
                    if (ruleExecutionResponse != null) {
                        LOGGER.debug("created ruleExecutionResponse");
                        String correlationId = ruleExecutionResponse.correlation_id;
                        LOGGER.debug("correlationId is " + correlationId);
                        response.setCorrelationId(correlationId);
                        String executionStatus = ruleExecutionResponse.execution_status;
                        LOGGER.debug("executionStatus is " + executionStatus);
                        response.setRuleExecutionStatus(executionStatus);
                        String resultStatus = ruleExecutionResponse.result_status;
                        LOGGER.debug("resultStatus is " + resultStatus);
                        response.setRuleExecutionResultStatus(resultStatus);
                        response.setRuleId(Integer.toString(ruleExecutionResponse.rule_id));
                        response.setRuleName(ruleExecutionResponse.rule_name);
                        if (ruleExecutionResponse.outputsXML != null) {
                            LOGGER.debug("ruleExecutionResponse.outputsXML is not null");
                            if (ruleExecutionResponse.outputsXML.getOutputs() != null) {
                                LOGGER.debug("ruleExecutionResponse.outputs.getOutputs() is not null");
                                response.setRuleExecutionOutputs(ruleExecutionResponse.outputsXML.getOutputs());
                                LOGGER.debug("outputs size is " + ruleExecutionResponse.outputsXML.getOutputs().size());
                                for (IntegrateOutput integrateOutput : ruleExecutionResponse.outputsXML.getOutputs()) {
                                    LOGGER.debug("integrateOutput name is " + integrateOutput.name);
                                    LOGGER.debug("integrateOutput type is " + integrateOutput.type);
                                    LOGGER.debug("integrateOutput value is " + integrateOutput.value);
                                }
                            } else {
                                LOGGER.debug("ruleExecutionResponse.outputs.getOutputs() is null");
                            }
                        } else {
                            LOGGER.debug("ruleExecutionResponse.outputs is null");
                        }
                    } else {
                        LOGGER.warn("ruleExecutionResponse is null");
                        response.setStatus("FAILURE");
                        response.setMessage("ruleExecutionResponse is null");
                    }
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
