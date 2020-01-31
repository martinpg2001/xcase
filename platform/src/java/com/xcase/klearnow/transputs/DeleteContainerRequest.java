package com.xcase.klearnow.transputs;

public interface DeleteContainerRequest extends KlearNowRequest {
	String getContainerId();
	
	String getShipmentId();

	String getContainerNumber();
	
	void setContainerId(String containerId);

	void setShipmentId(String shipmentId);

	void setContainerNumber(String containerNumber);
}
