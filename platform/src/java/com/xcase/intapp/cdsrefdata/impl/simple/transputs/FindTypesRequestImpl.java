package com.xcase.intapp.cdsrefdata.impl.simple.transputs;

import com.xcase.intapp.cdsrefdata.transputs.FindTypesRequest;

public class FindTypesRequestImpl extends ListableRefDataRequestImpl implements FindTypesRequest {
    private String key;
    private String name;	
    private String type;
    private String operationPath = "api/v1/rdcds/";

	@Override
	public String getKey() {
		return key;
	}

	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public String getOperationPath() {
		return operationPath;
	}
    
	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public String getType() {
		return type;
	}

}
