package com.xcase.intapp.cdsrefdata.impl.simple.transputs;

import com.xcase.intapp.cdsrefdata.transputs.FindDepartmentsRequest;

public class FindDepartmentsRequestImpl extends ListableRefDataRequestImpl implements FindDepartmentsRequest {
    private String key;
    private String name;
    private String operationPath = "api/v1/rdcds/departments?";
    
	@Override
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public void setName(String name) {
		this.name = name;
	}

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
}
