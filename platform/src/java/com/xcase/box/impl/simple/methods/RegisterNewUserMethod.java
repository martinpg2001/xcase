/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.google.gson.JsonObject;
import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.RegisterNewUserRequest;
import com.xcase.box.transputs.RegisterNewUserResponse;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;
import org.dom4j.DocumentException;

/**
 * @author martin
 *
 */
public class RegisterNewUserMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method is used to register a user. Upon a successful registration,
     * the status param will be 'successful_register'. If registration wasn't
     * successful, status field can be: 'e_register', 'email_invalid',
     * 'email_already_registered', 'application_restricted'.
     *
     * @param registerNewUserRequest request
     * @return response
     * @throws IOException io exception
     * @throws BoxException box exception
     */
    public RegisterNewUserResponse registerNewUser(RegisterNewUserRequest registerNewUserRequest) throws IOException, BoxException {
        LOGGER.debug("starting registerNewUser()");
        RegisterNewUserResponse registerNewUserResponse = BoxResponseFactory.createRegisterNewUserResponse();
        String accessToken = registerNewUserRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String loginName = registerNewUserRequest.getLoginName();
        LOGGER.debug("loginName is " + loginName);
        String name = registerNewUserRequest.getName();
        LOGGER.debug("name is " + name);
        try {
            if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
                LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
                StringBuffer urlBuff = super.getApiUrl("users");
                String url = urlBuff.toString();
                LOGGER.debug("url is " + url);
                String bearerString = "Bearer " + accessToken;
                LOGGER.debug("bearerString is " + bearerString);
                Header header = new BasicHeader("Authorization", bearerString);
                LOGGER.debug("created Authorization header");
                Header[] headers = {header};
                List<NameValuePair> data = new ArrayList<NameValuePair>();
                StringBuffer requestBodyStringBuffer = new StringBuffer("{");
                requestBodyStringBuffer.append("\"login\":\"" + loginName + "\"");
                LOGGER.debug("appended login");
                requestBodyStringBuffer.append(",");
                requestBodyStringBuffer.append("\"name\":\"" + name + "\"");
                LOGGER.debug("appended name");
                boolean firstAttribute = false;
                if (registerNewUserRequest.getRole() != null) {
                    String role = registerNewUserRequest.getRole();
                    LOGGER.debug("role is " + role);
                    if (!firstAttribute) {
                        requestBodyStringBuffer.append(",");
                    }

                    requestBodyStringBuffer.append("\"role\":\"" + role + "\"");
                    LOGGER.debug("appended role");
                }

                requestBodyStringBuffer.append("}");
                String requestBody = requestBodyStringBuffer.toString();
                LOGGER.debug("requestBody is " + requestBody);
                JsonObject putJsonObject = (JsonObject) httpManager.doJsonPost(url, headers, data, requestBody);
                LOGGER.debug("done POST");
            } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {

            } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {

            } else {

            }
        } catch (DocumentException de) {
            BoxException be = new BoxException("failed to parse to a document.", de);
            be.setStatus(registerNewUserResponse.getStatus());
            throw be;
        } catch (Exception e) {
            BoxException be = new BoxException("failed to parse to a document.", e);
            be.setStatus(registerNewUserResponse.getStatus());
            throw be;
        }

        return registerNewUserResponse;
    }
}
