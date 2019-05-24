package com.xcase.intapp.cdsusers.transputs;

public interface CreateRoleRequest extends CDSUsersRequest {

    void setRoleString(String string);

    String getOperationPath();

    String getRoleString();

}
