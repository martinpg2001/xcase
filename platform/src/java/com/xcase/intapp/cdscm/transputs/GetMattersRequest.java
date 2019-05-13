package com.xcase.intapp.cdscm.transputs;

public interface GetMattersRequest extends CDSCMRequest {

	void setClientId(String clientId);

	String getClientId();

	String getOperationPath();

	int getSuccessResponseCode();

}
