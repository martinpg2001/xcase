/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.*;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.objects.BoxUserImpl;
import com.xcase.box.objects.BoxException;
import com.xcase.box.objects.BoxUser;
import com.xcase.box.transputs.GetUserInfoRequest;
import com.xcase.box.transputs.GetUserInfoResponse;
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
public class GetUserInfoMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method gets user info. 'file_id' param should contain valid if of
     * user file.
     *
     * On successful a result, you will receive status 's_get_file_info' and
     * file info in 'info'. If the result wasn't successful, status field can
     * be: e_access_denied.
     *
     * @param getUserInfoRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public GetUserInfoResponse getUserInfo(GetUserInfoRequest getUserInfoRequest) throws IOException, BoxException {
        LOGGER.debug("starting getUserInfo()");
        GetUserInfoResponse getUserInfoResponse = BoxResponseFactory.createGetUserInfoResponse();
        String accessToken = getUserInfoRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String userId = getUserInfoRequest.getUserId();
        LOGGER.debug("userId is " + userId);
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            URLCodec codec = new URLCodec();
            userId = codec.encode(userId, "ISO-8859-1");
            StringBuffer urlBuff = super.getApiUrl("users");
            urlBuff.append("/" + userId);
            String usersApiUrl = urlBuff.toString();
            LOGGER.debug("usersApiUrl is " + usersApiUrl);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header[] headers = {header};
            try {
                String userInfoResult = httpManager.doStringGet(usersApiUrl, headers, null);
                LOGGER.debug("userInfoResult is " + userInfoResult);
                Gson gson = new Gson();
                BoxUser user = gson.fromJson(userInfoResult, BoxUserImpl.class);
                LOGGER.debug("created user from userInfoString");
                getUserInfoResponse.setUser(user);
                LOGGER.debug("set user");
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

        return getUserInfoResponse;
    }
}
