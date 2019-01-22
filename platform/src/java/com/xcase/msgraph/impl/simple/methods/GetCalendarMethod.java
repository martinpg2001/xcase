/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.impl.simple.methods;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.msgraph.factories.MSGraphResponseFactory;
import com.xcase.msgraph.objects.MSGraphCalendar;
import com.xcase.msgraph.transputs.GetCalendarRequest;
import com.xcase.msgraph.transputs.GetCalendarResponse;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetCalendarMethod extends BaseMSGraphMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetCalendarMethod() {
        super();
//        LOGGER.debug("finishing GetCalendarMethod()");
    }

    public GetCalendarResponse getCalendar(GetCalendarRequest getCalendarRequest) {
        LOGGER.debug("starting getCalendar()");
        try {
            GetCalendarResponse response = MSGraphResponseFactory.createGetCalendarResponse();
            String baseVersionUrl = super.apiUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String endPoint = baseVersionUrl;
            LOGGER.debug("endPoint is " + endPoint);
            if (getCalendarRequest.getUserId() != null && getCalendarRequest.getCalendarId() != null) {
                endPoint = endPoint + "users" + CommonConstant.SLASH_STRING + getCalendarRequest.getUserId() + CommonConstant.SLASH_STRING + "calendars" + CommonConstant.SLASH_STRING + getCalendarRequest.getCalendarId() + "/calendarView";
            }

            endPoint = endPoint + "?startDateTime=2017-06-01T00:00:00.0000000&endDateTime=2017-07-01T00:00:00.0000000";
            LOGGER.debug("endPoint is " + endPoint);
            String accessToken = getCalendarRequest.getAccessToken();
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
                MSGraphCalendar calendar = gson.fromJson(responseEntityJsonObject, MSGraphCalendar.class);
                LOGGER.debug("got calendar " + calendar.id);
                response.setCalendar(calendar);
            }

            return response;
        } catch (Exception e) {
            LOGGER.warn("exception getting calendar: " + e.getMessage());
        }

        return null;
    }
}
