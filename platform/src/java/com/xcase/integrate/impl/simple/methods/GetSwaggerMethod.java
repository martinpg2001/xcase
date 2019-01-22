/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.factories.IntegrateResponseFactory;
import com.xcase.integrate.transputs.GetSwaggerRequest;
import com.xcase.integrate.transputs.GetSwaggerResponse;
import java.lang.invoke.*;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class GetSwaggerMethod extends BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private String endPoint;

    public GetSwaggerResponse getSwagger(GetSwaggerRequest request) {
        LOGGER.debug("starting getSwagger()");
        GetSwaggerResponse response = IntegrateResponseFactory.createGetSwaggerResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = super.apiVersionUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            endPoint = baseVersionUrl + CommonConstant.SLASH_STRING + IntegrateConstant.APPLICATION_SWAGGER;
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            String detail = request.getDetail();
            LOGGER.debug("detail is " + detail);
            if (detail != null) {
                endPoint = endPoint + CommonConstant.QUESTION_MARK_STRING + IntegrateConstant.DETAIL + CommonConstant.EQUALS_SIGN_STRING + detail;
            }

            Header authorizationHeader = createIntegrateAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader("json");
            Header contentTypeHeader = createContentTypeHeader("json");
//            Header xHTTPMethodHeader = new BasicHeader(CommonConstant.X_HTTP_METHOD_STRING, "GET");
//            LOGGER.debug("created xHTTPMethodHeader header");
            Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, null, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
                response.setSwagger(responseEntityString);
                if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_JSON.equals(this.apiRequestFormat)) {

                } else if (IntegrateConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(this.apiRequestFormat)) {

                } else {
                    LOGGER.warn("unexpected API request format: " + this.apiRequestFormat);
                    response.setMessage("Unexpected API request format: " + this.apiRequestFormat);
                    response.setStatus("FAIL");
                }
            } else {
                handleUnexpectedResponseCode(response, commonHttpResponse);
            }
        } catch (Exception e) {
            handleUnexpectedException(response, e);
        }

        return response;
    }
}
