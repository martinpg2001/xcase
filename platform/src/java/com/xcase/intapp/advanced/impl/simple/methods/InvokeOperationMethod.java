package com.xcase.intapp.advanced.impl.simple.methods;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.advanced.factories.AdvancedResponseFactory;
import com.xcase.intapp.advanced.transputs.InvokeOperationRequest;
import com.xcase.intapp.advanced.transputs.InvokeOperationResponse;
import java.lang.invoke.MethodHandles;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class InvokeOperationMethod extends BaseAdvancedMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public InvokeOperationResponse invokeOperation(InvokeOperationRequest request) {
        LOGGER.debug("starting invokeOperation()");
        InvokeOperationResponse response = AdvancedResponseFactory.createInvokeOperationResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = request.getAPIURL();
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String method = request.getMethod();
            LOGGER.debug("method is " + method);
            endPoint = baseVersionUrl + request.getOperationPath();
            LOGGER.debug("endPoint is " + endPoint);
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createAdvancedAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            if (request.getAccept() != null && !request.getAccept().isEmpty()) {
                acceptHeader = createAcceptHeader(request.getAccept());
            }
            
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
            List<NameValuePair> parameters = request.getParameters();
            LOGGER.debug("got parameters");
            String entityString = request.getEntityString();
            LOGGER.debug("entityString is " + entityString);            
            boolean redirect = false;
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod(method, endPoint, headers, parameters, entityString, null, redirect);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            if (responseCode == request.getSuccessResponseCode()) {
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
