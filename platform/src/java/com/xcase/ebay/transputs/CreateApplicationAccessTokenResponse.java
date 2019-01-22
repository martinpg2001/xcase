package com.xcase.ebay.transputs;

public interface CreateApplicationAccessTokenResponse extends EBayResponse {

    void setStatus(String status);

    void setAccessToken(String accessToken);

    void setExpiresIn(int expiresIn);

    void setRefreshToken(String newRefreshToken);

	String getAccessToken();

}
