package com.xcase.klearexpress.impl.simple.transputs;

import com.xcase.klearexpress.transputs.GetAccessTokenRequest;

public class GetAccessTokenRequestImpl extends KlearExpressRequestImpl implements GetAccessTokenRequest {
    private String entityRequest;
    
    @Override
    public String getEntityRequest() {
        return entityRequest;
    }

    @Override
    public void setEntityRequest(String entityRequest) {
        this.entityRequest = entityRequest;
    }

}
