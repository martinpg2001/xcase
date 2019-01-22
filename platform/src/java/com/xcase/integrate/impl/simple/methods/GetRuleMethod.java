/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.integrate.objects.IntegrateRule;
import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.transputs.GetRuleRequest;
import com.xcase.integrate.transputs.GetRuleResponse;
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
public class GetRuleMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public GetRuleResponse getRule(GetRuleRequest request) {
        LOGGER.debug("starting getRule()");
        GetRuleResponse response = IntegrateResponseFactory.createGetRuleResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "rules";
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object;
            Integer ruleId = request.getRuleId();
            LOGGER.debug("ruleId is " + ruleId);
            if (ruleId != null) {
                endPoint = endPoint + CommonConstant.SLASH_STRING + ruleId;
            }
            
            endPoint = endPoint + CommonConstant.QUESTION_MARK_STRING;
            String locationSeparator = request.getLocationSeparator();
            LOGGER.debug("locationSeparator is " + locationSeparator);
            if (locationSeparator != null) {
                endPoint = endPoint + IntegrateConstant.LOCATION_SEPARATOR + CommonConstant.EQUALS_SIGN_STRING + locationSeparator;
            }

            String includeTemplate = request.getIncludeTemplate();
            LOGGER.debug("includeTemplate is " + includeTemplate);
            if (includeTemplate != null) {
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + IntegrateConstant.INCLUDE_TEMPLATE + CommonConstant.EQUALS_SIGN_STRING + includeTemplate;
            }
            
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createIntegrateAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, null, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
                if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_JSON.equals(this.apiRequestFormat)) {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
                    JsonObject ruleJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
                    IntegrateRule rule = gson.fromJson(ruleJsonObject, IntegrateRule.class);
                    response.setRule(rule);
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(IntegrateRule.class);
                    LOGGER.debug("created jaxbContext");
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    LOGGER.debug("created jaxbUnmarshaller");
                    StringReader stringReader = new StringReader(responseEntityString);
                    LOGGER.debug("created stringReader");
                    IntegrateRule rule = (IntegrateRule) jaxbUnmarshaller.unmarshal(stringReader);
                    LOGGER.debug("created rule");
                    response.setRule(rule);
                } else {
                    LOGGER.warn("unexpected API request format: " + this.apiRequestFormat);
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
