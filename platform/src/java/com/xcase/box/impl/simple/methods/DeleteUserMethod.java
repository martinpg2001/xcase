/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.DeleteUserRequest;
import com.xcase.box.transputs.DeleteUserResponse;
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
public class DeleteUserMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method deletes a user.
     *
     * @param deleteUserRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public DeleteUserResponse deleteUser(DeleteUserRequest deleteUserRequest) throws IOException, BoxException {
        LOGGER.debug("starting deleteUser()");
        DeleteUserResponse deleteUserResponse = BoxResponseFactory.createDeleteUserResponse();
        String accessToken = deleteUserRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String id = deleteUserRequest.getId();
        LOGGER.debug("id is " + id);
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            StringBuffer urlBuff = super.getApiUrl("users");
            urlBuff.append(CommonConstant.SLASH_STRING + id);
            String usersApiUrl = urlBuff.toString();
            LOGGER.debug("usersApiUrl is " + usersApiUrl);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header xHTTPMethodHeader = new BasicHeader(CommonConstant.X_HTTP_METHOD_STRING, "DELETE");
            LOGGER.debug("created xHTTPMethodHeader header");
            Header[] headers = {header, xHTTPMethodHeader};
            try {
                String result = httpManager.doStringDelete(usersApiUrl, headers, null);
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

        return deleteUserResponse;
    }
}
