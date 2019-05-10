package com.xcase.intapp.cdscm.transputs;

public interface DeleteMatterSecurityRequest extends CDSCMRequest {

	void setClientId(String clientId);

	void setMatterId(String matterId);

	String getClientId();

	String getMatterId();

	String getOperationPath();

	int getSuccessResponseCode();
}
