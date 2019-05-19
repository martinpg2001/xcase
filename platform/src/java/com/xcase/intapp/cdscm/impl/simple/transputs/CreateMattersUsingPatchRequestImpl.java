package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.CreateMattersUsingPatchRequest;

public class CreateMattersUsingPatchRequestImpl extends CDSCMRequestImpl implements CreateMattersUsingPatchRequest {
    private String clientId;
    private String[] mattersArray;
    private String operationPath = "api/v1/clients/{clientId}/matters";
    private int successResponseCode = 207;
    
	@Override
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public void setMatters(String[] mattersArray) {
		this.mattersArray = mattersArray;
	}

	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public String[] getMatters() {
		return mattersArray;
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
