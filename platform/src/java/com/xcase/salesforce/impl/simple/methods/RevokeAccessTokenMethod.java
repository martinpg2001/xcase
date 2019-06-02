package com.xcase.salesforce.impl.simple.methods;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.salesforce.constant.SalesforceConstant;
import com.xcase.salesforce.factories.SalesforceResponseFactory;
import com.xcase.salesforce.impl.simple.core.SalesforceConfigurationManager;
import com.xcase.salesforce.transputs.RevokeAccessTokenRequest;
import com.xcase.salesforce.transputs.RevokeAccessTokenResponse;
import java.lang.invoke.MethodHandles;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RevokeAccessTokenMethod extends BaseSalesforceMethod {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public RevokeAccessTokenResponse revokeAccessToken(RevokeAccessTokenRequest request) {
        LOGGER.debug("starting revokeAccessToken()");
        RevokeAccessTokenResponse response = SalesforceResponseFactory.createRevokeAccessTokenResponse();
        LOGGER.debug("created response");
        try {
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            String endPoint = SalesforceConfigurationManager.getConfigurationManager().getConfig().getProperty(SalesforceConstant.CONFIG_API_OAUTH_REVOKE_PREFIX);
            LOGGER.debug("endPoint is " + endPoint);
            String bearerString = "Bearer " + accessToken;
            LOGGER.debug("bearerString is " + bearerString);
            Header authorizationHeader = new BasicHeader("Authorization", bearerString);
            LOGGER.debug("created Authorization header");
            Header contentTypeHeader = new BasicHeader("Content-Type", "application/x-www-form-urlencoded");
            LOGGER.debug("created Content-Type header");
            Header[] headers = { authorizationHeader, contentTypeHeader };
            List<NameValuePair> parameters = new ArrayList<NameValuePair>();
            String entityString = "token=" + accessToken;
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponsePost(endPoint, headers, parameters, entityString, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                handleExpectedResponseCode(response, commonHttpResponse);
                JsonElement jsonElement = response.getJsonElement();
                if (jsonElement != null && !jsonElement.isJsonNull()) {
                    LOGGER.debug("jsonElement is " + jsonElement.toString());
                } else {
                    LOGGER.debug("jsonElement is null");
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
