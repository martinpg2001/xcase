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
import static com.xcase.msgraph.impl.simple.methods.GetUsersMethod.LOGGER;
import com.xcase.msgraph.objects.MSGraphUser;
import com.xcase.msgraph.transputs.GetUserRequest;
import com.xcase.msgraph.transputs.GetUserResponse;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetUserMethod extends BaseMSGraphMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetUserMethod() {
        super();
//        LOGGER.debug("finishing GetUserMethod()");
    }

    public GetUserResponse getUser(GetUserRequest getUserRequest) {
        LOGGER.debug("starting getUser()");
        try {
            GetUserResponse response = MSGraphResponseFactory.createGetUserResponse();
            String baseVersionUrl = super.apiUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String endPoint = baseVersionUrl + "users" + CommonConstant.SLASH_STRING;
            LOGGER.debug("endPoint is " + endPoint);
            if (getUserRequest.getUserId() != null) {
                endPoint = endPoint + getUserRequest.getUserId();
            }

            String accessToken = getUserRequest.getAccessToken();
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
                MSGraphUser user = gson.fromJson(responseEntityJsonObject, MSGraphUser.class);
                LOGGER.debug("got user " + user.displayName);
                response.setUser(user);
            }

            return response;
        } catch (Exception e) {
            LOGGER.warn("exception getting user: " + e.getMessage());
        }

        return null;
    }
}
