/**
 * Copyright 2017 Xcase All rights reserved.
 */

package com.xcase.msgraph.impl.simple.methods;

import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.msgraph.MSGraphExternalAPI;
import com.xcase.msgraph.SimpleMSGraphImpl;
import com.xcase.msgraph.constant.MSGraphConstant;
import com.xcase.msgraph.factories.MSGraphRequestFactory;
import com.xcase.msgraph.impl.simple.core.MSGraphConfigurationManager;
import com.xcase.msgraph.transputs.GetAccessTokenRequest;
import com.xcase.msgraph.transputs.GetAccessTokenResponse;
import java.lang.invoke.*;
import java.util.Properties;
import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class BaseMSGraphMethod {

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
    protected Properties config = MSGraphConfigurationManager.getConfigurationManager().getConfig();

    /**
     * API OAuth authorize prefix
     */
    protected String apiOAuthAuthorizePrefix;

    /**
     * API OAuth revoke prefix
     */
    protected String apiOAuthRevokePrefix;

    /**
     * API OAuth authorize prefix
     */
    protected String apiOAuthTokenPrefix;

    /**
     * API URL prefix.
     */
    protected String apiUrlPrefix;

    /**
     * API upload URL prefix.
     */
    protected String apiUploadUrlPrefix;

    /**
     * API version.
     */
    protected String apiVersion;

    /**
     * API request format.
     */
    protected String apiRequestFormat;

    /**
     * OAuth authorize XML URL, it's static, so no need to read each time.
     */
    protected String xmlOAuthApiUrl;

    /**
     * OAuth authorize XML URL, it's static, so no need to read each time.
     */
    protected String xmlOAuthRevokeUrl;

    /**
     * OAuth authorize XML URL, it's static, so no need to read each time.
     */
    protected String xmlOAuthTokenUrl;

    /**
     * API XML URL, it's static, so no need to read each time.
     */
    protected String apiUrl;

    /**
     *
     */
    public BaseMSGraphMethod() {
//        LOGGER.debug("Starting BaseMSGraphMethod()");
        this.apiOAuthTokenPrefix = config.getProperty(MSGraphConstant.CONFIG_API_OAUTH_TOKEN_PREFIX);
//        LOGGER.debug("apiOAuthTokenPrefix is " + apiOAuthTokenPrefix);
        this.apiOAuthAuthorizePrefix = config.getProperty(MSGraphConstant.CONFIG_API_OAUTH_AUTHORIZE_PREFIX);
//        LOGGER.debug("apiOAuthAuthorizePrefix is " + apiOAuthAuthorizePrefix);
        this.apiOAuthRevokePrefix = config.getProperty(MSGraphConstant.CONFIG_API_OAUTH_REVOKE_PREFIX);
//        LOGGER.debug("apiOAuthAuthorizePrefix is " + apiOAuthAuthorizePrefix);
        this.apiUrlPrefix = config.getProperty(MSGraphConstant.CONFIG_API_URL_PREFIX);
//        LOGGER.debug("apiUrlPrefix is " + apiUrlPrefix);
        this.apiVersion = config.getProperty(MSGraphConstant.CONFIG_API_VERSION);
//        LOGGER.debug("apiVersion is " + apiVersion);
        this.apiRequestFormat = config.getProperty(MSGraphConstant.CONFIG_API_REQUEST_FORMAT);
//        LOGGER.debug("apiVersion is " + apiVersion);
        this.apiUrl = getApiUrl();
        this.xmlOAuthApiUrl = getOAuthAuthorizeUrl();
        this.xmlOAuthRevokeUrl = getOAuthRevokeUrl();
        this.xmlOAuthTokenUrl = getOAuthTokenUrl();
    }

    /**
     * Returns a StringBuffer representation of the base API URL.
     * @return url
     */
    public StringBuffer getAPIUrl() {
        StringBuffer urlBuf = new StringBuffer();
        urlBuf.append(this.apiUrlPrefix);
        urlBuf.append(CommonConstant.SLASH_STRING);
        urlBuf.append(this.apiVersion);
        urlBuf.append(CommonConstant.SLASH_STRING);
        urlBuf.append(MSGraphConstant.CONFIG_API_REQUEST_FORMAT_REST);
        urlBuf.append(CommonConstant.QUESTION_MARK_STRING);
        urlBuf.append(CommonConstant.ACTION_EQUALS_STRING);
        return urlBuf;
    }

    /**
     *
     * @param actionType
     * @return actionType
     */
    public StringBuffer getApiUploadUrl(String actionType) {
//        LOGGER.debug("starting getApiUploadUrl()");
        StringBuffer apiUploadUrlBuf = new StringBuffer();
        apiUploadUrlBuf.append(this.apiUploadUrlPrefix);
        apiUploadUrlBuf.append(CommonConstant.SLASH_STRING);
        apiUploadUrlBuf.append(this.apiVersion);
        apiUploadUrlBuf.append(CommonConstant.SLASH_STRING);
        apiUploadUrlBuf.append(actionType);
//        LOGGER.debug("apiUploadUrlBuf is " + apiUploadUrlBuf.toString());
        return apiUploadUrlBuf;
    }

    /**
     * According to action name, return a string buffer. i.e. "get_ticket" can
     * result a "http://www.box.net/api/1.0/rest?action=get_ticket"
     *
     * @return the URL in string buffer
     */
    public String getApiUrl() {
//        LOGGER.debug("starting getApiUrl()");
        StringBuffer urlBuf = new StringBuffer();
        urlBuf.append(this.apiUrlPrefix);
        urlBuf.append(CommonConstant.SLASH_STRING);
        urlBuf.append(this.apiVersion);
        urlBuf.append(CommonConstant.SLASH_STRING);
//        LOGGER.debug("urlBuf is " + urlBuf.toString());
        return urlBuf.toString();
    }

    /**
     * get the OAuth URL. like: "https://www.box.com/api/oauth2/authorize"
     *
     * @return URL string
     */
    private String getOAuthAuthorizeUrl() {
        //LOGGER.debug("starting getOAuthApiUrl()");
        StringBuffer urlBuf = new StringBuffer();
        urlBuf.append(apiOAuthAuthorizePrefix);
//        urlBuf.append(BoxConstant.SLASH_STRING);
//        urlBuf.append(apiVersion);
//        urlBuf.append(BoxConstant.SLASH_STRING);
//        urlBuf.append(BoxConstant.CONFIG_API_REQUEST_FORMAT_XML);
        //LOGGER.debug("urlBuf is " + urlBuf.toString());
        return urlBuf.toString();
    }

    private String getOAuthRevokeUrl() {
        //LOGGER.debug("starting getOAuthRevokeUrl()");
        StringBuffer urlBuf = new StringBuffer();
        urlBuf.append(apiOAuthRevokePrefix);
        return urlBuf.toString();
    }

    /**
     * get the OAuth URL. like: "https://www.box.com/api/oauth2/authorize"
     *
     * @return URL string
     */
    private String getOAuthTokenUrl() {
        //LOGGER.debug("starting getOAuthTokenUrl()");
        StringBuffer urlBuf = new StringBuffer();
        urlBuf.append(apiOAuthTokenPrefix);
        //LOGGER.debug("urlBuf is " + urlBuf.toString());
        return urlBuf.toString();
    }

    private String getAccessToken(String clientId, String clientSecret, String tenantId) throws Exception {
        LOGGER.debug("starting getAccessToken()");
        GetAccessTokenRequest getAccessTokenRequest = MSGraphRequestFactory.createGetAccessTokenRequest(clientId, clientSecret, tenantId);
        LOGGER.debug("created getAccessTokenRequest");
        MSGraphExternalAPI iMSGraphExternalAPI = new SimpleMSGraphImpl();
        GetAccessTokenResponse getAccessTokenResponse = iMSGraphExternalAPI.getAccessToken(getAccessTokenRequest);
        String accessToken = getAccessTokenResponse.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        return accessToken;
    }

    /**
     *
     * @return header
     */
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

    /**
     *
     * @param accept
     * @return header
     */
    public Header createAcceptHeader(String accept) {
        String acceptHeader = "application/xml";
        if ("json".equals(accept)) {
            acceptHeader = "application/json";
        } else if ("xml".equals(accept)) {
        } else {
            LOGGER.warn("unexpected format: " + accept);
        }

        LOGGER.debug("acceptHeader is " + acceptHeader);
        return new BasicHeader("Accept", acceptHeader);
    }

    /**
     *
     * @return header
     */
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

    /**
     *
     * @param contentType
     * @return header
     */
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

    /**
     *
     * @param accessToken
     * @return header
     */
    public Header createMSGraphAuthenticationTokenHeader(String accessToken) {
        return new BasicHeader("Authorization", "Bearer " + accessToken);
    }
}
