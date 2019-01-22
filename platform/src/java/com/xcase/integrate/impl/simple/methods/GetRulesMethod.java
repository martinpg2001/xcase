/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.google.gson.*;
import com.xcase.integrate.objects.IntegrateRule;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.transputs.GetRulesRequest;
import com.xcase.integrate.transputs.GetRulesResponse;
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
public class GetRulesMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public GetRulesResponse getRules(GetRulesRequest request) {
        LOGGER.debug("starting getRules()");
        GetRulesResponse response = IntegrateResponseFactory.createGetRulesResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "rules";
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object;
            endPoint = endPoint + CommonConstant.QUESTION_MARK_STRING;
            String locationSeparator = request.getLocationSeparator();
            LOGGER.debug("locationSeparator is " + locationSeparator);
            if (locationSeparator != null) {
                endPoint = endPoint + IntegrateConstant.LOCATION_SEPARATOR + CommonConstant.EQUALS_SIGN_STRING + locationSeparator;
            }

            String parentPath = request.getParentPath();
            LOGGER.debug("parentPath is " + parentPath);
            if (parentPath != null) {
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + IntegrateConstant.PARENT_PATH + CommonConstant.EQUALS_SIGN_STRING + parentPath;
            }
            
            Boolean recursive = request.getRecursive();
            LOGGER.debug("recursive is " + recursive);
            if (recursive != null) {
                endPoint = endPoint + CommonConstant.AND_SIGN_STRING + IntegrateConstant.RECURSIVE + CommonConstant.EQUALS_SIGN_STRING + recursive.booleanValue();
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
                    JsonArray rulesJsonArray = (JsonArray) ConverterUtils.parseStringToJson(responseEntityString);
                    IntegrateRule[] rules = gson.fromJson(rulesJsonArray, IntegrateRule[].class);
                    response.setRules(rules);                        
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(IntegrateRule.class);
                    LOGGER.debug("created jaxbContext");
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    LOGGER.debug("created jaxbUnmarshaller");
                    StringReader stringReader = new StringReader(responseEntityString);
                    LOGGER.debug("created stringReader");
                    IntegrateRule[] rules = (IntegrateRule[]) jaxbUnmarshaller.unmarshal(stringReader);
                    LOGGER.debug("created rule");
                    response.setRules(rules);
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
