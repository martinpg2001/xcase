package com.xcase.intapp.cdsusers.transputs;

public interface UploadEntitiesRequest extends CDSUsersRequest {
	String getOperationPath();

	String getEntityString();

	String getEntity();

	void setEntity(String entity);

	void setEntityString(String entityString);

}
