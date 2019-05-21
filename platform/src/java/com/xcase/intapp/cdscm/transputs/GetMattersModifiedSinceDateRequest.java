package com.xcase.intapp.cdscm.transputs;

public interface GetMattersModifiedSinceDateRequest extends ListableCDSCMRequest {
	int getSuccessResponseCode();
	
	void setSince(String since);

	String getSince();

	String getOperationPath();

}
