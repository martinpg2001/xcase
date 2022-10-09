/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.*;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.GetCollaborationsForGroupRequest;
import com.xcase.box.transputs.GetCollaborationsForGroupResponse;
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
public class GetCollaborationsForGroupMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method gets memberships for a group.
     *
     * @param getCollaborationsForGroupRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public GetCollaborationsForGroupResponse getCollaborationsForGroup(GetCollaborationsForGroupRequest getCollaborationsForGroupRequest) throws IOException, BoxException {
        LOGGER.debug("starting getGroup()");
        GetCollaborationsForGroupResponse getCollaborationsForGroupResponse = BoxResponseFactory.createGetCollaborationsForGroupResponse();
        String accessToken = getCollaborationsForGroupRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String id = getCollaborationsForGroupRequest.getId();
        LOGGER.debug("id is " + id);
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            StringBuffer urlBuff = super.getApiUrl("groups");
            urlBuff.append(CommonConstant.SLASH_STRING + id + CommonConstant.SLASH_STRING + "memberships");
            String groupsApiUrl = urlBuff.toString();
            LOGGER.debug("groupsApiUrl is " + groupsApiUrl);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            try {
                String getCollaborationsForGroupResult = httpManager.doStringGet(groupsApiUrl, headers, null);
                LOGGER.info("done get");
                JsonObject jsonObject = (JsonObject) JsonParser.parseString(getCollaborationsForGroupResult);
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

        return getCollaborationsForGroupResponse;
    }
}
