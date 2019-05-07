package com.xcase.intapp.time.impl.simple.methods;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.time.factories.TimeResponseFactory;
import com.xcase.intapp.time.transputs.GetClientsRequest;
import com.xcase.intapp.time.transputs.GetClientsResponse;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;


public class GetClientsMethod extends BaseTimeMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

	public GetClientsResponse getClients(GetClientsRequest request) {
        LOGGER.debug("starting getClients()");
        GetClientsResponse response = TimeResponseFactory.createGetClientsResponse();
        LOGGER.debug("created response");
        try {
            String baseVersionUrl = getAPIVersionUrl();
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            endPoint = baseVersionUrl + request.getOperationPath();
            LOGGER.debug("endPoint is " + endPoint);
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            String refreshToken = request.getRefreshToken();
            LOGGER.debug("refreshToken is " + refreshToken);
            Header authorizationHeader = createTimeAuthorizationHeader(accessToken);
            LOGGER.debug("created Authorization header");
            Header acceptHeader = createAcceptHeader();
            Header acceptLanguageHeader = new BasicHeader("Accept-Language", "en-US");
            Header contentTypeHeader = createContentTypeHeader();
            Header cookieHeader = createCookieHeader(accessToken, refreshToken);
            LOGGER.debug("created cookieHeader header");
            Header[] headers = {acceptHeader, acceptLanguageHeader, authorizationHeader, cookieHeader};
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("Authorization", "Bearer " + accessToken));
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseGet(endPoint, headers, null, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
                if (responseEntityString != null && !responseEntityString.isEmpty()) {
                    Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
                    JsonArray jsonArray = (JsonArray) ConverterUtils.parseStringToJson(responseEntityString);
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
