package com.xcase.klearnow.impl.simple.transputs;

import com.xcase.klearnow.transputs.CreateContainerRequest;

public class CreateContainerRequestImpl extends KlearNowRequestImpl implements CreateContainerRequest {
    private String shipmentId;
    
    @Override
    public String getShipmentId() {
        return this.shipmentId;
    }

    @Override
    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

}
