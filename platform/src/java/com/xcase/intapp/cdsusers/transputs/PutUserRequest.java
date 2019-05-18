package com.xcase.intapp.cdsusers.transputs;

public interface PutUserRequest extends CDSUsersRequest {

	void setKey(String key);

	void setUserString(String user);

	String getKey();

	String getOperationPath();

	String getUserString();

}
