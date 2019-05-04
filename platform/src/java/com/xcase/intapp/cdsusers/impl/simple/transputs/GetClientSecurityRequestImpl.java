package com.xcase.intapp.cdsusers.impl.simple.transputs;

import com.xcase.intapp.cdsusers.transputs.GetClientSecurityRequest;

public class GetClientSecurityRequestImpl extends CDSUsersRequestImpl implements GetClientSecurityRequest {
    private String clientId;
    private String operationPath;
    
    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public String getClientId() {
        return clientId;
    }

}
