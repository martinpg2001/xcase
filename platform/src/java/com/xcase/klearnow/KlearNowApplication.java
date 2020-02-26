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
import com.xcase.klearnow.transputs.CreateActorResponse;

import java.io.File;
import java.lang.invoke.MethodHandles;
import java.time.Instant;
import java.util.HashMap;
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
            String shipmentId = UUID.randomUUID().toString().substring(0, 31);
            LOGGER.debug("shipmentId is " + shipmentId);
            shipment.shipmentId = shipmentId;
            shipment.supplierEmail = "qa@klearexpress.us";
            shipment.description = "";
            shipment.referenceNumber = "";
            shipment.masterBolNumber = "";
            shipment.houseBolNumber = "HBOL123455666";
            shipment.vesselName = "EVER SMILE";
            long departureDate = Instant.now().toEpochMilli();
            shipment.departureDate = Long.toString(departureDate);
            LOGGER.debug("departureDate is " + departureDate);
            long arrivalDate = Instant.now().plusMillis(1000 * 7 * 24 * 60 * 60).toEpochMilli();
            shipment.arrivalDate = Long.toString(arrivalDate);
            LOGGER.debug("arrivalDate is " + arrivalDate);
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
            shipment.supplierActorId = "877325595";
            shipment.consigneeActorId = "";
            shipment.sellerActorId = "877325595";
            shipment.buyerActorId = "";
            shipment.manufacturerActorId = "877325595";
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
            /* Search shipments */
            LOGGER.debug("shipmentId is " + shipmentId);
            SearchShipmentsRequest searchShipmentsRequest = KlearNowRequestFactory.createSearchShipmentsRequest(accessToken);
            searchShipmentsRequest.setAPIUrl(apiEventsURL);
            String searchMessage = "{ \"keywords\" : \"COMFORT SOFA\" }";
            searchShipmentsRequest.setMessage(searchMessage);
            SearchShipmentsResponse searchShipmentsResponse = klearNowExternalAPI.searchShipments(searchShipmentsRequest);
            responseCode = searchShipmentsResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            SearchShipmentsResponseMessage searchShipmentsResponseMessage = gson.fromJson(searchShipmentsResponse.getEntityString(), SearchShipmentsResponseMessage.class);
            LOGGER.debug("parsed entity string as SearchShipmentsResponseMessage");
            if (searchShipmentsResponseMessage != null) {
	            String[] shipmentIdArray = searchShipmentsResponseMessage.shipmentIdArray;
	            LOGGER.debug("shipmentIdArray as " + shipmentIdArray);
	            for (String searchShipmentId : shipmentIdArray) {
	                LOGGER.debug("searchShipmentId is " + searchShipmentId);
	                getShipmentRequest = KlearNowRequestFactory.createGetShipmentRequest(accessToken, searchShipmentId);
	                getShipmentRequest.setAPIUrl(apiEventsURL);
	                getShipmentResponse = klearNowExternalAPI.getShipment(getShipmentRequest);
	                responseCode = getShipmentResponse.getResponseCode();
	                LOGGER.debug("responseCode is " + responseCode);
	                Shipment searchshipment = gson.fromJson(getShipmentResponse.getEntityString(), Shipment.class);
	                LOGGER.debug("parsed entity string as Shipment");
	            }
            }
            
            /* Update shipment */
            LOGGER.debug("shipmentId is " + shipmentId);
            UpdateShipmentRequest updateShipmentRequest = KlearNowRequestFactory.createUpdateShipmentRequest(accessToken, shipmentId);
            updateShipmentRequest.setAPIUrl(apiEventsURL);            
            Shipment updateShipment = new Shipment();
            updateShipment.houseBolNumber = "HBOL987654321";
            departureDate = Instant.now().toEpochMilli();
            updateShipment.departureDate = Long.toString(departureDate);
            LOGGER.debug("departureDate is " + departureDate);
            arrivalDate = Instant.now().plusMillis(1000 * 8 * 24 * 60 * 60).toEpochMilli();
            updateShipment.arrivalDate = Long.toString(arrivalDate);
            LOGGER.debug("arrivalDate is " + arrivalDate);
            updateShipment.portOfLadingCode = "48945";
            updateShipment.portOfUnladingCode = "4601";
            updateShipment.supplierActorId = "877325595";
            updateShipment.sellerActorId = "877325595";
            updateShipment.manufacturerActorId = "877325595";
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
            /* Get shipment status */
            GetShipmentStatusRequest getShipmentStatusRequest = KlearNowRequestFactory.createGetShipmentStatusRequest(accessToken, shipmentId);
            getShipmentStatusRequest.setAPIUrl(apiEventsURL);
            GetShipmentStatusResponse getShipmentStatusResponse = klearNowExternalAPI.getShipmentStatus(getShipmentStatusRequest);
            responseCode = getShipmentStatusResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            /* Create actor 
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
            String createActorString = gson.toJson(actor);
            LOGGER.debug("createActorString is " + createActorString);
            createActorRequest.setMessage(createActorString);
            CreateActorResponse createActorResponse = klearNowExternalAPI.createActor(createActorRequest);
            responseCode = createActorResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            ActorResponseMessage actorResponseMessage = gson.fromJson(createActorResponse.getEntityString(), ActorResponseMessage.class);
            String actorId = actorResponseMessage.knActorId; */
            /* Get actor 
            GetActorRequest getActorRequest = KlearNowRequestFactory.createGetActorRequest(accessToken);
            getActorRequest.setAPIUrl(apiEventsURL);
            getActorRequest.setActorId(actorId);
            GetActorResponse getActorResponse = klearNowExternalAPI.getActor(getActorRequest);
            responseCode = getActorResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);*/
            /* Delete actor 
            DeleteActorRequest deleteActorRequest = KlearNowRequestFactory.createDeleteActorRequest(accessToken);
            deleteActorRequest.setAPIUrl(apiEventsURL);
            deleteActorRequest.setActorId(actorId);
            DeleteActorResponse deleteActorResponse = klearNowExternalAPI.deleteActor(deleteActorRequest);
            responseCode = deleteActorResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode); */
            /* Create supplier admin 
            CreateSupplierAdminRequest createSupplierAdminRequest = KlearNowRequestFactory.createCreateSupplierAdminRequest(accessToken);
            createSupplierAdminRequest.setAPIUrl(apiEventsURL);
            SupplierAdmin supplierAdmin = new SupplierAdmin();
            supplierAdmin.adminEmail = "";
            supplierAdmin.companyName = "";
            supplierAdmin.contactName = "";
            supplierAdmin.role = "";
            String supplierAdminString = gson.toJson(supplierAdmin);
            LOGGER.debug("supplierAdminString is " + supplierAdminString);
            createSupplierAdminRequest.setMessage(supplierAdminString);
            CreateSupplierAdminResponse createSupplierAdminResponse = klearNowExternalAPI.createSupplierAdmin(createSupplierAdminRequest);
            responseCode = createSupplierAdminResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode); */
            /* Get supplier onboarding status 
            GetSupplierOnboardingStatusRequest getSupplierOnboardingStatusRequest = KlearNowRequestFactory.createGetSupplierOnboardingStatusRequest(accessToken);
            getSupplierOnboardingStatusRequest.setAPIUrl(apiEventsURL);
            SupplierOnboardingStatusRequest supplierOnboardingStatusRequest = new SupplierOnboardingStatusRequest();
            supplierOnboardingStatusRequest.adminEmail = "";
            String supplierOnboardingStatusRequestString = gson.toJson(supplierOnboardingStatusRequest);
            LOGGER.debug("supplierAdminString is " + supplierAdminString);
            getSupplierOnboardingStatusRequest.setMessage(supplierAdminString);
            GetSupplierOnboardingStatusResponse getSupplierOnboardingStatusResponse = klearNowExternalAPI.getSupplierOnboardingStatus(getSupplierOnboardingStatusRequest);
            responseCode = getSupplierOnboardingStatusResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode); */
            /* Add supplier team member 
            AddSupplierTeamMemberRequest addSupplierTeamMemberRequest = KlearNowRequestFactory.createAddSupplierTeamMemberRequest(accessToken);
            addSupplierTeamMemberRequest.setAPIUrl(apiEventsURL);
            SupplierTeamMemberRequest supplierTeamMemberRequest = new SupplierTeamMemberRequest();
            supplierTeamMemberRequest.role = "";
            supplierTeamMemberRequest.adminEmail = "john@supplier.com";
            supplierTeamMemberRequest.name = "John";
            supplierTeamMemberRequest.companyName = "John company";
            String supplierTeamMemberRequestString = gson.toJson(supplierTeamMemberRequest);
            LOGGER.debug("supplierTeamMemberRequestString is " + supplierTeamMemberRequestString);
            addSupplierTeamMemberRequest.setMessage(supplierTeamMemberRequestString);
            AddSupplierTeamMemberResponse addSupplierTeamMemberResponse = klearNowExternalAPI.addSupplierTeamMember(addSupplierTeamMemberRequest);
            responseCode = addSupplierTeamMemberResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode); */
            /* Create container
            CreateContainerRequest createContainerRequest = KlearNowRequestFactory.createCreateContainerRequest(accessToken);
            createContainerRequest.setAPIUrl(apiEventsURL);
            createContainerRequest.setShipmentId(shipmentId);
            Container container = new Container();
            container.containerNumber = "ZZZZ9999999";
            container.containerType = "CLOSE_TOP_40_FT";
            Address containerAddress = new Address();
            containerAddress.addressLine1 = "1 High Street";
            containerAddress.addressLine2 = "Second Floor";
            containerAddress.province = "Province";
            containerAddress.country = "Country";
            containerAddress.zip = "95000";
            container.destinationAddress = containerAddress;
            String createContainerString = gson.toJson(container);
            LOGGER.debug("createContainerString is " + createContainerString);
            createContainerRequest.setMessage(createContainerString);
            CreateContainerResponse createContainerResponse = klearNowExternalAPI.createContainer(createContainerRequest);
            responseCode = createContainerResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode); */
            /* Get container status
            GetContainerRequest getContainerRequest = KlearNowRequestFactory.createGetContainerRequest(accessToken);
            getContainerRequest.setAPIUrl(apiEventsURL);
            getContainerRequest.setShipmentId(shipmentId);
            getContainerRequest.setContainerNumber("ZZZZ9999999");
            GetContainerResponse getContainerResponse = klearNowExternalAPI.getContainer(getContainerRequest);
            responseCode = getContainerResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            ContainerStatus containerStatus = gson.fromJson(getContainerResponse.getEntityString(), ContainerStatus.class);
            String containerNumber = containerStatus.containerNumber;
            LOGGER.debug("containerNumber is " + containerNumber); */
            /* Update container
            UpdateContainerRequest updateContainerRequest = KlearNowRequestFactory.createUpdateContainerRequest(accessToken);
            updateContainerRequest.setAPIUrl(apiEventsURL);
            updateContainerRequest.setShipmentId(shipmentId);
            updateContainerRequest.setContainerNumber("ZZZZ9999999");
            Container updateContainer = new Container();
            updateContainer.containerType = "OPEN_TOP_40_FT";
            Address updateContainerAddress = new Address();
            updateContainerAddress.addressLine1 = "1 Low Street";
            updateContainerAddress.addressLine2 = "Third Floor";
            updateContainerAddress.province = "Updated Province";
            updateContainerAddress.country = "Updated Country";
            updateContainerAddress.zip = "96000";
            container.destinationAddress = updateContainerAddress;
            String updateContainerString = gson.toJson(container);
            LOGGER.debug("updateContainerString is " + updateContainerString);
            updateContainerRequest.setMessage(updateContainerString);
            UpdateContainerResponse updateContainerResponse = klearNowExternalAPI.updateContainer(updateContainerRequest);
            responseCode = updateContainerResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode); */
            /* Get container status
            getContainerRequest = KlearNowRequestFactory.createGetContainerRequest(accessToken);
            getContainerRequest.setAPIUrl(apiEventsURL);
            getContainerRequest.setShipmentId(shipmentId);
            getContainerRequest.setContainerNumber("ZZZZ9999999");
            getContainerResponse = klearNowExternalAPI.getContainer(getContainerRequest);
            responseCode = getContainerResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            containerStatus = gson.fromJson(getContainerResponse.getEntityString(), ContainerStatus.class);
            containerNumber = containerStatus.containerNumber;
            LOGGER.debug("containerNumber is " + containerNumber); */
            /* Delete container
            DeleteContainerRequest deleteContainerRequest = KlearNowRequestFactory.createDeleteContainerRequest(accessToken);
            deleteContainerRequest.setAPIUrl(apiEventsURL);
            deleteContainerRequest.setShipmentId(shipmentId);
            deleteContainerRequest.setContainerNumber("ZZZZ9999999");
            DeleteContainerResponse deleteContainerResponse = klearNowExternalAPI.deleteContainer(deleteContainerRequest);
            responseCode = deleteContainerResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode); */
            /* Get container status: should throw some sort of error
            getContainerRequest = KlearNowRequestFactory.createGetContainerRequest(accessToken);
            getContainerRequest.setAPIUrl(apiEventsURL);
            getContainerRequest.setShipmentId(shipmentId);
            getContainerRequest.setContainerNumber("ZZZZ9999999");
            getContainerResponse = klearNowExternalAPI.getContainer(getContainerRequest);
            responseCode = getContainerResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            containerStatus = gson.fromJson(getContainerResponse.getEntityString(), ContainerStatus.class);
            containerNumber = containerStatus.containerNumber;
            LOGGER.debug("containerNumber is " + containerNumber); */         
            /* Create merchandise line item */
            CreateMerchandiseLineItemRequest createMerchandiseLineItemRequest = KlearNowRequestFactory.createCreateMerchandiseLineItemRequest(accessToken);
            createMerchandiseLineItemRequest.setAPIUrl(apiEventsURL);
            createMerchandiseLineItemRequest.setShipmentId(shipmentId);
            MerchandiseLineItem merchandiseLineItem = new MerchandiseLineItem();
            merchandiseLineItem.commercialDescription = "PARTS OF FURNITRE: TXTLE EXCPT COTTON: OTR";
            merchandiseLineItem.htsCode = "9403.90.60.80";
            merchandiseLineItem.manufacturerId = "877325595";
            merchandiseLineItem.originCountry = "China";
            merchandiseLineItem.sequenceID = 1;
            MerchandiseCreateRequestMessage merchandiseCreateRequestMessage = new MerchandiseCreateRequestMessage();
            merchandiseCreateRequestMessage.merchandises = new MerchandiseLineItem[] { merchandiseLineItem };
            String merchandiseCreateRequestMessageString = gson.toJson(merchandiseCreateRequestMessage);
            LOGGER.debug("merchandiseCreateRequestMessageString is " + merchandiseCreateRequestMessageString);
            createMerchandiseLineItemRequest.setMessage(merchandiseCreateRequestMessageString);
            CreateMerchandiseLineItemResponse createMerchandiseLineItemResponse = klearNowExternalAPI.createMerchandiseLineItem(createMerchandiseLineItemRequest);
            responseCode = createMerchandiseLineItemResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode);
            /* Upload documents
            LOGGER.debug("shipmentId is " + shipmentId);
            UploadDocumentsRequest uploadDocumentsRequest = KlearNowRequestFactory.createUploadDocumentsRequest(accessToken, shipmentId);
            uploadDocumentsRequest.setAPIUrl(apiEventsURL);
            HashMap<String, File> dataMap = new HashMap<String, File>();
            File sampleFile = new File("docs", "Sample.txt");
            dataMap.put("Sample", sampleFile);
            uploadDocumentsRequest.setDataMap(dataMap);
            UploadDocumentsResponse uploadDocumentsResponse = klearNowExternalAPI.uploadDocuments(uploadDocumentsRequest);
            responseCode = uploadDocumentsResponse.getResponseCode();
            LOGGER.debug("responseCode is " + responseCode); */
        } catch (Exception e) {
            LOGGER.warn("exception invoking API operation: " + e.getMessage());
        }
    }
}
