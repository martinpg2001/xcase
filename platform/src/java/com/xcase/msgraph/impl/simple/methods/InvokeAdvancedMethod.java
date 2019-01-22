/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.msgraph.impl.simple.methods;

import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.msgraph.constant.MSGraphConstant;
import com.xcase.msgraph.factories.MSGraphResponseFactory;
import com.xcase.msgraph.objects.MSGraphGroup;
import com.xcase.msgraph.transputs.InvokeAdvancedRequest;
import com.xcase.msgraph.transputs.InvokeAdvancedResponse;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class InvokeAdvancedMethod extends BaseMSGraphMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public InvokeAdvancedMethod() {
        super();
//        LOGGER.debug("finishing InvokeAdvancedMethod()");
    }

    public InvokeAdvancedResponse invokeAdvanced(InvokeAdvancedRequest invokeAdvancedRequest) {
        LOGGER.debug("starting invokeAdvanced()");
        try {
            InvokeAdvancedResponse response = MSGraphResponseFactory.createInvokeAdvancedResponse();
            String baseVersionUrl = super.apiUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String method = invokeAdvancedRequest.getMethod();
            LOGGER.debug("method is " + method);
            String advancedUrl = invokeAdvancedRequest.getAdvancedUrl();
            LOGGER.debug("advancedUrl is " + advancedUrl);
            String endPoint = baseVersionUrl + advancedUrl;
            LOGGER.debug("endPoint is " + endPoint);
            List<NameValuePair> parameters = invokeAdvancedRequest.getParameters();
            String memberBody = invokeAdvancedRequest.getMemberBody();
            LOGGER.debug("memberBody is " + memberBody);            
            String accessToken = invokeAdvancedRequest.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createMSGraphAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod(method, endPoint, headers, parameters, memberBody, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode < 400) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
                response.setResponseEntityString(responseEntityString);
            }

            return response;
        } catch (Exception e) {
            LOGGER.warn("exception invoking method: " + e.getMessage());
        }

        return null;
    }
}
