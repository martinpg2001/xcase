package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.GetMatterRequest;

public class GetMatterRequestImpl extends CDSCMRequestImpl implements GetMatterRequest {
    private String clientId;
    private String matterId;
    private String matterKey;
    private String operationPath = "api/v1/clients/{clientId}/matters/{matterId}";
    private int successResponseCode = 200;
    
    public int getSuccessResponseCode() {
        return this.successResponseCode;
    }

    public void setSuccessResponseCode(int successResponseCode) {
        this.successResponseCode = successResponseCode;
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
	public String getMatterId() {
		return matterId;
	}
	
    @Override
    public String getMatterKey() {
        return matterKey;
    }

    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public void setMatterId(String matterId) {
        this.matterId = matterId;
    }
    
    @Override
    public void setMatterKey(String matterKey) {
        this.matterKey = matterKey;
    }
}
