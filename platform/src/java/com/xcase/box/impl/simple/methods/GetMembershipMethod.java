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
import com.xcase.box.transputs.GetMembershipRequest;
import com.xcase.box.transputs.GetMembershipResponse;
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
public class GetMembershipMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method gets a group membership.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be:
     * 'e_no_parent_folder', 'not_logged_in', 'application_r'stricted'.
     *
     * @param getMembershipRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public GetMembershipResponse getMembership(GetMembershipRequest getMembershipRequest) throws IOException, BoxException {
        LOGGER.debug("starting getMembership()");
        GetMembershipResponse getMembershipResponse = BoxResponseFactory.createGetMembershipResponse();
        String accessToken = getMembershipRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String membershipId = getMembershipRequest.getId();
        LOGGER.debug("membershipId is " + membershipId);
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            URLCodec codec = new URLCodec();
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            LOGGER.debug("set headers");
            try {
                LOGGER.debug("about to build collaborationsApiUrl");
                StringBuffer urlBuff = super.getApiUrl("group_memberships");
                urlBuff.append(CommonConstant.SLASH_STRING + membershipId);
                String groupMembershipsApiUrl = urlBuff.toString();
                LOGGER.debug("groupMembershipsApiUrl is " + groupMembershipsApiUrl);
                String result = httpManager.doStringGet(groupMembershipsApiUrl, headers, null);
                LOGGER.info("done get");
                LOGGER.debug("result is " + result);
                JsonObject jsonObject = (JsonObject) JsonParser.parseString(result);
                BoxMembership boxMembership = ConverterUtils.toBoxMembership(jsonObject);
                getMembershipResponse.setMembership(boxMembership);
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

        return getMembershipResponse;
    }
}
