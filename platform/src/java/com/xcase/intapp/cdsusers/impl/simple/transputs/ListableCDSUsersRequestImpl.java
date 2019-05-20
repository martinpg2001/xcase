package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.ListableCDSUsersRequest;

public class ListableCDSUsersRequestImpl extends CDSUsersRequestImpl implements ListableCDSUsersRequest {
    private int limit;
    private int skip;
    
	@Override
	public int getLimit() {
		return limit;
	}

	@Override
	public int getSkip() {
		return skip;
	}

	@Override
	public void setLimit(int limit) {
		this.limit = limit;
	}

	@Override
	public void setskip(int skip) {
		this.skip = skip;
	}

}
