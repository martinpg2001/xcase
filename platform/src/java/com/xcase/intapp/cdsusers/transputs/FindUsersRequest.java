package com.xcase.intapp.cdsusers.transputs;

public interface FindUsersRequest extends ListableCDSUsersRequest {
	String getOperationPath();

	String getSearch();
	
	void setSearch(String search);

}
