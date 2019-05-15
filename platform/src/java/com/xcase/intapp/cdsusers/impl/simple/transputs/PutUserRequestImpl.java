package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.PutUserRequest;

public class PutUserRequestImpl extends CDSUsersRequestImpl implements PutUserRequest {
    private String key;
    private String operationPath = "api/v1/cds/users/{key}";
    private String userString;
    
	@Override
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public void setUserString(String userString) {
		this.userString = userString;
	}

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getOperationPath() {
		return operationPath;
	}

	@Override
	public String getUserString() {
		return userString;
	}

}
