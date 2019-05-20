package com.xcase.intapp.cdsrefdata.impl.simple.transputs;

import com.xcase.intapp.cdsrefdata.transputs.ListableRefDataRequest;

public class ListableRefDataRequestImpl extends CDSRefDataRequestImpl implements ListableRefDataRequest {
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
