package com.xcase.intapp.cdscm.transputs;

public interface CDSCMRequest {
    public String getAccessToken();

    public String getOperationPath();

    public void setAccessToken(String accessToken);

    public void setOperationPath(String operationPath);
}
