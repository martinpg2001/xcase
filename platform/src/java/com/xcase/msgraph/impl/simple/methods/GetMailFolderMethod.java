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
import com.xcase.msgraph.objects.MSGraphMailFolder;
import com.xcase.msgraph.transputs.GetMailFolderRequest;
import com.xcase.msgraph.transputs.GetMailFolderResponse;
import java.lang.invoke.MethodHandles;
import java.net.URL;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetMailFolderMethod extends BaseMSGraphMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetMailFolderMethod() {
        super();
//        LOGGER.debug("finishing GetMailFolderMethod()");
    }

    public GetMailFolderResponse getMailFolder(GetMailFolderRequest request) {
        LOGGER.debug("starting getMailFolder()");
        try {
            GetMailFolderResponse response = MSGraphResponseFactory.createGetMailFolderResponse();
            String baseVersionUrl = super.apiUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String endPoint = baseVersionUrl;
            LOGGER.debug("endPoint is " + endPoint);
            if (request.getUserId() != null && request.getMailFolderId() != null) {
                endPoint = endPoint + "users" + CommonConstant.SLASH_STRING + request.getUserId() + CommonConstant.SLASH_STRING + "mailFolders" + CommonConstant.SLASH_STRING + request.getMailFolderId();
            } else {
                endPoint = endPoint + "me" + CommonConstant.SLASH_STRING + "mailFolders" + CommonConstant.SLASH_STRING + request.getMailFolderId();
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
                MSGraphMailFolder mailFolder = gson.fromJson(responseEntityJsonObject, MSGraphMailFolder.class);
                LOGGER.debug("got mail folder " + mailFolder.id);
                response.setMailFolder(mailFolder);
            }

            return response;
        } catch (Exception e) {
            LOGGER.warn("exception getting mail folder: " + e.getMessage());
        }

        return null;
    }
}
