package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.FindCapabilitiesRequest;

public class FindCapabilitiesRequestImpl extends ListableCDSUsersRequestImpl implements FindCapabilitiesRequest {
    private String role;
    private String operationPath = "api/v1/cds/capabilities";
    
    @Override
    public void setRole(String role) {
        this.role = role;
    }

    @Override
    public String getOperationPath() {
        return operationPath;
    }

    @Override
    public String getRole() {
        return role;
    }

}
