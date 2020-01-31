package com.xcase.klearnow;

import java.lang.invoke.MethodHandles;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import com.xcase.klearnow.transputs.*;
import com.xcase.klearnow.impl.simple.methods.*;

public class SimpleKlearNowImpl implements KlearNowExternalAPI {
    /**
     * log4j logger.
     */
    protected static final Logger LOGGER = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    
    /**
     * CreateActorMethod action implementation.
     */
    private AddSupplierTeamMemberMethod addSupplierTeamMemberMethod = new AddSupplierTeamMemberMethod();
    
    /**
     * CreateActorMethod action implementation.
     */
    private CreateActorMethod createActorMethod = new CreateActorMethod();
    
    /**
     * CreateContainerMethod action implementation.
     */
    private CreateContainerMethod createContainerMethod = new CreateContainerMethod();
    
    /**
     * CreateMerchandiseLineItemMethod action implementation.
     */
    private CreateMerchandiseLineItemMethod createMerchandiseLineItemMethod = new CreateMerchandiseLineItemMethod();

    /**
     * CreateShipmentMethod action implementation.
     */
    private CreateShipmentMethod createShipmentMethod = new CreateShipmentMethod();
    
    /**
     * CreateSupplierAdminMethod action implementation.
     */
    private CreateSupplierAdminMethod createSupplierAdminMethod = new CreateSupplierAdminMethod();
    
    /**
     * DeleteActorMethod action implementation.
     */
    private DeleteActorMethod deleteActorMethod = new DeleteActorMethod();
    
    /**
     * DeleteContainerMethod action implementation.
     */
    private DeleteContainerMethod deleteContainerMethod = new DeleteContainerMethod();
    
    /**
     * GetAccessTokenMethod action implementation.
     */
    private GetAccessTokenMethod getAccessTokenMethod = new GetAccessTokenMethod();
    
    /**
     * GetActorMethod action implementation.
     */
    private GetActorMethod getActorMethod = new GetActorMethod();
    
    /**
     * GetContainerMethod action implementation.
     */
    private GetContainerMethod getContainerMethod = new GetContainerMethod(); 
    
    /**
     * GetShipmentMethod action implementation.
     */
    private GetShipmentMethod getShipmentMethod = new GetShipmentMethod();
    
    /**
     * GetShipmentStatusMethod action implementation.
     */
    private GetShipmentStatusMethod getShipmentStatusMethod = new GetShipmentStatusMethod();
    
    /**
     * GetSupplierOnboardingStatusMethod action implementation.
     */
    private GetSupplierOnboardingStatusMethod getSupplierOnboardingStatusMethod = new GetSupplierOnboardingStatusMethod();

    /**
     * SearchShipmentsMethod action implementation.
     */
    private SearchShipmentsMethod searchShipmentsMethod = new SearchShipmentsMethod();
    
    /**
     * SendMessageMethod action implementation.
     */
    private SendMessageMethod sendMessageMethod = new SendMessageMethod();
    
    /**
     * UpdateContainerMethod action implementation.
     */
    private UpdateContainerMethod updateContainerMethod = new UpdateContainerMethod();
    
    /**
     * UpdateShipmentMethod action implementation.
     */
    private UpdateShipmentMethod updateShipmentMethod = new UpdateShipmentMethod();
    
    /**
     * UploadDocumentsMethod action implementation.
     */
    private UploadDocumentsMethod uploadDocumentsMethod = new UploadDocumentsMethod();

	@Override
	public AddSupplierTeamMemberResponse addSupplierTeamMember(
			AddSupplierTeamMemberRequest request) {
		return this.addSupplierTeamMemberMethod.addSupplierTeamMember(request);
	}
    
	@Override
	public CreateActorResponse createActor(CreateActorRequest request) {
        return this.createActorMethod.createActor(request);
	}
	
	@Override
	public CreateContainerResponse createContainer(CreateContainerRequest request) {
		return this.createContainerMethod.createContainer(request);
	}
	
	@Override
	public CreateMerchandiseLineItemResponse createMerchandiseLineItem(
			CreateMerchandiseLineItemRequest request) {
		return this.createMerchandiseLineItemMethod.createMerchandiseLineItem(request);
	}
    
    @Override
    public CreateShipmentResponse createShipment(CreateShipmentRequest request) {
        return this.createShipmentMethod.createShipment(request);
    }
    
	@Override
	public CreateSupplierAdminResponse createSupplierAdmin(CreateSupplierAdminRequest request) {
		return this.createSupplierAdminMethod.createSupplierAdmin(request);
	}
    
	@Override
	public DeleteActorResponse deleteActor(DeleteActorRequest request) {
        return this.deleteActorMethod.deleteActor(request);
	}
	
	@Override
	public DeleteContainerResponse deleteContainer(DeleteContainerRequest request) {
        return this.deleteContainerMethod.deleteContainer(request);
	}

    @Override
    public GetAccessTokenResponse getAccessToken(GetAccessTokenRequest request) {
        return this.getAccessTokenMethod.getAccessToken(request);
    }
    
	@Override
	public GetActorResponse getActor(GetActorRequest request) {
        return this.getActorMethod.getActor(request);
	}
	
	@Override
	public GetContainerResponse getContainer(GetContainerRequest request) {
        return this.getContainerMethod.getContainer(request);
	}

    @Override
    public GetShipmentResponse getShipment(GetShipmentRequest request) {
        return this.getShipmentMethod.getShipment(request);
    }
    
	@Override
	public GetShipmentStatusResponse getShipmentStatus(GetShipmentStatusRequest request) {
		return this.getShipmentStatusMethod.getShipmentStatus(request);
	}
    
	@Override
	public GetSupplierOnboardingStatusResponse getSupplierOnboardingStatus(GetSupplierOnboardingStatusRequest request) {
	    return this.getSupplierOnboardingStatusMethod.getSupplierOnboardingStatus(request);
	}
	
    @Override
    public SearchShipmentsResponse searchShipments(SearchShipmentsRequest request) {
        return this.searchShipmentsMethod.searchShipments(request);
    }
    
    @Override
    public SendMessageResponse sendMessage(SendMessageRequest request) {
        return this.sendMessageMethod.sendMessage(request);
    }
    
	@Override
	public UpdateContainerResponse updateContainer(UpdateContainerRequest request) {
		return this.updateContainerMethod.updateContainer(request);
	}

    @Override
    public UpdateShipmentResponse updateShipment(UpdateShipmentRequest request) {
        return this.updateShipmentMethod.updateShipment(request);
    }

	@Override
	public UploadDocumentsResponse uploadDocuments(UploadDocumentsRequest request) {
		return this.uploadDocumentsMethod.uploadDocuments(request);
	}

}
