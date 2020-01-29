package com.xcase.klearnow.transputs;

public interface CreateMerchandiseLineItemRequest extends KlearNowRequest {

	String getShipmentId();

	void setShipmentId(String shipmentId);
}
