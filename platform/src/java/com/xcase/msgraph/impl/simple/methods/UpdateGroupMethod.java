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
import com.xcase.msgraph.objects.MSGraphGroup;
import com.xcase.msgraph.transputs.UpdateGroupRequest;
import com.xcase.msgraph.transputs.UpdateGroupResponse;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class UpdateGroupMethod extends BaseMSGraphMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public UpdateGroupMethod() {
        super();
//        LOGGER.debug("finishing UpdateGroupMethod()");
    }

    public UpdateGroupResponse updateGroup(UpdateGroupRequest request) {
        LOGGER.debug("starting updateGroup()");
        try {
            UpdateGroupResponse response = MSGraphResponseFactory.createUpdateGroupResponse();
            String baseVersionUrl = super.apiUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String endPoint = baseVersionUrl + "groups" + CommonConstant.SLASH_STRING;
            LOGGER.debug("endPoint is " + endPoint);
            if (request.getGroupId() != null) {
                endPoint = endPoint + request.getGroupId();
            }

            MSGraphGroup group = request.getGroup();
            Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
            String groupJson = gson.toJson(group);
            LOGGER.debug("groupJson is " + groupJson);
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createMSGraphAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponsePatch(endPoint, headers, null, groupJson, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 204) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
            }

            return response;
        } catch (Exception e) {
            LOGGER.warn("exception updating group: " + e.getMessage());
        }

        return null;
    }
}
