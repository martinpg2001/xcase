package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.GetServiceUserRequest;

public class GetServiceUserRequestImpl extends CDSUsersRequestImpl implements GetServiceUserRequest {
    private String key;
    private String operationPath = "api/v1/cds/service-users/{key}";
    
	@Override
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String getOperationPath() {
		return operationPath;
	}

	@Override
	public String getKey() {
		return key;
	}

}
