package com.xcase.klearnow.impl.simple.methods;

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
import com.xcase.klearnow.transputs.KlearNowResponse;

public class BaseKlearNowMethod {
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

    public void processExpectedResponseCode(KlearNowResponse response, CommonHttpResponse commonHttpResponse) {
        /* Store generic REST response data */
        String responseEntityString = commonHttpResponse.getResponseEntityString();
        LOGGER.debug("responseEntityString is " + responseEntityString);
        response.setEntityString(responseEntityString);
        LOGGER.debug("responseCode is " + commonHttpResponse.getResponseCode());
        response.setResponseCode(commonHttpResponse.getResponseCode());
        LOGGER.debug("statusLine is " + commonHttpResponse.getStatusLine());
        response.setStatusLine(commonHttpResponse.getStatusLine());
        /* Now parse for KlearNow response data */
        JsonElement responseEntityJsonElement = ConverterUtils.parseStringToJson(responseEntityString);
        if (!responseEntityJsonElement.isJsonNull()) {
            LOGGER.debug("responseEntityJsonElement is not null");
            JsonObject responseEntityJsonObject = (JsonObject) responseEntityJsonElement;
            LOGGER.debug("got responseEntityJsonObject");
            response.setEventMessage(responseEntityJsonObject);
        } else {
            LOGGER.debug("responseEntityJsonElement is JsonNull");
        }

    }

    public void processUnexpectedResponseCode(KlearNowResponse response, CommonHttpResponse commonHttpResponse) {
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
