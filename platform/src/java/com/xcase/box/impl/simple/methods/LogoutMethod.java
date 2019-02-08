/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.box.impl.simple.methods;

import com.xcase.box.constant.BoxConstant;
import com.xcase.box.factories.BoxResponseFactory;
import com.xcase.box.objects.BoxException;
import com.xcase.box.transputs.LogoutRequest;
import com.xcase.box.transputs.LogoutResponse;
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
public class LogoutMethod extends BaseBoxMethod {

    /**
     * log4j object.
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
     * @throws BoxException box exception
     */
    public LogoutResponse logout(LogoutRequest logoutRequest) throws IOException, BoxException {
        LOGGER.debug("starting logout()");
        LogoutResponse baseBoxResponse = BoxResponseFactory.createLogoutResponse();
        String accessToken = logoutRequest.getAccessToken();
        LOGGER.debug("accessToken is " + accessToken);
        String clientId = logoutRequest.getClientId();
        LOGGER.debug("clientId is " + clientId);
        String clientSecret = logoutRequest.getClientSecret();
        LOGGER.debug("clientSecret is " + clientSecret);
        if (BoxConstant.CONFIG_API_REQUEST_FORMAT_REST.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is " + BoxConstant.CONFIG_API_REQUEST_FORMAT_REST);
            String apiOAuthRevokeUrl = super.apiOAuthRevokePrefix;
            LOGGER.debug("apiOAuthRevokeUrl is " + apiOAuthRevokeUrl);
            HttpPost postMethod = new HttpPost(apiOAuthRevokeUrl);
            LOGGER.debug("created postMethod with URL " + apiOAuthRevokeUrl);
            List<NameValuePair> data = new ArrayList<NameValuePair>();
            data.add(new BasicNameValuePair("token", accessToken));
            data.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_CLIENT_ID, clientId));
            data.add(new BasicNameValuePair(BoxConstant.PARAM_NAME_CLIENT_SECRET, clientSecret));
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
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(baseBoxResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_XML.equals(apiRequestFormat)) {
            Document document = DocumentHelper.createDocument();
            Element requestElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_REQUEST);
            document.add(requestElm);
            Element actionElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_ACTION);
            Element apiKeyElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_API_KEY);
            Element authTokenElm = DocumentHelper.createElement(BoxConstant.PARAM_NAME_AUTH_TOKEN);
            requestElm.add(actionElm);
            requestElm.add(apiKeyElm);
            requestElm.add(authTokenElm);
            actionElm.setText(BoxConstant.ACTION_NAME_LOGOUT);
            try {
                String result = httpManager.doStringPost(xmlApiUrl, null, null, document.asXML(), null);
                Document doc = DocumentHelper.parseText(result);
                Element responseElm = doc.getRootElement();
                Element statusElm = responseElm.element(BoxConstant.PARAM_NAME_STATUS);
                String status = statusElm.getText();
                baseBoxResponse.setStatus(status);
            } catch (Exception e) {
                BoxException be = new BoxException("failed to parse to a document.", e);
                be.setStatus(baseBoxResponse.getStatus());
                throw be;
            }
        } else if (BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP.equals(apiRequestFormat)) {
            LOGGER.debug("apiRequestFormat is BoxConstant.CONFIG_API_REQUEST_FORMAT_SOAP");
        } else {
            LOGGER.debug("apiRequestFormat is unrecognized");
        }

        return baseBoxResponse;

    }
}
