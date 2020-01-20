package com.xcase.klearnow;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.xcase.klearnow.constant.*;
import com.xcase.klearnow.factories.KlearNowRequestFactory;
import com.xcase.klearnow.impl.simple.core.KlearNowConfigurationManager;
import com.xcase.klearnow.transputs.GetAccessTokenRequest;
import com.xcase.klearnow.transputs.GetAccessTokenResponse;
import com.xcase.klearnow.transputs.SendMessageRequest;
import com.xcase.klearnow.transputs.SendMessageResponse;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KlearNowApplication {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        try {
            LOGGER.debug("starting main()");
            KlearNowExternalAPI klearNowExternalAPI = new SimpleKlearNowImpl();
            String apiEventsURL = KlearNowConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KlearNowConstant.LOCAL_API_URL);
            LOGGER.debug("apiEventsURL is " + apiEventsURL);
            String userEmail = KlearNowConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KlearNowConstant.LOCAL_USER_EMAIL);
            LOGGER.debug("userEmail is " + userEmail);
            String userPassword = KlearNowConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KlearNowConstant.LOCAL_USER_PASSWORD);
            LOGGER.debug("userPassword is " + userPassword);
            /* First, get access token */
            String accessToken = null;
            GetAccessTokenRequest getAccessTokenRequest = KlearNowRequestFactory.createGetAccessTokenRequest();
            getAccessTokenRequest.setAPIUrl(apiEventsURL);
            getAccessTokenRequest.setEntityRequest("{\n" +
                                                   "  \"email\" : \"" + userEmail + "\",\n" +
                                                   "  \"password\": \"" + userPassword + "\",\n" +
                                                   "}");
            GetAccessTokenResponse getAccessTokenResponse = klearNowExternalAPI.getAccessToken(getAccessTokenRequest);
            JsonObject eventMessageJsonObject = getAccessTokenResponse.getEventMessage();
            LOGGER.debug("got eventMessageJsonObject");
            JsonObject userJsonObject = eventMessageJsonObject.getAsJsonObject("user");
            LOGGER.debug("got userJsonObject");
            JsonPrimitive kxTokenJsonPrimitive = userJsonObject.getAsJsonPrimitive("kxToken");
            LOGGER.debug("kxTokenJsonPrimitive is " + kxTokenJsonPrimitive);
            if (kxTokenJsonPrimitive != null && !kxTokenJsonPrimitive.isJsonNull()) {
                LOGGER.debug("kxTokenJsonPrimitive is not null");
                accessToken = kxTokenJsonPrimitive.getAsString();
                LOGGER.debug("accessToken is " + accessToken);
                KlearNowConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(KlearNowConstant.LOCAL_ACCESS_TOKEN, accessToken);
                KlearNowConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
                LOGGER.debug("stored local config properties");
            } else {
                JsonElement errorElement = eventMessageJsonObject.get("error");
                JsonElement errorDescriptionElement = eventMessageJsonObject.get("error_description");
                LOGGER.debug("error description is " + errorDescriptionElement.getAsString());
            }

            /* Create shipment */
            SendMessageRequest sendMessageRequest = KlearNowRequestFactory.createSendMessageRequest(accessToken);
            sendMessageRequest.setAPIUrl(apiEventsURL);
            sendMessageRequest.setMessage(" {\n" +
                    "\"eventMessage\": {\n" +
                    "\"carrierId\": \"36\",\n" +
                    "\"carrierType\": \"VESSEL\"\n" +
                    "},\n" +
                    "\"eventType\": \"GET_VESSEL_DTL\",\n" +
                    "\"eventTime\": 1554999583589\n" +
                    "}");
            SendMessageResponse sendMessageResponse = klearNowExternalAPI.sendMessage(sendMessageRequest);
        } catch (Exception e) {
            LOGGER.warn("exception invoking events API: " + e.getMessage());
        }
    }
}
