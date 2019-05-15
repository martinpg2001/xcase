package com.xcase.intapp.cdsusers.transputs;

public interface PublishEntitiesRequest extends CDSUsersRequest {

	void setTopic(String string);

	void setEntity(String string);

	String getOperationPath();

	String getTopic();

	String getEntity();

}
