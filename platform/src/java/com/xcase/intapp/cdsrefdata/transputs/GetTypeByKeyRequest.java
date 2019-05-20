package com.xcase.intapp.cdsrefdata.transputs;

public interface GetTypeByKeyRequest extends CDSRefDataRequest {

	void setType(String string);

	void setKey(String string);

	String getType();

	String getOperationPath();

	String getKey();

}
