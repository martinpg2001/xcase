package com.xcase.slack.impl.simple.methods;

import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.klearnow.factories.KlearNowResponseFactory;
import com.xcase.slack.factories.SlackResponseFactory;
import com.xcase.slack.transputs.GetAccessTokenRequest;
import com.xcase.slack.transputs.GetAccessTokenResponse;

import java.lang.invoke.MethodHandles;

import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class GetAccessTokenMethod extends BaseSlackMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public GetAccessTokenResponse getAccessToken(GetAccessTokenRequest request) {
        LOGGER.debug("starting getAccessToken()");
        try {
            GetAccessTokenResponse response = SlackResponseFactory.createGetAccessTokenResponse();
            /* TODO: investigate generating access token
            String endPoint = request.getAPIUrl() + "login";
            LOGGER.debug("endPoint is " + endPoint);
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {contentTypeHeader};
            String entityMessage = request.getEntityRequest();
            LOGGER.debug("entityMessage is " + entityMessage);
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponsePost(endPoint, headers, null, entityMessage, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                processExpectedResponseCode(response, commonHttpResponse);
            } else {
                LOGGER.warn("unexpected response code " + responseCode);
                processUnexpectedResponseCode(response, commonHttpResponse);
            }
            */

            return response;
        } catch (Exception e) {
            LOGGER.warn("exception sending message: " + e.getMessage());
        }

        return null;
    }

}
