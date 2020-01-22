package com.xcase.klearnow;

import com.xcase.klearnow.transputs.*;

public interface KlearNowExternalAPI {
	
	CreateActorResponse createActor(CreateActorRequest createActorRequest);
	
    CreateShipmentResponse createShipment(CreateShipmentRequest request);
    
    GetAccessTokenResponse getAccessToken(GetAccessTokenRequest request);

    GetShipmentResponse getShipment(GetShipmentRequest getShipmentRequest);

    SendMessageResponse sendMessage(SendMessageRequest request);

    UpdateShipmentResponse updateShipment(UpdateShipmentRequest updateShipmentRequest);

}
