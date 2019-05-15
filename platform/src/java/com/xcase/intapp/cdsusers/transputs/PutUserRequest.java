package com.xcase.intapp.cdsusers.transputs;

public interface PutUserRequest extends CDSUsersRequest {

	void setKey(String string);

	void setUserString(String string);

	String getKey();

	String getOperationPath();

	String getUserString();

}
