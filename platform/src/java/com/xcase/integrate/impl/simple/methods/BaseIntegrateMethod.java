/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.integrate.impl.simple.methods;

import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.impl.simple.core.ConfigurationManager;
import com.xcase.integrate.constant.IntegrateConstant;
import com.xcase.integrate.impl.simple.core.IntegrateConfigurationManager;
import com.xcase.integrate.transputs.IntegrateResponse;
import java.lang.invoke.*;
import java.util.Properties;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;

/**
 * @author martin
 *
 */
public class BaseIntegrateMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * core http manager.
     */
    protected CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();

    /**
     * the configuration.
     */
    protected Properties integrateConfig = IntegrateConfigurationManager.getConfigurationManager().getConfig();

    /**
     * the configuration.
     */
    protected Properties localCommonConfig = ConfigurationManager.getConfigurationManager().getLocalConfig();

    /**
     * the configuration.
     */
    protected Properties localIntegrateConfig = IntegrateConfigurationManager.getConfigurationManager().getLocalConfig();

    /**
     * API Request Format, it's static, so no need to read each time.
     */
    protected String apiRequestFormat;

    /**
     * API Version, it's static, so no need to read each time.
     */
    protected String apiVersion;

    /**
     * Authentication header name, it's static, so no need to read each time.
     */
    protected String authenticationHeader;

    /**
     * API SOAP URL, it's static, so no need to read each time.
     */
    protected String baseUrl;

    /**
     * API VERSION URL, it's static, so no need to read each time.
     */
    protected String apiVersionUrl;

    /**
     *
     */
    public BaseIntegrateMethod() {
//        LOGGER.debug("Starting BaseIntegrateMethod()");
        String host = localIntegrateConfig.getProperty(CommonConstant.HOST);
        this.baseUrl = "https://" + host + "/api";
//        LOGGER.debug("baseUrl is " + baseUrl);
        this.authenticationHeader = integrateConfig.getProperty(IntegrateConstant.CONFIG_API_AUTHENTICATION_HEADER);
//        LOGGER.debug("authenticationHeader is " + authenticationHeader);
        this.apiVersion = localIntegrateConfig.getProperty(IntegrateConstant.CONFIG_API_VERSION);
//        LOGGER.debug("apiVersion is " + apiVersion);
        this.apiRequestFormat = localIntegrateConfig.getProperty(IntegrateConstant.CONFIG_API_REQUEST_FORMAT);
//        LOGGER.debug("apiRequestFormat is " + apiRequestFormat);
        this.apiVersionUrl = getAPIVersionUrl().toString();
//        LOGGER.debug("apiVersionUrl is " + apiVersionUrl);
    }

    /**
     *
     */
    public BaseIntegrateMethod(String requestFormat) {
//        LOGGER.debug("Starting BaseIntegrateMethod()");
        String host = localIntegrateConfig.getProperty(CommonConstant.HOST);
        this.baseUrl = "https://" + host + "/api";
//        LOGGER.debug("baseUrl is " + baseUrl);
        this.authenticationHeader = integrateConfig.getProperty(IntegrateConstant.CONFIG_API_AUTHENTICATION_HEADER);
//        LOGGER.debug("authenticationheader is " + authenticationheader);
        this.apiVersion = localIntegrateConfig.getProperty(IntegrateConstant.CONFIG_API_VERSION);
//        LOGGER.debug("apiVersion is " + apiVersion);
        this.apiRequestFormat = requestFormat;
//        LOGGER.debug("apiRequestFormat is " + apiRequestFormat);
        this.apiVersionUrl = getAPIVersionUrl().toString();
//        LOGGER.debug("apiVersionUrl is " + apiVersionUrl);
    }

    /**
     * According to action name, return a string buffer. result a
     * "http://nightly51/api/1.0"
     *
     * @return the URL in string buffer
     */
    public StringBuffer getAPIVersionUrl() {
        StringBuffer urlBuf = new StringBuffer();
        urlBuf.append(this.baseUrl);
        urlBuf.append(CommonConstant.SLASH_STRING);
        urlBuf.append(this.apiVersion);
        return urlBuf;
    }

    public Header createAcceptHeader() {
        String acceptHeader = "application/xml";
        if ("json".equals(this.apiRequestFormat)) {
            acceptHeader = "application/json";
        } else if ("xml".equals(this.apiRequestFormat)) {

        } else {
            LOGGER.warn("unexpected apiRequestFormat: " + this.apiRequestFormat);
        }

        LOGGER.debug("acceptHeader is " + acceptHeader);
        return new BasicHeader("Accept", acceptHeader);
    }

    public Header createAcceptHeader(String accept) {
        String acceptHeader = "application/xml";
        if ("json".equals(accept)) {
            acceptHeader = "application/json";
        } else if ("octet".equals(accept)) {
            acceptHeader = "application/octet-stream";
        } else if ("xml".equals(accept)) {
            /* This is default */
        } else {
            LOGGER.warn("unexpected accept: " + accept);
        }

        LOGGER.debug("acceptHeader is " + acceptHeader);
        return new BasicHeader("Accept", acceptHeader);
    }

    public Header createContentTypeHeader() {
        String contentTypeHeader = "application/xml";
        if ("json".equals(this.apiRequestFormat)) {
            contentTypeHeader = "application/json";
        } else if ("xml".equals(this.apiRequestFormat)) {

        } else {
            LOGGER.warn("unexpected apiRequestFormat: " + this.apiRequestFormat);
        }

        LOGGER.debug("contentTypeHeader is " + contentTypeHeader);
        return new BasicHeader("Content-Type", contentTypeHeader);
    }

    public Header createContentTypeHeader(String contentType) {
        String contentTypeHeader = "application/xml";
        if ("json".equals(contentType)) {
            contentTypeHeader = "application/json";
        } else if ("xml".equals(contentType)) {

        } else {
            LOGGER.warn("unexpected apiRequestFormat: " + this.apiRequestFormat);
        }

        LOGGER.debug("contentTypeHeader is " + contentTypeHeader);
        return new BasicHeader("Content-Type", contentTypeHeader);
    }

    public Header createIntegrateAuthenticationTokenHeader(String accessToken) {
        return new BasicHeader(this.authenticationHeader, accessToken);
    }
    
    public void handleUnexpectedResponseCode(IntegrateResponse response, CommonHttpResponse commonHttpResponse) {
        LOGGER.warn("unexpected response code: " + commonHttpResponse.getResponseCode());
        response.setMessage("Unexpected response code: " + commonHttpResponse.getResponseCode());
        response.setResponseCode(commonHttpResponse.getResponseCode());
        response.setStatusLine(commonHttpResponse.getStatusLine());
        response.setStatus(commonHttpResponse.getStatusLine().getReasonPhrase());
    }
    
    public void handleUnexpectedException(IntegrateResponse response, Exception e) {
        LOGGER.warn("exception invoking API: " + e.getMessage());
        response.setMessage("Exception invoking API: " + e.getMessage());
        response.setStatus("FAIL");
    }
}
