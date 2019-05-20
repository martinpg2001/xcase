package com.xcase.intapp.cdsrefdata.impl.simple.transputs;

import com.xcase.intapp.cdsrefdata.transputs.PostTypesRequest;

public class PostTypesRequestImpl extends CDSRefDataRequestImpl implements PostTypesRequest {
    private String type;
    private String operationPath = "api/v1/rdcds/{type}/_bulk";
    
	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getOperationPath() {
		return operationPath;
	}

	@Override
	public String getType() {
		return type;
	}

}
