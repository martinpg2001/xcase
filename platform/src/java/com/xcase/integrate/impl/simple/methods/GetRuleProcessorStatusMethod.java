/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.integrate.objects.RuleProcessorStatus;
import com.xcase.integrate.objects.RuleProcessorStatuses;
import com.xcase.integrate.objects.RuleProcessorInfos;
import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.transputs.GetRuleProcessorStatusRequest;
import com.xcase.integrate.transputs.GetRuleProcessorStatusResponse;
import java.io.StringReader;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martinpg
 */
public class GetRuleProcessorStatusMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public GetRuleProcessorStatusResponse getRuleProcessorStatus(GetRuleProcessorStatusRequest request) {
        LOGGER.debug("starting getRuleProcessorStatus()");
        GetRuleProcessorStatusResponse response = IntegrateResponseFactory.createGetRuleProcessorStatusResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "appliance";
            String rpStatus = "rp/status";
            LOGGER.debug("rpStatus is " + rpStatus);
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object + CommonConstant.SLASH_STRING + rpStatus;
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
                LOGGER.debug("responseEntityString is " + responseEntityString);
                if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_JSON.equals(this.apiRequestFormat)) {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
                    JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
                    RuleProcessorStatuses ruleProcessorStatuses = gson.fromJson(responseEntityJsonObject, RuleProcessorStatuses.class);
                    LOGGER.debug("created ruleProcessorStatuses");
                    JsonPrimitive totalRuleProcessorCPUUsageObject = responseEntityJsonObject.getAsJsonPrimitive("total_rule_processor_cpu_usage");
                    LOGGER.debug("created totalRuleProcessorCPUUsageObject");
                    String totalRuleProcessorCPUUsage = totalRuleProcessorCPUUsageObject.getAsString();
                    LOGGER.debug("totalRuleProcessorCPUUsage is " + totalRuleProcessorCPUUsage);
                    JsonArray ruleProcessorCPUUsageArray = responseEntityJsonObject.getAsJsonArray("rule_processor_cpu_usages");
                    Iterator<JsonElement> ruleProcessorCPUUsageArrayIterator = ruleProcessorCPUUsageArray.iterator();
                    List<RuleProcessorStatus> ruleProcessorStatusList = new ArrayList<RuleProcessorStatus>();
                    while (ruleProcessorCPUUsageArrayIterator.hasNext()) {
                        RuleProcessorStatus ruleProcessorStatus = gson.fromJson(ruleProcessorCPUUsageArrayIterator.next(), RuleProcessorStatus.class);
                        ruleProcessorStatusList.add(ruleProcessorStatus);
                    }

                    RuleProcessorInfos ruleProcessorInfos = new RuleProcessorInfos();
                    ruleProcessorInfos.setRuleProcessorStatusList(ruleProcessorStatusList);
                    ruleProcessorStatuses.ruleProcessorInfos = ruleProcessorInfos;
                    response.setRuleProcessorStatuses(ruleProcessorStatuses);
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(RuleProcessorStatuses.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    StringReader stringReader = new StringReader(responseEntityString);
                    LOGGER.debug("created stringReader");
                    RuleProcessorStatuses ruleProcessorStatuses = (RuleProcessorStatuses) jaxbUnmarshaller.unmarshal(stringReader);
                    LOGGER.debug("created ruleProcessorStatuses");
                    LOGGER.debug("ruleProcessorStatuses ruleProcessorInfos is " + ruleProcessorStatuses.ruleProcessorInfos);
                    LOGGER.debug("ruleProcessorStatuses.ruleProcessorInfos.ruleProcessorStatusList is " + ruleProcessorStatuses.ruleProcessorInfos.ruleProcessorStatusList);
                    response.setRuleProcessorStatuses(ruleProcessorStatuses);
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
