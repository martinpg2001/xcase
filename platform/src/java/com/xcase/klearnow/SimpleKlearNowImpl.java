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
    private CreateActorMethod createActorMethod = new CreateActorMethod();

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
     * GetAccessTokenMethod action implementation.
     */
    private GetAccessTokenMethod getAccessTokenMethod = new GetAccessTokenMethod();
    
    /**
     * GetActorMethod action implementation.
     */
    private GetActorMethod getActorMethod = new GetActorMethod();
    
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
     * SendMessageMethod action implementation.
     */
    private SendMessageMethod sendMessageMethod = new SendMessageMethod();
    
    /**
     * UpdateShipmentMethod action implementation.
     */
    private UpdateShipmentMethod updateShipmentMethod = new UpdateShipmentMethod();
    
	@Override
	public CreateActorResponse createActor(CreateActorRequest request) {
        return this.createActorMethod.createActor(request);
	}
    
    @Override
    public CreateShipmentResponse createShipment(CreateShipmentRequest request) {
        return this.createShipmentMethod.createShipment(request);
    }
    
	@Override
	public DeleteActorResponse deleteActor(DeleteActorRequest request) {
        return this.deleteActorMethod.deleteActor(request);
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
    public SendMessageResponse sendMessage(SendMessageRequest request) {
        return this.sendMessageMethod.sendMessage(request);
    }

    @Override
    public UpdateShipmentResponse updateShipment(UpdateShipmentRequest request) {
        return this.updateShipmentMethod.updateShipment(request);
    }

	@Override
	public CreateSupplierAdminResponse createSupplierAdmin(CreateSupplierAdminRequest request) {
		return this.createSupplierAdminMethod.createSupplierAdmin(request);
	}

}