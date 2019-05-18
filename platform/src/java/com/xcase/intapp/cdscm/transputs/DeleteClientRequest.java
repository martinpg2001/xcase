package com.xcase.intapp.cdscm.transputs;

public interface DeleteClientRequest extends CDSCMRequest {

    String getClientId();

    int getSuccessResponseCode();

    void setClientId(String clientId);

    public String getOperationPath();

    public void setOperationPath(String operationPath);
}
