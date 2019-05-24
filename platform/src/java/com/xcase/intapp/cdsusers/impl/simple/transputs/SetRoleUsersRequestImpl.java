package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.SetRoleUsersRequest;

public class SetRoleUsersRequestImpl extends CDSUsersRequestImpl implements SetRoleUsersRequest {
    private String key;
    private String operationPath = "api/v1/cds/roles/{key}/users";
    private String[] userArray;
    
    @Override
    public void setKey(String key) {
        this.key = key;
    }

    @Override
    public String getOperationPath() {
        return operationPath;
    }

    @Override
    public String getKey() {
        return key;
    }

    @Override
    public String[] getUsers() {
        return userArray;
    }

    @Override
    public void setUsers(String[] userArray) {
        this.userArray = userArray;
    }

}
