package com.xcase.test.klearexpress;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.junit.Test;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.xcase.klearexpress.KlearExpressExternalAPI;
import com.xcase.klearexpress.SimpleKlearExpressImpl;
import com.xcase.klearexpress.constant.KlearExpressConstant;
import com.xcase.klearexpress.factories.KlearExpressRequestFactory;
import com.xcase.klearexpress.impl.simple.core.KlearExpressConfigurationManager;
import com.xcase.klearexpress.transputs.GetAccessTokenRequest;
import com.xcase.klearexpress.transputs.GetAccessTokenResponse;
import com.xcase.klearexpress.transputs.SendMessageRequest;
import com.xcase.klearexpress.transputs.SendMessageResponse;

public class KlearExpressJUnitTest {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    protected static KlearExpressExternalAPI klearExpressExternalAPI = new SimpleKlearExpressImpl();
    
    @BeforeClass
    public static void getAccessToken() {
        String apiUrl = KlearExpressConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KlearExpressConstant.LOCAL_API_URL);
        LOGGER.debug("apiUrl is " + apiUrl);        
        String userEmail = KlearExpressConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KlearExpressConstant.LOCAL_USER_EMAIL);
        LOGGER.debug("userEmail is " + userEmail);
        String userPassword = KlearExpressConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KlearExpressConstant.LOCAL_USER_PASSWORD);
        LOGGER.debug("userPassword is " + userPassword);
        /* First, get access token */
        String accessToken = null;
        GetAccessTokenRequest getAccessTokenRequest = KlearExpressRequestFactory.createGetAccessTokenRequest();
        getAccessTokenRequest.setAPIUrl(apiUrl);
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
    }
    
    public void test1() {
        LOGGER.debug("starting test1()");
        String apiUrl = KlearExpressConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KlearExpressConstant.LOCAL_API_URL);
        LOGGER.debug("apiUrl is " + apiUrl); 
        String accessToken = KlearExpressConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KlearExpressConstant.LOCAL_ACCESS_TOKEN);
        SendMessageRequest sendMessageRequest = KlearExpressRequestFactory.createSendMessageRequest(accessToken);
        sendMessageRequest.setAPIUrl(apiUrl);
        sendMessageRequest.setMessage(" {\n" + 
                "\"eventMessage\": {\n" + 
                "\"carrierId\": \"36\",\n" + 
                "\"carrierType\": \"VESSEL\"\n" + 
                "},\n" + 
                "\"eventType\": \"GET_VESSEL_DTL\",\n" + 
                "\"eventTime\": 1554999583589\n" + 
                "}");
        SendMessageResponse sendMessageResponse = klearExpressExternalAPI.sendMessage(sendMessageRequest);
    }
    
    public void test2() {
        LOGGER.debug("starting test2()");
        String apiUrl = KlearExpressConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KlearExpressConstant.LOCAL_API_URL);
        LOGGER.debug("apiUrl is " + apiUrl); 
        String accessToken = KlearExpressConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KlearExpressConstant.LOCAL_ACCESS_TOKEN);
        SendMessageRequest sendMessageRequest = KlearExpressRequestFactory.createSendMessageRequest(accessToken);
        sendMessageRequest.setAPIUrl(apiUrl);
        sendMessageRequest.setMessage("{\n" + 
        		"  \"eventMessage\": {\n" + 
        		"    \"searchType\": \"SEARCH_TYPE_CUSTOMER\",\n" + 
        		"    \"filters\": [\n" + 
        		"      {\n" + 
        		"        \"key\": \"SEARCH_FILTERS_KXEMAIL\",\n" + 
        		"        \"value\": \"qa@klearexpress.us\"\n" + 
        		"      }\n" + 
        		"      \n" + 
        		"    ],\n" + 
        		"    \"pageNumber\": 1,\n" + 
        		"    \"searchTab\": \"SEARCH_TAB_ARRIVAL_TIME\"\n" + 
        		"  },\n" + 
        		"  \"eventType\": \"SEARCH_SHIPMENTS\",\n" + 
        		"  \"eventTime\": 1539730411376\n" + 
        		"}");
        SendMessageResponse sendMessageResponse = klearExpressExternalAPI.sendMessage(sendMessageRequest);
        JsonObject eventMessageJsonObject = sendMessageResponse.getEventMessage();
        if (eventMessageJsonObject != null) {
        	LOGGER.debug("eventMessageJsonObject is not null");
        	JsonObject headerObject = eventMessageJsonObject.getAsJsonObject("header");
        	if (headerObject != null) {
        		LOGGER.debug("headerObject is not null");
        		assertEquals("Unexpected response code.", 200, headerObject.getAsJsonPrimitive("code").getAsInt());
        	} else {
        		assertNotNull(headerObject);
        	}
        } else {
        	assertNotNull(eventMessageJsonObject);
        }
    }
    
    public void test3() {
        LOGGER.debug("starting test3()");
        String apiUrl = KlearExpressConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KlearExpressConstant.LOCAL_API_URL);
        LOGGER.debug("apiUrl is " + apiUrl);
        String accessToken = KlearExpressConfigurationManager.getConfigurationManager().getLocalConfig().getProperty(KlearExpressConstant.LOCAL_ACCESS_TOKEN);
        SendMessageRequest sendMessageRequest = KlearExpressRequestFactory.createSendMessageRequest(accessToken);
        sendMessageRequest.setAPIUrl(apiUrl);
        sendMessageRequest.setMessage("{\n" + 
        		"  \"eventMessage\": {\n" + 
        		"    \"searchType\": \"SEARCH_TYPE_CUSTOMER\",\n" + 
        		"    \"filters\": [\n" + 
        		"      {\n" + 
        		"        \"key\": \"SEARCH_FILTERS_KXEMAIL\",\n" + 
        		"        \"value\": \"lifestyle@shipment.klearexpress.com\"\n" + 
        		"      },\n" + 
        		"       {\n" + 
        		"        \"key\": \"SEARCH_FILTERS_START_DATE_OF_UNLADING\",\n" + 
        		"        \"value\": \"1554768000000\"\n" + 
        		"      },\n" + 
        		"       {\n" + 
        		"        \"key\": \"SEARCH_FILTERS_VESSEL\",\n" + 
        		"        \"value\": \"EVER LOTUS\"\n" + 
        		"      }\n" + 
        		"      \n" + 
        		"    ],\n" + 
        		"    \"pageNumber\": 1,\n" + 
        		"    \"searchTab\": \"SEARCH_TAB_ARRIVAL_TIME\"\n" + 
        		"  },\n" + 
        		"  \"eventType\": \"SEARCH_SHIPMENTS\",\n" + 
        		"  \"eventTime\": 1539730411376\n" + 
        		"}");
        SendMessageResponse sendMessageResponse = klearExpressExternalAPI.sendMessage(sendMessageRequest);
        JsonObject eventMessageJsonObject = sendMessageResponse.getEventMessage();
        if (eventMessageJsonObject != null) {
        	LOGGER.debug("eventMessageJsonObject is not null");
        	JsonObject headerObject = eventMessageJsonObject.getAsJsonObject("header");
        	if (headerObject != null) {
        		LOGGER.debug("headerObject is not null");
        		assertEquals("Unexpected response code.", 400, headerObject.getAsJsonPrimitive("code").getAsInt());
        	} else {
        		assertNotNull(headerObject);
        	}
        } else {
        	assertNotNull(eventMessageJsonObject);
        }
    }
    
    @AfterClass
    public static void cleanup() {

    }
}
