package com.xcase.intapp.cdsusers.transputs;

public interface SetRoleUsersRequest extends CDSUsersRequest {
    String getOperationPath();

    String getKey();

    String[] getUsers();
    
    void setKey(String key);

    void setUsers(String[] userArray);

}
