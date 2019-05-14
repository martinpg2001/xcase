package com.xcase.intapp.document;

import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.cdsusers.impl.simple.methods.CreatePersonMethod;
import com.xcase.intapp.document.constant.DocumentConstant;
import com.xcase.intapp.document.impl.simple.core.DocumentConfigurationManager;
import com.xcase.intapp.document.impl.simple.methods.*;
import com.xcase.intapp.document.transputs.*;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SimpleDocumentImpl implements DocumentExternalAPI {
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
	
	public SimpleDocumentImpl(String clientId, String clientSecret, String swaggerAPIURL, String tokenURL) {
		setClientId(clientId);
		setClientSecret(clientSecret);
		setSwaggerAPIURL(swaggerAPIURL);
		setTokenURL(tokenURL);
	}
	
    /**
     * method implementation.
     */
    private GetCategoriesMethod getCategoriesMethod = new GetCategoriesMethod();
	
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
        
        DocumentConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(DocumentConstant.ACCESS_TOKEN, accessToken);
        DocumentConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
        Header authorizationHeader = createDocumentAuthenticationTokenHeader(getAccessToken());
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
        DocumentConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(DocumentConstant.API_VERSION_URL, getApiURL());
        DocumentConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
	}
	
    /**
     * configuration manager
     */
    public DocumentConfigurationManager localConfigurationManager = DocumentConfigurationManager.getConfigurationManager();
    
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
    
    public static Header createDocumentAuthenticationTokenHeader(String accessToken) {
        return new BasicHeader(DocumentConfigurationManager.getConfigurationManager().getConfig().getProperty(DocumentConstant.CONFIG_API_AUTHENTICATION_HEADER), accessToken);
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

	@Override
	public GetCategoriesResponse getCategories(GetCategoriesRequest getCategoriesRequest) {
		return this.getCategoriesMethod.getCategories(getCategoriesRequest);
	}
}
