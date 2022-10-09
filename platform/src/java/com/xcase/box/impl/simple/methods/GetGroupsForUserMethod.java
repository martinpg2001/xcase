/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.*;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.utils.ConverterUtils;
import com.xcase.box.objects.BoxException;
import com.xcase.box.objects.BoxGroup;
import com.xcase.box.transputs.GetGroupsForUserRequest;
import com.xcase.box.transputs.GetGroupsForUserResponse;
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
public class GetGroupsForUserMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method gets memberships for a user.
     *
     * @param getGroupsForUserRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public GetGroupsForUserResponse getGroupsForUser(GetGroupsForUserRequest getGroupsForUserRequest) throws IOException, BoxException {
        LOGGER.debug("starting getGroupsForUser()");
        GetGroupsForUserResponse getGroupsForUserResponse = BoxResponseFactory.createGetGroupsForUserResponse();
        String accessToken = getGroupsForUserRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            StringBuffer urlBuff = super.getApiUrl("groups");
            String getGroupsforUserApiUrl = urlBuff.toString();
            LOGGER.debug("getMembershipforUserApiUrl is " + getGroupsforUserApiUrl);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            try {
                String getGroupsForUserResult = httpManager.doStringGet(getGroupsforUserApiUrl, headers, null);
                LOGGER.debug("getGroupsForUserResult is " + getGroupsForUserResult);
                JsonObject jsonObject = (JsonObject) JsonParser.parseString(getGroupsForUserResult);
                String totalCount = jsonObject.getAsJsonPrimitive("total_count").getAsString();
                getGroupsForUserResponse.setTotalCount(totalCount);
                LOGGER.debug("totalCount is " + totalCount);
                JsonArray groupArray = jsonObject.getAsJsonArray("entries");
                List<BoxGroup> boxGroupList = ConverterUtils.toBoxGroupList(groupArray);
                getGroupsForUserResponse.setGroups(boxGroupList);
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

        return getGroupsForUserResponse;
    }
}
