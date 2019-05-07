package com.xcase.intapp.time.impl.simple.transputs;

import com.xcase.intapp.time.transputs.GetClientsRequest;

public class GetClientsRequestImpl extends TimeRequestImpl implements GetClientsRequest {
    private String operationPath = "/time/api/v1/clients";
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
