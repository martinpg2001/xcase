package com.xcase.intapp.cdsusers.transputs;

import com.xcase.intapp.cdsusers.objects.UserPostDTO;

public interface CreateUserRequest extends CDSUsersRequest {
    String getOperationPath();
    
    UserPostDTO getUser();

    String getUserString();
    
    void setUserString(String user);

    void setUser(UserPostDTO userPostDTO);

}
