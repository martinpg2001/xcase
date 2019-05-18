package com.xcase.intapp.cdsusers.transputs;

public interface PublishEntitiesRequest extends CDSUsersRequest {

	void setTopic(String topic);

	void setEntity(String entity);

	String getOperationPath();

	String getTopic();

	String getEntity();

}
