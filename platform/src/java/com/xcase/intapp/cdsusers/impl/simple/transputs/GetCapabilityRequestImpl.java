package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.GetCapabilityRequest;

public class GetCapabilityRequestImpl extends CDSUsersRequestImpl implements GetCapabilityRequest {
    private String key;
    private String operationPath = "api/v1/cds/capabilities/{key}";
    
    @Override
    public void setKey(String key) {
        this.key = key;
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
