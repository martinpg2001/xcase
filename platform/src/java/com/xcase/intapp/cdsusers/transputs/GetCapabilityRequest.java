package com.xcase.intapp.cdsusers.transputs;

public interface GetCapabilityRequest extends CDSUsersRequest {

    void setKey(String key);

    String getOperationPath();

    String getKey();

}
