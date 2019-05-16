package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.PublishClientsRequest;
import com.xcase.intapp.cdsusers.impl.simple.transputs.CDSUsersRequestImpl;

public class PublishClientsRequestImpl extends CDSUsersRequestImpl implements PublishClientsRequest {
    private String operationPath = "api/v1/clients/sync?topicName={topicName}";
    private String topicName;
    private String[] keysArray;
    private int successResponseCode = 200;
    
	@Override
	public String getOperationPath() {
		return operationPath;
	}

	@Override
	public String[] getKeysArray() {
		return keysArray;
	}

	@Override
	public int getSuccessResponseCode() {
		return successResponseCode;
	}

	@Override
	public void setClientsArray(String[] keysArray) {
        this.keysArray = keysArray;
	}

	@Override
	public String getTopicName() {
		return topicName;
	}

	@Override
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

}
