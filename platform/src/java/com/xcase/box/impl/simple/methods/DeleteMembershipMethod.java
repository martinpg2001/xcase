/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.DeleteMembershipRequest;
import com.xcase.box.transputs.DeleteMembershipResponse;
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
public class DeleteMembershipMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method deletes a group membership.
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
     * @param deleteMembershipRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public DeleteMembershipResponse deleteMembership(DeleteMembershipRequest deleteMembershipRequest) throws IOException, BoxException {
        LOGGER.debug("starting deleteMembership()");
        DeleteMembershipResponse deleteMembershipResponse = BoxResponseFactory.createDeleteMembershipResponse();
        String accessToken = deleteMembershipRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String groupId = deleteMembershipRequest.getGroupId();
        String membershipId = deleteMembershipRequest.getId();
        LOGGER.debug("membershipId is " + membershipId);
        String userId = deleteMembershipRequest.getUserId();
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            StringBuffer urlBuff = super.getApiUrl("group_memberships");
            urlBuff.append(CommonConstant.SLASH_STRING + membershipId);
            String groupMembershipsApiUrl = urlBuff.toString();
            LOGGER.debug("groupMembershipsApiUrl is " + groupMembershipsApiUrl);
            URLCodec codec = new URLCodec();
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header xHTTPMethodHeader = new BasicHeader(CommonConstant.X_HTTP_METHOD_STRING, "DELETE");
            LOGGER.debug("created xHTTPMethodHeader header");
            Header[] headers = {header, xHTTPMethodHeader};
            LOGGER.debug("set headers");
            try {
                String result = httpManager.doStringDelete(groupMembershipsApiUrl, headers, null, null, null);
                LOGGER.info("done delete");
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

        return deleteMembershipResponse;
    }
}
