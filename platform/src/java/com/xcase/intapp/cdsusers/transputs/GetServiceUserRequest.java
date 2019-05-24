package com.xcase.intapp.cdsusers.transputs;

public interface GetServiceUserRequest extends CDSUsersRequest {
	String getKey();
	
	String getOperationPath();
	
	void setKey(String key);

}
