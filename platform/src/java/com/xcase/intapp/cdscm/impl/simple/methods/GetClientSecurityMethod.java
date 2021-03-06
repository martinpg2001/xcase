package com.xcase.intapp.cdscm.impl.simple.methods;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.cdscm.factories.CDSCMResponseFactory;
import com.xcase.intapp.cdscm.transputs.GetClientSecurityRequest;
import com.xcase.intapp.cdscm.transputs.GetClientSecurityResponse;
import java.io.StringReader;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.Unmarshaller;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetClientSecurityMethod extends BaseCDSCMMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetClientSecurityResponse getClientSecurity(GetClientSecurityRequest request) {
        LOGGER.debug("starting getClientSecurity()");
        GetClientSecurityResponse response = CDSCMResponseFactory.createGetClientSecurityResponse();
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
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, parameters, null);
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
