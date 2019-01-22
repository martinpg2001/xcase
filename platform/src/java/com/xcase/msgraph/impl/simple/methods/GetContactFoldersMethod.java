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
import com.xcase.msgraph.objects.MSGraphContactFolder;
import com.xcase.msgraph.transputs.GetContactFoldersRequest;
import com.xcase.msgraph.transputs.GetContactFoldersResponse;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetContactFoldersMethod extends QueryMSGraphMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetContactFoldersMethod() {
        super();
//        LOGGER.debug("finishing GetContactFoldersMethod()");
    }

    public GetContactFoldersResponse getContactFolders(GetContactFoldersRequest request) {
        LOGGER.debug("starting getContactFolders()");
        try {
            GetContactFoldersResponse response = MSGraphResponseFactory.createGetContactFoldersResponse();
            String baseVersionUrl = super.apiUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String endPoint = baseVersionUrl;
            LOGGER.debug("endPoint is " + endPoint);
            if (request.getUserId() != null) {
                LOGGER.debug("userId is " + request.getUserId());
                endPoint = endPoint + "users" + CommonConstant.SLASH_STRING + request.getUserId() + CommonConstant.SLASH_STRING + "contactFolders";
            } else {
                LOGGER.debug("userId is null");
                endPoint = endPoint + "me" + CommonConstant.SLASH_STRING + "contactFolders";
            }

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
                MSGraphContactFolder[] contactFolders = gson.fromJson(groupsJsonArray, MSGraphContactFolder[].class);
                LOGGER.debug("got contact folders " + contactFolders.length);
                response.setContactFolders(contactFolders);
            }

            return response;
        } catch (Exception e) {
            LOGGER.warn("exception getting contact folders: " + e.getMessage());
        }

        return null;
    }
}
