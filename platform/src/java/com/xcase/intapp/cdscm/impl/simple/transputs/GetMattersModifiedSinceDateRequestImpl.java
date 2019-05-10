package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.GetMattersModifiedSinceDateRequest;

public class GetMattersModifiedSinceDateRequestImpl extends CDSCMRequestImpl
		implements GetMattersModifiedSinceDateRequest {
    private String clientId;
    private String matterId;
    private String operationPath = "api/v1/matters";
    private String since;
    
	@Override
	public void setSince(String since) {
		this.since = since;
	}

	@Override
	public String getSince() {
		return since;
	}

	@Override
	public String getOperationPath() {
		return operationPath;
	}

}
