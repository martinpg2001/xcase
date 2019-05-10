package com.xcase.intapp.cdscm.transputs;

public interface DeleteClientRequest extends CDSCMRequest {

    String getClientId();
    
    int getSuccessResponseCode();

    void setClientId(String string);

    public String getOperationPath();

    public void setOperationPath(String operationPath);
}
