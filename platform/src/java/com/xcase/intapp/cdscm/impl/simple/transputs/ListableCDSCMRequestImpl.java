package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.ListableCDSCMRequest;

public class ListableCDSCMRequestImpl extends CDSCMRequestImpl implements ListableCDSCMRequest {
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
	public void setSkip(int skip) {
		this.skip = skip;
	}

}
