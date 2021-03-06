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
import com.xcase.intapp.cdsusers.transputs.FindCapabilitiesRequest;
import com.xcase.intapp.cdsusers.transputs.FindCapabilitiesResponse;

public class FindCapabilitiesMethod extends BaseCDSUsersMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public FindCapabilitiesResponse findCapabilities(FindCapabilitiesRequest request) {
        LOGGER.debug("starting findRoles()");
        FindCapabilitiesResponse response = CDSUsersResponseFactory.createFindCapabilitiesResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = getAPIVersionUrl();
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            endPoint = baseVersionUrl + request.getOperationPath() + "?";
            LOGGER.debug("endPoint is " + endPoint);
            int limit = request.getLimit();
            if (limit > 0) {
                endPoint = endPoint + "&limit=" + limit;
            }
            
            int skip = request.getSkip();
            if (skip > 0) {
                endPoint = endPoint + "&skip=" + skip;
            }
            
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            String role = request.getRole();
            LOGGER.debug("role is " + role);
            Header authorizationHeader = createCDSUsersAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header acceptLanguageHeader = new BasicHeader("Accept-Language", "en-US");
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {acceptHeader, acceptLanguageHeader, authorizationHeader, contentTypeHeader};
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            //parameters.add(new BasicNameValuePair("Authorization", "Bearer " + accessToken));
            if (role != null && !role.isEmpty()) {
                parameters.add(new BasicNameValuePair("role", role));
            }
            
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, parameters, null);
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
