/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.*;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.impl.simple.utils.ConverterUtils;
import com.xcase.box.objects.BoxException;
import com.xcase.box.objects.BoxUser;
import com.xcase.box.transputs.CreateUserRequest;
import com.xcase.box.transputs.CreateUserResponse;
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
public class CreateUserMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method creates a new user.
     *
     * @param createUserRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public CreateUserResponse createUser(CreateUserRequest createUserRequest) throws IOException, BoxException {
        LOGGER.debug("starting createUser()");
        CreateUserResponse createUserResponse = BoxResponseFactory.createCreateUserResponse();
        String accessToken = createUserRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String name = createUserRequest.getName();
        String login = createUserRequest.getLogin();
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            URLCodec codec = new URLCodec();
            name = codec.encode(name, "ISO-8859-1");
            StringBuffer urlBuff = super.getApiUrl("users");
            String usersApiUrl = urlBuff.toString();
            LOGGER.debug("usersApiUrl is " + usersApiUrl);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header header = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header xHTTPMethodHeader = new BasicHeader(CommonConstant.X_HTTP_METHOD_STRING, "POST");
            LOGGER.debug("created xHTTPMethodHeader header");
            Header[] headers = {header, xHTTPMethodHeader};
            String entityString = "{\"login\":\"" + login + "\", \"name\": \"" + name + "\"}";
            LOGGER.debug("entityString is " + entityString);
            try {
                JsonElement jsonElement = httpManager.doJsonPost(usersApiUrl, headers, null, entityString);
                LOGGER.info("done Json post");
                JsonObject jsonObject = (JsonObject) jsonElement;
                JsonElement typeElement = jsonObject.get("type");
                JsonElement idElement = jsonObject.get("id");
                JsonElement nameElement = jsonObject.get("name");
                BoxUser boxUser = ConverterUtils.toBoxUser(jsonObject);
                createUserResponse.setUser(boxUser);
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

        return createUserResponse;
    }
}
