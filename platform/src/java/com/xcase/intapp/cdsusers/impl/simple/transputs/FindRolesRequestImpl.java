package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.FindRolesRequest;

public class FindRolesRequestImpl extends CDSUsersRequestImpl implements FindRolesRequest {
    private String name;
    private String operationPath = "api/v1/cds/roles";
    
    @Override
    public String getOperationPath() {
        return operationPath;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

}
