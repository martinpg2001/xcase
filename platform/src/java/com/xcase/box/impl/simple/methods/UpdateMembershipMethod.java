/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.*;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.objects.BoxMembershipImpl;
import com.xcase.box.objects.BoxException;
import com.xcase.box.objects.BoxMembership;
import com.xcase.box.transputs.UpdateMembershipRequest;
import com.xcase.box.transputs.UpdateMembershipResponse;
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
public class UpdateMembershipMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method updates a group.
     *
     * @param updateMembershipRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public UpdateMembershipResponse updateMembership(UpdateMembershipRequest updateMembershipRequest) throws IOException, BoxException {
        LOGGER.debug("starting updateMembership()");
        UpdateMembershipResponse updateMembershipResponse = BoxResponseFactory.createUpdateMembershipResponse();
        String accessToken = updateMembershipRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String id = updateMembershipRequest.getId();
        LOGGER.debug("accessToken is " + accessToken);
        String role = updateMembershipRequest.getRole();
        LOGGER.debug("role is " + role);
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            StringBuffer urlBuff = super.getApiUrl("group_memberships");
            urlBuff.append(CommonConstant.SLASH_STRING + id);
            String groupMembershipsApiUrl = urlBuff.toString();
            LOGGER.debug("groupsApiUrl is " + groupMembershipsApiUrl);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header xHTTPMethodHeader = new BasicHeader(CommonConstant.X_HTTP_METHOD_STRING, "PUT");
            LOGGER.debug("created xHTTPMethodHeader header");
            Header[] headers = {header, xHTTPMethodHeader};
            String entityString = "{\"role\": \"" + role + "\"}";
            LOGGER.debug("entityString is " + entityString);
            try {
                String updateMembershipResult = httpManager.doStringPut(groupMembershipsApiUrl, headers, null, entityString);
                LOGGER.debug("updateMembershipResult is " + updateMembershipResult);
                Gson gson = new Gson();
                BoxMembership boxMembership = gson.fromJson(updateMembershipResult, BoxMembershipImpl.class);
                LOGGER.debug("created membership from userInfoString");
                updateMembershipResponse.setMembership(boxMembership);
                LOGGER.debug("set boxMembership in response");
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

        return updateMembershipResponse;
    }
}
