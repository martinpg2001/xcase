package com.xcase.intapp.cdscm.transputs;

public interface PutClientSecurityRequest extends CDSCMRequest {

    String getClientId();

    void setClientId(String clientId);

    public String getOperationPath();

    public void setOperationPath(String operationPath);

    String getClientSecurity();

    void setClientSecurity(String clientSecurity);
}
