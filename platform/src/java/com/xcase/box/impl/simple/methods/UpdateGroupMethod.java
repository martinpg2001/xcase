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
import com.xcase.box.transputs.UpdateGroupRequest;
import com.xcase.box.transputs.UpdateGroupResponse;
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
public class UpdateGroupMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method updates a group.
     *
     * @param updateGroupRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public UpdateGroupResponse updateGroup(UpdateGroupRequest updateGroupRequest) throws IOException, BoxException {
        LOGGER.debug("starting updateGroup()");
        UpdateGroupResponse updateGroupResponse = BoxResponseFactory.createUpdateGroupResponse();
        String accessToken = updateGroupRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String id = updateGroupRequest.getId();
        LOGGER.debug("id is " + id);
        String name = updateGroupRequest.getName();
        LOGGER.debug("name is " + name);
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
            Header xHTTPMethodHeader = new BasicHeader(CommonConstant.X_HTTP_METHOD_STRING, "PUT");
            LOGGER.debug("created xHTTPMethodHeader header");
            Header[] headers = {header, xHTTPMethodHeader};
            String entityString = "{\"name\": \"" + name + "\"}";
            LOGGER.debug("entityString is " + entityString);
            try {
                String updateGroupResult = httpManager.doStringPut(groupsApiUrl, headers, null, entityString);
                LOGGER.debug("updateGroupResult is " + updateGroupResult);
                JsonElement jsonElement = com.xcase.common.utils.ConverterUtils.parseStringToJson(updateGroupResult);
                JsonObject jsonObject = (JsonObject) jsonElement;
                JsonElement typeElement = jsonObject.get("type");
                JsonElement idElement = jsonObject.get("id");
                JsonElement nameElement = jsonObject.get("name");
                LOGGER.info("about to convert jsonObject to BoxGroup");
                BoxGroup boxGroup = ConverterUtils.toBoxGroup(jsonObject);
                LOGGER.info("converted jsonObject to BoxGroup");
                updateGroupResponse.setGroup(boxGroup);
                LOGGER.info("set group in response");
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

        return updateGroupResponse;
    }
}
