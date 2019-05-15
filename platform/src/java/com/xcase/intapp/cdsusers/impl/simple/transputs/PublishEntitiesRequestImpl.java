package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.PublishEntitiesRequest;

public class PublishEntitiesRequestImpl extends CDSUsersRequestImpl implements PublishEntitiesRequest {
    private String topic;
    private String entity;
    private String operationPath = "api/v1/cds/initialload?topic={topic}&entity={entity}";
    
	@Override
	public void setTopic(String topic) {
		this.topic = topic;
	}

	@Override
	public void setEntity(String entity) {
		this.entity = entity;
	}

	@Override
	public String getOperationPath() {
		return operationPath;
	}

	@Override
	public String getTopic() {
		return topic;
	}

	@Override
	public String getEntity() {
		return entity;
	}

}
