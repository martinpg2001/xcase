package com.xcase.rest.generator.raml;

import com.xcase.rest.generator.*;
import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.utils.ConverterUtils;
import java.lang.invoke.MethodHandles;
import java.io.*;
import java.net.*;
import java.nio.file.*;
import java.util.*;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RAMLProxy extends RESTProxy
{
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public RAMLProxy() { }


    public RAMLProxy(URI baseUrl) {
        
    }

    public RAMLProxy(URI baseUrl, String username, String password, String tenant) {
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
            parameters.add(new BasicNameValuePair("password", "pass2app"));
            parameters.add(new BasicNameValuePair("tenantId", "stage1"));
            String baseUrl = _baseUrl.toString();
            LOGGER.debug("baseUrl is " + baseUrl);
            baseUrl = baseUrl.substring(0, baseUrl.length() - 4);
            LOGGER.debug("baseUrl is " + baseUrl);
            LOGGER.debug("_baseUrl is " + _baseUrl);
            HttpResponse httpResponse = httpClient.doHttpResponsePost(baseUrl + "auth/oauth/token", null, parameters, null);
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
}
