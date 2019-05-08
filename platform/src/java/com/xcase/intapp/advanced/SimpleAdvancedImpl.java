package com.xcase.intapp.advanced;

import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.advanced.constant.AdvancedConstant;
import com.xcase.intapp.advanced.impl.simple.core.AdvancedConfigurationManager;
import com.xcase.intapp.advanced.impl.simple.methods.*;
import com.xcase.intapp.advanced.transputs.*;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleAdvancedImpl implements AdvancedExternalAPI {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
	private String accessToken;
	
	private String apiURL;
	
	private String clientId;
	
	private String clientSecret;
	
	private String refreshToken;

	private String swaggerAPIURL;

	private String tokenURL;
	
	public SimpleAdvancedImpl(String clientId, String clientSecret, String swaggerAPIURL, String tokenURL) {
		setClientId(clientId);
		setClientSecret(clientSecret);
		setSwaggerAPIURL(swaggerAPIURL);
		setTokenURL(tokenURL);
	}
	
	public void generateTokenPair() throws Exception {
        CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
        List<NameValuePair> parameters = new ArrayList<NameValuePair>();
        parameters.add(new BasicNameValuePair("grant_type", "client_credentials"));
        parameters.add(new BasicNameValuePair("client_id", getClientId()));
        parameters.add(new BasicNameValuePair("client_secret", getClientSecret()));
        parameters.add(new BasicNameValuePair("scope", "openid offline_access"));
        CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", tokenURL, null, parameters, null, null);
        LOGGER.debug("got response status code " + commonHttpResponse.getResponseCode());
        String responseEntityString = commonHttpResponse.getResponseEntityString();
        LOGGER.debug("responseEntityString is " + responseEntityString);
        JsonObject responseEntityJsonObject = (JsonObject) ConverterUtils.parseStringToJson(responseEntityString);
        setAccessToken(responseEntityJsonObject.get("access_token").getAsString());
        LOGGER.debug("accessToken is " + accessToken);
        if (responseEntityJsonObject.get("refresh_token") != null) {
            setRefreshToken(responseEntityJsonObject.get("refresh_token").getAsString());
            LOGGER.debug("refreshToken is " + refreshToken);
        }
        
        AdvancedConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(AdvancedConstant.ACCESS_TOKEN, accessToken);
        AdvancedConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
        Header authorizationHeader = createAdvancedAuthenticationTokenHeader(getAccessToken());
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
        setApiURL("https://" + host + basePath);
        LOGGER.debug("set apiURL to " + getApiURL());
        AdvancedConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(AdvancedConstant.API_VERSION_URL, getApiURL());
        AdvancedConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
	}
	
    /**
     * configuration manager
     */
    public AdvancedConfigurationManager localConfigurationManager = AdvancedConfigurationManager.getConfigurationManager();
    
    /**
     * method implementation.
     */
    private GenerateTokensMethod generateTokensMethod = new GenerateTokensMethod();
    
    /**
     * method implementation.
     */
    private GetSwaggerDocumentMethod getSwaggerDocumentMethod = new GetSwaggerDocumentMethod();
    
    /**
     * method implementation.
     */
    private InvokeOperationMethod invokeOperationMethod = new InvokeOperationMethod();
    
    @Override
    public GenerateTokensResponse generateTokens(GenerateTokensRequest generateTokensRequest) {
        return this.generateTokensMethod.generateTokens(generateTokensRequest);
    }
    
    @Override
    public GetSwaggerDocumentResponse getSwaggerDocument(GetSwaggerDocumentRequest getSwaggerDocumentRequest) {
        return this.getSwaggerDocumentMethod.getSwaggerDocument(getSwaggerDocumentRequest);
    }

    @Override
    public InvokeOperationResponse invokeOperation(InvokeOperationRequest invokeOperationRequest) {
        return this.invokeOperationMethod.invokeOperation(invokeOperationRequest);
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
    
	public String getAccessToken() {
		return accessToken;
	}

	public String getClientId() {
		return clientId;
	}

	public void setClientId(String clientId) {
		this.clientId = clientId;
	}
	
	public String getClientSecret() {
		return clientSecret;
	}

	public void setClientSecret(String clientSecret) {
		this.clientSecret = clientSecret;
	}

	public String getTokenURL() {
		return tokenURL;
	}

	public void setTokenURL(String tokenURL) {
		this.tokenURL = tokenURL;
	}
	
	public String getSwaggerAPIURL() {
		return swaggerAPIURL;
	}

	public void setSwaggerAPIURL(String swaggerAPIURL) {
		this.swaggerAPIURL = swaggerAPIURL;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getRefreshToken() {
		return refreshToken;
	}

	public void setRefreshToken(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	public String getApiURL() {
		return apiURL;
	}

	public void setApiURL(String apiURL) {
		this.apiURL = apiURL;
	}
}
