package com.xcase.klearnow.impl.simple.transputs;

import com.xcase.klearnow.transputs.UpdateShipmentRequest;

public class UpdateShipmentRequestImpl extends KlearNowRequestImpl implements UpdateShipmentRequest {
    private String shipmentId;
    
    public String getShipmentId() {
        return this.shipmentId;
    }
    
    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }
}
