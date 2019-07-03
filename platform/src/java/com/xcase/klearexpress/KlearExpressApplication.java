package com.xcase.klearexpress;


import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.xcase.common.impl.simple.core.CommonHTTPManager;
import com.xcase.common.impl.simple.core.CommonHttpResponse;
import com.xcase.common.utils.ConverterUtils;
import com.xcase.klearexpress.constant.*;
import com.xcase.klearexpress.factories.KlearExpressRequestFactory;
import com.xcase.klearexpress.impl.simple.core.KlearExpressConfigurationManager;
import com.xcase.klearexpress.transputs.SendMessageRequest;
import com.xcase.klearexpress.transputs.SendMessageResponse;
import com.xcase.mail.constant.MailConstant;
import com.xcase.mail.factories.MailRequestFactory;
import com.xcase.mail.transputs.SendEmailRequest;
import com.xcase.mail.transputs.SendEmailResponse;

import java.lang.invoke.MethodHandles;
import java.util.ArrayList;

import org.apache.http.Header;
import org.apache.http.message.BasicHeader;
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
            String userEmail = KlearExpressConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KlearExpressConstant.LOCAL_USER_EMAIL);
            LOGGER.debug("userEmail is " + userEmail);
            String userPassword = KlearExpressConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KlearExpressConstant.LOCAL_USER_PASSWORD);
            LOGGER.debug("userPassword is " + userPassword);
            String apiEventsURL = "https://api.klearexpress.com/staging/v1/events";
    		/* First get token */
            String accessToken = null;
    		String entityString = "{\n" + 
    				"\"eventMessage\": {\n" + 
    				"\"email\" : \"" + userEmail + "\",\n" + 
    				"\"hashedPassword\": \"" + userPassword + "\",\n" + 
    				"\"typeOfUser\" : \"CUSTOMER_USER\"\n" + 
    				"},\n" + 
    				"\"eventType\": \"KXUSER_LOGIN\",\n" + 
    				"\"eventTime\": 1234\n" + 
    				"}\n" + 
    				"";
            LOGGER.debug("entityString is " + entityString);
            Header contentTypeHeader = createContentTypeHeader();
            ArrayList<Header> headerArrayList = new ArrayList<Header>();
            headerArrayList.add(contentTypeHeader);
            Header[] headers = headerArrayList.toArray(new Header[0]);
            CommonHTTPManager httpManager = CommonHTTPManager.refreshCommonHTTPManager();
            CommonHttpResponse commonHttpResponse = httpManager.doCommonHttpResponseMethod("POST", apiEventsURL, headers, null, entityString, null);
            int responseCode = commonHttpResponse.getResponseCode();
            if (responseCode == 200) {
               try { 
                    JsonElement responseEntityJsonElement = ConverterUtils.parseStringToJson(commonHttpResponse.getResponseEntityString());;
                    if (!responseEntityJsonElement.isJsonNull()) {
                        LOGGER.debug("responseEntityJsonElement is not null");
                        JsonObject responseEntityJsonObject = (JsonObject) responseEntityJsonElement;
                        LOGGER.debug("got responseEntityJsonObject");
                        JsonObject eventMessageJsonObject = responseEntityJsonObject.getAsJsonObject("eventMessage");
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
                            JsonElement errorElement = responseEntityJsonObject.get("error");
                            JsonElement errorDescriptionElement = responseEntityJsonObject.get("error_description");
                            LOGGER.debug("error description is " + errorDescriptionElement.getAsString());
                        }
                    } else {

                    }
                } catch (Exception e) {
                    throw new Exception("Failed to parse to a document.", e);
                }
            } else {
                LOGGER.debug("apiRequestFormat is unrecognized");
            }

            /* Get vessel information */
            SendMessageRequest sendMessageRequest = KlearExpressRequestFactory.createSendMessageRequest(accessToken);
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

	private static Header createAccessTokenHeader(String accessToken) {
	    return new BasicHeader("kxToken", accessToken);
    }

    private static Header createContentTypeHeader() {
        return new BasicHeader("Content-Type", "application/json");
	}
}
