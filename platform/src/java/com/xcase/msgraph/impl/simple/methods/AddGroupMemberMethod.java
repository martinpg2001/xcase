/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.impl.simple.methods;

import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.msgraph.constant.MSGraphConstant;
import com.xcase.msgraph.factories.MSGraphResponseFactory;
import com.xcase.msgraph.objects.MSGraphGroup;
import com.xcase.msgraph.transputs.AddGroupMemberRequest;
import com.xcase.msgraph.transputs.AddGroupMemberResponse;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class AddGroupMemberMethod extends BaseMSGraphMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public AddGroupMemberMethod() {
        super();
//        LOGGER.debug("finishing AddGroupMemberMethod()");
    }

    public AddGroupMemberResponse addGroupMember(AddGroupMemberRequest addGroupMemberRequest) {
        LOGGER.debug("starting addGroupMember()");
        try {
            AddGroupMemberResponse response = MSGraphResponseFactory.createAddGroupMemberResponse();
            String baseVersionUrl = super.apiUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String endPoint = baseVersionUrl + "groups" + CommonConstant.SLASH_STRING;
            LOGGER.debug("endPoint is " + endPoint);
            if (addGroupMemberRequest.getGroupId() != null) {
                endPoint = endPoint + addGroupMemberRequest.getGroupId() + CommonConstant.SLASH_STRING + "members" + CommonConstant.SLASH_STRING + "$ref";
            }

            String memberBody = "{\"@odata.id\": \"https://graph.microsoft.com/v1.0/directoryObjects/{id}\"}";
            if (addGroupMemberRequest.getMemberId() != null) {
                memberBody = memberBody.replace("{id}", addGroupMemberRequest.getMemberId());
            }

            String accessToken = addGroupMemberRequest.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createMSGraphAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponsePost(endPoint, headers, null, memberBody, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 204) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
            }

            return response;
        } catch (Exception e) {
            LOGGER.warn("exception adding group member: " + e.getMessage());
        }

        return null;
    }
}
