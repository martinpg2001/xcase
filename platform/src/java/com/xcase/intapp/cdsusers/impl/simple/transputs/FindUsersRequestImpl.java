package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.FindUsersRequest;

public class FindUsersRequestImpl extends ListableCDSUsersRequestImpl implements FindUsersRequest {
    private String operationPath = "api/v1/cds/users";
    
    @Override
    public String getOperationPath() {
        return operationPath;
    }

}
