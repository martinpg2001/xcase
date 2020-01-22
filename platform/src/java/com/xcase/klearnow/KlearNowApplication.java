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
import com.xcase.klearnow.objects.Shipment.ModeOfTransport;
import com.xcase.klearnow.transputs.*;
import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.util.UUID;

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
            CreateShipmentRequest createShipmentRequest = KlearNowRequestFactory.createCreateShipmentRequest(accessToken);
            createShipmentRequest.setAPIUrl(apiEventsURL);
            Shipment shipment = new Shipment();
            String shipmentId = UUID.randomUUID().toString();
            LOGGER.debug("shipmentId is " + shipmentId);
            shipment.shipmentId = shipmentId;
            shipment.supplierEmail = "qa@klearexpress.us";
            shipment.description = "";
            shipment.referenceNumber = "";
            shipment.masterBolNumber = "";
            shipment.houseBolNumber = "HBOL123455666";
            shipment.vesselName = "EVER SMILE";
            shipment.departureDate = Instant.now().toEpochMilli();
            shipment.arrivalDate = Instant.now().plusMillis(1000 * 7 * 24 * 60 * 60).toEpochMilli();
            shipment.modeOfTransport = Shipment.ModeOfTransport.OCEAN;
            shipment.originCountry = "";
            shipment.originCity = "";
            shipment.destinationCountry = "";
            shipment.destinationState = "";
            shipment.destinationCity = "";
            shipment.portOfLadingCode = "48945";
            shipment.portOfUnladingCode = "4601";
            shipment.portOfEntryCode = "";
            shipment.currentLocationFirm = "";
            shipment.examSiteFirm = "";
            shipment.goNumber = "";
            shipment.paymentTypeCode = "";
            shipment.examPortCode = "";
            shipment.supplierActorId = "";
            shipment.consigneeActorId = "";
            shipment.sellerActorId = "";
            shipment.buyerActorId = "";
            shipment.manufacturerActorId = "";
            shipment.shipToActorId = "";
            shipment.stufferActorId = "";
            shipment.consolidatorActorId = "";
            shipment.notifyPartyActorId = "";
            String createShipmentString = gson.toJson(shipment);
            LOGGER.debug("createShipmentString is " + createShipmentString);
            createShipmentRequest.setMessage(createShipmentString);
            CreateShipmentResponse createShipmentResponse = klearNowExternalAPI.createShipment(createShipmentRequest);
            int responseCode = createShipmentResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            /* Get shipment */
            LOGGER.debug("shipmentId is " + shipmentId);
            GetShipmentRequest getShipmentRequest = KlearNowRequestFactory.createGetShipmentRequest(accessToken, shipmentId);
            getShipmentRequest.setAPIUrl(apiEventsURL);
            GetShipmentResponse getShipmentResponse = klearNowExternalAPI.getShipment(getShipmentRequest);
            responseCode = getShipmentResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            /* Update shipment */
            LOGGER.debug("shipmentId is " + shipmentId);
            UpdateShipmentRequest updateShipmentRequest = KlearNowRequestFactory.createUpdateShipmentRequest(accessToken, shipmentId);
            updateShipmentRequest.setAPIUrl(apiEventsURL);            
            Shipment updateShipment = new Shipment();
            updateShipment.houseBolNumber = "HBOL987654321";
            updateShipment.departureDate = Instant.now().toEpochMilli();
            updateShipment.arrivalDate = Instant.now().plusMillis(1000 * 8 * 24 * 60 * 60).toEpochMilli();
            updateShipment.portOfLadingCode = "48945";
            updateShipment.portOfUnladingCode = "4601";
            updateShipment.supplierActorId = "";
            updateShipment.sellerActorId = "";
            updateShipment.manufacturerActorId = "";
            String updateShipmentString = gson.toJson(updateShipment);
            LOGGER.debug("updateShipmentString is " + updateShipmentString);
            updateShipmentRequest.setMessage(updateShipmentString);
            UpdateShipmentResponse updateShipmentResponse = klearNowExternalAPI.updateShipment(updateShipmentRequest);
            responseCode = updateShipmentResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            /* Get shipment */
            getShipmentRequest = KlearNowRequestFactory.createGetShipmentRequest(accessToken, shipmentId);
            getShipmentRequest.setAPIUrl(apiEventsURL);
            getShipmentResponse = klearNowExternalAPI.getShipment(getShipmentRequest);
            responseCode = getShipmentResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            /* Create actor */
            CreateActorRequest createActorRequest = KlearNowRequestFactory.createCreateActorRequest(accessToken);
            createActorRequest.setAPIUrl(apiEventsURL);
            Actor actor = new Actor();
            actor.name = "Firstname Lastname";
            Address address = new Address();
            address.addressLine1 = "1 High Street";
            address.addressLine2 = "Second Floor";
            address.province = "Province";
            address.country = "Country";
            address.zip = "95000";
            actor.address = address;
            String createActorString = gson.toJson(shipment);
            LOGGER.debug("createActorString is " + createActorString);
            createActorRequest.setMessage(createActorString);
            CreateActorResponse response = klearNowExternalAPI.createActor(createActorRequest);
            responseCode = response.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
        } catch (Exception e) {
            LOGGER.warn("exception invoking API operation: " + e.getMessage());
        }
    }
}
