/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.impl.simple.methods;

import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.integrate.objects.IntegrateRule;
import com.xcase.msgraph.factories.MSGraphResponseFactory;
import com.xcase.msgraph.objects.MSGraphCalendar;
import com.xcase.msgraph.transputs.GetCalendarsRequest;
import com.xcase.msgraph.transputs.GetCalendarsResponse;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetCalendarsMethod extends QueryMSGraphMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetCalendarsMethod() {
        super();
//        LOGGER.debug("finishing GetCalendarsMethod()");
    }

    public GetCalendarsResponse getCalendars(GetCalendarsRequest request) {
        LOGGER.debug("starting getCalendars()");
        try {
            GetCalendarsResponse response = MSGraphResponseFactory.createGetCalendarsResponse();
            String baseVersionUrl = super.apiUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String endPoint = baseVersionUrl;
            LOGGER.debug("endPoint is " + endPoint);
            if (request.getUserId() != null) {
                LOGGER.debug("userId is " + request.getUserId());
                endPoint = endPoint + "users" + CommonConstant.SLASH_STRING + request.getUserId() + CommonConstant.SLASH_STRING + "calendars";
            } else {
                LOGGER.debug("userId is null");
                endPoint = endPoint + "me" + CommonConstant.SLASH_STRING + "calendars";
            }

            StringBuffer endPointStringBuffer = new StringBuffer(endPoint);
            endPoint = addQuery(endPointStringBuffer, request);
            LOGGER.debug("endPoint is " + endPoint);
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createMSGraphAuthenticationTokenHeader(accessToken);
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
                Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
                JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
                LOGGER.debug("parsed responseEntityString to JsonObject");
                JsonArray groupsJsonArray = responseEntityJsonObject.getAsJsonArray("value");
                MSGraphCalendar[] calendars = gson.fromJson(groupsJsonArray, MSGraphCalendar[].class);
                LOGGER.debug("got calendars " + calendars.length);
                response.setCalendars(calendars);
            }

            return response;
        } catch (Exception e) {
            LOGGER.warn("exception getting calendars: " + e.getMessage());
        }

        return null;
    }
}
