package com.xcase.intapp.cdsrefdata.impl.simple.transputs;

import com.xcase.intapp.cdsrefdata.transputs.PatchTypesRequest;

public class PatchTypesRequestImpl extends CDSRefDataRequestImpl implements PatchTypesRequest {
    private String type;
    private String operationPath = "api/v1/rdcds/";
    
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
