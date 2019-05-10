package com.xcase.intapp.cdscm.transputs;

public interface DeleteClientSecurityRequest extends CDSCMRequest {

	String getClientId();
	
	void setClientId(String clientId);

	String getOperationPath();

}
