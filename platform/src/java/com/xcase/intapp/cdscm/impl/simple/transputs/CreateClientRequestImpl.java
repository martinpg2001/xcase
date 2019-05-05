package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.CreateClientRequest;
import com.xcase.intapp.cdscm.impl.simple.transputs.CDSCMRequestImpl;

public class CreateClientRequestImpl extends CDSCMRequestImpl implements CreateClientRequest {
    private String clientId;
    private String operationPath = "api/v1/clients";

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

}
