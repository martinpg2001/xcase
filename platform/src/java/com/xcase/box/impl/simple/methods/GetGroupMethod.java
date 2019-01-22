/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.*;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.objects.BoxGroupImpl;
import com.xcase.box.objects.BoxException;
import com.xcase.box.objects.BoxGroup;
import com.xcase.box.transputs.GetGroupRequest;
import com.xcase.box.transputs.GetGroupResponse;
import com.xcase.common.constant.CommonConstant;
import java.io.IOException;
import java.lang.invoke.*;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;

/**
 * @author martin
 *
 */
public class GetGroupMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method gets a group.
     *
     * @param getGroupRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public GetGroupResponse getGroup(GetGroupRequest getGroupRequest) throws IOException, BoxException {
        LOGGER.debug("starting getGroup()");
        GetGroupResponse getGroupResponse = BoxResponseFactory.createGetGroupResponse();
        String accessToken = getGroupRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String id = getGroupRequest.getId();
        LOGGER.debug("id is " + id);
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            StringBuffer urlBuff = super.getApiUrl("groups");
            urlBuff.append(CommonConstant.SLASH_STRING + id);
            String groupsApiUrl = urlBuff.toString();
            LOGGER.debug("groupsApiUrl is " + groupsApiUrl);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            try {
                String getGroupResult = httpManager.doStringGet(groupsApiUrl, headers, null);
                LOGGER.debug("getGroupResult is " + getGroupResult);
                Gson gson = new Gson();
                BoxGroup boxGroup = gson.fromJson(getGroupResult, BoxGroupImpl.class);
                LOGGER.debug("created group from getGroupResult");
                getGroupResponse.setGroup(boxGroup);
                LOGGER.debug("set group");
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

        return getGroupResponse;
    }
}
