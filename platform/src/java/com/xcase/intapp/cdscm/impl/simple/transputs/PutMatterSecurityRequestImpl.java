package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.PutMatterSecurityRequest;

public class PutMatterSecurityRequestImpl extends CDSCMRequestImpl implements PutMatterSecurityRequest {
    private String clientId;
    private String matterId;
    private String matterSecurity;
    private String operationPath = "api/v1/clients/{clientId}/matters/{matterId}/security";
    private int successResponseCode = 200;
    
    public int getSuccessResponseCode() {
        return this.successResponseCode;
    }

    public void setSuccessResponseCode(int successResponseCode) {
        this.successResponseCode = successResponseCode;
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
	public void setMatterSecurity(String matterSecurity) {
		this.matterSecurity = matterSecurity;
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
	public String getOperationPath() {
		return operationPath;
	}

	@Override
	public String getMatterSecurity() {
		return matterSecurity;
	}

}
