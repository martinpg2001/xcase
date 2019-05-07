package com.xcase.intapp.cdsusers;

import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.cdsusers.CDSUsersExternalAPI;
import com.xcase.intapp.cdsusers.SimpleCDSUsersImpl;
import com.xcase.intapp.cdsusers.constant.CDSUsersConstant;
import com.xcase.intapp.cdsusers.factories.CDSUsersRequestFactory;
import com.xcase.intapp.cdsusers.impl.simple.core.CDSUsersConfigurationManager;
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

public class CDSUsersApplication {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        CDSUsersExternalAPI cdsUsersExternalAPI = new SimpleCDSUsersImpl();
        LOGGER.debug("created cdsUsersExternalAPI");
        try {
            generateTokenPair();
        } catch (Exception e) {
            LOGGER.warn("exception executing methods: " + e.getMessage());
        }
    }

    private static void generateTokenPair() throws Exception, IOException {
        String cdsUsersClientID = CDSUsersConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSUsersConstant.CONFIG_CLIENT_ID);
        String cdsUsersClientSecret = CDSUsersConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSUsersConstant.CONFIG_CLIENT_SECRET);
        String cdsUsersSwaggerAPIURL = CDSUsersConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSUsersConstant.CONFIG_SWAGGER_API_URL);
        String cdsUsersTokenURL = CDSUsersConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSUsersConstant.CONFIG_TOKEN_URL);
        CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
        parameters.add(new BasicNameValuePair("client_id", cdsUsersClientID));
        parameters.add(new BasicNameValuePair("client_secret", cdsUsersClientSecret));
        parameters.add(new BasicNameValuePair("scope", "openid offline_access"));
        CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", cdsUsersTokenURL, null, parameters, null, null);
        LOGGER.debug("got response status code " + commonHttpResponse.getResponseCode());
        String responseEntityString = commonHttpResponse.getResponseEntityString();
        LOGGER.debug("responseEntityString is " + responseEntityString);
        JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
        String accessToken = responseEntityJsonObject.get("access_token").getAsString();
        LOGGER.debug("accessToken is " + accessToken);
        CDSUsersConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(CDSUsersConstant.ACCESS_TOKEN, accessToken);
        CDSUsersConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
        Header authorizationHeader = createCDSUsersAuthenticationTokenHeader(accessToken);
        LOGGER.debug("created Authorization header");
        Header acceptHeader = createAcceptHeader();
        Header contentTypeHeader = createContentTypeHeader();
        Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
        CommonHttpResponse swaggerHttpResponse = httpManager.doCommonHttpResponseMethod("GET", cdsUsersSwaggerAPIURL, null, parameters, null, null);
        LOGGER.debug("got response status code " + swaggerHttpResponse.getResponseCode());
        String swaggerResponseEntityString = swaggerHttpResponse.getResponseEntityString();
        LOGGER.debug("swaggerResponseEntityString is " + swaggerResponseEntityString);
        JsonObject swaggerEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(swaggerResponseEntityString);
        String host = swaggerEntityJsonObject.get("host").getAsString();
        LOGGER.debug("host is " + host);
        String basePath = swaggerEntityJsonObject.get("basePath").getAsString();
        LOGGER.debug("basePath is " + basePath);
        CDSUsersConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(CDSUsersConstant.API_VERSION_URL, "https://" + host + basePath);
        CDSUsersConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
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

    public static Header createCDSUsersAuthenticationTokenHeader(String accessToken) {
        return new BasicHeader(CDSUsersConfigurationManager.getConfigurationManager().getConfig().getProperty(CDSUsersConstant.CONFIG_API_AUTHENTICATION_HEADER), accessToken);
    }
}
