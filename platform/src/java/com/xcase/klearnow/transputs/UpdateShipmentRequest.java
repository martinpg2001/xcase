package com.xcase.klearnow.transputs;

public interface UpdateShipmentRequest extends KlearNowRequest {
    String getShipmentId();
    void setShipmentId(String shipmentId);
}
