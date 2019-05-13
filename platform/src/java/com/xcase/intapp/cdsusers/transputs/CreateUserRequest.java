package com.xcase.intapp.cdsusers.transputs;

public interface CreateUserRequest extends CDSUsersRequest {

    void setUserString(String string);

    String getOperationPath();

    String getUserString();

}
