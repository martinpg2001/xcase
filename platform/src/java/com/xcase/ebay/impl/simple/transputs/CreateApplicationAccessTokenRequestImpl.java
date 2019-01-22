package com.xcase.ebay.impl.simple.transputs;

import com.xcase.ebay.transputs.CreateApplicationAccessTokenRequest;

public class CreateApplicationAccessTokenRequestImpl extends EBayRequestImpl implements CreateApplicationAccessTokenRequest {
    private String clientId;
    private String clientSecret;
    
    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

}
