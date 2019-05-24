package com.xcase.intapp.cdsusers.transputs;

public interface DeleteRoleRequest extends CDSUsersRequest {

    void setKey(String string);

    String getOperationPath();

    String getKey();

}
