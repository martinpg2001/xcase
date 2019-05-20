package com.xcase.intapp.cdsrefdata.transputs;

public interface FindTypesRequest extends ListableRefDataRequest {

	void setType(String string);

	String getType();

	String getOperationPath();

	String getKey();

	String getName();

}
