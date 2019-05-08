package com.xcase.intapp.advanced.impl.simple.transputs;

import com.xcase.intapp.advanced.transputs.GenerateTokensRequest;

public class GenerateTokensRequestImpl extends AdvancedRequestImpl implements GenerateTokensRequest {

    private String clientId;
    private String clientSecret;
    private String swaggerAPIURL;
    private String tokenURL;
    
    @Override
    public String getClientId() {
        return clientId;
    }

    @Override
    public String getClientSecret() {
        return clientSecret;
    }

    @Override
    public String getSwaggerAPIURL() {
        return swaggerAPIURL;
    }

    @Override
    public String getTokenURL() {
        return tokenURL;
    }

    @Override
    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    @Override
    public void setClientSecret(String clientSecret) {
        this.clientSecret = clientSecret;
    }

    @Override
    public void setSwaggerAPIURL(String swaggerAPIURL) {
        this.swaggerAPIURL = swaggerAPIURL;
    }

    @Override
    public void setTokenURL(String tokenURL) {
        this.tokenURL = tokenURL;
    }

}
