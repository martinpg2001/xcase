package com.xcase.klearnow.impl.simple.transputs;

import com.xcase.klearnow.transputs.GetAccessTokenRequest;

public class GetAccessTokenRequestImpl extends KlearNowRequestImpl implements GetAccessTokenRequest {
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
