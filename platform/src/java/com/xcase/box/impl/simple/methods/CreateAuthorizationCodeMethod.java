/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.CreateAuthorizationCodeRequest;
import com.xcase.box.transputs.CreateAuthorizationCodeResponse;
import com.xcase.common.constant.CommonConstant;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.*;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.*;

/**
 * @author martin
 *
 */
public class CreateAuthorizationCodeMethod extends BaseBoxMethod {

    /**
     * Fixed URL to Box OAuth authentication
     */
    private static String BoxOAuth2AuthorizeURL = "https://app.box.com/api/oauth2/authorize";

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    private static String extractCode(String location) {
        int icPosition = location.indexOf("code");
        String code = location.substring(icPosition + 5, icPosition + 37);
        LOGGER.debug("code is " + code);
        return code;
    }

    private static String extractICValue(String responseString) {
        int icPosition = responseString.indexOf("name=\"ic\"");
        String icValue = responseString.substring(icPosition + 17, icPosition + 81);
        LOGGER.debug("icValue is " + icValue);
        return icValue;
    }

    private static String extractRequestToken(String responseString) {
        int requestTokenPosition = responseString.indexOf("var request_token = '");
        String requestTokenValue = responseString.substring(requestTokenPosition + 21, requestTokenPosition + 85);
        LOGGER.debug("requestTokenValue is " + requestTokenValue);
        return requestTokenValue;
    }

