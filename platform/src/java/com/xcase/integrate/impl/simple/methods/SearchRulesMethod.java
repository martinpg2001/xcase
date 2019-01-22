/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.common.utils.URLUtils;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.objects.IntegrateRule;
import com.xcase.integrate.objects.IntegrateRuleList;
import com.xcase.integrate.transputs.SearchRulesRequest;
import com.xcase.integrate.transputs.SearchRulesResponse;
import java.io.StringReader;
import java.lang.invoke.*;
import java.util.*;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martinpg
 */
public class SearchRulesMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public SearchRulesResponse searchRules(SearchRulesRequest request) {
        LOGGER.debug("starting searchRules()");
        SearchRulesResponse response = IntegrateResponseFactory.createSearchRulesResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "rules";
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object + CommonConstant.QUESTION_MARK_STRING;
            LOGGER.debug("endPoint is " + endPoint);
            String createdEnd = request.getCreatedEnd();
            LOGGER.debug("createdEnd is " + createdEnd);
            if (createdEnd != null) {
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + IntegrateConstant.CREATED_END + CommonConstant.EQUALS_SIGN_STRING + createdEnd;
            }
            
            String createdStart = request.getCreatedStart();
            LOGGER.debug("createdStart is " + createdStart);
            if (createdStart != null) {
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + IntegrateConstant.CREATED_START + CommonConstant.EQUALS_SIGN_STRING + createdStart;
            }
            
            String errorState = request.getErrorState();
            LOGGER.debug("errorState is " + errorState);
            if (errorState != null) {
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + IntegrateConstant.ERROR_STATE + CommonConstant.EQUALS_SIGN_STRING + errorState;
            }
            
            String filterOption = request.getFilterOption();
            LOGGER.debug("filterOption is " + filterOption);
            if (filterOption != null) {
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + IntegrateConstant.FILTER_OPTION + CommonConstant.EQUALS_SIGN_STRING + filterOption;
            }
            
            String keyword = request.getKeyword();
            LOGGER.debug("keyword is " + keyword);
            if (keyword != null) {
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + IntegrateConstant.KEYWORD + CommonConstant.EQUALS_SIGN_STRING + keyword;
            }

            String logSettings = request.getLogSettings();
            LOGGER.debug("logSettings is " + logSettings);
            if (logSettings != null) {
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + IntegrateConstant.LOG_SETTINGS + CommonConstant.EQUALS_SIGN_STRING + logSettings;
            }
            
            String modifiedEnd = request.getModifiedEnd();
            LOGGER.debug("modifiedEnd is " + modifiedEnd);
            if (createdEnd != null) {
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + IntegrateConstant.MODIFIED_END + CommonConstant.EQUALS_SIGN_STRING + modifiedEnd;
            }
            
            String modifiedStart = request.getModifiedStart();
            LOGGER.debug("modifiedStart is " + modifiedStart);
            if (modifiedStart != null) {
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + IntegrateConstant.MODIFIED_START + CommonConstant.EQUALS_SIGN_STRING + modifiedStart;
            }
            
            String status = request.getStatus();
            LOGGER.debug("status is " + status);
            if (status != null) {
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + IntegrateConstant.RULE_STATUS + CommonConstant.EQUALS_SIGN_STRING + status;
            }

            LOGGER.debug("endPoint is " + endPoint);
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
                    JsonArray rulesJsonArray = (JsonArray) ConverterUtils.parseStringToJson(responseEntityString);
                    LOGGER.debug("created rulesJsonArray");
                    IntegrateRule[] rules = gson.fromJson(rulesJsonArray, IntegrateRule[].class);
                    LOGGER.debug("created rules");
                    response.setRules(rules);
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(IntegrateRuleList.class);
                    LOGGER.debug("created jaxbContext");
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    LOGGER.debug("created jaxbUnmarshaller");
                    StringReader stringReader = new StringReader(responseEntityString);
                    LOGGER.debug("created stringReader");
                    IntegrateRuleList ruleList = (IntegrateRuleList) jaxbUnmarshaller.unmarshal(stringReader);
                    LOGGER.debug("created ruleList");
                    List<IntegrateRule> integrateRuleList = ruleList.getRules();
                    LOGGER.debug("created integrateRuleList");
                    if (integrateRuleList != null) {
                        IntegrateRule[] rules = integrateRuleList.toArray(new IntegrateRule[0]);
                        LOGGER.debug("created rules");
                        response.setRules(rules);
                    }
                    
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
