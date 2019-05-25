package com.xcase.intapp.cdsusers.impl.simple.methods;

import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.intapp.cdsusers.factories.CDSUsersResponseFactory;
import com.xcase.intapp.cdsusers.transputs.UploadEntitiesRequest;
import com.xcase.intapp.cdsusers.transputs.UploadEntitiesResponse;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class UploadEntitiesMethod extends BaseCDSUsersMethod {
	private HashMap<String, String> entityHashMap = new HashMap<String, String>();
	
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public UploadEntitiesResponse uploadEntities(UploadEntitiesRequest request) {
        LOGGER.debug("starting uploadEntities()");
        UploadEntitiesResponse response = CDSUsersResponseFactory.createUploadEntitiesResponse();
        LOGGER.debug("created response");
        try {
        	int expectedSuccessCode = 200;
        	entityHashMap.put("User", "users");
        	entityHashMap.put("Role", "roles");
        	entityHashMap.put("Person", "persons");
            String baseVersionUrl = getAPIVersionUrl();
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            endPoint = baseVersionUrl + request.getOperationPath();
            String entity = request.getEntity();
            endPoint = endPoint.replace("{entity}",  entityHashMap.get(entity));
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
            String entityString = request.getEntityString();
            LOGGER.debug("entityString is " + entityString);
            CommonHttpResponse commonHttpResponse = null;
            if ("Person".equals(entity)) {
                commonHttpResponse = httpManager.doCommonHttpResponsePatch(endPoint, headers, parameters, entityString, null);
            } else if ("Role".equals(entity)) {
            	endPoint = endPoint + "/bulk";
            	commonHttpResponse = httpManager.doCommonHttpResponsePost(endPoint, headers, parameters, entityString, null);
            } else if ("User".equals(entity)) {
            	commonHttpResponse = httpManager.doCommonHttpResponsePatch(endPoint, headers, parameters, entityString, null);
            } else {
            	throw new Exception("Unrecognized entity " + entity);
            }
            
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == expectedSuccessCode) {
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
