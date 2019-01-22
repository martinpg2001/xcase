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
import com.xcase.msgraph.objects.MSGraphMailFolder;
import com.xcase.msgraph.transputs.GetMailFoldersRequest;
import com.xcase.msgraph.transputs.GetMailFoldersResponse;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetMailFoldersMethod extends QueryMSGraphMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetMailFoldersMethod() {
        super();
//        LOGGER.debug("finishing GetMailFoldersMethod()");
    }

    public GetMailFoldersResponse getMailFolders(GetMailFoldersRequest request) {
        LOGGER.debug("starting getMailFolders()");
        try {
            GetMailFoldersResponse response = MSGraphResponseFactory.createGetMailFoldersResponse();
            String baseVersionUrl = super.apiUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String endPoint = baseVersionUrl;
            LOGGER.debug("endPoint is " + endPoint);
            if (request.getUserId() != null) {
                LOGGER.debug("userId is " + request.getUserId());
                endPoint = endPoint + "users" + CommonConstant.SLASH_STRING + request.getUserId() + CommonConstant.SLASH_STRING + "mailFolders";
            } else {
                LOGGER.debug("userId is null");
                endPoint = endPoint + "me" + CommonConstant.SLASH_STRING + "mailFolders";
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
                MSGraphMailFolder[] mailFolders = gson.fromJson(groupsJsonArray, MSGraphMailFolder[].class);
                LOGGER.debug("got mail folders " + mailFolders.length);
                response.setMailFolders(mailFolders);
            }

            return response;
        } catch (Exception e) {
            LOGGER.warn("exception getting mail folders: " + e.getMessage());
        }

        return null;
    }
}
