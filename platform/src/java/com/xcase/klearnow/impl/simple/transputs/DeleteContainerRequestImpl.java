package com.xcase.klearnow.impl.simple.transputs;

import com.xcase.klearnow.transputs.DeleteContainerRequest;

public class DeleteContainerRequestImpl extends KlearNowRequestImpl implements DeleteContainerRequest {
	private String containerId;
	private String containerNumber;
    private String shipmentId;
    
	@Override
	public String getContainerId() {
		return this.containerId;
	}
	
	@Override
	public String getContainerNumber() {
		return this.containerNumber;
	}
    
	@Override
	public String getShipmentId() {
		return this.shipmentId;
	}
	
	@Override
	public void setContainerId(String containerId) {
		this.containerId = containerId;
	}
	
	@Override
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

	@Override
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}
}
