package com.xcase.intapp.cdsusers.transputs;

public interface GetUserRequest extends CDSUsersRequest {

	void setKey(String key);

	String getOperationPath();

	String getKey();

}
