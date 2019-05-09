package com.xcase.intapp.cdscm.transputs;

public interface GetClientRequest extends CDSCMRequest {

    void setClientId(String clientId);

    String getClientId();

    String getOperationPath();

}
