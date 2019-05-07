package com.xcase.intapp.advanced.impl.simple.methods;

import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
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

    public Header createContentTypeHeader() {
        String contentTypeHeader = "application/json";
        LOGGER.debug("contentTypeHeader is " + contentTypeHeader);
        return new BasicHeader("Content-Type", contentTypeHeader);
    }

    public Header createAdvancedAuthenticationTokenHeader(String accessToken) {
        return new BasicHeader(AdvancedConfigurationManager.getConfigurationManager().getConfig().getProperty(AdvancedConstant.CONFIG_API_AUTHENTICATION_HEADER), "Bearer " + accessToken);
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