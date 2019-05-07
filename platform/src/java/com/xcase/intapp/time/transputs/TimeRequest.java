package com.xcase.intapp.time.transputs;

public interface TimeRequest {
    public String getAccessToken();

    public String getOperationPath();

    public void setAccessToken(String accessToken);

    public void setOperationPath(String operationPath);
}
