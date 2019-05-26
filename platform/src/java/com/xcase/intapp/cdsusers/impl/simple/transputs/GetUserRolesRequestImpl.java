package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.GetUserRolesRequest;

public class GetUserRolesRequestImpl extends ListableCDSUsersRequestImpl implements GetUserRolesRequest {
    private String key;
    private String operationPath = "api/v1/cds/users/{key}/roles";
    
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

}
