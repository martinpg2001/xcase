package com.xcase.intapp.cdscm.transputs;

public interface CreateClientRequest extends CDSCMRequest {

    String getClientId();

    String getClientString();

    public String getOperationPath();

    int getSuccessResponseCode();

    void setClientId(String clientId);

    void setOperationPath(String operationPath);

    void setClientString(String clientString);
}
