package com.xcase.intapp.cdscm;

import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.cdscm.CDSCMExternalAPI;
import com.xcase.intapp.cdscm.SimpleCDSCMImpl;
import com.xcase.intapp.cdscm.constant.CDSCMConstant;
import com.xcase.intapp.cdscm.factories.CDSCMRequestFactory;
import com.xcase.intapp.cdscm.impl.simple.core.CDSCMConfigurationManager;
import com.xcase.intapp.cdscm.transputs.*;
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

public class CDSCMApplication {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        CDSCMExternalAPI cdscmExternalAPI = new SimpleCDSCMImpl();
        LOGGER.debug("created CDSCMExternalAPI");
        try {
            generateTokenPair();
            String clientId = "66666";
            LOGGER.debug("clientId is " + clientId);
            String accessToken = CDSCMConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSCMConstant.ACCESS_TOKEN);
            LOGGER.debug("about to create client");
            CreateClientRequest createClientRequest = CDSCMRequestFactory.createCreateClientRequest(accessToken);
            LOGGER.debug("created createClientRequest");
            createClientRequest.setClientId(clientId);
            CreateClientResponse createClientResponse = cdscmExternalAPI.createClient(createClientRequest);
            LOGGER.debug("created client");
            LOGGER.debug("about to put client security");
            PutClientSecurityRequest putClientSecurityRequest = CDSCMRequestFactory.createPutClientSecurityRequest(accessToken);
            LOGGER.debug("created getClientSecurityRequest");
            putClientSecurityRequest.setClientId(clientId);
            PutClientSecurityResponse putClientSecurityResponse = cdscmExternalAPI.putClientSecurity(putClientSecurityRequest);
            LOGGER.debug("got client security");
            LOGGER.debug("about to get client security");
            GetClientSecurityRequest getClientSecurityRequest = CDSCMRequestFactory.createGetClientSecurityRequest(accessToken);
            LOGGER.debug("created getClientSecurityRequest");
            getClientSecurityRequest.setClientId(clientId);
            GetClientSecurityResponse getClientSecurityResponse = cdscmExternalAPI.getClientSecurity(getClientSecurityRequest);
            LOGGER.debug("got client security");
            LOGGER.debug("about to delete client");
            DeleteClientRequest deleteClientRequest = CDSCMRequestFactory.createDeleteClientRequest(accessToken);
            LOGGER.debug("created deleteClientRequest");
            deleteClientRequest.setClientId(clientId);
            DeleteClientResponse deleteClientResponse = cdscmExternalAPI.deleteClient(deleteClientRequest);
            LOGGER.debug("deleted client"); 
        } catch (Exception e) {
            LOGGER.warn("exception executing methods: " + e.getMessage());
        }
    }

    private static void generateTokenPair() throws Exception, IOException {
        String CDSCMClientID = CDSCMConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSCMConstant.CONFIG_CLIENT_ID);
        String CDSCMClientSecret = CDSCMConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSCMConstant.CONFIG_CLIENT_SECRET);
        String CDSCMSwaggerAPIURL = CDSCMConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSCMConstant.CONFIG_SWAGGER_API_URL);
        String CDSCMTokenURL = CDSCMConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSCMConstant.CONFIG_TOKEN_URL);
        CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
        parameters.add(new BasicNameValuePair("client_id", CDSCMClientID));
        parameters.add(new BasicNameValuePair("client_secret", CDSCMClientSecret));
        parameters.add(new BasicNameValuePair("scope", "openid offline_access"));
        CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", CDSCMTokenURL, null, parameters, null, null);
        LOGGER.debug("got response status code " + commonHttpResponse.getResponseCode());
        String responseEntityString = commonHttpResponse.getResponseEntityString();
        LOGGER.debug("responseEntityString is " + responseEntityString);
        JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
        String accessToken = responseEntityJsonObject.get("access_token").getAsString();
        LOGGER.debug("accessToken is " + accessToken);
        CDSCMConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(CDSCMConstant.ACCESS_TOKEN, accessToken);
        CDSCMConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
        LOGGER.debug("stored local config properties");
        Header authorizationHeader = createCDSCMAuthenticationTokenHeader(accessToken);
        LOGGER.debug("created Authorization header");
        Header acceptHeader = createAcceptHeader();
        Header contentTypeHeader = createContentTypeHeader();
        Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
        CommonHttpResponse swaggerHttpResponse = httpManager.doCommonHttpResponseMethod("GET", CDSCMSwaggerAPIURL, null, parameters, null, null);
        LOGGER.debug("got response status code " + swaggerHttpResponse.getResponseCode());
        String swaggerResponseEntityString = swaggerHttpResponse.getResponseEntityString();
        LOGGER.debug("swaggerResponseEntityString is " + swaggerResponseEntityString);
        JsonObject swaggerEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(swaggerResponseEntityString);
        String host = swaggerEntityJsonObject.get("host").getAsString();
        LOGGER.debug("host is " + host);
        String basePath = swaggerEntityJsonObject.get("basePath").getAsString();
        LOGGER.debug("basePath is " + basePath);
        CDSCMConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(CDSCMConstant.API_VERSION_URL, "https://" + host + basePath);
        CDSCMConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
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

    public static Header createCDSCMAuthenticationTokenHeader(String accessToken) {
        return new BasicHeader(CDSCMConfigurationManager.getConfigurationManager().getConfig().getProperty(CDSCMConstant.CONFIG_API_AUTHENTICATION_HEADER), accessToken);
    }
}
