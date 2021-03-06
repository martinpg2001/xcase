package com.xcase.intapp.cdscm.impl.simple.methods;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.intapp.cdscm.constant.CDSCMConstant;
import com.xcase.intapp.cdscm.impl.simple.core.CDSCMConfigurationManager;
import com.xcase.intapp.cdscm.transputs.CDSCMResponse;
import java.lang.invoke.MethodHandles;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class BaseCDSCMMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * core http manager.
     */
    protected CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();

    public String apiVersionUrl = CDSCMConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSCMConstant.API_VERSION_URL);

    public String endPoint;

    public String getAPIVersionUrl() {
        return CDSCMConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(CDSCMConstant.API_VERSION_URL);
    }

    public Header createAcceptHeader() {
        String acceptHeader = "application/vnd.intapp+json";
        LOGGER.debug("acceptHeader is " + acceptHeader);
        return new BasicHeader("Accept", acceptHeader);
    }

    public Header createContentTypeHeader() {
        String contentTypeHeader = "application/json";
        LOGGER.debug("contentTypeHeader is " + contentTypeHeader);
        return new BasicHeader("Content-Type", contentTypeHeader);
    }

    public Header createCDSCMAuthenticationTokenHeader(String accessToken) {
        return new BasicHeader(CDSCMConfigurationManager.getConfigurationManager().getConfig().getProperty(CDSCMConstant.CONFIG_API_AUTHENTICATION_HEADER), "Bearer " + accessToken);
    }
    
    public void handleExpectedResponseCode(CDSCMResponse response, CommonHttpResponse commonHttpResponse) {
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

    public void handleUnexpectedResponseCode(CDSCMResponse response, CommonHttpResponse commonHttpResponse) {
        LOGGER.warn("unexpected response code: " + commonHttpResponse.getResponseCode());
        response.setMessage("Unexpected response code: " + commonHttpResponse.getResponseCode());
        LOGGER.warn("response entity string is " + commonHttpResponse.getResponseEntityString());
        response.setResponseCode(commonHttpResponse.getResponseCode());
        response.setStatusLine(commonHttpResponse.getStatusLine());
        response.setStatus(commonHttpResponse.getStatusLine().getReasonPhrase());
    }

    public void handleUnexpectedException(CDSCMResponse response, Exception e) {
        LOGGER.warn("exception invoking API: " + e.getMessage());
        response.setMessage("Exception invoking API: " + e.getMessage());
        response.setStatus("FAIL");
    }
}
