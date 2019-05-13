package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.GetPersonsRequest;

public class GetPersonsRequestImpl extends CDSUsersRequestImpl implements GetPersonsRequest {
    private String operationPath = "api/v1/cds/persons";
    
    @Override
    public String getOperationPath() {
        return operationPath;
    }

}