    /**
     *
     * @param createAuthorizationCodeRequest request
     * @return response
     * @throws IOException IO exception
     * @throws BoxException box exception
     */
    public CreateAuthorizationCodeResponse createAuthorizationCode(CreateAuthorizationCodeRequest createAuthorizationCodeRequest) throws IOException, BoxException {
        LOGGER.debug("starting createAuthorizationCode()");
        CreateAuthorizationCodeResponse createAuthorizationCodeResponse = BoxResponseFactory.createCreateAuthorizationCodeResponse();
        String clientId = createAuthorizationCodeRequest.getApiKey();
        LOGGER.debug("clientId is " + clientId);
        String userName = createAuthorizationCodeRequest.getUsername();
        LOGGER.debug("userName is " + userName);
        String password = createAuthorizationCodeRequest.getPassword();
        LOGGER.debug("password is " + password);
        String redirectURI = createAuthorizationCodeRequest.getRedirectURI();
        LOGGER.debug("redirectURI is " + redirectURI);
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_REST");
            /*
             * This method simulates three browser requests: firstPost, loginPost, grantPost
             * The firstPost simulates pointing a browser to the Box authorization URL. The response is the
             * login page.
             * The loginPost simulates entering your username and password and clicking login. The response is the Grant page.
             * The grantPost simulates clicking Grant. The response is the redirect which conatins the authorization code.
             */
            try {
                /* Start first post */
                StringBuffer urlBuff = new StringBuffer(BoxOAuth2AuthorizeURL);
                LOGGER.debug("urlBuff is " + urlBuff.toString());
                String urlString = urlBuff.toString();
                LOGGER.debug("urlString is " + urlString);
                List<NameValuePair> firstParametersBody = new ArrayList<NameValuePair>();
                firstParametersBody.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_RESPONSE_TYPE, BoxConstant.LOCAL_OAUTH2_CODE));
                firstParametersBody.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_CLIENT_ID, clientId));
                firstParametersBody.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_REDIRECT_URI, redirectURI));
                firstParametersBody.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_SCOPE, "root_readwrite admin_on_behalf_of manage_enterprise"));
                firstParametersBody.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_STATE, "authenticated"));
                String firstResponseEntityString = httpManager.doStringPost(urlString, null, firstParametersBody, null, null);
                LOGGER.debug("*** Start First Response ***");
                LOGGER.debug(firstResponseEntityString);
                LOGGER.debug("*** Finish First Response ***");
                /* Finish first post */
 /* Start login post */
                List<NameValuePair> loginParametersBody = new ArrayList<NameValuePair>();
                loginParametersBody.add(new BasicNameValuePair("login", userName));
                loginParametersBody.add(new BasicNameValuePair("password", password));
                loginParametersBody.add(new BasicNameValuePair("login_submit", "Authorize"));
                loginParametersBody.add(new BasicNameValuePair("dologin", "1"));
                loginParametersBody.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_CLIENT_ID, clientId));
                loginParametersBody.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_RESPONSE_TYPE, BoxConstant.LOCAL_OAUTH2_CODE));
                loginParametersBody.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_REDIRECT_URI, redirectURI));
                loginParametersBody.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_SCOPE, "root_readwrite admin_on_behalf_of manage_enterprise"));
                loginParametersBody.add(new BasicNameValuePair("folder_id", ""));
                loginParametersBody.add(new BasicNameValuePair("file_id", ""));
                loginParametersBody.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_STATE, "authenticated"));
                loginParametersBody.add(new BasicNameValuePair("reg_step", ""));
                loginParametersBody.add(new BasicNameValuePair("submit1", "1"));
                loginParametersBody.add(new BasicNameValuePair("folder", ""));
                loginParametersBody.add(new BasicNameValuePair("login_or_register_mode", "login"));
                loginParametersBody.add(new BasicNameValuePair("new_login_or_register_mode", ""));
                loginParametersBody.add(new BasicNameValuePair("__login", "1"));
                loginParametersBody.add(new BasicNameValuePair("_redirect_url", "/api/oauth2/authorize?response_type=code&state=authenticated&client_id=" + clientId + "&redirect_uri=" + redirectURI));
                loginParametersBody.add(new BasicNameValuePair("_pw_sql", ""));
                LOGGER.debug("about to login");
                String loginResponseEntityString = httpManager.doStringPost(urlString, null, loginParametersBody, null, null);
                LOGGER.debug("*** Start Login Response ***");
                LOGGER.debug(loginResponseEntityString);
                LOGGER.debug("*** Finish Login Response ***");
                String icString = extractICValue(loginResponseEntityString);
                LOGGER.debug("icString is " + icString);
                String requestTokenString = extractRequestToken(loginResponseEntityString);
                LOGGER.debug("requestTokenString is " + requestTokenString);
                /* Finish login post */
                /* Start grant post */
                List<NameValuePair> grantParametersBody = new ArrayList<NameValuePair>();
                grantParametersBody.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_CLIENT_ID, clientId));
                grantParametersBody.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_RESPONSE_TYPE, BoxConstant.LOCAL_OAUTH2_CODE));
                grantParametersBody.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_REDIRECT_URI, redirectURI));
                grantParametersBody.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_SCOPE, "root_readwrite admin_on_behalf_of manage_enterprise"));
                grantParametersBody.add(new BasicNameValuePair("folder_id", ""));
                grantParametersBody.add(new BasicNameValuePair("file_id", ""));
                grantParametersBody.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_STATE, "authenticated"));
                grantParametersBody.add(new BasicNameValuePair("doconsent", "doconsent"));
                grantParametersBody.add(new BasicNameValuePair("ic", icString));
                grantParametersBody.add(new BasicNameValuePair("consent_accept", "Grant access to Box"));
                grantParametersBody.add(new BasicNameValuePair("request_token", requestTokenString));
                LOGGER.debug("about to grant access");
                HttpResponse grantResponse = httpManager.doHttpResponsePost(urlString, null, grantParametersBody, null);
                int grantCode = grantResponse.getStatusLine().getStatusCode();
                HttpEntity grantResponseEntity = grantResponse.getEntity();
                LOGGER.debug("*** Start Grant Response ***");
                String grantResponseEntityString = EntityUtils.toString(grantResponseEntity);
                LOGGER.debug(grantResponseEntityString);
                LOGGER.debug("*** Finish Grant Response ***");
                Header[] grantHeaderArray = grantResponse.getAllHeaders();
                String code = null;
                String location = null;
                if (CommonConstant.HTTP_SEND_REDIRECT == grantCode) {
                    for (int i = 0; i < grantHeaderArray.length; i++) {
                        Header grantHeader = grantHeaderArray[i];
                        LOGGER.debug("grantHeader is " + grantHeader.getName() + ":" + grantHeader.getValue());
                        if (grantHeader.getName().equals(BoxConstant.HEADER_NAME_LOCATION)) {
                            location = grantHeader.getValue();
                            LOGGER.debug("location is " + location);
                            code = extractCode(location);
                        }
                    }

                    if (location == null) {
                        LOGGER.warn("The endpoint did not pass in valid location header for redirect");
                        throw new RuntimeException("The endpoint did not pass in valid location header for redirect");
                    }
                } else {
                    LOGGER.warn("Was expecting code 302 from endpoint to indicate redirect. Received httpCode " + grantCode);
                    throw new RuntimeException("Was expecting code 302 from endpoint to indicate redirect. Received httpCode " + grantCode);
                }
                /* Finish grant post */
                LOGGER.debug("code is " + code);
                createAuthorizationCodeResponse.setAuthorizationCode(code);
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
        return createAuthorizationCodeResponse;
    }
}
