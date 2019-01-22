/**
 * Copyright 2019 Xcase All rights reserved.
 */
package com.xcase.rest.generator.swagger;

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
public class OpenOnpremiseSwaggerProxy extends SwaggerProxy implements ISwaggerProxy {

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
    public OpenOnpremiseSwaggerProxy(URL url) {
        _baseUrl = url;
    }

    /**
     * This constructor sets the base URL used by the proxy classes. The URL
     * must end in a /.
     *
     * @param url
     */
    public OpenOnpremiseSwaggerProxy(URL url, String accessToken) {
        this.token = accessToken;
        this._baseUrl = url;
    }

    public OpenOnpremiseSwaggerProxy(URL baseUrl, String username, String password, String tenant) {
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
        try {
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair("grant_type", "password"));
            parameters.add(new BasicNameValuePair("username", "Admin"));
            parameters.add(new BasicNameValuePair("password", ""));
            parameters.add(new BasicNameValuePair("tenantId", ""));
            String baseUrl = _baseUrl.toString();
            LOGGER.debug("baseUrl is " + baseUrl);
//            baseUrl = baseUrl.substring(0, baseUrl.length() - 4);
            LOGGER.debug("baseUrl is " + baseUrl);
            LOGGER.debug("_baseUrl is " + _baseUrl);
            HttpResponse httpResponse = httpClient.doHttpResponsePost(baseUrl + "token", null, parameters, null);
            LOGGER.debug("posted request");
            HttpEntity httpEntity = httpResponse.getEntity();
            LOGGER.debug("got responseEntity");
            if (httpEntity != null) {
                String responseEntityString = EntityUtils.toString(httpEntity);
                LOGGER.debug("responseEntityString is " + responseEntityString);
                JsonParser jsonParser = new JsonParser();
                JsonElement jsonElement = ConverterUtils.parseStringToJson(responseEntityString);
                if (jsonElement != null) {
                    JsonElement tokenElement = ((JsonObject) jsonElement).get("access_token");
                    token = tokenElement.getAsString();
                    LOGGER.debug("token is " + token);
                }
            }
        } catch (Exception e) {
            LOGGER.warn("exception getting authentication token: " + e.getMessage());
        }
    }

    public Header[] setHeaders() {
        Header acceptHeader = new BasicHeader("Accept", "application/json");
        Header bearerHeader = new BasicHeader("Authorization", "Bearer " + token);
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
        LOGGER.debug("starting GetOpenSwaggerDocument()");
        String url = "api/swagger/docs/v1";
        LOGGER.debug("url is " + url);
        CommonHTTPManager apiClient = buildHttpClient();
        LOGGER.debug("about to invoke method using url " + url);
        LOGGER.debug("method is GET");
        String requestURL = _baseUrl + url;
        LOGGER.debug("requestURL is " + requestURL);
        LOGGER.debug("about to send request for Swagger document");
        Header[] headers = setHeaders();
        String content = apiClient.doStringGet(requestURL, headers, null);
        LOGGER.debug("content is " + content);
        return content;
    }
}
