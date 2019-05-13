package com.xcase.intapp.advanced.impl.simple.methods;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.advanced.constant.AdvancedConstant;
import com.xcase.intapp.advanced.impl.simple.core.AdvancedConfigurationManager;
import com.xcase.intapp.advanced.transputs.AdvancedResponse;
import java.lang.invoke.MethodHandles;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseAdvancedMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * core http manager.
     */
    protected CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();

    public String endPoint;

    public String getAPIVersionUrl() {
        return AdvancedConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(AdvancedConstant.API_VERSION_URL);
    }

    public Header createAcceptHeader() {
        String acceptHeader = "application/json";
        LOGGER.debug("acceptHeader is " + acceptHeader);
        return new BasicHeader("Accept", acceptHeader);
    }
    
    public Header createAcceptHeader(String accept) {
        LOGGER.debug("accept is " + accept);
        return new BasicHeader("Accept", accept);
    }

    public Header createContentTypeHeader() {
        String contentTypeHeader = "application/json";
        LOGGER.debug("contentTypeHeader is " + contentTypeHeader);
        return new BasicHeader("Content-Type", contentTypeHeader);
    }

    public Header createAdvancedAuthenticationTokenHeader(String accessToken) {
        return new BasicHeader(AdvancedConfigurationManager.getConfigurationManager().getConfig().getProperty(AdvancedConstant.CONFIG_API_AUTHENTICATION_HEADER), "Bearer " + accessToken);
    }
    
    public void handleExpectedResponseCode(AdvancedResponse response, CommonHttpResponse commonHttpResponse) {
        String responseEntityString = commonHttpResponse.getResponseEntityString();
        LOGGER.debug("responseEntityString is " + responseEntityString);
        response.setEntityString(responseEntityString);
        response.setResponseCode(commonHttpResponse.getResponseCode());
        response.setStatus(commonHttpResponse.getStatusLine().getReasonPhrase());
        response.setStatusLine(commonHttpResponse.getStatusLine());
        if (responseEntityString != null && !responseEntityString.isEmpty()) {
        	Gson gson = new GsonBuilder().setDateFormat("yyyy-MM-dd' 'HH:mm:ss").create();
            JsonElement jsonElement = (JsonElement) ConverterUtils.parseStringToJson(responseEntityString);
            if (jsonElement.isJsonArray()) {
                JsonArray jsonArray = (JsonArray) jsonElement;
            } else {
                JsonObject jsonObject = (JsonObject) jsonElement;
            }
        } else {
        	LOGGER.debug("responseEntityString is null or empty");
        }    	
    }

    public void handleUnexpectedResponseCode(AdvancedResponse response, CommonHttpResponse commonHttpResponse) {
        LOGGER.warn("unexpected response code: " + commonHttpResponse.getResponseCode());
        response.setMessage("Unexpected response code: " + commonHttpResponse.getResponseCode());
        response.setResponseCode(commonHttpResponse.getResponseCode());
        response.setStatusLine(commonHttpResponse.getStatusLine());
        response.setStatus(commonHttpResponse.getStatusLine().getReasonPhrase());
    }

    public void handleUnexpectedException(AdvancedResponse response, Exception e) {
        LOGGER.warn("exception invoking API: " + e.getMessage());
        response.setMessage("Exception invoking API: " + e.getMessage());
        response.setStatus("FAIL");
    }
}
