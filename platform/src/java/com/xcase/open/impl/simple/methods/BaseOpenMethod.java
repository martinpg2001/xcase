/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.open.constant.OpenConstant;
import com.xcase.open.impl.simple.core.OpenConfigurationManager;
import java.lang.invoke.*;
import java.util.Properties;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class BaseOpenMethod {

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
    protected Properties config = OpenConfigurationManager.getConfigurationManager().getConfig();

    /**
     * the local configuration.
     */
    protected Properties localConfig = OpenConfigurationManager.getConfigurationManager().getLocalConfig();
    
    /**
     * API Request Format, it's static, so no need to read each time.
     */
    protected String apiRequestFormat = "json";

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
     * API Swagger URL, it's static, so no need to read each time.
     */
    protected String swaggerApiUrl;

    public BaseOpenMethod() {
//        LOGGER.debug("starting BaseOpenMethod()");
        this.swaggerApiUrl = localConfig.getProperty(OpenConstant.CONFIG_SWAGGER_API_URL);
//        LOGGER.debug("swaggerApiUrl is " + this.swaggerApiUrl);
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
        } else if ("xml".equals(accept)) {
            acceptHeader = "application/xml";
        } else {
            acceptHeader = accept;
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
            contentTypeHeader = "application/xml";
        } else {
            contentTypeHeader = contentType;;
        }

        LOGGER.debug("contentTypeHeader is " + contentTypeHeader);
        return new BasicHeader("Content-Type", contentTypeHeader);
    }

    public Header createIntegrateAuthenticationTokenHeader(String accessToken) {
        return new BasicHeader(this.authenticationHeader, accessToken);
    }
}
