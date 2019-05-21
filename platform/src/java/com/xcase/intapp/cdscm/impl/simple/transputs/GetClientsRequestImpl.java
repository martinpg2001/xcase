package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.GetClientsRequest;

public class GetClientsRequestImpl extends ListableCDSCMRequestImpl implements GetClientsRequest {
    private String operationPath = "api/v1/clients";
    private int successResponseCode = 200;

	@Override
	public String getOperationPath() {
		return operationPath;
	}

	@Override
	public int getSuccessResponseCode() {
		return successResponseCode;
	}

}
