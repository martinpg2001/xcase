package com.xcase.klearnow;

import com.xcase.klearnow.transputs.*;

public interface KlearNowExternalAPI {

    SendMessageResponse sendMessage(SendMessageRequest request);

    GetAccessTokenResponse getAccessToken(GetAccessTokenRequest request);

    CreateShipmentResponse createShipment(CreateShipmentRequest request);

    GetShipmentResponse getShipment(GetShipmentRequest getShipmentRequest);

    UpdateShipmentResponse updateShipment(UpdateShipmentRequest updateShipmentRequest);

}
