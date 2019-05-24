package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.FindServiceUsersRequest;

public class FindServiceUsersRequestImpl extends ListableCDSUsersRequestImpl implements FindServiceUsersRequest {
    private String operationPath = "api/v1/cds/service-users";
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
