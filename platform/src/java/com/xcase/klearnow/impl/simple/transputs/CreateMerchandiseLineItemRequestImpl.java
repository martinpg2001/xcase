package com.xcase.klearnow.impl.simple.transputs;

import com.xcase.klearnow.transputs.CreateMerchandiseLineItemRequest;

public class CreateMerchandiseLineItemRequestImpl extends KlearNowRequestImpl
        implements CreateMerchandiseLineItemRequest {
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
