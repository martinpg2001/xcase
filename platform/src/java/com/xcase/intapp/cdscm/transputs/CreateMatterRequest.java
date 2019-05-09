package com.xcase.intapp.cdscm.transputs;

public interface CreateMatterRequest extends CDSCMRequest {

	void setClientId(String clientId);

	void setMatterId(String matterId);

	void setEntityString(String replace);

}
