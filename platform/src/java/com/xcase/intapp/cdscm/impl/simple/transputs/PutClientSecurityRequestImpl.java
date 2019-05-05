package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.PutClientSecurityRequest;
import com.xcase.intapp.cdscm.impl.simple.transputs.CDSCMRequestImpl;

public class PutClientSecurityRequestImpl extends CDSCMRequestImpl implements PutClientSecurityRequest {
    private String clientId;
    private String operationPath = "api/v1/clients/{clientId}/security";

    public String getClientId() {
        return clientId;
    }
    
    public String getOperationPath() {
        return operationPath;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public void setOperationPath(String operationPath) {
        this.operationPath = operationPath;
    }
}
