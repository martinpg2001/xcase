package com.xcase.intapp.cdsusers.transputs;

public interface FindServiceUsersRequest extends ListableCDSUsersRequest {

	String getOperationPath();

	String getSearch();
	
	void setSearch(String search);
}
