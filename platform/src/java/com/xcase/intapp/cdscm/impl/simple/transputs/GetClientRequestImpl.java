package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.GetClientRequest;

public class GetClientRequestImpl extends CDSCMRequestImpl implements GetClientRequest {
    private String clientId;
    private String operationPath = "api/v1/clients/{clientId}";;
    private int successResponseCode = 200;
    
    public int getSuccessResponseCode() {
        return this.successResponseCode;
    }

    public void setSuccessResponseCode(int successResponseCode) {
        this.successResponseCode = successResponseCode;
    }
    
    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getOperationPath() {
        return operationPath;
    }
    
    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

}
