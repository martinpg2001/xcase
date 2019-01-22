/**
 * Copyright 2017 Xcase. All rights reserved.
 */

package com.xcase.msgraph.impl.simple.methods;

import com.google.gson.*;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.msgraph.constant.MSGraphConstant;
import com.xcase.msgraph.factories.MSGraphResponseFactory;
import com.xcase.msgraph.transputs.GetAuthorizationCodeRequest;
import com.xcase.msgraph.transputs.GetAuthorizationCodeResponse;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

/**
 *
 * @author martin
 */
public class GetAuthorizationCodeMethod extends BaseMSGraphMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetAuthorizationCodeMethod() {
        super();
//        LOGGER.debug("finishing GetAuthorizationCodeMethod()");
    }

    public GetAuthorizationCodeResponse getAuthorizationCode(GetAuthorizationCodeRequest getAuthorizationCodeRequest) {
        LOGGER.debug("starting getAuthorizationCode()");
        try {
//            GetAuthorizationCodeResponse getAuthorizationCodeResponse = MSGraphResponseFactory.createGetAuthorizationCodeResponse();
            String clientId = getAuthorizationCodeRequest.getClientId();
            LOGGER.debug("clientId is " + clientId);
            String tenantId = getAuthorizationCodeRequest.getTenantId();
            LOGGER.debug("tenantId is " + tenantId);
            String username = getAuthorizationCodeRequest.getUsername();
            LOGGER.debug("username is " + username);
            String password = getAuthorizationCodeRequest.getPassword();
            LOGGER.debug("password is " + password);           
            List<NameValuePair> authorizeParameters = new ArrayList<NameValuePair>();
            NameValuePair clientIdNVP = new BasicNameValuePair("client_id", clientId);
            authorizeParameters.add(clientIdNVP);
            NameValuePair clientSecretNVP = new BasicNameValuePair("redirect_uri", "http://localhost");
            authorizeParameters.add(clientSecretNVP);
            NameValuePair grantTypeNVP = new BasicNameValuePair("response_type", "code");
            authorizeParameters.add(grantTypeNVP);
            NameValuePair scopeNVP = new BasicNameValuePair("scope", "openid offline_access https://graph.microsoft.com/mail.read");
            authorizeParameters.add(scopeNVP);
            //String authorizeEndPoint = config.getProperty(MSGraphConstant.CONFIG_API_OAUTH_TOKEN_PREFIX) + CommonConstant.SLASH_STRING + "common" + CommonConstant.SLASH_STRING + "oauth2" + CommonConstant.SLASH_STRING + "authorize";
            //String authorizeEndPoint = "https://login.microsoftonline.com/common/oauth2/v2.0/authorize";
            String authorizeEndPoint = "https://login.microsoftonline.com/" + tenantId + "/oauth2/v2.0/authorize";
            LOGGER.debug("authorizeEndPoint is " + authorizeEndPoint);
            CommonHttpResponse authorizeCommonHttpResponse = httpManager.doCommonHttpResponsePost(authorizeEndPoint, null, authorizeParameters, null, null);
            List<NameValuePair> loginParameters = new ArrayList<NameValuePair>();
            Document authorizeDocument = Jsoup.parse(authorizeCommonHttpResponse.getResponseEntityString());
            Elements inputs = authorizeDocument.getElementsByTag("input");
            for (Element input : inputs) {
                String name = input.attr("name");
                LOGGER.debug("name is " + name);
                String value = input.attr("value");
                LOGGER.debug("value is " + value);
                if (name.equals("ctx")) {
                    NameValuePair ctxNVP = new BasicNameValuePair("ctx", value);
                    loginParameters.add(ctxNVP);
                    continue;
                }
                
                if (name.equals("flowToken")) {
                    NameValuePair flowTokenNVP = new BasicNameValuePair("flowToken", value);
                    loginParameters.add(flowTokenNVP);
                    continue;
                }
                
                if (name.equals("canary")) {
                NameValuePair canaryNVP = new BasicNameValuePair("canary", value);
                loginParameters.add(canaryNVP);
                    continue;
                }
            }
            
            //String loginEndPoint = config.getProperty(MSGraphConstant.CONFIG_API_OAUTH_TOKEN_PREFIX) + CommonConstant.SLASH_STRING + "common" + CommonConstant.SLASH_STRING + "login";
            //String loginEndPoint = "https://login.microsoftonline.com/common/login";
            String loginEndPoint = "https://login.microsoftonline.com/" + tenantId + "/login";
            LOGGER.debug("loginEndPoint is " + loginEndPoint);
            NameValuePair userIdNVP = new BasicNameValuePair("login", username);
            loginParameters.add(userIdNVP);
            NameValuePair passwordNVP = new BasicNameValuePair("passwd", password);
            loginParameters.add(passwordNVP);
            NameValuePair dssoTokenNVP = new BasicNameValuePair("dssoToken", "");
            loginParameters.add(dssoTokenNVP);
            CommonHttpResponse loginCommonHttpResponse = httpManager.doCommonHttpResponsePost(loginEndPoint, null, loginParameters, null, null);
            LOGGER.debug("response code is " + loginCommonHttpResponse.getResponseCode());
            /* Logged in, now grant access */
            //String grantEndPoint = "https://login.microsoftonline.com/common/Consent/Grant";
            String grantEndPoint = "https://login.microsoftonline.com/" + tenantId + "/Consent/Grant";
            LOGGER.debug("grantEndPoint is " + grantEndPoint);
            List<NameValuePair> grantParameters = new ArrayList<NameValuePair>();
            Document grantDocument = Jsoup.parse(loginCommonHttpResponse.getResponseEntityString());
            Elements grantInputs = grantDocument.getElementsByTag("input");
            ArrayList<String> parameterNamesArrayList = new ArrayList<String>();
            for (Element input : grantInputs) {
                String name = input.attr("name");
                LOGGER.debug("name is " + name);
                String value = input.attr("value");
                LOGGER.debug("value is " + value);
                if (name.equals("ctx")) {
                    if (!parameterNamesArrayList.contains("ctx")) {
                        NameValuePair ctxNVP = new BasicNameValuePair("ctx", value);
                        grantParameters.add(ctxNVP);
                        parameterNamesArrayList.add("ctx");
                        LOGGER.debug("added ctx parameter");
                    } else {
                        LOGGER.debug("ctx parameter already added");
                    }
                    
                    continue;
                }
                
                if (name.equals("flowToken")) {
                    if (!parameterNamesArrayList.contains("flowToken")) {
                        NameValuePair flowTokenNVP = new BasicNameValuePair("flowToken", value);
                        grantParameters.add(flowTokenNVP);
                        parameterNamesArrayList.add("flowToken");
                        LOGGER.debug("added flowToken parameter");
                    } else {
                        LOGGER.debug("flowToken parameter already added");
                    }
                    
                    continue;
                }
                
                if (name.equals("canary")) {
                    if (!parameterNamesArrayList.contains("canary")) {
                        NameValuePair canaryNVP = new BasicNameValuePair("canary", value);
                        grantParameters.add(canaryNVP);
                        parameterNamesArrayList.add("canary");
                        LOGGER.debug("added canary parameter");
                    } else {
                        LOGGER.debug("canary parameter already added");
                    }
                    
                    continue;
                }
            }
            
            CommonHttpResponse grantCommonHttpResponse = httpManager.doCommonHttpResponsePost(grantEndPoint, null, grantParameters, null, null);
            LOGGER.debug("grant response code is " + grantCommonHttpResponse.getResponseCode());            
            return null;
        } catch (Exception e) {
            LOGGER.warn("exception getting authorization code: " + e.getMessage());
        }

        return null;
    }     
}
