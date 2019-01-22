/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.GetAuthTokenRequest;
import com.xcase.box.transputs.GetAuthTokenResponse;
import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.*;
import org.apache.http.Header;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.*;

/**
 * @author martin
 *
 */
public class GetAuthTokenMethod extends BaseBoxMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     */
    public GetAuthTokenMethod() {
        super();
    }

    /**
     *
     * @param getAuthTokenRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public GetAuthTokenResponse getAuthToken(GetAuthTokenRequest getAuthTokenRequest) throws IOException, BoxException {
        LOGGER.debug("starting getAuthToken()");
        GetAuthTokenResponse getAuthTokenResponse = BoxResponseFactory.createGetAuthTokenResponse();
        String apiKey = getAuthTokenRequest.getApiKey();
        LOGGER.debug("apiKey is " + apiKey);
        String userName = getAuthTokenRequest.getUsername();
        LOGGER.debug("userName is " + userName);
        String password = getAuthTokenRequest.getPassword();
        LOGGER.debug("password is " + password);
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_REST");
            StringBuffer urlBuff = new StringBuffer("https://www.box.com/api/oauth2/authorize?response_type=code&state=authenticated");
            LOGGER.debug("urlBuff is " + urlBuff.toString());
            urlBuff.append(CommonConstant.AND_SIGN_STRING);
            urlBuff.append(BoxConstant.PARAM_NAME_CLIENT_ID);
            urlBuff.append(CommonConstant.EQUALS_SIGN_STRING);
            urlBuff.append(apiKey);
            try {
                String urlString = urlBuff.toString();
                LOGGER.debug("urlString is " + urlString);
                CommonHTTPManager commonHTTPManager = CommonHTTPManager.refreshCommonHTTPManager();
                String responseEntityString = commonHTTPManager.doStringGet(urlString, null, null);
                LOGGER.debug("responseEntityString is " + responseEntityString);
                String requestToken = getRequestToken(responseEntityString);
                LOGGER.debug("requestToken is " + requestToken);
                List<NameValuePair> loginParameters = new ArrayList<NameValuePair>();
                StringBuffer redirectUrlStringBuffer = new StringBuffer("/api/oauth2/authorize?response_type=code&state=authenticated");
                LOGGER.debug("redirectUrlStringBuffer is " + redirectUrlStringBuffer.toString());
                redirectUrlStringBuffer.append(CommonConstant.AND_SIGN_STRING);
                redirectUrlStringBuffer.append(BoxConstant.PARAM_NAME_CLIENT_ID);
                redirectUrlStringBuffer.append(CommonConstant.EQUALS_SIGN_STRING);
                redirectUrlStringBuffer.append(apiKey);
                String redirectUrlString = redirectUrlStringBuffer.toString();
                LOGGER.debug("redirectUrlString is " + redirectUrlString);
                NameValuePair redirectUrlNVP = new BasicNameValuePair("_redirect_url", redirectUrlString);
                loginParameters.add(redirectUrlNVP);
                NameValuePair dologinNVP = new BasicNameValuePair("dologin", "1");
                loginParameters.add(dologinNVP);
                NameValuePair clientIDNVP = new BasicNameValuePair("client_id", apiKey);
                loginParameters.add(clientIDNVP);
                NameValuePair responseTypeNVP = new BasicNameValuePair("response_type", "code");
                loginParameters.add(responseTypeNVP);
                NameValuePair redirectURINVP = new BasicNameValuePair("redirect_uri", "https://localhost");
                loginParameters.add(redirectURINVP);
                NameValuePair scopeNVP = new BasicNameValuePair("scope", "root_readwrite admin_on_behalf_of");
                loginParameters.add(scopeNVP);
                NameValuePair folderNVP = new BasicNameValuePair("folder", "");
                loginParameters.add(folderNVP);
                NameValuePair folderIDNVP = new BasicNameValuePair("folder_id", "");
                loginParameters.add(folderIDNVP);
                NameValuePair stateNVP = new BasicNameValuePair("state", "authenticated");
                loginParameters.add(stateNVP);
                NameValuePair regStepNVP = new BasicNameValuePair("reg_step", "");
                loginParameters.add(regStepNVP);
                NameValuePair submit1NVP = new BasicNameValuePair("submit1", "1");
                loginParameters.add(submit1NVP);
                NameValuePair loginOrRegisterModeNVP = new BasicNameValuePair("login_or_register_mode", "login");
                loginParameters.add(loginOrRegisterModeNVP);
                NameValuePair newLoginOrRegisterModeNVP = new BasicNameValuePair("new_login_or_register_mode", "");
                loginParameters.add(newLoginOrRegisterModeNVP);
                NameValuePair underscoreLoginNVP = new BasicNameValuePair("__login", "1");
                loginParameters.add(underscoreLoginNVP);
                NameValuePair pwSQLNVP = new BasicNameValuePair("_pw_sql", "");
                loginParameters.add(pwSQLNVP);
                NameValuePair rememberLoginNVP = new BasicNameValuePair("remember_login", "on");
                loginParameters.add(rememberLoginNVP);
                NameValuePair loginNVP = new BasicNameValuePair("login", userName);
                loginParameters.add(loginNVP);
                NameValuePair passwordNVP = new BasicNameValuePair("password", password);
                loginParameters.add(passwordNVP);
                NameValuePair requestTokenNVP = new BasicNameValuePair("request_token", requestToken);
                loginParameters.add(requestTokenNVP);
                NameValuePair redirectURLNVP = new BasicNameValuePair("redirect_url", redirectUrlString);
                loginParameters.add(redirectURLNVP);
                NameValuePair skipFrameworkLoginNVP = new BasicNameValuePair("skip_framework_login", "1");
                loginParameters.add(skipFrameworkLoginNVP);
                LOGGER.debug("about to login");
                responseEntityString = commonHTTPManager.doStringPost(urlString, null, loginParameters, null, null);
                LOGGER.debug("responseEntityString is " + responseEntityString);
                List<NameValuePair> allowParameters = new ArrayList<NameValuePair>();
                allowParameters.add(clientIDNVP);
                allowParameters.add(responseTypeNVP);
                allowParameters.add(redirectURINVP);
                allowParameters.add(scopeNVP);
                allowParameters.add(stateNVP);
                allowParameters.add(requestTokenNVP);
                NameValuePair doConsentNVP = new BasicNameValuePair("doconsent", "doconsent");
                allowParameters.add(doConsentNVP);
                String icParameter = getICParameter(responseEntityString);
                LOGGER.debug("icParameter is " + icParameter);
                NameValuePair icNVP = new BasicNameValuePair("ic", icParameter);
                allowParameters.add(icNVP);
                NameValuePair consentAcceptButtonNVP = new BasicNameValuePair("consent_accept", "Accept");
                allowParameters.add(consentAcceptButtonNVP);
                LOGGER.debug("about to accept");
                HttpResponse httpResponse = commonHTTPManager.doHttpResponsePost(urlString, null, allowParameters, null);
                Header[] responseHeaders = httpResponse.getAllHeaders();
                for (Header responseHeader : responseHeaders) {
                    LOGGER.debug("responseHeader is " + responseHeader.getName() + ":" + responseHeader.getValue());
                }

                String authorizationCode = null;
                if (httpResponse.getFirstHeader("Location") != null) {
                    String location = httpResponse.getFirstHeader("Location").getValue();
                    LOGGER.debug("location is " + location);
                    int lastEqualsIndex = location.lastIndexOf("=");
                    authorizationCode = location.substring(lastEqualsIndex + 1, location.length());
                }

                LOGGER.debug("authorizationCode is " + authorizationCode);
                getAuthTokenResponse.setAuthToken(authorizationCode);
            } catch (Exception e) {
                throw new BoxException("failed to parse to a document.", e);
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_XML");
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return getAuthTokenResponse;
    }

    private String getICParameter(String responseEntityString) {
        LOGGER.debug("starting getICParameter()");
        int indexOfNameEqualsICToken = responseEntityString.indexOf("name=\"ic\"");
        LOGGER.debug("indexOfNameEqualsICToken is " + indexOfNameEqualsICToken);
        int indexOfEqualsValue = responseEntityString.indexOf("=", indexOfNameEqualsICToken + 9);
        LOGGER.debug("indexOfEqualsValue is " + indexOfEqualsValue);
        int indexOfOpenQuoteICValue = responseEntityString.indexOf("\"", indexOfEqualsValue + 1);
        LOGGER.debug("indexOfOpenQuoteICValue is " + indexOfOpenQuoteICValue);
        int lengthOfICValue = 64;
        LOGGER.debug("lengthOfICValue is " + lengthOfICValue);
        String ICValue = responseEntityString.substring(indexOfOpenQuoteICValue + 1, indexOfOpenQuoteICValue + 65);
        LOGGER.debug("ICValue is " + ICValue);
        return ICValue;
    }

    private String getRequestToken(String responseEntityString) {
        LOGGER.debug("starting getRequestToken()");
        int indexOfRequestToken = responseEntityString.indexOf("request_token");
        LOGGER.debug("indexOfRequestToken is " + indexOfRequestToken);
        int indexOfOpenQuoteRequestToken = responseEntityString.indexOf("'", indexOfRequestToken + 1);
        LOGGER.debug("indexOfOpenQuoteRequestToken is " + indexOfOpenQuoteRequestToken);
        int indexOfCloseQuoteRequestToken = responseEntityString.indexOf("'", indexOfOpenQuoteRequestToken + 1);
        LOGGER.debug("indexOfCloseQuoteRequestToken is " + indexOfCloseQuoteRequestToken);
        int lengthOfRequestToken = indexOfCloseQuoteRequestToken - indexOfOpenQuoteRequestToken;
        LOGGER.debug("lengthOfRequestToken is " + lengthOfRequestToken);
        String requestToken = responseEntityString.substring(indexOfOpenQuoteRequestToken + 1, indexOfCloseQuoteRequestToken);
        LOGGER.debug("requestToken is " + requestToken);
        return requestToken;
    }
}
