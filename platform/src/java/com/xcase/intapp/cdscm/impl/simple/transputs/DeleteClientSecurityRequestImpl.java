package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.DeleteClientSecurityRequest;

public class DeleteClientSecurityRequestImpl extends CDSCMRequestImpl implements DeleteClientSecurityRequest {
    private String clientId;
    private String operationPath = "api/v1/clients/{clientId}/security";
    
	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public void setClientId(String clientId) {
        this.clientId = clientId;
	}

	@Override
	public String getOperationPath() {
		return operationPath;
	}

}
