package com.xcase.intapp.cdsusers.transputs;

public interface FindRolesRequest extends ListableCDSUsersRequest {

    String getOperationPath();

    String getName();

    void setName(String name);

}
