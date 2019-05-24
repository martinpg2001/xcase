package com.xcase.intapp.cdsusers.transputs;

public interface CreateServiceUserRequest extends CDSUsersRequest {

    void setServiceUserString(String serviceUserString);

    String getOperationPath();

    String getServiceUserString();

}
