package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.CreateServiceUserRequest;

public class CreateServiceUserRequestImpl extends CDSUsersRequestImpl implements CreateServiceUserRequest {
    private String operationPath = "api/v1/cds/service-users";
    private String serviceUserString;

    @Override
    public String getOperationPath() {
        return operationPath;
    }

    @Override
    public String getServiceUserString() {
        return serviceUserString;
    }

    @Override
    public void setServiceUserString(String serviceUserString) {
        this.serviceUserString = serviceUserString;
    }

}
