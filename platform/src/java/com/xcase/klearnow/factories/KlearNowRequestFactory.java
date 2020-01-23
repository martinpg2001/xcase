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
    
	public static CreateActorRequest createCreateActorRequest(String accessToken) {
        Object obj = newInstanceOf("klearnow.config.requestfactory.CreateActorRequest");
        return (CreateActorRequest) obj;
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
    
	public static GetSupplierOnboardingStatusRequest createGetSupplierOnboardingStatusRequest() {
        Object obj = newInstanceOf("klearnow.config.requestfactory.GetSupplierOnboardingStatusRequest");
        return (GetSupplierOnboardingStatusRequest) obj;
	}
    
	public static GetSupplierOnboardingStatusRequest createGetSupplierOnboardingStatusRequest(String accessToken) {
		GetSupplierOnboardingStatusRequest request = createGetSupplierOnboardingStatusRequest();
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
}
