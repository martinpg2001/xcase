package com.xcase.intapp.cdsrefdata.transputs;

public interface FindDepartmentsRequest extends CDSRefDataRequest {

	void setKey(String key);

	void setName(String name);

	String getOperationPath();

	String getKey();

	String getName();

}
