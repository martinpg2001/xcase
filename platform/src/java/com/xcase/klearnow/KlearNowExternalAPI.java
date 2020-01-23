package com.xcase.klearnow;

import com.xcase.klearnow.transputs.*;

public interface KlearNowExternalAPI {
	
	CreateActorResponse createActor(CreateActorRequest createActorRequest);
	
    CreateShipmentResponse createShipment(CreateShipmentRequest request);
    
	CreateSupplierAdminResponse createSupplierAdmin(CreateSupplierAdminRequest createSupplierAdminRequest);
	
	DeleteActorResponse deleteActor(DeleteActorRequest deleteActorRequest);
    
    GetAccessTokenResponse getAccessToken(GetAccessTokenRequest request);
    
	GetActorResponse getActor(GetActorRequest getActorRequest);

    GetShipmentResponse getShipment(GetShipmentRequest getShipmentRequest);
    
	GetShipmentStatusResponse getShipmentStatus(GetShipmentStatusRequest getShipmentStatusRequest);
    
	GetSupplierOnboardingStatusResponse getSupplierOnboardingStatus(
			GetSupplierOnboardingStatusRequest getSupplierOnboardingStatusRequest);

    SendMessageResponse sendMessage(SendMessageRequest request);

    UpdateShipmentResponse updateShipment(UpdateShipmentRequest updateShipmentRequest);
}
