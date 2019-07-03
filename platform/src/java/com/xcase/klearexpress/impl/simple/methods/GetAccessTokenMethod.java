package com.xcase.klearexpress.impl.simple.methods;

import java.lang.invoke.MethodHandles;

import org.apache.http.Header;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.klearexpress.factories.KlearExpressResponseFactory;
import com.xcase.klearexpress.transputs.GetAccessTokenRequest;
import com.xcase.klearexpress.transputs.GetAccessTokenResponse;
import com.xcase.klearexpress.transputs.SendMessageResponse;

public class GetAccessTokenMethod extends BaseKlearExpressMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public GetAccessTokenResponse getAccessToken(GetAccessTokenRequest request) {
        LOGGER.debug("starting getAccessToken()");
        try {
            GetAccessTokenResponse response = KlearExpressResponseFactory.createGetAccessTokenResponse();
            String baseVersionUrl = super.apiUrl;
            LOGGER.debug("baseVersionUrl is " + baseVersionUrl);
            String endPoint = baseVersionUrl;
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
                String responseEntityString = commonHttpResponse.getResponseEntityString();
                LOGGER.debug("responseEntityString is " + responseEntityString);
                JsonElement responseEntityJsonElement = ConverterUtils.parseStringToJson(commonHttpResponse.getResponseEntityString());
                if (!responseEntityJsonElement.isJsonNull()) {
                    LOGGER.debug("responseEntityJsonElement is not null");
                    JsonObject responseEntityJsonObject = (JsonObject) responseEntityJsonElement;
                    LOGGER.debug("got responseEntityJsonObject");
                    JsonPrimitive eventIdJsonPrimitive = responseEntityJsonObject.getAsJsonPrimitive("eventId");
                    response.setEventId(eventIdJsonPrimitive.getAsString());
                    JsonPrimitive eventTypeJsonPrimitive = responseEntityJsonObject.getAsJsonPrimitive("eventType");
                    response.setEventType(eventTypeJsonPrimitive.getAsString());
                    JsonObject eventMessageJsonObject = responseEntityJsonObject.getAsJsonObject("eventMessage");
                    LOGGER.debug("got eventMessageJsonObject");
                    response.setEventMessage(eventMessageJsonObject);
                } else {
                    LOGGER.debug("responseEntityJsonElement is JsonNull");
                }
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
