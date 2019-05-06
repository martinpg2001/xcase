package com.xcase.intapp.cdscm.transputs;

public interface CreateClientRequest extends CDSCMRequest {

    String getClientId();
    
    String getClientString();
    
    public String getOperationPath();

    void setClientId(String string);

    public void setOperationPath(String operationPath);

    void setClientString(String string);
}
