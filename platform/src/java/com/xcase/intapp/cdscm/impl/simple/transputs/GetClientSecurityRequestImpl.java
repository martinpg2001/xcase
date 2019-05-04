package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.GetClientSecurityRequest;

public class GetClientSecurityRequestImpl extends CDSCMRequestImpl implements GetClientSecurityRequest {
    private String clientId;
    private String operationPath = "api/v1/clients/10001/security";

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
