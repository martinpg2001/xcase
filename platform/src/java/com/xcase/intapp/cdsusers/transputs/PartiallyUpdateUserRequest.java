package com.xcase.intapp.cdsusers.transputs;

public interface PartiallyUpdateUserRequest extends CDSUsersRequest {
    String getKey();
    
    String getOperationPath();
    
    String getUserString();
    
    void setKey(String string);

    void setUserString(String string);

}
