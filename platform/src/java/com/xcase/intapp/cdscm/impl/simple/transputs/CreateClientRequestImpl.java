package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.CreateClientRequest;
import com.xcase.intapp.cdscm.impl.simple.transputs.CDSCMRequestImpl;

public class CreateClientRequestImpl extends CDSCMRequestImpl implements CreateClientRequest {
    private String clientId;
    private String clientString;
    private String operationPath = "api/v1/clients";
    private int successResponseCode = 201;
    
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
    
    @Override
    public void setOperationPath(String operationPath) {
        this.operationPath = operationPath;
    }

    @Override
    public String getClientString() {
        return clientString;
    }

    @Override
    public void setClientString(String clientString) {
        this.clientString = clientString;
    }

}
