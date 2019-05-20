package com.xcase.intapp.cdsrefdata.impl.simple.transputs;

import com.xcase.intapp.cdsrefdata.transputs.GetTypeByKeyRequest;

public class GetTypeByKeyRequestImpl extends ListableRefDataRequestImpl implements GetTypeByKeyRequest {
    private String key;	
    private String type;
    private String operationPath = "api/v1/rdcds/";
    
	@Override
	public void setType(String type) {
		this.type = type;
	}

	@Override
	public void setKey(String key) {
		this.key = key;
	}

	@Override
	public String getType() {
		return type;
	}

	@Override
	public String getOperationPath() {
		return operationPath;
	}

	@Override
	public String getKey() {
		return key;
	}

}
