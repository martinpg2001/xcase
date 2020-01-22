package com.xcase.klearnow;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import com.xcase.klearnow.constant.*;
import com.xcase.klearnow.factories.KlearNowRequestFactory;
import com.xcase.klearnow.impl.simple.core.KlearNowConfigurationManager;
import com.xcase.klearnow.objects.*;
import com.xcase.klearnow.transputs.*;
import java.lang.invoke.MethodHandles;
import java.util.UUID;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.joda.time.Instant;

public class KlearNowApplication {
    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());

    public static void main(String[] args) {
        try {
            LOGGER.debug("starting main()");
            Gson gson = new GsonBuilder().setPrettyPrinting().create();
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
                                                   "  \"password\": \"" + userPassword + "\"\n" +
                                                   "}");
            GetAccessTokenResponse getAccessTokenResponse = klearNowExternalAPI.getAccessToken(getAccessTokenRequest);
            JsonObject eventMessageJsonObject = getAccessTokenResponse.getEventMessage();
            LOGGER.debug("got eventMessageJsonObject");
            JsonPrimitive kxTokenJsonPrimitive = eventMessageJsonObject.getAsJsonPrimitive("kxToken");
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
            CreateShipmentRequest request = KlearNowRequestFactory.createCreateShipmentRequest(accessToken);
            request.setAPIUrl(apiEventsURL);
            Shipment shipment = new Shipment();
            String uuid = UUID.randomUUID().toString();
            LOGGER.debug("uuid is " + uuid);
            shipment.shipmentId = uuid;
            shipment.supplierEmail = "qa@klearexpress.us";
            shipment.houseBolNumber = "HBOL123455666";
            shipment.departureDate = Instant.now().getMillis();
            shipment.arrivalDate = Instant.now().getMillis();
            shipment.portOfLadingCode = "48945";
            shipment.portOfUnladingCode = "4601";
            shipment.supplierActorId = "";
            shipment.sellerActorId = "";
            shipment.manufacturerActorId = "";
            String createShipmentString = gson.toJson(shipment);
            LOGGER.debug("createShipmentString is " + createShipmentString);
            request.setMessage(createShipmentString);
            CreateShipmentResponse createShipmentResponse = klearNowExternalAPI.createShipment(request);
            int responseCode = createShipmentResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
        } catch (Exception e) {
            LOGGER.warn("exception invoking API operation: " + e.getMessage());
        }
    }
}
