/**
 * Copyright 2017 Xcase All rights reserved.
 */
package com.xcase.open.impl.simple.methods;

import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.common.utils.URLUtils;
import com.xcase.open.factories.OpenResponseFactory;
import com.xcase.open.transputs.CreateAuthorizationCodeFromApplicationRequest;
import com.xcase.open.transputs.CreateAuthorizationCodeFromApplicationResponse;
import com.google.gson.JsonObject;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.auth.Credentials;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

/**
 *
 * @author martin
 */
public class CreateAuthorizationCodeFromApplicationMethod extends BaseOpenMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public CreateAuthorizationCodeFromApplicationMethod() {
        super();
//        LOGGER.debug("finishing CreateAuthorizationCodeFromApplication()");
    }

    public CreateAuthorizationCodeFromApplicationResponse createAuthorizationCodeFromApplication(CreateAuthorizationCodeFromApplicationRequest request) {
        LOGGER.debug("starting createAuthorizationCodeFromApplication()");
        try {
            CreateAuthorizationCodeFromApplicationResponse createTokensFromAuthorizationCodeResponse = OpenResponseFactory.createCreateAuthorizationCodeFromApplicationResponse();
            String baseUrl = request.getBaseUrl();
            LOGGER.debug("baseUrl is " + baseUrl);
            String password = request.getPassword();
            LOGGER.debug("password is " + password);
            String tenantId = request.getTenantId();
            LOGGER.debug("tenantId is " + tenantId);
            String username = request.getUsername();
            LOGGER.debug("username is " + username);
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("GET", baseUrl, null, null, null, null);
            List<NameValuePair> loginParameters = new ArrayList<NameValuePair>();
            loginParameters.add(new BasicNameValuePair("X$A$txtUsername", username));
            loginParameters.add(new BasicNameValuePair("X$A$txtPassword", password));
            commonHttpResponse = httpManager.doCommonHttpResponseMethod("GET", baseUrl + "app/Intake/Default.aspx", null, loginParameters, null, null);
            String authorizationUrl = request.getAuthorizationUrl();
            if (authorizationUrl == null|| authorizationUrl.isEmpty()) {
                authorizationUrl = baseUrl + "auth/oauth/authorize";
            }
            
            LOGGER.debug("authorizationUrl is " + authorizationUrl);
            String redirectUrl = request.getRedirectUrl();
            if (redirectUrl == null|| redirectUrl.isEmpty()) {
                redirectUrl = baseUrl + "api/oauth2/callback";
            }
            
            LOGGER.debug("redirectUrl is " + redirectUrl);
            Header acceptHeader = createAcceptHeader("text/html");
            Header contentTypeHeader = createContentTypeHeader("application/x-www-form-urlencoded");
            Header[] headers = {acceptHeader, contentTypeHeader};
            String clientId = request.getClientId();
            LOGGER.debug("clientId is " + clientId);
            String clientSecret = request.getClientSecret();
            LOGGER.debug("clientSecret is " + clientSecret);
            List<NameValuePair> generateParameters = new ArrayList<NameValuePair>();
            generateParameters.add(new BasicNameValuePair("response_type", "code"));
            generateParameters.add(new BasicNameValuePair("client_id", clientId));
            generateParameters.add(new BasicNameValuePair("redirect_uri", redirectUrl));
            generateParameters.add(new BasicNameValuePair("state", "default"));
            Credentials credentials = new UsernamePasswordCredentials(username, password);
            CommonHttpResponse generateCommonHttpResponse = httpManager.doCommonHttpResponseMethod("GET", authorizationUrl, headers, generateParameters, null, credentials);
            LOGGER.debug("got response status code " + generateCommonHttpResponse.getResponseCode());
            String responseEntityString = generateCommonHttpResponse.getResponseEntityString();
            LOGGER.debug("responseEntityString is " + responseEntityString);
            List<NameValuePair> authorizeParameters = new ArrayList<NameValuePair>();
            authorizeParameters.add(new BasicNameValuePair("deployment", "Cloud"));
            authorizeParameters.add(new BasicNameValuePair("password", password));
            authorizeParameters.add(new BasicNameValuePair("tenant", tenantId));
            authorizeParameters.add(new BasicNameValuePair("username", username));
            authorizeParameters.add(new BasicNameValuePair("submit.Signin", "Sign+In"));
            String returnUrl = "/auth/oauth/authorize?response_type=code" + CommonConstant.AND_SIGN_STRING + "client_id=" + clientId + CommonConstant.AND_SIGN_STRING + "redirect_uri=" + redirectUrl + CommonConstant.AND_SIGN_STRING + "state=default";
            CommonHttpResponse authorizeCommonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", baseUrl + "/auth/Account/Login?ReturnUrl=" + URLUtils.encodeUrl(returnUrl), headers, authorizeParameters, null, credentials);
            responseEntityString = authorizeCommonHttpResponse.getResponseEntityString();
            String authorizationCode = responseEntityString;
            LOGGER.debug("authorizationCode is " + authorizationCode);
            createTokensFromAuthorizationCodeResponse.setAuthorizationCode(authorizationCode);
            return createTokensFromAuthorizationCodeResponse;
        } catch (Exception e) {
            LOGGER.warn("exception creating tokens from authorization code: " + e.getMessage());
        }

        return null;
    }            
}
