package com.xcase.intapp.cdscm.transputs;

public interface DeleteMatterRequest extends CDSCMRequest {
	String getMatterId();

	String getOperationPath();

	String getClientId();
	
	int getSuccessResponseCode();
	
	void setClientId(String clientId);

	void setMatterId(String matterId);

}
