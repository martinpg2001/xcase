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
import com.xcase.box.transputs.UpdateCollaborationRequest;
import com.xcase.box.transputs.UpdateCollaborationResponse;
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
public class UpdateCollaborationMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method updates a collaboration.
     *
     * On a successful result, the status will be 'create_ok'.
     *
     * If the result wasn't successful, status field can be:
     * 'e_no_parent_folder', 'not_logged_in', 'application_r'stricted'.
     *
     * @param updateCollaborationRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public UpdateCollaborationResponse updateCollaboration(UpdateCollaborationRequest updateCollaborationRequest) throws IOException, BoxException {
        LOGGER.debug("starting updateCollaboration()");
        UpdateCollaborationResponse updateCollaborationResponse = BoxResponseFactory.createUpdateCollaborationResponse();
        String accessToken = updateCollaborationRequest.getAccessToken();
        String collaborationId = updateCollaborationRequest.getId();
        String role = updateCollaborationRequest.getRole();
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
            Header xHTTPMethodHeader = new BasicHeader(CommonConstant.X_HTTP_METHOD_STRING, "PUT");
            LOGGER.debug("created xHTTPMethodHeader header");
            Header[] headers = {header, xHTTPMethodHeader};
            String entityString = "{\"role\": \"" + role + "\"}";
            LOGGER.debug("entityString is " + entityString);
            try {
                String updateCollaborationResult = httpManager.doStringPut(collaborationUrl, headers, null, entityString);
                LOGGER.debug("updateCollaborationResult is " + updateCollaborationResult);
                Gson gson = new Gson();
                BoxCollaboration boxCollaboration = gson.fromJson(updateCollaborationResult, BoxCollaborationImpl.class);
                LOGGER.debug("created boxCollaboration from updateCollaborationResult");
                updateCollaborationResponse.setCollaboration(boxCollaboration);
                LOGGER.info("set collaboration in response");
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

        return updateCollaborationResponse;
    }
}
