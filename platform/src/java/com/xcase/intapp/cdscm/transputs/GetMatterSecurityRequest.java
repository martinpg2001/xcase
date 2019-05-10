package com.xcase.intapp.cdscm.transputs;

public interface GetMatterSecurityRequest extends CDSCMRequest {

	void setClientId(String clientId);

	void setMatterId(String matterId);

	String getClientId();

	String getMatterId();

	String getOperationPath();

}
