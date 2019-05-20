package com.xcase.intapp.cdsusers.transputs;

public interface FindCapabilitiesRequest extends ListableCDSUsersRequest {

    void setRole(String role);

    String getOperationPath();

    String getRole();

}
