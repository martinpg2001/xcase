package com.xcase.salesforce.impl.simple.transputs;

import com.xcase.salesforce.transputs.GetUserRequest;

public class GetUserRequestImpl extends SalesforceRequestImpl implements GetUserRequest {
    private String userId;
    
    @Override
    public void setUserId(String userId) {
        this.userId = userId;
    }

    @Override
    public String getUserId() {
        return userId;
    }

}
