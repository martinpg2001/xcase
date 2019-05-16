package com.xcase.intapp.cdscm.transputs;

public interface PublishMattersRequest extends CDSCMRequest {

	void setKeysArray(String[] mattersArray);

	void setTopicName(String mattersTopicName);

	String[] getKeysArray();

	String getTopicName();

	String getOperationPath();

	int getSuccessResponseCode();

}
