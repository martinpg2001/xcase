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
import com.xcase.msgraph.objects.MSGraphContactFolder;
import com.xcase.msgraph.transputs.GetContactFolderRequest;
import com.xcase.msgraph.transputs.GetContactFolderResponse;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetContactFolderMethod extends BaseMSGraphMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetContactFolderMethod() {
        super();
//        LOGGER.debug("finishing GetContactFolderMethod()");
    }

    public GetContactFolderResponse getContactFolder(GetContactFolderRequest request) {
        LOGGER.debug("starting getContactFolder()");
        try {
            GetContactFolderResponse response = MSGraphResponseFactory.createGetContactFolderResponse();
            String baseVersionUrl = super.apiUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String endPoint = baseVersionUrl;
            LOGGER.debug("endPoint is " + endPoint);
            if (request.getUserId() != null && request.getContactFolderId() != null) {
                endPoint = endPoint + "users" + CommonConstant.SLASH_STRING + request.getUserId() + CommonConstant.SLASH_STRING + "mailFolders" + CommonConstant.SLASH_STRING + request.getContactFolderId();
            } else {
                endPoint = endPoint + "me" + CommonConstant.SLASH_STRING + "mailFolders" + CommonConstant.SLASH_STRING + request.getContactFolderId();
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
                MSGraphContactFolder mailFolder = gson.fromJson(responseEntityJsonObject, MSGraphContactFolder.class);
                LOGGER.debug("got mail folder " + mailFolder.id);
                response.setContactFolder(mailFolder);
            }

            return response;
        } catch (Exception e) {
            LOGGER.warn("exception getting mail folder: " + e.getMessage());
        }

        return null;
    }
}
