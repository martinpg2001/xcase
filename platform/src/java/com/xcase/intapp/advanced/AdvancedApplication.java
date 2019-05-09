package com.xcase.intapp.advanced;

import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.advanced.AdvancedExternalAPI;
import com.xcase.intapp.advanced.SimpleAdvancedImpl;
import com.xcase.intapp.advanced.constant.AdvancedConstant;
import com.xcase.intapp.advanced.factories.AdvancedRequestFactory;
import com.xcase.intapp.advanced.impl.simple.core.AdvancedConfigurationManager;
import com.xcase.intapp.advanced.transputs.*;
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

public class AdvancedApplication {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        LOGGER.debug("starting main()");
        String clientID = AdvancedConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AdvancedConstant.CONFIG_CLIENT_ID);
        String clientSecret = AdvancedConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AdvancedConstant.CONFIG_CLIENT_SECRET);
        String swaggerAPIURL = AdvancedConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AdvancedConstant.CONFIG_SWAGGER_API_URL);
        String tokenURL = AdvancedConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AdvancedConstant.CONFIG_TOKEN_URL);
        AdvancedExternalAPI advancedExternalAPI = new SimpleAdvancedImpl(clientID, clientSecret, swaggerAPIURL, tokenURL);
        LOGGER.debug("created advancedExternalAPI");
        try {
        	advancedExternalAPI.generateTokenPair();
            String accessToken = advancedExternalAPI.getAccessToken();
            LOGGER.debug("about to invoke operation");
            InvokeOperationRequest invokeOperationRequest = AdvancedRequestFactory.createInvokeOperationRequest();
            LOGGER.debug("created invokeOperationRequest");
            invokeOperationRequest.setAccessToken(accessToken);
            invokeOperationRequest.setAPIURL(advancedExternalAPI.getApiURL());
            invokeOperationRequest.setMethod("POST");
            invokeOperationRequest.setOperationPath("api/v1/clients/");
            String clientId = "55555";
            invokeOperationRequest.setEntityString("{\"clientId\":\"{clientId}\",\"name\":\"Underture Science\",\"status\":\"ACT\",\"description\":\"\",\"closedOn\":\"\",\"dunsNumber\":\"\",\"rounding\":null,\"timeNote\":\"\",\"billableStatus\":\"\",\"industry\":\"\",\"clientPersons\":[],\"externalIdentifiers\":[],\"lcidDictionary\":\"\",\"ebillinghubValidation\":\"\",\"timelinks\":{},\"openedOn\":\"\",\"_pricingAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"_experienceAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"_timeAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"security\":{\"defaultAccess\":255,\"users\":[]}}".replace("{clientId}", clientId));
            InvokeOperationResponse invokeOperationResponse = advancedExternalAPI.invokeOperation(invokeOperationRequest);            
            LOGGER.debug("invoked operation");
            invokeOperationRequest.setMethod("GET");
            invokeOperationRequest.setOperationPath("api/v1/clients/_count");
            invokeOperationRequest.setEntityString(null);
            invokeOperationResponse = advancedExternalAPI.invokeOperation(invokeOperationRequest);            
            LOGGER.debug("invoked operation");
            invokeOperationRequest.setMethod("PUT");
            invokeOperationRequest.setOperationPath("api/v1/clients/55555/security");
            invokeOperationRequest.setEntityString("{\"defaultAccess\":255,\"users\":[{\"userId\":\"ADMIN\",\"access\":255}]}");
            invokeOperationResponse = advancedExternalAPI.invokeOperation(invokeOperationRequest);            
            LOGGER.debug("invoked operation");
            invokeOperationRequest.setMethod("GET");
            invokeOperationRequest.setOperationPath("api/v1/clients/55555/security");
            invokeOperationRequest.setEntityString(null);
            invokeOperationResponse = advancedExternalAPI.invokeOperation(invokeOperationRequest);
            LOGGER.debug("invoked operation");
            invokeOperationRequest.setMethod("POST");
            invokeOperationRequest.setOperationPath("api/v1/clients/55555/matters");
            String matterId = "0001";
            invokeOperationRequest.setEntityString("{\"matterId\":\"{matterId}\",\"name\":\"The soup is a lie\",\"status\":\"ACT\",\"description\":\"\",\"closedOn\":\"\",\"dunsNumber\":\"\",\"rounding\":null,\"timeNote\":\"\",\"billableStatus\":\"\",\"industry\":\"\",\"clientPersons\":[],\"externalIdentifiers\":[],\"lcidDictionary\":\"\",\"ebillinghubValidation\":\"\",\"timelinks\":{},\"openedOn\":\"\",\"_pricingAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"_experienceAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"_timeAppData\":{\"isBillableExampleField\":null,\"shortDescriptionExampleField\":\"\"},\"security\":{\"defaultAccess\":255,\"users\":[]}}".replace("{matterId}", matterId));
            invokeOperationResponse = advancedExternalAPI.invokeOperation(invokeOperationRequest);            
            LOGGER.debug("invoked operation");
            invokeOperationRequest.setMethod("GET");
            invokeOperationRequest.setOperationPath("api/v1/clients/55555/matters");
            invokeOperationRequest.setEntityString(null);
            invokeOperationResponse = advancedExternalAPI.invokeOperation(invokeOperationRequest);
            LOGGER.debug("invoked operation");
            invokeOperationRequest.setMethod("GET");
            invokeOperationRequest.setOperationPath("api/v1/clients/55555/matters/_count");
            invokeOperationRequest.setEntityString(null);
            invokeOperationResponse = advancedExternalAPI.invokeOperation(invokeOperationRequest);
            LOGGER.debug("invoked operation");
            invokeOperationRequest.setMethod("DELETE");
            invokeOperationRequest.setOperationPath("api/v1/clients/55555/matters/0001");
            invokeOperationRequest.setEntityString(null);
            invokeOperationResponse = advancedExternalAPI.invokeOperation(invokeOperationRequest);            
            LOGGER.debug("invoked operation");
            invokeOperationRequest.setMethod("DELETE");
            invokeOperationRequest.setOperationPath("api/v1/clients/55555");
            invokeOperationRequest.setEntityString(null);
            invokeOperationResponse = advancedExternalAPI.invokeOperation(invokeOperationRequest);            
            LOGGER.debug("invoked operation");
            /* Use parameters to get tokens and document through methods */
            LOGGER.debug("about to generate tokens");
            GenerateTokensRequest generateTokensRequest = AdvancedRequestFactory.createGenerateTokensRequest();
            LOGGER.debug("created generateTokensRequest");
            generateTokensRequest.setClientId(clientID);
            generateTokensRequest.setClientSecret(clientSecret);
            generateTokensRequest.setTokenURL(tokenURL);
            GenerateTokensResponse generateTokensResponse = advancedExternalAPI.generateTokens(generateTokensRequest);
            LOGGER.debug("generated tokens");
            LOGGER.debug("about to get Swagger document");
            GetSwaggerDocumentRequest getSwaggerDocumentRequest = AdvancedRequestFactory.createGetSwaggerDocumentRequest();
            LOGGER.debug("created getSwaggerDocument");
            getSwaggerDocumentRequest.setAccessToken(generateTokensResponse.getAccessToken());
            getSwaggerDocumentRequest.setSwaggerAPIURL(swaggerAPIURL);
            GetSwaggerDocumentResponse getSwaggerDocumentResponse = advancedExternalAPI.getSwaggerDocument(getSwaggerDocumentRequest);
            LOGGER.debug("got Swagger document");
            if (getSwaggerDocumentResponse.getSwaggerDocument() != null) {
            	JsonObject swaggerEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(getSwaggerDocumentResponse.getSwaggerDocument());
                String host = swaggerEntityJsonObject.get("host").getAsString();
                LOGGER.debug("host is " + host);
                String basePath = swaggerEntityJsonObject.get("basePath").getAsString();
                LOGGER.debug("basePath is " + basePath);
                String apiURL = "https://" + host + basePath;
                LOGGER.debug("apiURL is " + apiURL);
            }
        } catch (Exception e) {
            LOGGER.warn("exception executing methods: " + e.getMessage());
        }
    }

    private static void generateTokenPair() throws Exception, IOException {
        String clientID = AdvancedConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AdvancedConstant.CONFIG_CLIENT_ID);
        String clientSecret = AdvancedConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AdvancedConstant.CONFIG_CLIENT_SECRET);
        String swaggerAPIURL = AdvancedConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AdvancedConstant.CONFIG_SWAGGER_API_URL);
        String tokenURL = AdvancedConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AdvancedConstant.CONFIG_TOKEN_URL);
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
        AdvancedConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(AdvancedConstant.ACCESS_TOKEN, accessToken);
        AdvancedConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
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
        AdvancedConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(AdvancedConstant.API_VERSION_URL, "https://" + host + basePath);
        AdvancedConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
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
        return new BasicHeader(AdvancedConfigurationManager.getConfigurationManager().getConfig().getProperty(AdvancedConstant.CONFIG_API_AUTHENTICATION_HEADER), accessToken);
    }
}
