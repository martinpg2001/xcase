package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.FindUsersRequest;

public class FindUsersRequestImpl extends ListableCDSUsersRequestImpl implements FindUsersRequest {
    private String operationPath = "api/v1/cds/users";
    private String search;
    
	@Override
	public String getOperationPath() {
		return operationPath;
	}

	@Override
	public String getSearch() {
		return search;
	}

	@Override
	public void setSearch(String search) {
		this.search = search;
	}

}
