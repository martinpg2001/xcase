package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.CreateMatterRequest;

public class CreateMatterRequestImpl extends CDSCMRequestImpl implements CreateMatterRequest {
    private String clientId;
    private String matterId;
    private String entityString;
    private String operationPath = "api/v1/clients/{clientId}/matters";
    
	@Override
	public void setClientId(String clientId) {
		this.clientId = clientId;
	}

	@Override
	public void setMatterId(String matterId) {
		this.matterId = matterId;
	}

	@Override
	public void setEntityString(String entityString) {
		this.entityString = entityString;
	}

	@Override
	public String getMatterId() {
		return matterId;
	}

	@Override
	public String getOperationPath() {
		return operationPath;
	}

	@Override
	public String getClientId() {
		return clientId;
	}

	@Override
	public String getEntityString() {
		return entityString;
	}

}
