package com.xcase.klearnow;

import com.xcase.klearnow.transputs.*;

public interface KlearNowExternalAPI {
	AddSupplierTeamMemberResponse addSupplierTeamMember(AddSupplierTeamMemberRequest addSupplierTeamMemberRequest);
	
	CreateActorResponse createActor(CreateActorRequest createActorRequest);
	
	CreateContainerResponse createContainer(CreateContainerRequest createContainerRequest);
	
	CreateMerchandiseLineItemResponse createMerchandiseLineItem(
			CreateMerchandiseLineItemRequest createMerchandiseLineItemRequest);
	
    CreateShipmentResponse createShipment(CreateShipmentRequest request);
    
	CreateSupplierAdminResponse createSupplierAdmin(CreateSupplierAdminRequest createSupplierAdminRequest);
	
	DeleteActorResponse deleteActor(DeleteActorRequest deleteActorRequest);
	
	DeleteContainerResponse deleteContainer(DeleteContainerRequest request);
    
    GetAccessTokenResponse getAccessToken(GetAccessTokenRequest request);
    
	GetActorResponse getActor(GetActorRequest getActorRequest);
	
	GetContainerResponse getContainer(GetContainerRequest getContainerRequest);

    GetShipmentResponse getShipment(GetShipmentRequest getShipmentRequest);
    
	GetShipmentStatusResponse getShipmentStatus(GetShipmentStatusRequest getShipmentStatusRequest);
    
	GetSupplierOnboardingStatusResponse getSupplierOnboardingStatus(
			GetSupplierOnboardingStatusRequest getSupplierOnboardingStatusRequest);
	
	SearchShipmentsResponse searchShipments(SearchShipmentsRequest searchShipmentsRequest);

    SendMessageResponse sendMessage(SendMessageRequest request);
    
	UpdateContainerResponse updateContainer(UpdateContainerRequest updateContainerRequest);

    UpdateShipmentResponse updateShipment(UpdateShipmentRequest updateShipmentRequest);

}
