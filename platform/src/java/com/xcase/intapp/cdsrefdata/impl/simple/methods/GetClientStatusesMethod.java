package com.xcase.intapp.cdsrefdata.impl.simple.methods;

import java.lang.invoke.MethodHandles;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.cdsrefdata.factories.CDSRefDataResponseFactory;
import com.xcase.intapp.cdsrefdata.transputs.GetClientStatusesRequest;
import com.xcase.intapp.cdsrefdata.transputs.GetClientStatusesResponse;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetClientStatusesMethod extends BaseCDSRefDataMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetClientStatusesResponse getClientStatuses(GetClientStatusesRequest request) {
        LOGGER.debug("starting getClientStatuses()");
        GetClientStatusesResponse response = CDSRefDataResponseFactory.createGetClientStatusesResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = getAPIVersionUrl();
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            endPoint = baseVersionUrl + request.getOperationPath() + "?";
            int limit = request.getLimit();
            if (limit > 0) {
                endPoint = endPoint + "&limit=" + limit;
            }
            
            int skip = request.getSkip();
            if (skip > 0) {
                endPoint = endPoint + "&skip=" + skip;
            }
            
            LOGGER.debug("endPoint is " + endPoint);
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createCDSRefDataAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, null, null);
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
