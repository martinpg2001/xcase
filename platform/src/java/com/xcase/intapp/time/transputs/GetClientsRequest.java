package com.xcase.intapp.time.transputs;

public interface GetClientsRequest extends TimeRequest {
    public String getOperationPath();
    
    public String getRefreshToken();

    public void setOperationPath(String operationPath);

    public void setRefreshToken(String refreshToken);
}
