package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.GetMattersRequest;

public class GetMattersRequestImpl extends CDSCMRequestImpl implements GetMattersRequest {
    private String clientId;
    private String operationPath = "api/v1/clients/{clientId}/matters";
    private int successResponseCode = 200;
    
	@Override
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public String getOperationPath() {
		return operationPath;
	}

	@Override
	public int getSuccessResponseCode() {
		return successResponseCode;
	}

}
