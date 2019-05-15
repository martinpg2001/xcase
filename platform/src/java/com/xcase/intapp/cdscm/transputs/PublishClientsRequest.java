package com.xcase.intapp.cdscm.transputs;

public interface PublishClientsRequest extends CDSCMRequest {

	String getOperationPath();

	String[] getClientsArray();

	int getSuccessResponseCode();
	
	String getTopicName();

	void setClientsArray(String[] clientsArray);

	void setTopicName(String topicName);
}
