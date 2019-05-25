package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.UploadEntitiesRequest;

public class UploadEntitiesRequestImpl extends CDSUsersRequestImpl implements UploadEntitiesRequest {
    private String entity;
    private String entityString;
    private String operationPath = "api/v1/cds/{entity}";
    
	@Override
	public String getOperationPath() {
		return operationPath;
	}

	@Override
	public String getEntityString() {
		return entityString;
	}

	@Override
	public String getEntity() {
		return entity;
	}

	@Override
	public void setEntity(String entity) {
		this.entity = entity;
	}

	@Override
	public void setEntityString(String entityString) {
		this.entityString = entityString;
	}

}
