package com.xcase.klearnow.transputs;

import java.io.File;
import java.util.HashMap;

public interface UploadDocumentsRequest extends KlearNowRequest {

	String getShipmentId();
	
	HashMap<String, File> getDataMap();

	void setShipmentId(String shipmentId);

    void setDataMap(HashMap<String, File> dataMap);
}
