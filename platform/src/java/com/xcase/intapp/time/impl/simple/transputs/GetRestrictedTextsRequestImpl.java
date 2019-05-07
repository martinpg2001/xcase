package com.xcase.intapp.time.impl.simple.transputs;

import com.xcase.intapp.time.transputs.GetRestrictedTextsRequest;

public class GetRestrictedTextsRequestImpl extends TimeRequestImpl implements GetRestrictedTextsRequest {
    private String operationPath = "/time/api/v1/restrictedtexts";
    private String refreshToken;
    
    public String getOperationPath() {
        return operationPath;
    }
    
    public String getRefreshToken() {
        return refreshToken;
    }

    public void setOperationPath(String operationPath) {
        this.operationPath = operationPath;
    }
    
    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }
}
