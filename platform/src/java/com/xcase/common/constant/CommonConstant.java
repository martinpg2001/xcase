/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.common.constant;

/**
 *
 * @author martin
 */
public class CommonConstant {

    // config
    /**
     * config file name.
     */
    public static final String CONFIG_FILE_NAME = "common-config.properties";

    /**
     * default config file name.
     */
    public static final String CONFIG_FILE_DEFAULT_NAME = "default-common-config.properties";

    /**
     * config parameter.
     */
    public static final String CONFIG_HTTPCLIENT_MAXCONNECTIONSPERHOST = "common.config.httpclient.MaxConnectionsPerHost";

    /**
     * config parameter.
     */
    public static final String CONFIG_HTTPCLIENT_MAXTOTALCONNECTIONS = "common.config.httpclient.MaxTotalConnections";

    /**
     * config parameter.
     */
    public static final String CONFIG_HTTPCLIENT_CONNECTIONTIMEOUT = "common.config.httpclient.ConnectionTimeout";

    /**
     * config parameter.
     */
    public static final String CONFIG_HTTPCLIENT_SOCONNECTIONTIMEOUT = "common.config.httpclient.SoConnectionTimeout";

    /**
     * config parameter.
     */
    public static final String CONFIG_HTTPCLIENT_IGNORECOOKIES = "common.config.httpclient.IgnoreCookies";

    /**
     * config parameter.
     */
    public static final String CONFIG_API_REQUEST_FORMAT_REST = "rest";

    /**
     * config parameter.
     */
    public static final String CONFIG_API_REQUEST_FORMAT_XML = "xml";

    /**
     * config parameter.
     */
    public static final String CONFIG_API_REQUEST_FORMAT_SOAP = "soap";

    /**
     * host name.
     */
    public static final String HOST = "host";

    /**
     * config file name.
     */
    public static final String LOCAL_CONFIG_FILE_NAME = "common-local-config.properties";

    // characters
    /**
     * special characters.
     */
    public static final String SLASH_STRING = "/";

    /**
     * special characters.
     */
    public static final String QUESTION_MARK_STRING = "?";

    /**
     * special characters.
     */
    public static final String ACTION_EQUALS_STRING = "action=";

    /**
     * special characters.
     */
    public static final String AND_SIGN_STRING = "&";

    /**
     * special characters.
     */
    public static final String AUTH_URL_STRING = "auth";

    /**
     * special characters.
     */
    public static final String EQUALS_SIGN_STRING = "=";

    /**
     * special characters.
     */
    public static final int HTTP_SEND_REDIRECT = 302;

    /**
     * true string.
     */
    public static final String TRUE_STRING = "true";

    /**
     * X-HTTP-Method string
     */
    public static final String X_HTTP_METHOD_STRING = "X-HTTP-Method-Override";

    /**
     * ERROR string.
     */
    public static final String ERROR = "ERROR";

    /**
     * FAILURE string.
     */
    public static final String FAILURE = "FAILURE";

    /**
     * SUCCESS string.
     */
    public static final String SUCCESS = "SUCCESS";

    private CommonConstant() {
    }
}
