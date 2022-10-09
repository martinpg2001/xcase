/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.core;

import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.utils.ConverterUtils;
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
public class SwaggerProxy {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    protected String accessToken;
    protected URL baseUrl;

    /**
     * This constructor sets the base URL used by the proxy classes. The URL
     * must end in a /.
     *
     * @param url
     */
    public SwaggerProxy(URL url) {
        baseUrl = url;
    }

    /**
     * This constructor sets the base URL used by the proxy classes. The URL
     * must end in a /.
     *
     * @param url
     */
    public SwaggerProxy(URL url, String accessToken) {
        this.accessToken = accessToken;
        this.baseUrl = url;
    }

    public CommonHTTPManager buildHttpClient() {
        CommonHTTPManager httpClient = CommonHTTPManager.getCommonHTTPManager();
        getAuthenticationToken(httpClient);
        return httpClient;
    }

    public CommonHTTPManager buildHttpClient(String accessToken) {
        CommonHTTPManager commonHTTPManager = CommonHTTPManager.getCommonHTTPManager();
        return commonHTTPManager;
    }

    public void getAuthenticationToken(CommonHTTPManager httpClient) {
        LOGGER.debug("starting getAuthenticationToken()");
        try {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("grant_type", "password"));
            parameters.add(new BasicNameValuePair("username", "Admin"));
            parameters.add(new BasicNameValuePair("password", ""));
            parameters.add(new BasicNameValuePair("tenantId", ""));
            String tokenURL = baseUrl + "token";
            LOGGER.debug("tokenURL is " + tokenURL);
            HttpResponse httpResponse = httpClient.doHttpResponsePost(baseUrl + "token", null, parameters, null);
            LOGGER.debug("posted request");
            HttpEntity httpEntity = httpResponse.getEntity();
            LOGGER.debug("got responseEntity");
            if (httpEntity != null) {
                String responseEntityString = EntityUtils.toString(httpEntity);
                LOGGER.debug("responseEntityString is " + responseEntityString);
                JsonElement jsonElement = ConverterUtils.parseStringToJson(responseEntityString);
                if (jsonElement != null) {
                    JsonElement tokenElement = ((JsonObject) jsonElement).get("access_token");
                    accessToken = tokenElement.getAsString();
                    LOGGER.debug("token is " + accessToken);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("exception getting authentication token: " + e.getMessage());
        }
    }

    // helper function for building uris.
    public String AppendQuery(String currentUrl, String paramName, String value) {
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
}
