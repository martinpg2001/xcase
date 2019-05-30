/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.constant;

/**
 *
 * @author martin
 */
public class SalesforceConstant {

    public static final String CONFIG_API_AUTHENTICATION_HEADER = "salesforce.authentication.header";
    public static final String CONFIG_API_OAUTH_TOKEN_PREFIX = "salesforce.config.api.oauth2_token_prefix";
    public static final String CONFIG_API_OAUTH_AUTHORIZE_PREFIX = "salesforce.config.api.oauth2_authorize_prefix";
    public static final String CONFIG_API_OAUTH_REVOKE_PREFIX = "";
    public static final String CONFIG_API_REQUEST_FORMAT = "";
    public static final String CONFIG_API_REQUEST_FORMAT_SOAP = "";
    public static final String CONFIG_API_REQUEST_FORMAT_XML = "";
    public static final String CONFIG_API_REQUEST_FORMAT_REST = "";
    public static final String CONFIG_API_UPLOAD_URL_PREFIX = "";
    public static final String CONFIG_API_URL_PREFIX = "salesforce.config.api.url_prefix";
    public static final String CONFIG_API_VERSION = "salesforce.config.api.version";
    public static final String CONFIG_FILE_NAME = "salesforce-config.properties";
    public static final String CONFIG_FILE_DEFAULT_NAME = "salesforce-config-default.properties";
    public static final String LOCAL_CONFIG_FILE_NAME = "salesforce-local-config.properties";
    public static final String LOCAL_OAUTH2_AUTHORIZATION_CODE = "salesforce.authorization.code";
    public static final String LOCAL_OAUTH2_ACCESS_TOKEN = "salesforce.config.api.oauth2_access_token";
    public static final String LOCAL_OAUTH2_CLIENT_ID = "salesforce.config.api.oauth2_client_id";
    public static final String LOCAL_OAUTH2_CLIENT_SECRET = "salesforce.config.api.oauth2_client_secret";
    public static final String LOCAL_OAUTH2_INSTANCE_URL = "salesforce.config.api.instance_url";
    public static final String LOCAL_OAUTH2_REDIRECT_URL = "salesforce.redirect.url";
    public static final String LOCAL_OAUTH2_REFRESH_TOKEN = "salesforce.config.api.oauth2_refresh_token";
    public static final String LOCAL_OAUTH2_TOKEN_URL = "salesforce.token.url";
    public static final String PARAM_NAME_CLIENT_ID = "client_id";
    public static final String PARAM_NAME_CLIENT_SECRET = "client_secret";
    public static final String PARAM_NAME_CODE = "code";
    public static final String PARAM_NAME_GRANT_TYPE = "grant_type";
    public static final String PARAM_NAME_REDIRECT_URI = "redirect_uri";
    public static final String PARAM_NAME_REFRESH_TOKEN = "refresh_token";
    public static final String PARAM_NAME_SOAP_BODY = "";
    public static final String PARAM_NAME_SOAP_ENVELOPE = "";
    /**
     * OK status code.
     */
    public static final String STATUS_GET_ACCESS_TOKEN_OK = "OK";
    /**
     * NOT LOGGED IN status code.
     */
    public static final String STATUS_NOT_LOGGED_IN = "not_logged_in";
    
	public static final String CONFIG_CONSUMER_KEY = "salesforce.consumer.key";
	
	public static final String CONFIG_CONSUMER_SECRET = "salesforce.consumer.secret";
	
	public static final String API_VERSION_URL = "salesforce.api.version.url";

    private SalesforceConstant() {
    }
}
