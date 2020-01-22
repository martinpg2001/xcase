package com.xcase.klearnow.impl.simple.transputs;

import com.xcase.klearnow.transputs.GetShipmentRequest;

public class GetShipmentRequestImpl extends KlearNowRequestImpl implements GetShipmentRequest {
    private String shipmentId;
    
    public String getShipmentId() {
        return this.shipmentId;
    }
    
    public void setShipmentId(String shipmentId) {
        this.shipmentId = shipmentId;
    }

}
