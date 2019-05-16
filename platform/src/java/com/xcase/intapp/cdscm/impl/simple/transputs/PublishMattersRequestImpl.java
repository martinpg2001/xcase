package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.PublishMattersRequest;
import com.xcase.intapp.cdsusers.impl.simple.transputs.CDSUsersRequestImpl;

public class PublishMattersRequestImpl extends CDSUsersRequestImpl implements PublishMattersRequest {
    private String operationPath = "api/v1/matters/sync?topicName={topicName}";
    private String topicName;
    private String[] keysArray;
    private int successResponseCode = 200;
    
	@Override
	public void setKeysArray(String[] keysArray) {
		this.keysArray = keysArray;
	}

	@Override
	public void setTopicName(String topicName) {
		this.topicName = topicName;
	}

	@Override
	public String[] getKeysArray() {
		return keysArray;
	}

	@Override
	public String getTopicName() {
		return topicName;
	}

	@Override
	public String getOperationPath() {
		return operationPath;
	}

	@Override
	public int getSuccessResponseCode() {
		return successResponseCode;
	}

}
