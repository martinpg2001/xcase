/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.integrate.objects.IntegrateRuleList;
import com.xcase.integrate.objects.IntegrateRule;
import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.transputs.GetAllRulesRequest;
import com.xcase.integrate.transputs.GetAllRulesResponse;
import java.io.StringReader;
import java.lang.invoke.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetAllRulesMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public GetAllRulesResponse getAllRules(GetAllRulesRequest request) {
        LOGGER.debug("starting getAllRules()");
        GetAllRulesResponse response = IntegrateResponseFactory.createGetAllRulesResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "rules";
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object;
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            String locationSeparator = request.getLocationSeparator();
            LOGGER.debug("locationSeparator is " + locationSeparator);
            if (locationSeparator != null) {
                endPoint = endPoint + CommonConstant.QUESTION_MARK_STRING + IntegrateConstant.LOCATION_SEPARATOR + CommonConstant.EQUALS_SIGN_STRING + locationSeparator;
            }

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
                    JsonArray rulesJsonArray = (JsonArray) ConverterUtils.parseStringToJson(responseEntityString);
                    LOGGER.debug("parsed responseEntityString to JsonArray");
                    IntegrateRule[] rules = gson.fromJson(rulesJsonArray, IntegrateRule[].class);
                    LOGGER.debug("got rules " + rules.length);
                    response.setRules(rules);
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(IntegrateRuleList.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    StringReader stringReader = new StringReader(responseEntityString);
                    LOGGER.debug("created stringReader");
                    IntegrateRuleList ruleList = (IntegrateRuleList) jaxbUnmarshaller.unmarshal(stringReader);
                    LOGGER.debug("created ruleList");
                    IntegrateRule[] rules = ruleList.getRules().toArray(new IntegrateRule[0]);
                    LOGGER.debug("created rules");
                    response.setRules(rules);
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
