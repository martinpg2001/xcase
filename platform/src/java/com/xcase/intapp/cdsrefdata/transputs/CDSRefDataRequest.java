package com.xcase.intapp.cdsrefdata.transputs;

public interface CDSRefDataRequest {
    public String getAccessToken();

    public String getOperationPath();

    public void setAccessToken(String accessToken);

    public void setOperationPath(String operationPath);
}
