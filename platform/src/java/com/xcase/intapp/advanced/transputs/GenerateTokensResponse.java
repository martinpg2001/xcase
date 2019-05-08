package com.xcase.intapp.advanced.transputs;

public interface GenerateTokensResponse extends AdvancedResponse {
    String getAccessToken();
    
    String getRefreshToken();
    
    void setAccessToken(String accessToken);
    
    void setRefreshToken(String refreshToken);
}
