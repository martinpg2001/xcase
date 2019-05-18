package com.xcase.intapp.cdsusers.transputs;

public interface CreateUserRequest extends CDSUsersRequest {

    void setUserString(String user);

    String getOperationPath();

    String getUserString();

}
