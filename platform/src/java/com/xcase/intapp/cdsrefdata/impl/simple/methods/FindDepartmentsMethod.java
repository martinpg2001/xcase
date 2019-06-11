package com.xcase.intapp.cdsrefdata.impl.simple.methods;

import java.lang.invoke.MethodHandles;

import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.intapp.cdsrefdata.factories.CDSRefDataResponseFactory;
import com.xcase.intapp.cdsrefdata.transputs.FindDepartmentsRequest;
import com.xcase.intapp.cdsrefdata.transputs.FindDepartmentsResponse;

public class FindDepartmentsMethod extends BaseCDSRefDataMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public FindDepartmentsResponse findDepartments(FindDepartmentsRequest request) {
        LOGGER.debug("starting findDepartments()");
        FindDepartmentsResponse response = CDSRefDataResponseFactory.createFindDepartmentsResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = getAPIVersionUrl();
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String operationPath = request.getOperationPath();
            LOGGER.debug("operationPath is " + operationPath);
            endPoint = baseVersionUrl + operationPath + "?";
            String key = request.getKey();
            if (key != null && !key.isEmpty()) {
            	endPoint = endPoint + "key=" + key;
            }

            String name = request.getName();
            if (name != null && !name.isEmpty()) {
            	endPoint = endPoint + "&name=" + name;
            }

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
