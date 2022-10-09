/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.*;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.utils.ConverterUtils;
import com.xcase.box.objects.BoxException;
import com.xcase.box.objects.BoxMembership;
import com.xcase.box.transputs.GetMembershipsForUserRequest;
import com.xcase.box.transputs.GetMembershipsForUserResponse;
import com.xcase.common.constant.CommonConstant;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;

/**
 * @author martin
 *
 */
public class GetMembershipsForUserMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method gets memberships for a user.
     *
     * @param getMembershipsForUserRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public GetMembershipsForUserResponse getMembershipsForUser(GetMembershipsForUserRequest getMembershipsForUserRequest) throws IOException, BoxException {
        LOGGER.debug("starting getGroup()");
        GetMembershipsForUserResponse getMembershipsForUserResponse = BoxResponseFactory.createGetMembershipsForUserResponse();
        String accessToken = getMembershipsForUserRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String id = getMembershipsForUserRequest.getId();
        LOGGER.debug("id is " + id);
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            StringBuffer urlBuff = super.getApiUrl("users");
            urlBuff.append(CommonConstant.SLASH_STRING + id + CommonConstant.SLASH_STRING + "memberships");
            String getMembershipforUserApiUrl = urlBuff.toString();
            LOGGER.debug("getMembershipforUserApiUrl is " + getMembershipforUserApiUrl);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            try {
                String getMembershipsForUserResult = httpManager.doStringGet(getMembershipforUserApiUrl, headers, null);
                LOGGER.debug("getMembershipsForGroupResult is " + getMembershipsForUserResult);
                JsonObject jsonObject = (JsonObject) JsonParser.parseString(getMembershipsForUserResult);
                String totalCount = jsonObject.getAsJsonPrimitive("total_count").getAsString();
                getMembershipsForUserResponse.setTotalCount(totalCount);
                LOGGER.debug("totalCount is " + totalCount);
                JsonArray membershipArray = jsonObject.getAsJsonArray("entries");
                List<BoxMembership> boxMembershipList = ConverterUtils.toBoxMembershipList(membershipArray);
                getMembershipsForUserResponse.setMemberships(boxMembershipList);
            } catch (Exception e) {
                LOGGER.debug("failed to parse to a document");
                BoxException be = new BoxException("failed to parse to a document.", e);
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_XML");
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return getMembershipsForUserResponse;
    }
}
