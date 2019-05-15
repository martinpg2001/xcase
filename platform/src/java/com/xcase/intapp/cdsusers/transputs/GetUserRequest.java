package com.xcase.intapp.cdsusers.transputs;

public interface GetUserRequest extends CDSUsersRequest {

	void setKey(String string);

	String getOperationPath();

	String getKey();

}
