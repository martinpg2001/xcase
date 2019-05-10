package com.xcase.intapp.cdscm.impl.simple.methods;

import java.lang.invoke.MethodHandles;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.cdscm.factories.CDSCMResponseFactory;
import com.xcase.intapp.cdscm.transputs.GetMatterResponse;
import com.xcase.intapp.cdscm.transputs.GetMatterRequest;
import com.xcase.intapp.cdscm.transputs.GetMatterResponse;

public class GetMatterMethod extends BaseCDSCMMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
	public GetMatterResponse getMatter(GetMatterRequest request) {
        LOGGER.debug("starting getMatter()");
        GetMatterResponse response = CDSCMResponseFactory.createGetMatterResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = getAPIVersionUrl();
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String clientId = request.getClientId();
            LOGGER.debug("clientId is " + clientId);
            String matterId = request.getMatterId();
            LOGGER.debug("matterId is " + matterId);
            String matterKey = request.getMatterKey();
            LOGGER.debug("matterKey is " + matterKey);
            if (matterKey == null || matterKey.isEmpty()) {
                endPoint = baseVersionUrl + request.getOperationPath();
                endPoint = endPoint.replace("{clientId}", clientId);
                endPoint = endPoint.replace("{matterId}", matterId);
            } else {
                endPoint = baseVersionUrl + "api/v1/matters/{key}".replace("{key}", matterKey);
            }
            
            LOGGER.debug("endPoint is " + endPoint);
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createCDSCMAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header contentTypeHeader = createContentTypeHeader();
            Header authenticationToken = new BasicHeader("IntegrateAuthenticationToken", accessToken);
            Header[] headers = {acceptHeader, authenticationToken, authorizationHeader, contentTypeHeader};
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, null, null);
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
