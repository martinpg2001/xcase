package com.xcase.intapp.cdscm.impl.simple.transputs;

import com.xcase.intapp.cdscm.transputs.PutClientSecurityRequest;
import com.xcase.intapp.cdscm.impl.simple.transputs.CDSCMRequestImpl;

public class PutClientSecurityRequestImpl extends CDSCMRequestImpl implements PutClientSecurityRequest {
    private String clientId;
    private String clientSecurity;
    private String operationPath = "api/v1/clients/{clientId}/security";
    private int successResponseCode = 200;
    
    public int getSuccessResponseCode() {
        return this.successResponseCode;
    }

    public void setSuccessResponseCode(int successResponseCode) {
        this.successResponseCode = successResponseCode;
    }
    
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

    @Override
    public String getClientSecurity() {
        return clientSecurity;
    }

    @Override
    public void setClientSecurity(String clientSecurity) {
        this.clientSecurity = clientSecurity;
    }
}
