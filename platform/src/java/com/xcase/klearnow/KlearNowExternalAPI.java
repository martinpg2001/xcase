package com.xcase.klearnow;

import com.xcase.klearnow.transputs.*;

public interface KlearNowExternalAPI {
	
	CreateActorResponse createActor(CreateActorRequest createActorRequest);
	
    CreateShipmentResponse createShipment(CreateShipmentRequest request);
    
	CreateSupplierAdminResponse createSupplierAdmin(CreateSupplierAdminRequest createSupplierAdminRequest);
    
    GetAccessTokenResponse getAccessToken(GetAccessTokenRequest request);

    GetShipmentResponse getShipment(GetShipmentRequest getShipmentRequest);
    
	GetSupplierOnboardingStatusResponse getSupplierOnboardingStatus(
			GetSupplierOnboardingStatusRequest getSupplierOnboardingStatusRequest);

    SendMessageResponse sendMessage(SendMessageRequest request);

    UpdateShipmentResponse updateShipment(UpdateShipmentRequest updateShipmentRequest);


}
