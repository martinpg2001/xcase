package com.xcase.intapp.cdsusers.transputs;

public interface FindCapabilitiesRequest extends CDSUsersRequest {

    void setRole(String role);

    String getOperationPath();

    String getRole();

}
