package com.xcase.klearexpress.impl.simple.methods;

import java.lang.invoke.MethodHandles;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.klearexpress.transputs.KlearExpressResponse;

public class BaseKlearExpressMethod {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    /**
     * core http manager.
     */
    protected CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
    
    public Header createContentTypeHeader() {
        return new BasicHeader("Content-Type", "application/json");
    }

    public Header createAccessTokenHeader(String accessToken) {
        return new BasicHeader("kxToken", accessToken);
    }
    
    public void processExpectedResponseCode(KlearExpressResponse response, CommonHttpResponse commonHttpResponse) {
        /* Store generic REST response data */
        String responseEntityString = commonHttpResponse.getResponseEntityString();
        LOGGER.debug("responseEntityString is " + responseEntityString);
        response.setEntityString(responseEntityString);
        LOGGER.debug("responseCode is " + commonHttpResponse.getResponseCode());
        response.setResponseCode(commonHttpResponse.getResponseCode());
        LOGGER.debug("statusLine is " + commonHttpResponse.getStatusLine());
        response.setStatusLine(commonHttpResponse.getStatusLine());
        /* Now parse for KlearExpress response data */
        JsonElement responseEntityJsonElement = ConverterUtils.parseStringToJson(responseEntityString);
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
        
    }
    
    public void processUnexpectedResponseCode(KlearExpressResponse response, CommonHttpResponse commonHttpResponse) {
        /* Store generic REST response data */
        String responseEntityString = commonHttpResponse.getResponseEntityString();
        LOGGER.debug("responseEntityString is " + responseEntityString);
        response.setEntityString(responseEntityString);
        LOGGER.debug("responseCode is " + commonHttpResponse.getResponseCode());
        response.setResponseCode(commonHttpResponse.getResponseCode());
        LOGGER.debug("statusLine is " + commonHttpResponse.getStatusLine());
        response.setStatusLine(commonHttpResponse.getStatusLine());
    }
}
