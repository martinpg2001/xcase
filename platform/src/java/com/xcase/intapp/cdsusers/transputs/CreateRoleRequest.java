package com.xcase.intapp.cdsusers.transputs;

import com.google.gson.JsonElement;
import com.xcase.intapp.cdsusers.objects.RolePostDTO;

public interface CreateRoleRequest extends CDSUsersRequest {

    String getOperationPath();

    String getRoleString();

    RolePostDTO getRole();
    
    void setRole(RolePostDTO rolePostDTO);

    void setRoleString(String string);
}
