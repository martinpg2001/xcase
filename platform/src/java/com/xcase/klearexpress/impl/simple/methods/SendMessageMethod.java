package com.xcase.klearexpress.impl.simple.methods;

import org.apache.http.Header;

import com.xcase.common.constant.CommonConstant;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.klearexpress.factories.KlearExpressResponseFactory;
import com.xcase.klearexpress.transputs.SendMessageRequest;
import com.xcase.klearexpress.transputs.SendMessageResponse;

public class SendMessageMethod extends BaseKlearExpressMethod {

    public SendMessageResponse sendMessage(SendMessageRequest request) {
        LOGGER.debug("starting sendMessage()");
        try {
            SendMessageResponse response = KlearExpressResponseFactory.createSendMessageResponse();
            String baseVersionUrl = super.apiUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String endPoint = baseVersionUrl;
            LOGGER.debug("endPoint is " + endPoint);
            String accessToken = request.getAccessToken();
            LOGGER.debug("accessToken is " + accessToken);
            Header accessTokenHeader = createAccessTokenHeader(accessToken);
            LOGGER.debug("created accessTokenHeader");
            Header contentTypeHeader = createContentTypeHeader();
            Header[] headers = {accessTokenHeader, contentTypeHeader};
            String entityMessage = request.getMessage();
            LOGGER.debug("entityMessage is " + entityMessage);
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponsePost(endPoint, headers, null, entityMessage, null);
            int responseCode = commonHttpResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            response.setResponseCode(responseCode);
            if (responseCode == 200) {
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
            } else {
                LOGGER.warn("unexpected response code " + responseCode);
            }

            return response;
        } catch (Exception e) {
            LOGGER.warn("exception sending message: " + e.getMessage());
        }

        return null;
    }

}
