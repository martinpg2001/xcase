/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.integrate.objects.AgentActivity;
import com.xcase.integrate.objects.ApplianceAgentActivities;
import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.transputs.GetApplianceAgentStatusRequest;
import com.xcase.integrate.transputs.GetApplianceAgentStatusResponse;
import com.xcase.integrate.transputs.IntegrateResponse;
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
public class GetApplianceAgentStatusMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public GetApplianceAgentStatusResponse getApplianceAgentStatus(GetApplianceAgentStatusRequest request) {
        LOGGER.debug("starting getApplianceAgentStatus()");
        GetApplianceAgentStatusResponse response = IntegrateResponseFactory.createGetApplianceAgentStatusResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String object = "appliance";
            String agentStatus = "agent/status";
            LOGGER.debug("agentStatus is " + agentStatus);
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + object + CommonConstant.SLASH_STRING + agentStatus;
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
                    JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
                    ApplianceAgentActivities applianceAgentActivities = gson.fromJson(responseEntityJsonObject, ApplianceAgentActivities.class);
                    LOGGER.debug("created applianceAgentActivities");
                    JsonArray agentActivitiesArray = responseEntityJsonObject.getAsJsonArray("agent_activities");
                    Iterator<JsonElement> agentActivitiesArrayIterator = agentActivitiesArray.iterator();
                    List<AgentActivity> agentActivityList = new ArrayList<AgentActivity>();
                    while (agentActivitiesArrayIterator.hasNext()) {
                        AgentActivity agentActivity = gson.fromJson(agentActivitiesArrayIterator.next(), AgentActivity.class);
                        agentActivityList.add(agentActivity);
                    }

                    applianceAgentActivities.setAgentActivityList(agentActivityList);
                    response.setApplianceAgentActivities(applianceAgentActivities);
                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {
                    JAXBContext jaxbContext = JAXBContext.newInstance(ApplianceAgentActivities.class);
                    Unmarshaller jaxbUnmarshaller = jaxbContext.createUnmarshaller();
                    StringReader stringReader = new StringReader(responseEntityString);
                    LOGGER.debug("created stringReader");
                    ApplianceAgentActivities applianceAgentActivities = (ApplianceAgentActivities) jaxbUnmarshaller.unmarshal(stringReader);
                    LOGGER.debug("created applianceAgentActivities");
                    response.setApplianceAgentActivities(applianceAgentActivities);
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
