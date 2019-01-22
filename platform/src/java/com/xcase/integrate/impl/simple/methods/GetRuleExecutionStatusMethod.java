/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.integrate.objects.RuleExecutionResponse;
import com.xcase.integrate.objects.IntegrateOutput;
import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.transputs.GetRuleExecutionStatusRequest;
import com.xcase.integrate.transputs.GetRuleExecutionStatusResponse;
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
public class GetRuleExecutionStatusMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public GetRuleExecutionStatusResponse getRuleExecutionStatus(GetRuleExecutionStatusRequest request) {
        LOGGER.debug("starting getRuleExecutionStatus()");
        GetRuleExecutionStatusResponse response = IntegrateResponseFactory.createGetRuleExecutionStatusResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "rules";
            String correlationId = request.getCorrelationId();
            LOGGER.debug("correlationId is " + correlationId);
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object + CommonConstant.SLASH_STRING + "execution" + CommonConstant.SLASH_STRING + correlationId;
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createIntegrateAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header contentTypeHeader = createContentTypeHeader();
//            Header xHTTPMethodHeader = new BasicHeader(CommonConstant.X_HTTP_METHOD_STRING, "GET");
//            LOGGER.debug("created xHTTPMethodHeader header");
            Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, null, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                //responseEntityString = "{\"rule_id\":310,\"execution_status\":\"CANCELED\",\"outputs\":[{\"content\":\"AAA:BBB\",\"type\":\"scalar\",\"name\":\"output_1\"}],\"result_status\":\"CNCL\",\"correlation_id\":\"256f3144b5fc810ce5f4ee2488bdfa931091b59c0e8aa45f99c4128cf8f60331\",\"rule_name\":\"Execution Sleep Rule\"}";
                //responseEntityString = "<?xml version=\"1.0\" encoding=\"UTF-8\"?><rule_execution_result><rule_id>314</rule_id><rule_name>Execution Sleep Rule</rule_name><correlation_id>e69b85d4c2d7c20dfabe11d920478167bf155c7229da33f792145180e44b86e6</correlation_id><execution_status>CANCELED</execution_status><result_status>CNCL</result_status><outputs><output name=\"output_1\" type=\"scalar\">AAA:BBB</output></outputs></rule_execution_result>";
                LOGGER.debug("responseEntityString is " + responseEntityString);
                if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_JSON.equals(this.apiRequestFormat)) {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
                    JsonObject responseJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
                    LOGGER.debug("got responseJsonObject");
                    RuleExecutionResponse ruleExecutionResponse = gson.fromJson(responseJsonObject, RuleExecutionResponse.class);
                    if (ruleExecutionResponse != null) {
                        LOGGER.debug("created ruleExecutionResponse");
                        correlationId = ruleExecutionResponse.correlation_id;
                        LOGGER.debug("correlationId is " + correlationId);
                        response.setCorrelationId(correlationId);
                        String executionStatus = ruleExecutionResponse.execution_status;
                        LOGGER.debug("executionStatus is " + executionStatus);
                        response.setRuleExecutionStatus(executionStatus);
                        String resultStatus = ruleExecutionResponse.result_status;
                        LOGGER.debug("resultStatus is " + resultStatus);
                        response.setRuleExecutionResultStatus(resultStatus);
                    } else {
                        LOGGER.warn("ruleExecutionResponse is null");
                        response.setStatus("FAILURE");
                        response.setMessage("ruleExecutionResponse is null");
                    }
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(RuleExecutionResponse.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    StringReader stringReader = new StringReader(responseEntityString);
                    LOGGER.debug("created stringReader");
                    RuleExecutionResponse ruleExecutionResponse = (RuleExecutionResponse) jaxbUnmarshaller.unmarshal(stringReader);
                    if (ruleExecutionResponse != null) {
                        LOGGER.debug("created ruleExecutionResponse");
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
            } else {
                handleUnexpectedResponseCode(response, commonHttpResponse);
            }
        } catch (Exception e) {
            handleUnexpectedException(response, e);
        }

        return response;
    }
}
