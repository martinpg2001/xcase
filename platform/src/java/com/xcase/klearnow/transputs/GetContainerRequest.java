package com.xcase.klearnow.transputs;

public interface GetContainerRequest extends KlearNowRequest {

	String getShipmentId();

	String getContainerNumber();

	void setShipmentId(String shipmentId);

	void setContainerNumber(String containerNumber);
}
