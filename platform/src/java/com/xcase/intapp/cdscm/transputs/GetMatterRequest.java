package com.xcase.intapp.cdscm.transputs;

public interface GetMatterRequest extends CDSCMRequest {

	void setClientId(String clientId);

	void setMatterId(String matterId);

	String getOperationPath();

	String getClientId();

	String getMatterId();

}
