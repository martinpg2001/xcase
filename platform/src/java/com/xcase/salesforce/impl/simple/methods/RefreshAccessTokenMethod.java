/**
 * Copyright 2016 Xcase All rights reserved.
 */
package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.*;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceResponseFactory;
import com.xcase.salesforce.impl.simple.core.SalesforceConfigurationManager;
import com.xcase.salesforce.objects.SalesforceException;
import com.xcase.salesforce.transputs.RefreshAccessTokenRequest;
import com.xcase.salesforce.transputs.RefreshAccessTokenResponse;
import java.io.IOException;
import java.lang.invoke.*;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.http.message.BasicNameValuePair;
import org.apache.logging.log4j.*;

/**
 *
 * @author martin
 */
public class RefreshAccessTokenMethod extends BaseSalesforceMethod {

    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    /**
     *
     * @param request
     * @return response
     * @throws IOException
     * @throws SalesforceException
     */
    public RefreshAccessTokenResponse refreshAccessToken(RefreshAccessTokenRequest request)
            throws IOException, SalesforceException {
        LOGGER.debug("starting refreshAccessToken()");
        RefreshAccessTokenResponse response = SalesforceResponseFactory.createRefreshAccessTokenResponse();
        LOGGER.debug("created response");
        try {
            String clientId = request.getClientId();
            LOGGER.debug("clientId is " + clientId);
            String clientSecret = request.getClientSecret();
            LOGGER.debug("clientSecret is " + clientSecret);
            String refreshToken = request.getRefreshToken();
            LOGGER.debug("refreshToken is " + refreshToken);
            String endPoint = super.xmlOAuthTokenUrl;
            LOGGER.debug("endPoint is " + endPoint);
            Header[] headers = { };
            LOGGER.debug(
                    "SalesforceConstant.PARAM_NAME_GRANT_TYPE name is " + SalesforceConstant.PARAM_NAME_GRANT_TYPE);
            LOGGER.debug("SalesforceConstant.PARAM_NAME_CLIENT_ID name is " + SalesforceConstant.PARAM_NAME_CLIENT_ID);
            LOGGER.debug("SalesforceConstant.PARAM_NAME_CLIENT_ID value is " + clientId);
            LOGGER.debug("SalesforceConstant.PARAM_NAME_CLIENT_SECRET name is "
                    + SalesforceConstant.PARAM_NAME_CLIENT_SECRET);
            LOGGER.debug("SalesforceConstant.PARAM_NAME_CLIENT_SECRET value is " + clientSecret);
            LOGGER.debug("SalesforceConstant.PARAM_NAME_REFRESH_TOKEN name is "
                    + SalesforceConstant.PARAM_NAME_REFRESH_TOKEN);
            LOGGER.debug("SalesforceConstant.PARAM_NAME_REFRESH_TOKEN value is " + refreshToken);
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            parameters.add(new BasicNameValuePair(SalesforceConstant.PARAM_NAME_GRANT_TYPE,
                    SalesforceConstant.PARAM_NAME_REFRESH_TOKEN));
            parameters.add(new BasicNameValuePair(SalesforceConstant.PARAM_NAME_CLIENT_ID, clientId));
            parameters.add(new BasicNameValuePair(SalesforceConstant.PARAM_NAME_CLIENT_SECRET, clientSecret));
            parameters.add(new BasicNameValuePair(SalesforceConstant.PARAM_NAME_REFRESH_TOKEN, refreshToken));
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponsePost(endPoint, headers, parameters, null, 
                    null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                handleExpectedResponseCode(response, commonHttpResponse);
                JsonElement jsonElement = response.getJsonElement();
                if (!jsonElement.isJsonNull()) {
                    LOGGER.debug("jsonElement is " + jsonElement.toString());
                }
            } else {
                handleUnexpectedResponseCode(response, commonHttpResponse);
            }
        } catch (Exception e) {
            handleUnexpectedException(response, e);
        }

        return response;
    }
}
