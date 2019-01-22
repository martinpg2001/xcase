/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.sharepoint.impl.simple.methods;

import com.xcase.sharepoint.constant.SharepointConstant;
import com.xcase.sharepoint.factories.SharepointResponseFactory;
import com.xcase.sharepoint.objects.SharepointException;
import com.xcase.sharepoint.transputs.LogoutRequest;
import com.xcase.sharepoint.transputs.LogoutResponse;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.apache.logging.log4j.*;
import org.dom4j.Document;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

/**
 * @author martin
 *
 */
public class LogoutMethod extends BaseSharepointMethod {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     * This method is used to logout a user. On successful logout method will
     * return 'logout_ok' as status. If logout wasn't successful, then status
     * filed can be: 'invalid_auth_token' when auth_token is invalid.
     *
     * @param logoutRequest request
     * @return response
     * @throws IOException IO exception
     * @throws SharepointException box exception
     */
    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException, SharepointException {
        LOGGER.debug("starting logout()");
        LogoutResponse baseBoxResponse = SharepointResponseFactory.createLogoutResponse();
        String apiKey = logoutRequest.getApiKey();
        String accessToken = logoutRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String authToken = logoutRequest.getAuthToken();
        String clientSecret = logoutRequest.getClientSecret();
        LOGGER.debug("clientSecret is " + clientSecret);

        if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + SharepointConstant.CONFIG_API_REQUEST_FORMAT_REST);
            String apiOAuthRevokeUrl = super.apiOAuthRevokePrefix;
            LOGGER.debug("apiOAuthRevokeUrl is " + apiOAuthRevokeUrl);
            HttpPost postMethod = new HttpPost(apiOAuthRevokeUrl);
            LOGGER.debug("created postMethod with URL " + apiOAuthRevokeUrl);
            List<NameValuePair> data = new ArrayList<NameValuePair>();
            data.add(new BasicNameValuePair("token", accessToken));
            data.add(new BasicNameValuePair(SharepointConstant.PARAM_NAME_CLIENT_ID, apiKey));
            data.add(new BasicNameValuePair(SharepointConstant.PARAM_NAME_CLIENT_SECRET, clientSecret));
            try {
                postMethod.setEntity(new UrlEncodedFormEntity(data));
                LOGGER.debug("set request body data");
                HttpResponse httpResponse = httpManager.getHttpClient().execute(postMethod);
                int returnCode = httpResponse.getStatusLine().getStatusCode();
                LOGGER.debug("returnCode is " + returnCode);
                HttpEntity responseEntity = httpResponse.getEntity();
                String responseString = null;
                if (responseEntity != null) {
                    responseString = EntityUtils.toString(responseEntity);
                }

                LOGGER.debug("responseString is " + responseString);
            } catch (Exception e) {
                SharepointException be = new SharepointException("Failed to parse to a document.", e);
                be.setStatus(baseBoxResponse.getStatus());
                throw be;
            }

        } else if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);
            Element actionElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_API_KEY);
            Element authTokenElm = DocumentHelper.createElement(SharepointConstant.PARAM_NAME_AUTH_TOKEN);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(authTokenElm);
            actionElm.setText(SharepointConstant.ACTION_NAME_LOGOUT);
            apiKeyElm.setText(apiKey);
            authTokenElm.setText(authToken);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, document.asXML());
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(SharepointConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                baseBoxResponse.setStatus(status);
            } catch (Exception e) {
                SharepointException be = new SharepointException("failed to parse to a document.", e);
                be.setStatus(baseBoxResponse.getStatus());
                throw be;
            }
        } else if (SharepointConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + SharepointConstant.CONFIG_API_REQUEST_FORMAT_SOAP);
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized " + apiRequestFormat);
        }

        return baseBoxResponse;

    }
}
