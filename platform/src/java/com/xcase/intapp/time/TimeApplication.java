package com.xcase.intapp.time;

import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.time.TimeExternalAPI;
import com.xcase.intapp.time.SimpleTimeImpl;
import com.xcase.intapp.time.constant.TimeConstant;
import com.xcase.intapp.time.factories.TimeRequestFactory;
import com.xcase.intapp.time.impl.simple.core.TimeConfigurationManager;
import com.xcase.intapp.time.transputs.*;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TimeApplication {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        TimeExternalAPI timeExternalAPI = new SimpleTimeImpl();
        LOGGER.debug("created timeExternalAPI");
        try {
            generateTokenPair();
            String accessToken = TimeConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(TimeConstant.ACCESS_TOKEN);
            LOGGER.debug("accessToken is " + accessToken);
            String refreshToken = TimeConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(TimeConstant.REFRESH_TOKEN);            
            LOGGER.debug("refreshToken is " + refreshToken);
            LOGGER.debug("about to get clients");
            GetClientsRequest getClientsRequest = TimeRequestFactory.createGetClientsRequest(accessToken, refreshToken);
            LOGGER.debug("created getGetClientsRequest");
            GetClientsResponse getClientsResponse = timeExternalAPI.getClients(getClientsRequest);
            LOGGER.debug("got clients");
            LOGGER.debug("about to get restricted texts");
            GetRestrictedTextsRequest getRestrictedTextsRequest = TimeRequestFactory.createGetRestrictedTextsRequest(accessToken, refreshToken);
            LOGGER.debug("created getRestrictedTextsRequest");
            GetRestrictedTextsResponse getRestrictedTextsResponse = timeExternalAPI.getRestrictedTexts(getRestrictedTextsRequest);
            LOGGER.debug("got restricted texts");
        } catch (Exception e) {
            LOGGER.warn("exception executing methods: " + e.getMessage());
        }
    }

	private static void generateTokenPair() throws Exception, IOException {
        String timeClientID = TimeConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(TimeConstant.CONFIG_CLIENT_ID);
        String timeClientSecret = TimeConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(TimeConstant.CONFIG_CLIENT_SECRET);
        String timeSwaggerAPIURL = TimeConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(TimeConstant.CONFIG_SWAGGER_API_URL);
        String timeTokenURL = TimeConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(TimeConstant.CONFIG_TOKEN_URL);
        CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
        parameters.add(new BasicNameValuePair("client_id", timeClientID));
        parameters.add(new BasicNameValuePair("client_secret", timeClientSecret));
        parameters.add(new BasicNameValuePair("scope", "openid offline_access"));
        CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", timeTokenURL, null, parameters, null, null);
        LOGGER.debug("got response status code " + commonHttpResponse.getResponseCode());
        String responseEntityString = commonHttpResponse.getResponseEntityString();
        LOGGER.debug("responseEntityString is " + responseEntityString);
        JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
        String accessToken = responseEntityJsonObject.get("access_token").getAsString();
        LOGGER.debug("accessToken is " + accessToken);
        TimeConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(TimeConstant.ACCESS_TOKEN, accessToken);
        String refreshToken = responseEntityJsonObject.get("refresh_token").getAsString();
        LOGGER.debug("refreshToken is " + refreshToken);
        TimeConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(TimeConstant.REFRESH_TOKEN, refreshToken);
        TimeConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
        Header authorizationHeader = createTimeAuthenticationTokenHeader(accessToken);
        LOGGER.debug("created authorizationHeader header");
        Header acceptHeader = createAcceptHeader();
        Header contentTypeHeader = createContentTypeHeader();
        Header cookieHeader = createCookieHeader(accessToken);
        LOGGER.debug("created cookieHeader header");
        Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader, cookieHeader};
        CommonHttpResponse swaggerHttpResponse = httpManager.doCommonHttpResponseMethod("GET", timeSwaggerAPIURL, headers, parameters, null, null);
        LOGGER.debug("got response status code " + swaggerHttpResponse.getResponseCode());
        String swaggerResponseEntityString = swaggerHttpResponse.getResponseEntityString();
        LOGGER.debug("swaggerResponseEntityString is " + swaggerResponseEntityString);
        JsonObject swaggerEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(swaggerResponseEntityString);
        String host = swaggerEntityJsonObject.get("host").getAsString();
        LOGGER.debug("host is " + host);
        String basePath = swaggerEntityJsonObject.get("basePath").getAsString();
        LOGGER.debug("basePath is " + basePath);
        TimeConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(TimeConstant.API_VERSION_URL, "https://" + host + basePath);
        TimeConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
	}

    public static Header createAcceptHeader() {
        String acceptHeader = "application/json";
        LOGGER.debug("acceptHeader is " + acceptHeader);
        return new BasicHeader("Accept", acceptHeader);
    }

    public static Header createContentTypeHeader() {
        String contentTypeHeader = "application/json";
        LOGGER.debug("contentTypeHeader is " + contentTypeHeader);
        return new BasicHeader("Content-Type", contentTypeHeader);
    }
    
    public static Header createCookieHeader(String accessToken) {
        String cookieHeader = "Authorization=" + accessToken + "; Refresh-Token=";
        LOGGER.debug("cookieHeader is " + cookieHeader);
        return new BasicHeader("Cookie", cookieHeader);
    }

    public static Header createTimeAuthenticationTokenHeader(String accessToken) {
        return new BasicHeader(TimeConfigurationManager.getConfigurationManager().getConfig().getProperty(TimeConstant.CONFIG_API_AUTHENTICATION_HEADER), accessToken);
    }
}
