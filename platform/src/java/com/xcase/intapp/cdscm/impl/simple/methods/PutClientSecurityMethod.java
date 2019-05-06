package com.xcase.intapp.cdscm.impl.simple.methods;

import java.lang.invoke.MethodHandles;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.cdscm.factories.CDSCMResponseFactory;
import com.xcase.intapp.cdscm.transputs.GetClientSecurityRequest;
import com.xcase.intapp.cdscm.transputs.GetClientSecurityResponse;
import com.xcase.intapp.cdscm.transputs.PutClientSecurityRequest;
import com.xcase.intapp.cdscm.transputs.PutClientSecurityResponse;

public class PutClientSecurityMethod extends BaseCDSCMMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public PutClientSecurityResponse putClientSecurity(PutClientSecurityRequest request) {
        LOGGER.debug("starting putClientSecurity()");
        PutClientSecurityResponse response = CDSCMResponseFactory.createPutClientSecurityResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = getAPIVersionUrl();
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String clientId = request.getClientId();
            endPoint = baseVersionUrl + request.getOperationPath();
            endPoint = endPoint.replace("{clientId}", clientId);
            LOGGER.debug("endPoint is " + endPoint);
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createCDSCMAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header contentTypeHeader = createContentTypeHeader();
            Header authenticationToken = new BasicHeader("IntegrateAuthenticationToken", accessToken);
            Header[] headers = {acceptHeader, authenticationToken, authorizationHeader, contentTypeHeader};
            String entityString = "{\"defaultAccess\":255,\"users\":[{\"userId\":\"ADMIN\",\"access\":255}]}";
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponsePut(endPoint, headers, null, entityString);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
                if (responseEntityString != null && !responseEntityString.isEmpty()) {
                	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
                	JsonObject jsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
                } else {
                	LOGGER.debug("responseEntityString is null or empty");
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
