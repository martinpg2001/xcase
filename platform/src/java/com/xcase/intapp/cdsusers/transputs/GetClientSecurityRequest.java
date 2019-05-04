package com.xcase.intapp.cdsusers.transputs;

public interface GetClientSecurityRequest extends CDSUsersRequest {

    void setClientId(String clientId);

    String getClientId();
}
