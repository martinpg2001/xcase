package com.xcase.intapp.cdsusers.impl.simple.methods;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.intapp.cdsusers.factories.CDSUsersResponseFactory;
import com.xcase.intapp.cdsusers.transputs.PartiallyUpdateUserRequest;
import com.xcase.intapp.cdsusers.transputs.PartiallyUpdateUserResponse;

public class PartiallyUpdateUserMethod extends BaseCDSUsersMethod{
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public PartiallyUpdateUserResponse partiallyUpdateUser(PartiallyUpdateUserRequest request) {
        LOGGER.debug("starting partiallyUpdateUser()");
        PartiallyUpdateUserResponse response = CDSUsersResponseFactory.createPartiallyUpdateUserResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = getAPIVersionUrl();
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            endPoint = baseVersionUrl + request.getOperationPath();
            String key = request.getKey();
            endPoint = endPoint.replace("{key}", key);
            LOGGER.debug("endPoint is " + endPoint);
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createCDSUsersAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header acceptLanguageHeader = new BasicHeader("Accept-Language", "en-US");
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {acceptHeader, acceptLanguageHeader, authorizationHeader, contentTypeHeader};
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            //parameters.add(new BasicNameValuePair("Authorization", "Bearer " + accessToken));
            String userString = request.getUserString();
            LOGGER.debug("userString is " + userString);
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponsePatch(endPoint, headers, parameters, userString, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                handleExpectedResponseCode(response, commonHttpResponse);
            } else {
                handleUnexpectedResponseCode(response, commonHttpResponse);
            }
        } catch (Exception e) {
            handleUnexpectedException(response, e);
        }

        return response;
    }

}
