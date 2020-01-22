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
     * CreateShipmentMethod action implementation.
     */
    private CreateShipmentMethod createShipmentMethod = new CreateShipmentMethod();
    
    /**
     * GetAccessTokenMethod action implementation.
     */
    private GetAccessTokenMethod getAccessTokenMethod = new GetAccessTokenMethod();
    
    /**
     * GetShipmentMethod action implementation.
     */
    private GetShipmentMethod getShipmentMethod = new GetShipmentMethod();

    /**
     * SendMessageMethod action implementation.
     */
    private SendMessageMethod sendMessageMethod = new SendMessageMethod();
    
    /**
     * UpdateShipmentMethod action implementation.
     */
    private UpdateShipmentMethod updateShipmentMethod = new UpdateShipmentMethod();
    
    @Override
    public CreateShipmentResponse createShipment(CreateShipmentRequest request) {
        return this.createShipmentMethod.createShipment(request);
    }

    @Override
    public GetAccessTokenResponse getAccessToken(GetAccessTokenRequest getAccessTokenRequest) {
        return this.getAccessTokenMethod.getAccessToken(getAccessTokenRequest);
    }

    @Override
    public GetShipmentResponse getShipment(GetShipmentRequest getShipmentRequest) {
        return this.getShipmentMethod.getShipment(getShipmentRequest);
    }
    
    @Override
    public SendMessageResponse sendMessage(SendMessageRequest sendMessageRequest) {
        return this.sendMessageMethod.sendMessage(sendMessageRequest);
    }

    @Override
    public UpdateShipmentResponse updateShipment(UpdateShipmentRequest updateShipmentRequest) {
        return this.updateShipmentMethod.updateShipment(updateShipmentRequest);
    }

}
