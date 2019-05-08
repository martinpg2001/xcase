package com.xcase.intapp.advanced.transputs;

public interface GenerateTokensRequest extends AdvancedRequest {

    String getClientId();

    String getClientSecret();

    String getSwaggerAPIURL();

    String getTokenURL();
    
    void setClientId(String clientId);

    void setClientSecret(String clientSecret);

    void setSwaggerAPIURL(String swaggerAPIURL);

    void setTokenURL(String tokenURL);

}
