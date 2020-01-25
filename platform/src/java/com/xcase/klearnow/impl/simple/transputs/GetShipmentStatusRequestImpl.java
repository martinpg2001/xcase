package com.xcase.klearnow.impl.simple.transputs;

import com.xcase.klearnow.transputs.GetShipmentStatusRequest;

public class GetShipmentStatusRequestImpl extends KlearNowRequestImpl implements GetShipmentStatusRequest {
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
