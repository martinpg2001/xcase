package com.xcase.intapp.cdscm.transputs;

public interface CreateMattersUsingPatchRequest extends CDSCMRequest {

	void setClientId(String clientId);

	void setMatters(String[] mattersArray);

	String getClientId();

	String[] getMatters();

	String getOperationPath();

	int getSuccessResponseCode();

}
