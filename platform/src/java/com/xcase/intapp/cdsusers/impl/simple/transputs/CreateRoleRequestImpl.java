package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.objects.RolePostDTO;
import com.xcase.intapp.cdsusers.transputs.CreateRoleRequest;

public class CreateRoleRequestImpl extends CDSUsersRequestImpl implements CreateRoleRequest {
    private String operationPath = "api/v1/cds/roles";
    private RolePostDTO rolePostDTO;
    private String roleString;
    
    @Override
    public void setRoleString(String roleString) {
        this.roleString = roleString;
    }

    @Override
    public String getOperationPath() {
        return operationPath;
    }

    @Override
    public String getRoleString() {
        return roleString;
    }

    @Override
    public RolePostDTO getRole() {
        return rolePostDTO;
    }

    @Override
    public void setRole(RolePostDTO rolePostDTO) {
        this.rolePostDTO= rolePostDTO;
    }

}
