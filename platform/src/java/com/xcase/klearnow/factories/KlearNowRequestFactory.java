package com.xcase.klearnow.factories;

import java.lang.invoke.MethodHandles;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.xcase.klearnow.transputs.*;

public class KlearNowRequestFactory extends BaseKlearNowFactory {

    /**
     * log4j object.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
	public static AddSupplierTeamMemberRequest createAddSupplierTeamMemberRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.AddSupplierTeamMemberRequest");
        return (AddSupplierTeamMemberRequest) obj;
	}
	
	public static AddSupplierTeamMemberRequest createAddSupplierTeamMemberRequest(String accessToken) {
		AddSupplierTeamMemberRequest request = createAddSupplierTeamMemberRequest();
    	request.setAccessToken(accessToken);
        return request;
	}
	
    public static CreateActorRequest createCreateActorRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.CreateActorRequest");
        return (CreateActorRequest) obj;
    }
    
	public static CreateActorRequest createCreateActorRequest(String accessToken) {
        CreateActorRequest request = createCreateActorRequest();
        request.setAccessToken(accessToken);
        return request;
	}
	
	public static CreateContainerRequest createCreateContainerRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.CreateContainerRequest");
        return (CreateContainerRequest) obj;
	}
	
	public static CreateContainerRequest createCreateContainerRequest(String accessToken) {
		CreateContainerRequest request = createCreateContainerRequest();
    	request.setAccessToken(accessToken);
        return request;
	}
	
	public static CreateMerchandiseLineItemRequest createCreateMerchandiseLineItemRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.CreateMerchandiseLineItemRequest");
        return (CreateMerchandiseLineItemRequest) obj;
	}
	
	public static CreateMerchandiseLineItemRequest createCreateMerchandiseLineItemRequest(String accessToken) {
		CreateMerchandiseLineItemRequest request = createCreateMerchandiseLineItemRequest();
    	request.setAccessToken(accessToken);
        return request;
	}

    public static CreateShipmentRequest createCreateShipmentRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.CreateShipmentRequest");
        return (CreateShipmentRequest) obj;
    }
    
    public static CreateShipmentRequest createCreateShipmentRequest(String accessToken) {
    	CreateShipmentRequest request = createCreateShipmentRequest();
    	request.setAccessToken(accessToken);
        return request;
    }
    
	public static CreateSupplierAdminRequest createCreateSupplierAdminRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.CreateSupplierAdminRequest");
        return (CreateSupplierAdminRequest) obj;
	}
    
	public static CreateSupplierAdminRequest createCreateSupplierAdminRequest(String accessToken) {
		CreateSupplierAdminRequest request = createCreateSupplierAdminRequest();
    	request.setAccessToken(accessToken);
        return request;
	}
	
	public static DeleteActorRequest createDeleteActorRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.DeleteActorRequest");
        return (DeleteActorRequest) obj;
	}
	
	public static DeleteActorRequest createDeleteActorRequest(String accessToken) {
		DeleteActorRequest request = createDeleteActorRequest();
    	request.setAccessToken(accessToken);
        return request;
	}
	
	public static DeleteContainerRequest createDeleteContainerRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.DeleteContainerRequest");
        return (DeleteContainerRequest) obj;
	}
	
	public static DeleteContainerRequest createDeleteContainerRequest(String accessToken) {
		DeleteContainerRequest request = createDeleteContainerRequest();
    	request.setAccessToken(accessToken);
        return request;
	}
    
    public static GetAccessTokenRequest createGetAccessTokenRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.GetAccessTokenRequest");
        return (GetAccessTokenRequest) obj;
    }
    
	public static GetActorRequest createGetActorRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.GetActorRequest");
        return (GetActorRequest) obj;
	}
    
	public static GetActorRequest createGetActorRequest(String accessToken) {
		GetActorRequest request = createGetActorRequest();
    	request.setAccessToken(accessToken);
        return request;
	}
	
	public static GetContainerRequest createGetContainerRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.GetContainerRequest");
        return (GetContainerRequest) obj;
	}
	
	public static GetContainerRequest createGetContainerRequest(String accessToken) {
		GetContainerRequest request = createGetContainerRequest();
    	request.setAccessToken(accessToken);
        return request;
	}
    
    public static GetShipmentRequest createGetShipmentRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.GetShipmentRequest");
        return (GetShipmentRequest) obj;
    }
    
    public static GetShipmentRequest createGetShipmentRequest(String accessToken, String shipmentId) {
        GetShipmentRequest request = createGetShipmentRequest();
        request.setAccessToken(accessToken);
        request.setShipmentId(shipmentId);
        return request;
    }
    
	public static GetShipmentStatusRequest createGetShipmentStatusRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.GetShipmentStatusRequest");
        return (GetShipmentStatusRequest) obj;
	}
    
	public static GetShipmentStatusRequest createGetShipmentStatusRequest(String accessToken, String shipmentId) {
		GetShipmentStatusRequest request = createGetShipmentStatusRequest();
        request.setAccessToken(accessToken);
        request.setShipmentId(shipmentId);
        return request;
	}
    
	public static GetSupplierOnboardingStatusRequest createGetSupplierOnboardingStatusRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.GetSupplierOnboardingStatusRequest");
        return (GetSupplierOnboardingStatusRequest) obj;
	}
    
	public static GetSupplierOnboardingStatusRequest createGetSupplierOnboardingStatusRequest(String accessToken) {
		GetSupplierOnboardingStatusRequest request = createGetSupplierOnboardingStatusRequest();
    	request.setAccessToken(accessToken);
        return request;
	}
	
	public static SearchShipmentsRequest createSearchShipmentsRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.SearchShipmentsRequest");
        return (SearchShipmentsRequest) obj;
	}
	
	public static SearchShipmentsRequest createSearchShipmentsRequest(String accessToken) {
		SearchShipmentsRequest request = createSearchShipmentsRequest();
    	request.setAccessToken(accessToken);
        return request;
	}
    
    public static SendMessageRequest createSendMessageRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.SendMessageRequest");
        return (SendMessageRequest) obj;
    }

    public static SendMessageRequest createSendMessageRequest(String accessToken) {
        SendMessageRequest request = createSendMessageRequest();
        request.setAccessToken(accessToken);
        return request;
    }
    
    public static UpdateContainerRequest createUpdateContainerRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.UpdateContainerRequest");
        return (UpdateContainerRequest) obj;
    }
    
	public static UpdateContainerRequest createUpdateContainerRequest(String accessToken) {
        UpdateContainerRequest request = createUpdateContainerRequest();
        request.setAccessToken(accessToken);
        return request;
	}

    public static UpdateContainerRequest createUpdateContainerRequest(String accessToken, String containerId) {
        UpdateContainerRequest request = createUpdateContainerRequest();
        request.setAccessToken(accessToken);
        request.setContainerId(containerId);
        return request;
    }
    
    public static UpdateContainerRequest createUpdateContainerRequest(String accessToken, String shipmentId, String containerNumber) {
        UpdateContainerRequest request = createUpdateContainerRequest();
        request.setAccessToken(accessToken);
        request.setShipmentId(shipmentId);
        request.setContainerId(containerNumber);
        return request;
    }
    
    public static UpdateShipmentRequest createUpdateShipmentRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.UpdateShipmentRequest");
        return (UpdateShipmentRequest) obj;
    }

    public static UpdateShipmentRequest createUpdateShipmentRequest(String accessToken, String shipmentId) {
        UpdateShipmentRequest request = createUpdateShipmentRequest();
        request.setAccessToken(accessToken);
        request.setShipmentId(shipmentId);
        return request;
    }

	public static UploadDocumentsRequest createUploadDocumentsRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.UploadDocumentsRequest");
        return (UploadDocumentsRequest) obj;
	}
	
	public static UploadDocumentsRequest createUploadDocumentsRequest(String accessToken, String shipmentId) {
		UploadDocumentsRequest request = createUploadDocumentsRequest();
        request.setAccessToken(accessToken);
        request.setShipmentId(shipmentId);
        return request;
	}

}
