package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.GetClientRequest;

public class GetClientRequestImpl extends CDSCMRequestImpl implements GetClientRequest {
    private String clientId;
    private String operationPath = "api/v1/clients/{clientId}";;
    
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
