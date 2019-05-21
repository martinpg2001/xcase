package com.xcase.intapp.cdscm.transputs;

public interface GetMattersRequest extends ListableCDSCMRequest {

	void setClientId(String clientId);

	String getClientId();

	String getOperationPath();

	int getSuccessResponseCode();

}
