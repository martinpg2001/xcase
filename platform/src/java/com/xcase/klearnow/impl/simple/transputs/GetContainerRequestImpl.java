package com.xcase.klearnow.impl.simple.transputs;

import com.xcase.klearnow.transputs.GetContainerRequest;

public class GetContainerRequestImpl extends KlearNowRequestImpl implements GetContainerRequest {
	private String containerNumber;
    private String shipmentId;
    
	@Override
	public String getShipmentId() {
		return this.shipmentId;
	}

	@Override
	public String getContainerNumber() {
		return this.containerNumber;
	}

	@Override
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}

	@Override
	public void setContainerNumber(String containerNumber) {
		this.containerNumber = containerNumber;
	}

}
