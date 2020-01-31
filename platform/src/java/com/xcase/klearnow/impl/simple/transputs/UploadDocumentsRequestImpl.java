package com.xcase.klearnow.impl.simple.transputs;

import java.io.File;
import java.util.HashMap;

import com.xcase.klearnow.transputs.UploadDocumentsRequest;

public class UploadDocumentsRequestImpl extends KlearNowRequestImpl implements UploadDocumentsRequest {
    private String shipmentId;
    private HashMap<String, File> dataMap;
    
	@Override
	public String getShipmentId() {
		return this.shipmentId;
	}

	@Override
	public HashMap<String, File> getDataMap() {
		return this.dataMap;
	}

	@Override
	public void setShipmentId(String shipmentId) {
		this.shipmentId = shipmentId;
	}

	@Override
	public void setDataMap(HashMap<String, File> dataMap) {
		this.dataMap = dataMap;
	}

}
