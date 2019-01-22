/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.*;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.objects.BoxCollaborationImpl;
import com.xcase.box.objects.BoxCollaboration;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.GetCollaborationRequest;
import com.xcase.box.transputs.GetCollaborationResponse;
import com.xcase.common.constant.CommonConstant;
import java.io.IOException;
import java.lang.invoke.*;
import org.apache.commons.codec.net.URLCodec;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class GetCollaborationMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method gets a collaboration.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be:
     * 'e_no_parent_folder', 'not_logged_in', 'application_r'stricted'.
     *
     * @param getCollaborationRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public GetCollaborationResponse getCollaboration(GetCollaborationRequest getCollaborationRequest) throws IOException, BoxException {
        LOGGER.debug("starting getCollaboration()");
        GetCollaborationResponse getCollaborationResponse = BoxResponseFactory.createGetCollaborationResponse();
        String accessToken = getCollaborationRequest.getAccessToken();
        String collaborationId = getCollaborationRequest.getCollaborationId();
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            URLCodec codec = new URLCodec();
            StringBuffer urlBuff = super.getApiUrl("collaborations");
            String collaborationsApiUrl = urlBuff.toString();
            LOGGER.debug("collaborationsApiUrl is " + collaborationsApiUrl);
            urlBuff.append(CommonConstant.SLASH_STRING + collaborationId);
            String collaborationUrl = urlBuff.toString();
            Header header = new BasicHeader("Authorization", "Bearer " + accessToken);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            try {
                String getCollaborationResult = httpManager.doStringGet(collaborationUrl, headers, null);
                LOGGER.debug("getCollaborationResult is " + getCollaborationResult);
                Gson gson = new Gson();
                BoxCollaboration boxCollaboration = gson.fromJson(getCollaborationResult, BoxCollaborationImpl.class);
                LOGGER.debug("created boxCollaboration from getCollaborationResult");
                getCollaborationResponse.setCollaboration(boxCollaboration);
                LOGGER.debug("set collaboration");
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

        return getCollaborationResponse;
    }
}
