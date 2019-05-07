package com.xcase.intapp.time.impl.simple.transputs;

import com.xcase.intapp.time.transputs.GetRestrictedTextsRequest;

public class GetRestrictedTextsRequestImpl extends TimeRequestImpl implements GetRestrictedTextsRequest {
    private String operationPath = "/time/api/v1/restrictedtexts";
    
    public String getOperationPath() {
        return operationPath;
    }

    public void setOperationPath(String operationPath) {
        this.operationPath = operationPath;
    }
}
