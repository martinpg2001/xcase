package com.xcase.intapp.document;

import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.advanced.factories.AdvancedRequestFactory;
import com.xcase.intapp.advanced.transputs.InvokeOperationRequest;
import com.xcase.intapp.advanced.transputs.InvokeOperationResponse;
import com.xcase.intapp.document.DocumentExternalAPI;
import com.xcase.intapp.document.SimpleDocumentImpl;
import com.xcase.intapp.document.constant.DocumentConstant;
import com.xcase.intapp.document.factories.DocumentRequestFactory;
import com.xcase.intapp.document.impl.simple.core.DocumentConfigurationManager;
import com.xcase.intapp.document.transputs.*;
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

public class DocumentApplication {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        String clientID = DocumentConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(DocumentConstant.CONFIG_CLIENT_ID);
        String clientSecret = DocumentConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(DocumentConstant.CONFIG_CLIENT_SECRET);
        String swaggerAPIURL = DocumentConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(DocumentConstant.CONFIG_SWAGGER_API_URL);
        String tokenURL = DocumentConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(DocumentConstant.CONFIG_TOKEN_URL);
        DocumentExternalAPI documentExternalAPI = new SimpleDocumentImpl(clientID, clientSecret, swaggerAPIURL, tokenURL);
        LOGGER.debug("created documentExternalAPI");
        try {
        	documentExternalAPI.generateTokenPair();
            String accessToken = documentExternalAPI.getAccessToken();
            LOGGER.debug("about to get categories");
            GetCategoriesRequest getCategoriesRequest = DocumentRequestFactory.createGetCategoriesRequest(accessToken);
            LOGGER.debug("created getCategoriesRequest");
            GetCategoriesResponse getCategoriesResponse = documentExternalAPI.getCategories(getCategoriesRequest);            
            LOGGER.debug("got categories");
            LOGGER.debug("about to save template");
            SaveTemplateRequest saveTemplateRequest = DocumentRequestFactory.createSaveTemplateRequest(accessToken);
            LOGGER.debug("created saveTemplateRequest");
            saveTemplateRequest.setCategory("MartinCategory");
            saveTemplateRequest.setName("MartinTemplate");
            saveTemplateRequest.setItem1("MartinTemplate");
            saveTemplateRequest.setItem2("MartinTemplate".getBytes());            
            SaveTemplateResponse saveTemplateResponse = documentExternalAPI.saveTemplate(saveTemplateRequest);            
            LOGGER.debug("save template");            
            LOGGER.debug("about to get templates");
            GetTemplatesRequest getTemplatesRequest = DocumentRequestFactory.createGetTemplatesRequest(accessToken);
            LOGGER.debug("created getTemplatesRequest");
            GetTemplatesResponse getTemplatesResponse = documentExternalAPI.getTemplates(getTemplatesRequest);            
            LOGGER.debug("got templates");
            LOGGER.debug("about to head templates");
            HeadTemplatesRequest headTemplatesRequest = DocumentRequestFactory.createHeadTemplatesRequest(accessToken);
            LOGGER.debug("created headTemplatesRequest");
            HeadTemplatesResponse headTemplatesResponse = documentExternalAPI.headTemplates(headTemplatesRequest);            
            LOGGER.debug("got templates");
        } catch (Exception e) {
            LOGGER.warn("exception executing methods: " + e.getMessage());
        }
    }

    private static void generateTokenPair() throws Exception, IOException {
        String clientID = DocumentConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(DocumentConstant.CONFIG_CLIENT_ID);
        String clientSecret = DocumentConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(DocumentConstant.CONFIG_CLIENT_SECRET);
        String swaggerAPIURL = DocumentConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(DocumentConstant.CONFIG_SWAGGER_API_URL);
        String tokenURL = DocumentConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(DocumentConstant.CONFIG_TOKEN_URL);
        CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
        parameters.add(new BasicNameValuePair("client_id", clientID));
        parameters.add(new BasicNameValuePair("client_secret", clientSecret));
        parameters.add(new BasicNameValuePair("scope", "openid offline_access"));
        CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", tokenURL, null, parameters, null, null);
        LOGGER.debug("got response status code " + commonHttpResponse.getResponseCode());
        String responseEntityString = commonHttpResponse.getResponseEntityString();
        LOGGER.debug("responseEntityString is " + responseEntityString);
        JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
        String accessToken = responseEntityJsonObject.get("access_token").getAsString();
        LOGGER.debug("accessToken is " + accessToken);
        DocumentConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(DocumentConstant.ACCESS_TOKEN, accessToken);
        DocumentConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
        Header authorizationHeader = createAdvancedAuthenticationTokenHeader(accessToken);
        LOGGER.debug("created Authorization header");
        Header acceptHeader = createAcceptHeader();
        Header contentTypeHeader = createContentTypeHeader();
        Header[] headers = {acceptHeader, authorizationHeader, contentTypeHeader};
        CommonHttpResponse swaggerHttpResponse = httpManager.doCommonHttpResponseMethod("GET", swaggerAPIURL, null, parameters, null, null);
        LOGGER.debug("got response status code " + swaggerHttpResponse.getResponseCode());
        String swaggerResponseEntityString = swaggerHttpResponse.getResponseEntityString();
        LOGGER.debug("swaggerResponseEntityString is " + swaggerResponseEntityString);
        JsonObject swaggerEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(swaggerResponseEntityString);
        String host = swaggerEntityJsonObject.get("host").getAsString();
        LOGGER.debug("host is " + host);
        String basePath = swaggerEntityJsonObject.get("basePath").getAsString();
        LOGGER.debug("basePath is " + basePath);
        DocumentConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(DocumentConstant.API_VERSION_URL, "https://" + host + basePath);
        DocumentConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
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

    public static Header createAdvancedAuthenticationTokenHeader(String accessToken) {
        return new BasicHeader(DocumentConfigurationManager.getConfigurationManager().getConfig().getProperty(DocumentConstant.CONFIG_API_AUTHENTICATION_HEADER), accessToken);
    }
}
