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
import com.xcase.box.transputs.CreateMembershipRequest;
import com.xcase.box.transputs.CreateMembershipResponse;
import com.xcase.common.constant.CommonConstant;
import java.io.IOException;
import java.lang.invoke.*;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;

/**
 * @author martin
 *
 */
public class CreateMembershipMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method creates a new group membership.
     *
     * 'parent_id' param is the id of a folder in which a new file will be
     * created, 'name' param is the name of a new folder. Set 'share' to 1 if
     * you want to share a folder publicly.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be:
     * 'e_no_parent_folder', 'not_logged_in', 'application_r'stricted'.
     *
     * @param createMembershipRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public CreateMembershipResponse createMembership(CreateMembershipRequest createMembershipRequest) throws IOException, BoxException {
        LOGGER.debug("starting createMembership()");
        CreateMembershipResponse createMembershipResponse = BoxResponseFactory.createCreateMembershipResponse();
        String accessToken = createMembershipRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String groupId = createMembershipRequest.getGroupId();
        LOGGER.debug("groupId is " + groupId);
        String userId = createMembershipRequest.getUserId();
        LOGGER.debug("userId is " + userId);
        String role = createMembershipRequest.getRole();
        LOGGER.debug("role is " + role);
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            URLCodec codec = new URLCodec();
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header xHTTPMethodHeader = new BasicHeader(CommonConstant.X_HTTP_METHOD_STRING, "POST");
            LOGGER.debug("created xHTTPMethodHeader header");
            Header[] headers = {header, xHTTPMethodHeader};
            LOGGER.debug("set headers");
            String entityString = "{ \"user\": {\"id\":\"" + userId + "\" }, \"group\": {\"id\": \"" + groupId + "\" }, \"role\":\"" + role + "\" }";
            LOGGER.debug("entityString is " + entityString);
            try {
                LOGGER.debug("about to build groupMembershipsApiUrl");
                StringBuffer urlBuff = super.getApiUrl("group_memberships");
                String groupMembershipsApiUrl = urlBuff.toString();
                LOGGER.debug("groupMembershipsApiUrl is " + groupMembershipsApiUrl);
                String result = httpManager.doStringPost(groupMembershipsApiUrl, headers, null, entityString, null);
                LOGGER.info("done post");
                LOGGER.debug("result is " + result);
                JsonParser jsonParser = new JsonParser();
                JsonObject jsonObject = (JsonObject) jsonParser.parse(result);
                BoxMembership boxMembership = ConverterUtils.toBoxMembership(jsonObject);
                createMembershipResponse.setMembership(boxMembership);
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

        return createMembershipResponse;
    }
}
