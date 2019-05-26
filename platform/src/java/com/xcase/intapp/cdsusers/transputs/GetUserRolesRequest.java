package com.xcase.intapp.cdsusers.transputs;

public interface GetUserRolesRequest extends ListableCDSUsersRequest {

    void setKey(String string);

    String getOperationPath();

    String getKey();

}
