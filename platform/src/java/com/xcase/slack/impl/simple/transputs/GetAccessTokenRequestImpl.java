package com.xcase.slack.impl.simple.transputs;

import com.xcase.slack.transputs.GetAccessTokenRequest;

public class GetAccessTokenRequestImpl extends SlackRequestImpl implements GetAccessTokenRequest {
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
