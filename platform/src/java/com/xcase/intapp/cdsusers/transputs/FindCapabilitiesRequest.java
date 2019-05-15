package com.xcase.intapp.cdsusers.transputs;

public interface FindCapabilitiesRequest extends CDSUsersRequest {

    void setRole(String string);

    String getOperationPath();

    String getRole();

}
