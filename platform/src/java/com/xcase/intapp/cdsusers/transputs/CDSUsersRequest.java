package com.xcase.intapp.cdsusers.transputs;

public interface CDSUsersRequest {
    public String getAccessToken();
    
    public String getOperationPath();
    
    public void setAccessToken(String accessToken);
    
    public void setOperationPath(String operationPath);
}
