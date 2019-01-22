/**
 * Copyright 2019 Xcase All rights reserved.
 */
package com.xcase.rest.generator.raml;

import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.rest.generator.swagger.ISwaggerProxy;
import com.xcase.rest.generator.swagger.SwaggerProxy;
import java.lang.invoke.*;
import java.net.*;
import java.util.*;
import org.apache.http.*;
import org.apache.http.message.*;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class IntegrateRAMLProxy extends SwaggerProxy implements ISwaggerProxy {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This constructor sets the base URL used by the proxy classes. The URL
     * must end in a /.
     *
     * @param url
     */
    public IntegrateRAMLProxy(URL url) {
        _baseUrl = url;
    }

    /**
     * This constructor sets the base URL used by the proxy classes. The URL
     * must end in a /.
     *
     * @param url
     */
    public IntegrateRAMLProxy(URL url, String accessToken) {
        this.token = accessToken;
        this._baseUrl = url;
    }

    public IntegrateRAMLProxy(URL baseUrl, String username, String password, String tenant) {
        _baseUrl = baseUrl;
        _username = username;
        _password = password;
        _tenantId = tenant;
    }

    public CommonHTTPManager buildHttpClient() {
        LOGGER.debug("starting buildHttpClient()");
        CommonHTTPManager commonHTTPManager = CommonHTTPManager.getCommonHTTPManager();
        getAuthenticationToken(commonHTTPManager);
        return commonHTTPManager;
    }

    public CommonHTTPManager buildHttpClient(String accessToken) {
        LOGGER.debug("starting buildHttpClient()");
        CommonHTTPManager commonHTTPManager = CommonHTTPManager.getCommonHTTPManager();
        return commonHTTPManager;
    }

    public void getAuthenticationToken(CommonHTTPManager httpClient) {
        LOGGER.debug("starting getAuthenticationToken()");
        /* Nothing to do */
    }

    public Header[] setHeaders() {
        Header acceptHeader = new BasicHeader("Accept", "application/json");
        Header bearerHeader = new BasicHeader("IntegrateAuthenticationToken", token);
        Header contentTypeHeader = new BasicHeader("Content-Type", "application/json");
        Header[] headers = new Header[] { acceptHeader, bearerHeader, contentTypeHeader };
        return headers;
    }

    // helper function for building uris.
    public String appendQuery(String currentUrl, String paramName, String value) {
        try {
            LOGGER.debug("value is " + value);
            if (currentUrl.contains("?")) {
                currentUrl += String.format("&" + paramName + "=" + URLEncoder.encode(value, "UTF-8"));
            } else {
                currentUrl += String.format("?" + paramName + "=" + URLEncoder.encode(value, "UTF-8"));
            }
            LOGGER.debug("currentUrl is " + currentUrl);
        } catch (Exception e) {
            LOGGER.warn("exception appending query " + e.getMessage());
        }

        return currentUrl;
    }

    @Override
    public String getSwaggerDocument() throws Exception {
        LOGGER.debug("starting getSwaggerDocument()");
        String url = "api/v1/swagger";
        LOGGER.debug("url is " + url);
        CommonHTTPManager apiClient = buildHttpClient();
        LOGGER.debug("about to invoke method using url {0}", url);
        LOGGER.debug("method is GET");
        String requestURL = _baseUrl + url;
        LOGGER.debug("requestURL is {0}", requestURL);
        LOGGER.debug("about to send request for Swagger document");
        Header[] headers = setHeaders();
        String content = apiClient.doStringGet(requestURL, headers, null);
        LOGGER.debug("content is {0}", content);
        return content;
    }
}
