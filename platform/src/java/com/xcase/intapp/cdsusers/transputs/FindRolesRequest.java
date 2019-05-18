package com.xcase.intapp.cdsusers.transputs;

public interface FindRolesRequest extends CDSUsersRequest {

    String getOperationPath();

    String getName();

    void setName(String name);

}
