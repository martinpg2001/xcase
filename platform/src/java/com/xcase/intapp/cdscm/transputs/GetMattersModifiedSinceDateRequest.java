package com.xcase.intapp.cdscm.transputs;

public interface GetMattersModifiedSinceDateRequest extends CDSCMRequest {
	int getSuccessResponseCode();
	
	void setSince(String since);

	String getSince();

	String getOperationPath();

}
