package com.xcase.intapp.cdscm.transputs;

public interface CheckClientSecurityRequest extends CDSCMRequest {

	void setIdsArray(String[] idsArray);

	String getOperationPath();

	int getSuccessResponseCode();

	String[] getIdsArray();

}
