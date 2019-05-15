package com.xcase.intapp.cdsusers.transputs;

public interface GetCapabilityRequest extends CDSUsersRequest {

    void setKey(String string);

    String getOperationPath();

    String getKey();

}
