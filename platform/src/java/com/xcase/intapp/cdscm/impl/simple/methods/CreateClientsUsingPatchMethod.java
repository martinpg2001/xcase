package com.xcase.intapp.cdscm.impl.simple.methods;

import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.intapp.cdscm.factories.CDSCMResponseFactory;
import com.xcase.intapp.cdscm.transputs.CreateClientsUsingPatchRequest;
import com.xcase.intapp.cdscm.transputs.CreateClientsUsingPatchResponse;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CreateClientsUsingPatchMethod extends BaseCDSCMMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateClientsUsingPatchResponse createClientsUsingPatch(CreateClientsUsingPatchRequest request) {
        LOGGER.debug("starting createMattersUsingPatch()");
        CreateClientsUsingPatchResponse response = CDSCMResponseFactory.createCreateClientsUsingPatchResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = getAPIVersionUrl();
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String[] clientsArray = request.getClients();
            StringBuilder entityStringBuilder = new StringBuilder();
            entityStringBuilder.append("[");
            for (String client : clientsArray) {
                entityStringBuilder.append(client + ",");
            }
            
            entityStringBuilder.append("]");
            String entityString = entityStringBuilder.toString().replace(",]", "]");
            LOGGER.debug("entityString is " + entityString);
            endPoint = baseVersionUrl + request.getOperationPath();
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header authorizationHeader = createCDSCMAuthenticationTokenHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header contentTypeHeader = createContentTypeHeader();
            Header authenticationToken = new BasicHeader("IntegrateAuthenticationToken", accessToken);
            Header[] headers = {acceptHeader, authenticationToken, authorizationHeader, contentTypeHeader};
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponsePatch(endPoint, headers, parameters, entityString, null);
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
