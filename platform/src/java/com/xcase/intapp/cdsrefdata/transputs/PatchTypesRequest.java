package com.xcase.intapp.cdsrefdata.transputs;

public interface PatchTypesRequest extends CDSRefDataRequest {

	void setType(String string);

	String getOperationPath();

	String getType();

}
