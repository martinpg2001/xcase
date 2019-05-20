package com.xcase.intapp.cdsrefdata.impl.simple.methods;

import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.intapp.cdsrefdata.factories.CDSRefDataResponseFactory;
import com.xcase.intapp.cdsrefdata.transputs.FindTypesRequest;
import com.xcase.intapp.cdsrefdata.transputs.FindTypesResponse;
import com.xcase.intapp.cdsrefdata.transputs.GetTypeByKeyRequest;
import com.xcase.intapp.cdsrefdata.transputs.GetTypeByKeyResponse;
import java.lang.invoke.MethodHandles;
import java.util.HashMap;
import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetTypeByKeyMethod extends BaseCDSRefDataMethod {
	private HashMap<String, String> typeHashMap = new HashMap<String, String>();
	
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
	public GetTypeByKeyResponse getTypeByKey(GetTypeByKeyRequest request) {
        LOGGER.debug("starting getTypeByKey()");
        GetTypeByKeyResponse response = CDSRefDataResponseFactory.createGetTypeByKeyResponse();
        LOGGER.debug("created response");
        try {
        	typeHashMap.put("BillableStatus", "billablestatuses");
        	typeHashMap.put("ClientExternalId", "clientexternalidtypes");
        	typeHashMap.put("ClientPerson", "clientpersontypes");
        	typeHashMap.put("ClientStatus", "clientstatuses");
        	typeHashMap.put("CostPool", "costpools");
        	typeHashMap.put("Department", "departments");
        	typeHashMap.put("EBillingHubValidation", "ebillinghubvalidationtypes");
        	typeHashMap.put("MatterExternalId", "matterexternalidtypes");
        	typeHashMap.put("MatterPerson", "matterpersontypes");
        	typeHashMap.put("Office", "offices");
        	typeHashMap.put("PersonExternalId", "personexternalidtypes");
        	typeHashMap.put("Practice", "practices");
        	typeHashMap.put("Rounding", "roundingtypes");
        	typeHashMap.put("Title", "titles");
            String baseVersionUrl = getAPIVersionUrl();
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String operationPath = request.getOperationPath();
            LOGGER.debug("operationPath is " + operationPath);
            endPoint = baseVersionUrl + operationPath;
            String type = request.getType();
            endPoint = endPoint + typeHashMap.get(type);
            LOGGER.debug("endPoint is " + endPoint);
            String key = request.getKey();
            endPoint = endPoint + "?key=" + key;
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
