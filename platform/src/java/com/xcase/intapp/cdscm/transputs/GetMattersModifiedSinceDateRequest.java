package com.xcase.intapp.cdscm.transputs;

public interface GetMattersModifiedSinceDateRequest extends CDSCMRequest {

	void setSince(String since);

	String getSince();

	String getOperationPath();

}
