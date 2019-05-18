package com.xcase.intapp.cdsusers.transputs;

public interface PartiallyUpdateUserRequest extends CDSUsersRequest {
    String getKey();

    String getOperationPath();

    String getUserString();

    void setKey(String key);

    void setUserString(String user);

}
