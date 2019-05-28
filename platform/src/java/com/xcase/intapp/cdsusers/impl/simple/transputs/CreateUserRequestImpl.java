package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.objects.UserPostDTO;
import com.xcase.intapp.cdsusers.transputs.CreateUserRequest;

public class CreateUserRequestImpl extends CDSUsersRequestImpl implements CreateUserRequest {
    private String operationPath = "api/v1/cds/users";
    private UserPostDTO userPostDTO;
    private String userString;

    @Override
    public String getOperationPath() {
        return operationPath;
    }

    @Override
    public String getUserString() {
        return userString;
    }

    @Override
    public void setUserString(String userString) {
        this.userString = userString;
    }

    @Override
    public UserPostDTO getUser() {
        return userPostDTO;
    }

    @Override
    public void setUser(UserPostDTO userPostDTO) {
        this.userPostDTO = userPostDTO;        
    }
}
