package com.xcase.intapp.cdscm.transputs;

public interface PutMatterSecurityRequest extends CDSCMRequest {

	void setClientId(String clientId);

	void setMatterId(String matterId);

	void setMatterSecurity(String string);

}
