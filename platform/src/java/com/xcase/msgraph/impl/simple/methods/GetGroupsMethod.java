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
import com.xcase.msgraph.objects.MSGraphGroup;
import com.xcase.msgraph.transputs.GetGroupsRequest;
import com.xcase.msgraph.transputs.GetGroupsResponse;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetGroupsMethod extends QueryMSGraphMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetGroupsMethod() {
        super();
//        LOGGER.debug("finishing GetGroupsMethod()");
    }

    public GetGroupsResponse getGroups(GetGroupsRequest request) {
        LOGGER.debug("starting getGroups()");
        try {
            GetGroupsResponse response = MSGraphResponseFactory.createGetGroupsResponse();
            String baseVersionUrl = super.apiUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String endPoint = baseVersionUrl + "groups" + CommonConstant.QUESTION_MARK_STRING;
            LOGGER.debug("endPoint is " + endPoint);
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
                MSGraphGroup[] groups = gson.fromJson(groupsJsonArray, MSGraphGroup[].class);
                LOGGER.debug("got groups " + groups.length);
                response.setGroups(groups);
            }

            return response;
        } catch (Exception e) {
            LOGGER.warn("exception getting groups: " + e.getMessage());
        }

        return null;
    }
}
