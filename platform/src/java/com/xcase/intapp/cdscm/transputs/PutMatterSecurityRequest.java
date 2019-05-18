package com.xcase.intapp.cdscm.transputs;

public interface PutMatterSecurityRequest extends CDSCMRequest {

	int getSuccessResponseCode();

	void setClientId(String clientId);

	void setMatterId(String matterId);

	void setMatterSecurity(String matterSecurity);

	String getClientId();

	String getMatterId();

	String getOperationPath();

	String getMatterSecurity();

}
