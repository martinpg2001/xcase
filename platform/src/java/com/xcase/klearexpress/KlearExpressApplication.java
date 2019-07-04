package com.xcase.klearexpress;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.xcase.klearexpress.constant.*;
import com.xcase.klearexpress.factories.KlearExpressRequestFactory;
import com.xcase.klearexpress.impl.simple.core.KlearExpressConfigurationManager;
import com.xcase.klearexpress.transputs.GetAccessTokenRequest;
import com.xcase.klearexpress.transputs.GetAccessTokenResponse;
import com.xcase.klearexpress.transputs.SendMessageRequest;
import com.xcase.klearexpress.transputs.SendMessageResponse;
import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class KlearExpressApplication {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    public static void main(String[] args) {
        try {
            LOGGER.debug("starting main()");
            KlearExpressExternalAPI klearExpressExternalAPI = new SimpleKlearExpressImpl();
            String apiEventsURL = KlearExpressConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KlearExpressConstant.LOCAL_API_URL);
            LOGGER.debug("apiEventsURL is " + apiEventsURL);
            String userEmail = KlearExpressConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KlearExpressConstant.LOCAL_USER_EMAIL);
            LOGGER.debug("userEmail is " + userEmail);
            String userPassword = KlearExpressConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KlearExpressConstant.LOCAL_USER_PASSWORD);
            LOGGER.debug("userPassword is " + userPassword);
            /* First, get access token */
            String accessToken = null;
            GetAccessTokenRequest getAccessTokenRequest = KlearExpressRequestFactory.createGetAccessTokenRequest();
            getAccessTokenRequest.setAPIUrl(apiEventsURL);
            getAccessTokenRequest.setEntityRequest("{\n" + 
                    "\"eventMessage\": {\n" + 
                    "\"email\" : \"" + userEmail + "\",\n" + 
                    "\"hashedPassword\": \"" + userPassword + "\",\n" + 
                    "\"typeOfUser\" : \"CUSTOMER_USER\"\n" + 
                    "},\n" + 
                    "\"eventType\": \"KXUSER_LOGIN\",\n" + 
                    "\"eventTime\": 1234\n" + 
                    "}\n" + 
                    "");
            GetAccessTokenResponse getAccessTokenResponse = klearExpressExternalAPI.getAccessToken(getAccessTokenRequest);   
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
                KlearExpressConfigurationManager.getConfigurationManager().getLocalConfig().setProperty(KlearExpressConstant.LOCAL_ACCESS_TOKEN, accessToken);
                KlearExpressConfigurationManager.getConfigurationManager().storeLocalConfigProperties();
                LOGGER.debug("stored local config properties");
            } else {
                JsonElement errorElement = eventMessageJsonObject.get("error");
                JsonElement errorDescriptionElement = eventMessageJsonObject.get("error_description");
                LOGGER.debug("error description is " + errorDescriptionElement.getAsString());
            }
            
            /* Get vessel information */
            SendMessageRequest sendMessageRequest = KlearExpressRequestFactory.createSendMessageRequest(accessToken);
            sendMessageRequest.setAPIUrl(apiEventsURL);
            sendMessageRequest.setMessage(" {\n" + 
                    "\"eventMessage\": {\n" + 
                    "\"carrierId\": \"36\",\n" + 
                    "\"carrierType\": \"VESSEL\"\n" + 
                    "},\n" + 
                    "\"eventType\": \"GET_VESSEL_DTL\",\n" + 
                    "\"eventTime\": 1554999583589\n" + 
                    "}");
            SendMessageResponse sendMessageResponse = klearExpressExternalAPI.sendMessage(sendMessageRequest);   
        } catch (Exception e) {
            LOGGER.warn("exception invoking events API: " + e.getMessage());
        }
    }
}
