package com.xcase.ebay.transputs;

public interface CreateApplicationAccessTokenRequest extends EBayRequest {

    String getClientId();

    String getClientSecret();

    void setClientSecret(String clientSecret);

    void setClientId(String clientId);

}
