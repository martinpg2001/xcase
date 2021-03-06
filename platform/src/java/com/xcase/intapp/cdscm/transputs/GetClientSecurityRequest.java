package com.xcase.intapp.cdscm.transputs;

public interface GetClientSecurityRequest extends CDSCMRequest {

    void setClientId(String clientId);

    String getClientId();
    
    public String getOperationPath();
    
    int getSuccessResponseCode();

    public void setOperationPath(String operationPath);
}
